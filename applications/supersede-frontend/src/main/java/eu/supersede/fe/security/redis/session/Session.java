package eu.supersede.fe.security.redis.session;

import java.util.Date;

import eu.supersede.fe.security.DatabaseUser;

public class Session {

	public final static String SUPERSEDE_SESSION_PREFIX = "eu.supersede.session:";
	
	private String id;
	private Date creationTime;
	private Date lastAccessTime;
	private DatabaseUser databaseUser;
	
	public Session() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public DatabaseUser getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(DatabaseUser databaseUser) {
		this.databaseUser = databaseUser;
	}
}
