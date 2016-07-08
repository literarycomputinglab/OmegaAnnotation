/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import java.util.Date;

/**
 *
 * @author simone
 */
public class PubblicationDate implements DTOValue<Date, PubblicationDate>{

    Date pubblicationDate;

    private PubblicationDate() {
    }
    
    public static PubblicationDate instantiate() {
        return new PubblicationDate();
    }

    @Override
    public Date getValue() {
        return pubblicationDate;
    }

    @Override
    public void withValue(Date t) {
        this.pubblicationDate = t;
    }
    
}
