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
public class  DCNaN implements  DTOValueDC{

    private static final Object PARAM = null;
    
    DCNaN() {
    }

    
    @Override
    public Object getValue() {
        return PARAM;
    }

    @Override
    public DTOValueDC withValue(Object t) {
        return this;
    }
    
}
