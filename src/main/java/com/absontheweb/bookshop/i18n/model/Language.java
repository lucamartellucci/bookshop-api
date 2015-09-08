package com.absontheweb.bookshop.i18n.model;

/**
 * <p>Description:  TODO Insert description here</p>
 * <p>Copyright:    Copyright (c) 2014</p>
 * <p>Company:      B Human</p>
 *
 * @author damy
 * @version $Revision: $ ($Date: $)
 */
public class Language {

    private String name;
    private String locale;

    public Language() {
    }
    public Language( final String name, final String locale ) {
        this.name = name;
        this.locale = locale;
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

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Language ) ) return false;

        final Language language = ( Language ) o;

        if ( locale != null ? !locale.equals( language.locale ) : language.locale != null ) return false;
        if ( name != null ? !name.equals( language.name ) : language.name != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( locale != null ? locale.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( "Language{" );
        sb.append( "name='" ).append( name ).append( '\'' );
        sb.append( ", locale='" ).append( locale ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

}
