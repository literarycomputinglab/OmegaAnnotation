/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.PubblicationDate;
import it.cnr.ilc.lc.omega.adt.annotation.dto.Title;
import it.cnr.ilc.lc.omega.annotation.AbbreviationAnnotation;
import it.cnr.ilc.lc.omega.annotation.AbbreviationAnnotationBuilder;
import it.cnr.ilc.lc.omega.annotation.structural.WorkAnnotation;
import it.cnr.ilc.lc.omega.annotation.structural.WorkAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.Content;
import it.cnr.ilc.lc.omega.entity.ImageContent;
import it.cnr.ilc.lc.omega.entity.ImageLocus;
import it.cnr.ilc.lc.omega.entity.Locus;
import it.cnr.ilc.lc.omega.entity.Source;
import it.cnr.ilc.lc.omega.entity.TextContent;
import it.cnr.ilc.lc.omega.entity.TextLocus;
import it.cnr.ilc.lc.omega.exception.InvalidURIException;
import java.net.URI;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public class Work {

    private static final Logger log = LogManager.getLogger(Work.class);

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static

    private Annotation<TextContent, WorkAnnotation> annotation;

    private Work(String annotationAuthor, String[] authors, Date creationDate, 
            String info, PubblicationDate pubblicationDate, Title title,
            URI uri) throws ManagerAction.ActionException  {

        init(annotationAuthor,authors, creationDate, info, pubblicationDate, title, uri);
    }

    public static Work of(PubblicationDate pubblicationDate, Title title, URI uri) throws ManagerAction.ActionException {
        
        return new Work (null, null,null,null, pubblicationDate, title, uri);
    }
    
    private <T extends Content, L extends Locus<T>> void init(String annotationAuthor,
            String[] authors, Date creationDate, String info, PubblicationDate pubblicationDate, Title title,
            URI uri) throws ManagerAction.ActionException {

        log.info("Work init(): resourceManager=(" + resourceManager + ")");
        WorkAnnotationBuilder wab = new WorkAnnotationBuilder()
                .annotationAuthor(annotationAuthor)
                .authors(authors)
                .creationData(creationDate)
                .info(info)
                .pubblicationDate(pubblicationDate.getValue())
                .title(title.getValue())
                .URI(uri);

        annotation = resourceManager.createAnnotation(WorkAnnotation.class, wab);

    }

    public static <T extends Content> TextLocus createTextLocus(Source<T> source, int start, int end) throws ManagerAction.ActionException {

        try {
            return resourceManager.createLocus(source, start, end, TextContent.class);

        } catch (InvalidURIException ex) {
            log.error("Creating TextLocus", ex);
        }
        return null;
    }

    public <V extends Content> void addLocus(Locus<V> locus) throws ManagerAction.ActionException {
        if (locus instanceof TextLocus) {
            resourceManager.updateAnnotationLocus((TextLocus) locus, annotation, TextContent.class);
        } else if (locus instanceof ImageLocus) {
            resourceManager.updateAnnotationLocus((ImageLocus) locus, annotation, ImageContent.class);
        } else {
            log.error("Invalid type of locus " + locus);
            throw new UnsupportedOperationException("Invalid type of locus " + locus);
        }
    }

    public void save() throws ManagerAction.ActionException {
        // controllare che annotation non sia null
        resourceManager.saveAnnotation(annotation);
    }
}
