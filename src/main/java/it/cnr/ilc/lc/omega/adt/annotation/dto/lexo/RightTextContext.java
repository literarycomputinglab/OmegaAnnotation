/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.lexo;

/**
 *
 * @author simone
 */
public class RightTextContext implements LexoDTOValue<String>{

    String rightTextContex;

    RightTextContext() {
    }
    
    @Override
    public String getValue() {
        return rightTextContex;
    }

    @Override
    public <K extends LexoDTOValue<String>> K withValue(String t) {
        this.rightTextContex = t;
        return (K) this;
    }
}