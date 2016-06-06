package eu.supersede.fe.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class Listeners {

	@Autowired
	private ProfileListener profileListener;
	@Autowired
	private NotificationListener notificationListener;
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		
		MessageListenerAdapter profileAdapter = new MessageListenerAdapter(profileListener, "receiveMessage");
		profileAdapter.afterPropertiesSet();
		container.addMessageListener(profileAdapter, new PatternTopic("profile"));
		
		MessageListenerAdapter notificationAdapter = new MessageListenerAdapter(notificationListener, "receiveMessage");
		notificationAdapter.afterPropertiesSet();
		container.addMessageListener(notificationAdapter, new PatternTopic("notification"));

		return container;
	}
	
}
