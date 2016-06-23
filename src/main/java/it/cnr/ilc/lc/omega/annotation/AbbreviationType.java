/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.annotation;

import it.cnr.ilc.lc.omega.entity.Annotation;

/**
 *
 * @author simone
 */
public class AbbreviationType extends Annotation.Data {
    
    //abbreviazione sciolta
    private String abbrevationExpansion;
    
    //abbreviazione cosi' come compare nel testo
    private String abbrevation;

    public String getAbbrevationExpansion() {
        return abbrevationExpansion;
    }

    public void setAbbrevationExpansion(String abbrevationExpansion) {
        this.abbrevationExpansion = abbrevationExpansion;
    }

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    @Override
    public <E extends Annotation.Data> E get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
