package eu.supersede.fe.model;

public class Locale {

	private String lang;
	private String language;
	
	public Locale(java.util.Locale locale, String language) {
		this(locale.getLanguage(), language);
	}

	public Locale(String lang, String language) {
		this.setLang(lang);
		this.setLanguage(language);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
