package com.absontheweb.bookshop.model.i18n;

import java.io.Serializable;
import java.util.Locale;


public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String locale;
    private Boolean nameAsImg;

    public static final LanguageBean DEFAULT = new LanguageBean( "English", Locale.ENGLISH.toString(), Boolean.FALSE );

    public LanguageBean( String name, String locale, Boolean nameAsImg ) {
        super();
        this.name = name;
        this.locale = locale;
        this.nameAsImg = nameAsImg;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale( String locale ) {
        this.locale = locale;
    }
    public Boolean getNameAsImg() {
        return nameAsImg;
    }
    public void setNameAsImg( Boolean nameAsImg ) {
        this.nameAsImg = nameAsImg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "LanguageBean [name=" );
        builder.append( name );
        builder.append( ", locale=" );
        builder.append( locale );
        builder.append( ", nameAsImg=" );
        builder.append( nameAsImg );
        builder.append( "]" );
        return builder.toString();
    }


}
