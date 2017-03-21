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
public final class Couple<T,K> {
    
    final T first;
    final K second;

    public Couple(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public K getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", first.toString(), second.toString());
    }
    
    
}
