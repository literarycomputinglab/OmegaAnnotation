/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.Authors;
import it.cnr.ilc.lc.omega.adt.annotation.dto.Loci;
import it.cnr.ilc.lc.omega.adt.annotation.dto.PubblicationDate;
import it.cnr.ilc.lc.omega.adt.annotation.dto.SegmentOfInterest;
import it.cnr.ilc.lc.omega.adt.annotation.dto.Title;
import it.cnr.ilc.lc.omega.adt.annotation.dto.WorkSource;
import it.cnr.ilc.lc.omega.annotation.structural.WorkAnnotation;
import it.cnr.ilc.lc.omega.annotation.structural.WorkAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.core.datatype.ADTAbstractAnnotation;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public final class Work extends ADTAbstractAnnotation implements CatalogItem {

    private static final Logger log = LogManager.getLogger(Work.class);

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static

    private Annotation<TextContent, WorkAnnotation> annotation;

    private Work(String annotationAuthor, String[] authors, Date creationDate,
            String info, PubblicationDate pubblicationDate, Title title,
            URI uri) throws ManagerAction.ActionException {

        init(annotationAuthor, authors, creationDate, info, pubblicationDate, title, uri);
    }

    private Work(Annotation<TextContent, WorkAnnotation> ann) {

        this.annotation = ann;
    }

    /**
     *
     * @param authors
     * @param pubblicationDate
     * @param title
     * @param uri
     * @return
     * @throws it.cnr.ilc.lc.omega.core.ManagerAction.ActionException
     */
    public static Work of(Authors authors,
            PubblicationDate pubblicationDate,
            Title title, URI uri) throws ManagerAction.ActionException {
        log.info("of=(" + authors + " " + pubblicationDate + " " + title + " " + uri + ")");

        return new Work("user0", authors.getValue().toArray(new String[0]), Date.from(new Date().toInstant()), "", pubblicationDate, title, uri);
    }

    /**
     *
     * @param <T>
     * @param <L>
     * @param annotationAuthor
     * @param authors
     * @param creationDate
     * @param info
     * @param pubblicationDate
     * @param title
     * @param uri
     * @throws it.cnr.ilc.lc.omega.core.ManagerAction.ActionException
     */
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

        log.info(wab.toString());
        annotation = resourceManager.createAnnotation(WorkAnnotation.class, wab);

    }

    public static <T extends Content> TextLocus createTextLocus(Source<T> source) throws ManagerAction.ActionException {

        try {
            return resourceManager.createLocus(source, TextContent.class);

        } catch (InvalidURIException ex) {
            log.error("Creating TextLocus", ex);
        }
        return null;
    }

    public static <T extends Content> TextLocus createTextLocus(Source<T> source, int start, int end) throws ManagerAction.ActionException {

        try {
            return resourceManager.createLocus(source, start, end, TextContent.class);

        } catch (InvalidURIException ex) {
            log.error("Creating TextLocus", ex);
        }
        return null;
    }

    public <V extends Content> void addLoci(Loci loci) throws ManagerAction.ActionException {

        for (Locus locus : loci.getValues()) {

            if (locus instanceof TextLocus) {
                addTextLocus(locus);
            } else if (locus instanceof ImageLocus) {
                addImageLocus(locus);
            } else {
                log.error("throw new TypeNotPresentException(locus.getClass().getCanonicalName(), null);");
                throw new TypeNotPresentException(locus.getClass().getCanonicalName(), null);
            }
        }

    }

    private void addTextLocus(Locus<TextContent> locus) throws ManagerAction.ActionException {

        resourceManager.updateAnnotationLocus((TextLocus) locus, annotation, TextContent.class);
    }

    private void addImageLocus(Locus<ImageContent> locus) throws ManagerAction.ActionException {

        resourceManager.updateAnnotationLocus((ImageLocus) locus, annotation, ImageContent.class);
    }

    public void addLocus(WorkSource ws, SegmentOfInterest soi) throws ManagerAction.ActionException, InvalidURIException {

        TextLocus locus = resourceManager.createLocus(ws.getValue(), soi.getValue().getFirst(), soi.getValue().getSecond(), TextContent.class);
        resourceManager.updateAnnotationLocus(locus, annotation, TextContent.class);
    }

    public void addLocus(WorkSource ws) throws ManagerAction.ActionException, InvalidURIException {

        TextLocus locus = resourceManager.createLocus(ws.getValue(), TextContent.class);
        resourceManager.updateAnnotationLocus(locus, annotation, TextContent.class);
    }

    @Override
    public void save() throws ManagerAction.ActionException {
        // controllare che annotation non sia null
        resourceManager.saveAnnotation(annotation);
    }

    @Override
    protected Annotation<TextContent, WorkAnnotation> getAnnotation() {
        return this.annotation;
    }

    public static Work load(URI uri) throws ManagerAction.ActionException {

        Annotation annotationWork = resourceManager.loadAnnotation(uri, TextContent.class);

        return new Work(annotationWork);
    }

    public URI getURI() { //la URI e' della annotation e non quella in annotation.data
        return URI.create(annotation.getUri());
    }

    public static List<Work> loadAll() throws ManagerAction.ActionException {

        List<Work> array = new ArrayList<>();
        List<Annotation<TextContent, WorkAnnotation>> loa = resourceManager.loadAllAnnotationData(TextContent.class, WorkAnnotation.class);
        log.info("loadAll() result lenght " + loa.size());

        for (Annotation<TextContent, WorkAnnotation> ann : loa) {
            array.add(new Work(ann));
        }
        return array;
    }

    @Override
    public String toString() {
        return String.format("Work: %s", annotation.toString()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setAnnotation(Annotation<?, ?> annotation) {
        this.annotation = (Annotation<TextContent, WorkAnnotation>) annotation;
    }

    @Override
    public boolean isRemovable() throws ManagerAction.ActionException{

       return !resourceManager.isTargetOfRelation(annotation);
    }

    
    
}
