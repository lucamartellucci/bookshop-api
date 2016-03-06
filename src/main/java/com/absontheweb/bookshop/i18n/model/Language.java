package com.absontheweb.bookshop.i18n.model;

import lombok.Data;

@Data
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
    
}
