package eu.supersede.fe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "applications")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Application
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private String url;

    public Application()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}