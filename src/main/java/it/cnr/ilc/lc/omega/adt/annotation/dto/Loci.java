/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import it.cnr.ilc.lc.omega.entity.Locus;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simone
 */
public class Loci implements DTOValue<Locus> {

    List<Locus> loci;

    Loci() {
        loci = new ArrayList<>();
    }

    @Override
    public Locus getValue() {

        return getValue(0);
    }

    @Override
    public <K extends DTOValue<Locus>> K withValue(Locus t) {
        loci.add(t);
        return (K) this;
    }

    public Locus getValue(int i) {
        
        return loci.get(i);
    }

    public List<Locus> getValues() {
        
        return loci;
    }
}
