/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.security.redis.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ObjectRedisSerializer implements RedisSerializer<Object>
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public byte[] serialize(Object obj) throws SerializationException
    {
        if (obj == null)
        {
            return null;
        }

        try (ByteArrayOutputStream b = new ByteArrayOutputStream())
        {
            try (ObjectOutputStream o = new ObjectOutputStream(b))
            {
                o.writeObject(obj);
            }

            return b.toByteArray();
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }

        return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException
    {
        if (bytes == null)
        {
            return null;
        }

        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes))
        {
            try (ObjectInputStream o = new ObjectInputStream(b))
            {
                return o.readObject();
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }

        return null;
    }
}