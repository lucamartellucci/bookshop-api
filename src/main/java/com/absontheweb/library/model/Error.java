package com.absontheweb.library.model;

import java.util.HashMap;
import java.util.Map;


public class Error {
    private String code;
    private String message;
    private Map<String, String> additionalParams;

    public Error() {
    }

    public void addAdditionalParam( String key, String value ) {
        if ( additionalParams == null ) {
            additionalParams = new HashMap<String, String>();
        }
        additionalParams.put( key, value );
    }

    public Error( final String code, final String message ) {
        this.code = code;
        this.message = message;
    }

    public Error( final String code, final String message, final Map<String, String> additionalParams ) {
        this.code = code;
        this.message = message;
        this.additionalParams = additionalParams;
    }

    public String getCode() {
        return code;
    }
    public void setCode( final String code ) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage( final String message ) {
        this.message = message;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }
    public void setAdditionalParams( final Map<String, String> additionalParams ) {
        this.additionalParams = additionalParams;
    }
}
