/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.lexo;

import java.net.URI;

/**
 *
 * @author simone
 */
public class UriSense implements LexoDTOValue<URI> {

    URI uri;

    UriSense() {
    }
        
    @Override
    public URI getValue() {
        return uri;
    }

    @Override
    public <K extends LexoDTOValue<URI>> K withValue(URI t) {
        this.uri = t;
        return (K) this;
    }
    
}
