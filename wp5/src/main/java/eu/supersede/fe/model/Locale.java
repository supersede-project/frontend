package eu.supersede.fe.model;

public class Locale {

	private String lang;
	
	public Locale(java.util.Locale locale) {
		this(locale.getLanguage());
	}

	public Locale(String lang) {
		this.setLang(lang);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
