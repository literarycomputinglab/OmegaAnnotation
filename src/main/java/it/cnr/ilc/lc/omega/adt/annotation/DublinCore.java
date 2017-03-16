/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.Contributor;
import it.cnr.ilc.lc.omega.adt.annotation.dto.Relation;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotation;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.Content;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public class DublinCore<T extends Content> {

    private static final Logger log = LogManager.getLogger(DublinCore.class);

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static

    private Annotation<T, DublinCoreAnnotation> annotation;

    private DublinCore() {}

    
    
    private DublinCore(Work w) {
        init(w);
    }

        
    public static DublinCore of (Work w) {
        return new DublinCore(w);
    }

    private void init(Work w) {
        System.err.println("init()");
    }
    
    public DublinCore withTerms (Contributor contributor, Relation relation) {
        
        DublinCoreAnnotationBuilder dcab = new DublinCoreAnnotationBuilder()
                .contributor(contributor.getValue())
                .relation(new String[]{relation.getValue()});
                
                
        return this;
    }
    
}
