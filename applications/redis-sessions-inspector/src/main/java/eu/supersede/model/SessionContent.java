package eu.supersede.model;

import java.util.Date;

import eu.supersede.fe.security.DatabaseUser;

public class SessionContent {

	private String id;
	private Date creationTime;
	private Date lastAccessTime;
	private DatabaseUser databaseUser;
	
	public SessionContent() {
	}

	public SessionContent(String id, Date creationTime, Date lastAccessTime, DatabaseUser databaseUser)
	{
		this.id = id;
		this.creationTime = creationTime;
		this.lastAccessTime = lastAccessTime;
		this.databaseUser = databaseUser;
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
