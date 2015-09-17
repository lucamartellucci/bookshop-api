package com.absontheweb.bookshop.i18n.model;

public class Language {

    private String name;
    private String locale;
    private String flagUrl;

    public Language() {
    }
    public Language( final String name, final String locale, final String flagUrl ) {
        this.name = name;
        this.locale = locale;
        this.flagUrl = flagUrl;
    }

    public String getName() {
        return name;
    }
    public void setName( final String name ) {
        this.name = name;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale( final String locale ) {
        this.locale = locale;
    }
	public String getFlagUrl() {
		return flagUrl;
	}
	public void setFlagUrl(String flagUrl) {
		this.flagUrl = flagUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flagUrl == null) ? 0 : flagUrl.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (flagUrl == null) {
			if (other.flagUrl != null)
				return false;
		} else if (!flagUrl.equals(other.flagUrl))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Language [name=");
		builder.append(name);
		builder.append(", locale=");
		builder.append(locale);
		builder.append(", flagUrl=");
		builder.append(flagUrl);
		builder.append("]");
		return builder.toString();
	}

}
