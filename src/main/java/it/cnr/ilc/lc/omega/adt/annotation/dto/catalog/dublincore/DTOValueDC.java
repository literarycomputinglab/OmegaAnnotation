/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore;

/**
 *
 * @author simone
 * @param <T>
 * @param <K>
 */
public interface DTOValueDC<T> {

    static public <T,K extends DTOValueDC<T>> K instantiate(Class<K> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    abstract T getValue();

    abstract <K extends DTOValueDC<T>> K withValue(T t);

}
