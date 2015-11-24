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
	
	private final ArrayList<Notification> notificationsToSendEmail = new ArrayList<Notification>();
	private final ArrayList<Notification> internalNotificationsToSendEmail = new ArrayList<Notification>();
	private final ArrayList<Notification> notificationsToRemove = new ArrayList<Notification>();
	
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
			
			synchronized(notificationsToSendEmail)
			{
				notificationsToSendEmail.add(n);
			}
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
			
			synchronized(notificationsToSendEmail)
			{
				notificationsToSendEmail.add(n);
			}
		}
		
	}

	@Scheduled(fixedRateString = "${notifier.mail.sender.checkRate}")
	@Transactional
    public void checkNotifications()
	{
		log.debug("check notifications");
		
		//import all new notifications
		synchronized(notificationsToSendEmail)
		{
			log.debug("found " + notificationsToSendEmail.size() + " new notifications");
			internalNotificationsToSendEmail.addAll(notificationsToSendEmail);
			notificationsToSendEmail.clear();
		}
		
		//now
		Date now = new Date();
		
		for(Notification n : internalNotificationsToSendEmail)
		{
			//if notification has been created more than 'MAX_TIME' minutes ago, send an email
			if((now.getTime() - n.getCreationTime().getTime()) >= SENDER_DELAY)
			{
				//refresh notification and check if it was read on database
				Notification nTemp = notifications.findOne(n.getNotificationId());
				if(nTemp != null && !nTemp.getRead())
				{
					log.debug("send email to " + n.getUser().getEmail());
					sendEmail(n);
				}
				
				//else the notification has been read, so just forget about it
				notificationsToRemove.add(n);
			}
		}
		
		internalNotificationsToSendEmail.removeAll(notificationsToRemove);
		notificationsToRemove.clear();
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
