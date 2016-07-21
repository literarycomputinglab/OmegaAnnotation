/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import java.util.List;

/**
 *
 * @author simone
 */
public final class AnnotationAuthor implements DTOValue<String> {

    String name;

    AnnotationAuthor() {
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public <K extends DTOValue<String>> K withValue(String t) {
        this.name = t;
        return (K) this;
    }

}
