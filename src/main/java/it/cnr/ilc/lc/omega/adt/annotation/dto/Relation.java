/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import it.cnr.ilc.lc.omega.adt.annotation.dto.DTOValue;

/**
 *
 * @author simone
 */
public class Relation implements DTOValue<String>{

    private String relations;

    Relation() {
    }
        
    @Override
    public String getValue() {
        return relations;
    }

    @Override
    public <K extends DTOValue<String>> K withValue(String t) {
        relations = t;
        return (K) this;
    }
    
}
