package eu.supersede.fe.security.redis.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ObjectRedisSerializer implements RedisSerializer<Object> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		if(obj == null)
		{
			return null;
		}
		
		try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
			try(ObjectOutputStream o = new ObjectOutputStream(b)){
				o.writeObject(obj);
			}
			return b.toByteArray();
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
		}
		
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null)
		{
			return null;
		}
		
		try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
		catch(Exception ex)
		{
			log.error(ex.getMessage());
		}
		
		return null;
	}

}