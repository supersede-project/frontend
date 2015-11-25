package demo.notifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.jpa.NotificationsJpa;
import demo.jpa.ProfilesJpa;
import demo.jpa.UsersJpa;
import demo.model.Notification;
import demo.model.Profile;
import demo.model.User;

@Component
@PropertySource("classpath:wp5.properties")
public class Notifier {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private UsersJpa users;
	
	@Autowired
    private ProfilesJpa profiles;
	
	@Autowired
    private NotificationsJpa notifications;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${notifier.mail.sender.delay}")
	private int SENDER_DELAY;
	
	public void createForUsers(List<String> usersEmail, String message)
	{
		for(String email : usersEmail)
		{
			log.debug("Creating notification for " + email);
			
			User u = users.findByEmail(email);
			Notification n = new Notification(message, u);
			notifications.save(n);
		}
	}

	public void createForProfile(String profile, String message)
	{
		Profile p = profiles.findByName(profile);
		List<User> us = p.getUsers();
		for(User u : us)
		{
			Notification n = new Notification(message, u);
			notifications.save(n);
		}
		
	}

	@Scheduled(fixedRateString = "${notifier.mail.sender.checkRate}")
	@Transactional
    public void checkNotifications()
	{
		log.debug("check notifications");
		
		//now
		Date now = new Date();
		Date limit = new Date(now.getTime() - SENDER_DELAY);
		
		//get all notifications not read and not sent via email and created before 
		List<Notification> ns = notifications.findByReadAndEmailSentAndCreationTimeLessThan(false, false, limit);
		
		for(Notification n : ns)
		{
			log.debug("send email to " + n.getUser().getEmail());
			sendEmail(n);
			n.setEmailSent(true);
			notifications.save(n);
		}
    }
	
	private final String emailTemplate = "Hi %s, \nyou have just received a supersede notification.";
	
	private void sendEmail(Notification n)
	{
		try
		{
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			// SSL Certhificate.
			helper = new MimeMessageHelper(message, true);
			// Multipart messages.
			helper.setSubject("Supersede notification");
			helper.setTo(n.getUser().getEmail());
			helper.setText(String.format(emailTemplate, n.getUser().getName()), true);
			javaMailSender.send(message);
		}
		catch(MailException ex)
		{
			log.error("Exception while send an email: " + ex.getMessage());
			ex.printStackTrace();
		}
		catch(MessagingException ex)
		{
			log.error("Exception while send an email: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
}
