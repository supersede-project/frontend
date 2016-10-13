package eu.supersede.template;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SessionRedisTemplate extends RedisTemplate<String, Object> {

	public SessionRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		RedisSerializer<Object> objectSerializer = new ObjectRedisSerializer();
		setKeySerializer(stringSerializer);
		setValueSerializer(objectSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(objectSerializer);
	}
	
	public SessionRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
	
}