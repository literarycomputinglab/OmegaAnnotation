/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

/**
 *
 * @author simone
 */
public final class Title implements DTOValue<String>{
    
    String title;

    Title() {
    }
        
    
    @Override
    public String getValue() {
        return title;
    }

    @Override
    public <K extends DTOValue<String>> K withValue(String t) {
        this.title = t;
        return (K) this;
    }
            
    
}