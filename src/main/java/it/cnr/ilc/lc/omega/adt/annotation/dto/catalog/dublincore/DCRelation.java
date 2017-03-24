/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore;

import it.cnr.ilc.lc.omega.adt.annotation.dto.Couple;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author AMDG
 * @author SM
 */
public class DCRelation implements DTOValueDC<List<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>>> {

    private static final Logger log = LogManager.getLogger(DCRelation.class);

    private List<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>> relations;

    DCRelation() {
    }

    @Override
    public List<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>> getValue() {
        return relations;
    }

    @Override
    public <K extends DTOValueDC<List<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>>>> K
            withValue(List<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>> t) {

        relations = t;
        return (K) this;
    }

    public DCRelation addValue(Couple<DublinCoreAnnotation.DCTerms, DCRelationObject> val) {

        if (null == relations) {
            relations = new ArrayList<>();
        }
        relations.add(val);
        log.trace(val);
        return this;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Iterator<Couple<DublinCoreAnnotation.DCTerms, DCRelationObject>> iterator = relations.iterator(); iterator.hasNext();) {
            Couple<DublinCoreAnnotation.DCTerms, DCRelationObject> next = iterator.next();
            if (iterator.hasNext()) {
                sb.append(String.format("%s|", next.toString()));
            } else {
                sb.append(next.toString());
            }
        }
        log.trace(sb.toString());
        return sb.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
