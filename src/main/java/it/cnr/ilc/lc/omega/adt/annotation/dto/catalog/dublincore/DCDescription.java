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
public class DCDescription  implements  DTOValueDC<String>{

    private String description;
    
    public DCDescription() {
    }
        
    @Override
    public String getValue() {
        return description;
    }

    @Override
    public <K extends DTOValueDC<String>> K withValue(String t) {
        description = t;
        return (K) this;
    }
    
    
}
