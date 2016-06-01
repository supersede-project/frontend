package eu.supersede.fe.message;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import eu.supersede.fe.message.model.Profile;

public class ProfileRedisTemplate extends RedisTemplate<String, Profile> {
	
	public ProfileRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Profile> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Profile.class);
		setKeySerializer(stringSerializer);
		setValueSerializer(jacksonSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(stringSerializer);
	}
	
	public ProfileRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
}
