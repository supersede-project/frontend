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

package eu.supersede.fe.security.redis.session;

import java.util.Date;

import eu.supersede.fe.security.DatabaseUser;

public class Session
{
    public final static String SUPERSEDE_SESSION_PREFIX = "eu.supersede.session:";

    private String id;
    private Date creationTime;
    private Date lastAccessTime;
    private DatabaseUser databaseUser;

    public Session()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public Date getLastAccessTime()
    {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime)
    {
        this.lastAccessTime = lastAccessTime;
    }

    public DatabaseUser getDatabaseUser()
    {
        return databaseUser;
    }

    public void setDatabaseUser(DatabaseUser databaseUser)
    {
        this.databaseUser = databaseUser;
    }
}