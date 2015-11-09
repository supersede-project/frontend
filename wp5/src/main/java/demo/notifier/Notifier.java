package demo.notifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.jpa.NotificationsJpa;
import demo.jpa.UsersJpa;
import demo.model.Notification;
import demo.model.User;

@Component
public class Notifier {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private UsersJpa users;
	
	@Autowired
    private NotificationsJpa notifications;

	@Autowired
	private JavaMailSender javaMailSender;
	
	private final ArrayList<Notification> notificationsToSendEmail = new ArrayList<Notification>();
	private final ArrayList<Notification> internalNotificationsToSendEmail = new ArrayList<Notification>();
	private final ArrayList<Notification> notificationsToRemove = new ArrayList<Notification>();
	
	private final int MAX_TIME = 20 * 60 * 1000; // 20 minutes
	
	public void createForUsers(List<String> usersEmail, String message)
	{
		for(String email : usersEmail)
		{
			User u = users.findByEmail(email);
			Notification n = new Notification(message, u);
			notifications.save(n);
			
			synchronized(notificationsToSendEmail)
			{
				notificationsToSendEmail.add(n);
			}
		}
	}

	public void createForRole(String role, String message)
	{
		List<User> us = users.findByRole(role);
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

	@Scheduled(fixedRate = 15000)
    public void checkNotifications()
	{
		//import all new notifications
		synchronized(notificationsToSendEmail)
		{
			internalNotificationsToSendEmail.addAll(notificationsToSendEmail);
			notificationsToSendEmail.clear();
		}
		
		//now
		Date now = new Date();
		
		for(Notification n : internalNotificationsToSendEmail)
		{
			if(n.getRead())
			{
				//the notification has been read, so just forget about it
				notificationsToRemove.add(n);
			}
			//if notification has been created more than 'MAX_TIME' minutes ago, send an email
			else if((now.getTime() - n.getCreationTime().getTime()) >= MAX_TIME)
			{
				sendEmail(n);
				
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
		catch(MessagingException ex)
		{
			log.error("Exception while send an email: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
}
