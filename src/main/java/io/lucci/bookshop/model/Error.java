package io.lucci.bookshop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Error {
	
    private String code;
    private String message;
    private Map<String, String> additionalParams;
    private List<Violation> violations;

    public Error() {
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
    
    public void addAdditionalParam( String key, String value ) {
        if ( additionalParams == null ) {
            additionalParams = new HashMap<String, String>();
        }
        additionalParams.put( key, value );
    }
    

    
}
