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

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplates
{
    @Bean
    SessionRedisTemplate sessionTemplate(RedisConnectionFactory connectionFactory)
    {
        return new SessionRedisTemplate(connectionFactory);
    }

    @Bean
    SessionObjectRedisTemplate sessionObjectTemplate(RedisConnectionFactory connectionFactory)
    {
        return new SessionObjectRedisTemplate(connectionFactory);
    }
}