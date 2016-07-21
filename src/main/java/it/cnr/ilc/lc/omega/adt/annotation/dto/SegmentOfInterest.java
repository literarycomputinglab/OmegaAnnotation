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
public class SegmentOfInterest implements DTOValue<Couple<Integer, Integer>> {

    private Integer start;
    private Integer end;

    SegmentOfInterest() {
    }

    @Override
    public Couple<Integer, Integer> getValue() {

        return new Couple(start, end);
    }

    @Override
    public <K extends DTOValue<Couple<Integer, Integer>>> K withValue(Couple<Integer, Integer> t) {

        this.start = t.getFirst();
        this.end = t.getSecond();

        return (K) this;
    }

}
