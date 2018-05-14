/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author simone
 */
public final class PubblicationDate implements DTOValue<Date>{

    Date pubblicationDate;

    PubblicationDate() {
    }

    PubblicationDate(String pd ) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {

            pubblicationDate = formatter.parse(pd);
            System.out.println(pubblicationDate);
            System.out.println(formatter.format(pubblicationDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
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
