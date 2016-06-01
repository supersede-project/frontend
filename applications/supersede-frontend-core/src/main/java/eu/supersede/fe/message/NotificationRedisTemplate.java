package eu.supersede.fe.message;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import eu.supersede.fe.message.model.Notification;

public class NotificationRedisTemplate extends RedisTemplate<String, Notification> {
	
	public NotificationRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Notification> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Notification.class);
		setKeySerializer(stringSerializer);
		setValueSerializer(jacksonSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(stringSerializer);
	}
	
	public NotificationRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
}
