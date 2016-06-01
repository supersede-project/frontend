package eu.supersede.fe.message;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class Templates {

	@Bean
	ProfileRedisTemplate profileTemplate(RedisConnectionFactory connectionFactory) {
		return new ProfileRedisTemplate(connectionFactory);
	}
	
}
