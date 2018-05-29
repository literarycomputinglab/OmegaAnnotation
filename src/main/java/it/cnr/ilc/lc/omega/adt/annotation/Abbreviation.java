/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.annotation.AbbreviationAnnotation;
import it.cnr.ilc.lc.omega.annotation.AbbreviationAnnotationBuilder;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public final class Abbreviation extends ADTAbstractAnnotation {

    private static final Logger log = LogManager.getLogger(Abbreviation.class);

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static

    private Annotation<TextContent, AbbreviationAnnotation> annotation;

    public static <T extends Content> Abbreviation of(URI uri, String expansion, String fragment) throws ManagerAction.ActionException {
        log.info("Abbreviation.of con URI");
        //FIXME Aggiungere URI della annotazione
        return new Abbreviation(expansion, uri, fragment);
    }

    // per autocorrelazione tra i generici:  L extends Locus<T>
    public static <T extends Content, L extends Locus<T>> Abbreviation of(URI uri, String expansion, L locus) throws ManagerAction.ActionException {
        log.info("Abbreviation.of con locus");
        //FIXME Aggiungere URI della annotazione
        if (locus instanceof TextLocus) {
            TextLocus textLocus = (TextLocus) locus;
            return new Abbreviation(expansion, uri, textLocus.getFragment());
        }
        return new Abbreviation(expansion, uri, "");
    }

    private <T extends Content, L extends Locus<T>> Abbreviation(String expansion, URI uri, String fragment) throws ManagerAction.ActionException {

        init(expansion, uri, fragment);
    }

    private <T extends Content, L extends Locus<T>> void init(String expansion, URI uri, String fragment) throws ManagerAction.ActionException {
        log.info("Abbreviation init() " + resourceManager);
        AbbreviationAnnotationBuilder ab = new AbbreviationAnnotationBuilder()
                .abbrevationExpansion(expansion)
                .abbrevation(fragment)
                .URI(uri);

        annotation = resourceManager.createAnnotation(AbbreviationAnnotation.class, ab);

    }

    /**
     * ATTENZIONE: start e end su una generica source ha senso solo se testo!
     * start e end vanno sostituiti con una struttura piu' generica che
     * rappresenti i POI (es. WKT)
     *
     * @param <T>
     * @param <L>
     * @param source
     * @param start
     * @param end
     * @return
     * @throws it.cnr.ilc.lc.omega.core.ManagerAction.ActionException
     */
    public static <T extends Content> TextLocus createTextLocus(Source<T> source, int start, int end) throws ManagerAction.ActionException {

        try {
            return resourceManager.createLocus(source, start, end, TextContent.class);

        } catch (InvalidURIException ex) {
            log.error("Creating TextLocus", ex);
        }
        return null;
    }

    public static <T extends Content> ImageLocus createImageLocus(Source<T> source, int WKT, int WKT2) throws ManagerAction.ActionException {

        try {
            return resourceManager.createLocus(source, WKT, WKT2, ImageContent.class);

        } catch (InvalidURIException ex) {
            log.error("Creating ImageLocus", ex);
        }
        return null;
    }

    public <V extends Content> void addLocus(Locus<V> locus) throws ManagerAction.ActionException {
        if (locus instanceof TextLocus) {
            resourceManager.updateAnnotationLocus((TextLocus) locus, annotation, TextContent.class);
        } else if (locus instanceof ImageLocus) {
            resourceManager.updateAnnotationLocus((ImageLocus) locus, annotation, ImageContent.class);
        } else {
            throw new UnsupportedOperationException("Invalid type of locus " + locus);
        }
    }

    public void save() throws ManagerAction.ActionException {
        // controllare che annotation non sia null
        resourceManager.saveAnnotation(annotation);
    }

    @Override
    protected Annotation<TextContent, AbbreviationAnnotation> getAnnotation() {
        return this.annotation;
    }

    //TODO: valutare inserimento anche metodi remove
    /**
     * resourceManager.removeAnnotaion(annotation); annotation = null; // se
     * mettiamo la remove bisogna controllare in ogni metodo la validità
     * dell'istanza (l'integrità).
     */
}
