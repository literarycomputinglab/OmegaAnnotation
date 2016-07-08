/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

/**
 *
 * @author simone
 * @param <T>
 * @param <K>
 */
public interface DTOValue<T, K extends DTOValue> {

    static public <K> K instantiate() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    
    abstract T getValue();

    abstract void withValue(T t);

}
