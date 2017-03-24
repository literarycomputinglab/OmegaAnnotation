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
public class DCContributor implements  DTOValueDC<String[]>{

    private String[] contributors;

    DCContributor() {
    }
    
    
    @Override
    public String[] getValue() {
        return contributors;
    }

    @Override
    public <K extends DTOValueDC<String[]>> K withValue(String[] t) {
        contributors = t;
        return (K) this;
    }
    
    
}
