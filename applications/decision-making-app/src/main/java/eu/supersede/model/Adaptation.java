package eu.supersede.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "adaptations")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Adaptation
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adaptation_id;
    private String name;
    private String description;
    private String priority;

    public Adaptation()
    {
    }

    public Long getAdaptationId()
    {
        return adaptation_id;
    }

    public void setAdaptationId(Long adaptation_id)
    {
        this.adaptation_id = adaptation_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }
}