/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCContributor;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCCoverage;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCCreator;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCDate;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCDescription;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCFormat;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCIdentifier;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCLanguage;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCPublisher;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCRelation;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCRights;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCSource;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCSubject;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCTitle;
import it.cnr.ilc.lc.omega.adt.annotation.dto.catalog.dublincore.DCType;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotation;
import it.cnr.ilc.lc.omega.annotation.catalog.DublinCoreAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.core.annotation.AnnotationRelationType;
import it.cnr.ilc.lc.omega.core.datatype.ADTAbstractAnnotation;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.Content;
import it.cnr.ilc.lc.omega.entity.ext.DateEvent;
import it.cnr.ilc.lc.omega.exception.AnnotationAlreadyExistsException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public final class DublinCore<T extends Content> extends ADTAbstractAnnotation implements CatalogItem {

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

    private DublinCore(Annotation<T, DublinCoreAnnotation> annotation) throws ManagerAction.ActionException {

        log.debug("Creating DublinCore by annotation");

        this.annotation = annotation;
        this.uri = URI.create(annotation.getUri());
        this.w = Work.load(URI.create(annotation.getRelations().next().getTargetAnnotation().getUri()));
    }

    private void init(Work w, URI uri) {

        this.uri = uri;
        this.w = w;

        log.info("init()");
    }

    //contributor ", "coverage", "creator", "date", "description", 
    //"format", "identifier", "language", "publisher", "relation", "rights",
    //"source", "subject", "title", "type"
    public DublinCore withTerms(DCContributor contributor, DCCoverage coverage,
            DCCreator creator, DCDate date, DCDescription description, DCFormat format,
            DCIdentifier identifier, DCLanguage language, DCPublisher publisher,
            DCRelation relation, DCRights rights, DCSource source, DCSubject subject,
            DCTitle title, DCType type) throws ManagerAction.ActionException {

        if (null == annotation) {
            DublinCoreAnnotationBuilder dcab = new DublinCoreAnnotationBuilder()
                    .contributor(contributor.getValue())
                    .coverage(coverage.getValue())
                    .creator(creator.getValue())
                    .dateEvent(new DateEvent(date.getValue().getFirst(), date.getValue().getSecond())) //ATTENZIONE: DATEEVENT da gestire dentro il builder
                    .description(description.getValue())
                    .format(format.getValue())
                    .identifier(identifier.getValue())
                    .language(language.getValue())
                    .publisher(publisher.getValue())
                    .relation(relation.toString().split("\\|")) //DA FARE NEL BUILDER
                    .rights(rights.getValue())
                    .source(source.getValue())
                    .subject(subject.getValue())
                    .title(title.getValue())
                    .type(type.getValue())
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

    public static DublinCore load(URI uri) throws ManagerAction.ActionException {

        DublinCore dc = null;
        Annotation<Content, DublinCoreAnnotation> annotationDC = null;
        try {

            annotationDC = (Annotation<Content, DublinCoreAnnotation>) resourceManager.loadAnnotation(uri, Content.class);

        } catch (ManagerAction.ActionException e) {
            throw new ManagerAction.ActionException(new Exception("Error while loading Dublic Core annotation with URI " + uri, e));
        }

        try {
            if (annotationDC != null) {
                dc = new DublinCore(annotationDC);
            } else {
                throw new ManagerAction.ActionException(new Exception("Dublic Core annotation is null with URI " + uri));
            }
        } catch (ManagerAction.ActionException e) {
            throw new ManagerAction.ActionException(new Exception("Error while loading Work for Dublic Core annotation with Dublin Core URI " + uri, e));
        }

        return dc;
    }

    @Override
    protected Annotation<T, DublinCoreAnnotation> getAnnotation() {
        return this.annotation;
    }

}
