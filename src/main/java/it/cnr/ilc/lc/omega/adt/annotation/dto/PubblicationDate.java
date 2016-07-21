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
public final class PubblicationDate implements DTOValue<Date>{

    Date pubblicationDate;

    PubblicationDate() {
    }

    @Override
    public Date getValue() {
        return pubblicationDate;
    }

    @Override
    public <K extends DTOValue<Date>> K withValue(Date t) {
        this.pubblicationDate = t;
        return (K) this;
    }
    
    
}
