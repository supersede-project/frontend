package eu.supersede.fe.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="profiles_labels")
public class ProfileLabel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileLabelId;
	
    private String lang;
    private String label;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;


    public ProfileLabel() {
    }
    
	public Long getProfileLabelId() {
		return profileLabelId;
	}

	public void setProfileLabelId(Long profileLabelId) {
		this.profileLabelId = profileLabelId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
