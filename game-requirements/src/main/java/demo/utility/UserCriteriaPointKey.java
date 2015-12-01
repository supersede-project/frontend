package demo.utility;

import java.io.Serializable;

import demo.model.User;
import demo.model.ValutationCriteria;

public class UserCriteriaPointKey implements Serializable {

	private User user;	
	private ValutationCriteria valutationCriteria;

    public UserCriteriaPointKey() {
    }
    
    public UserCriteriaPointKey(User user, ValutationCriteria valutationCriteria) {
    	this.user = user;
    	this.valutationCriteria = valutationCriteria;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ValutationCriteria getValutationCritera() {
        return valutationCriteria;
    }

    public void setValutationCritera(ValutationCriteria valutationCriteria) {
        this.valutationCriteria = valutationCriteria;
    }

    public int hashCode() {
        return (int) user.hashCode() + (int) valutationCriteria.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UserCriteriaPointKey)) return false;
        if (obj == null) return false;
        UserCriteriaPointKey pk = (UserCriteriaPointKey) obj;
        return pk.user == user && pk.valutationCriteria.equals(valutationCriteria);
    }
}
