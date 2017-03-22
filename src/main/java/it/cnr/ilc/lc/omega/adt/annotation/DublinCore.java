/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.Contributor;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.Relation;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotation;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.core.annotation.AnnotationRelationType;
import it.cnr.ilc.lc.omega.core.datatype.ADTAbstractAnnotation;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.Content;
import it.cnr.ilc.lc.omega.exception.AnnotationAlreadyExistsException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public final class DublinCore<T extends Content> extends ADTAbstractAnnotation {

    private static final Logger log = LogManager.getLogger(DublinCore.class);

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static

    private Annotation<T, DublinCoreAnnotation> annotation;

    private URI uri;
    private Work w;

    private DublinCore() {
    }

    private DublinCore(Work w, URI uri) {

        init(w, uri);
    }

    public static DublinCore of(Work w, URI uri) {

        return new DublinCore(w, uri);
    }

    private void init(Work w, URI uri) {

        this.uri = uri;
        this.w = w;

        log.info("init()");
    }

    public DublinCore withTerms(Contributor contributor, Relation relation) throws ManagerAction.ActionException {

        if (null == annotation) {
            DublinCoreAnnotationBuilder dcab = new DublinCoreAnnotationBuilder()
                    .contributor(contributor.getValue())
                    .relation(relation.toString().split("\\|"))
                    .URI(uri);

            log.info("dcab=(" + dcab.toString() + ")");

            annotation = resourceManager.createAnnotation(DublinCoreAnnotation.class, dcab);
            resourceManager.updateAnnotationRelation(this, this.w, AnnotationRelationType.CATALOGRAPHIC_DESCRIPTION_OF);
        } else {
            throw new AnnotationAlreadyExistsException("When trying to overwrite an existing annotation " + annotation.getUri());
        }
        return this;
    }

    public void save() throws ManagerAction.ActionException {
        // controllare che annotation non sia null
        resourceManager.saveAnnotation(annotation);
    }

    @Override
    protected Annotation<T, DublinCoreAnnotation> getAnnotation() {
        return this.annotation;
    }

}
