/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore;

/**
 *
 * @author simone
 */
public class DCIdentifier   implements  DTOValueDC<String>{

    private String identifier;

    public DCIdentifier() {
    }
    
    
    @Override
    public String getValue() {
        return identifier;
    }

    @Override
    public <K extends DTOValueDC<String>> K withValue(String t) {
        identifier = t;
        return (K) this;
    }
    
}
