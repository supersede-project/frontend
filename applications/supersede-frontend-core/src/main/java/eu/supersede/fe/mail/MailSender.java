package eu.supersede.fe.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String subject, String text, String... to)
	{
		try
		{
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			// SSL Certhificate.
			helper = new MimeMessageHelper(message, true);
			// Multipart messages.
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(text, true);
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
