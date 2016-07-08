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
public class Title implements DTOValue<String, Title>{
    
    String title;

    private Title() {
    }
        
    public Title instantiate() {
        return new Title();
    }
    
    @Override
    public String getValue() {
        return title;
    }

    @Override
    public void withValue(String t) {
        title = t;
    }
            
    
}