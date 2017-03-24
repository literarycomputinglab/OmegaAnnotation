/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore;

import it.cnr.ilc.lc.omega.adt.annotation.dto.Couple;
import java.util.Date;

/**
 *
 * @author simone
 */
public class DCDate  implements DTOValueDC<Couple<Date,String>>{

    private Couple<Date,String> dcdate;
    
    DCDate() {
    }

    
    @Override
    public Couple<Date, String> getValue() {
        return dcdate;
    }

    @Override
    public <K extends DTOValueDC<Couple<Date, String>>> K withValue(Couple<Date, String> t) {
        dcdate = t;
        return (K) this;
    }
    
}
