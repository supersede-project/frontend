package eu.supersede.model;

import java.util.Date;

public class SessionContent {

	private String id;
	private Date creationTime;
	private Date lastAccessTime;
	private String name;
	
	public SessionContent() {
	}

	public SessionContent(String id, Date creationTime, Date lastAccessTime, String name)
	{
		this.id = id;
		this.creationTime = creationTime;
		this.lastAccessTime = lastAccessTime;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
