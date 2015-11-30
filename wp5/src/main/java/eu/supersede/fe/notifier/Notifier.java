package eu.supersede.fe.notifier;

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

import eu.supersede.fe.jpa.NotificationsJpa;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Notification;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;
import eu.supersede.fe.multitenant.MultiJpaProvider;

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
	
	@Autowired
	private MultiJpaProvider multiJpaProvider;
	
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
		
		List<NotificationsJpa> notificationsJpa = multiJpaProvider.getRepositories(NotificationsJpa.class);
		for(NotificationsJpa nJpa : notificationsJpa)
		{
			//DEBUG
			log.debug("found total notifications: " + nJpa.count());
			
			//get all notifications not read and not sent via email and created before 
			List<Notification> ns = nJpa.findByReadAndEmailSentAndCreationTimeLessThan(false, false, limit);
			
			log.debug("found " + ns.size() + " notifications to be notified by email.");
			
			for(Notification n : ns)
			{
				log.debug("send email to " + n.getUser().getEmail());
				sendEmail(n);
				n.setEmailSent(true);
				nJpa.save(n);
			}
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
