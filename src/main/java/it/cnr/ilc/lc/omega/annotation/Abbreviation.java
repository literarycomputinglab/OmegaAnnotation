/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.annotation;

import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.ResourceManager;
import it.cnr.ilc.lc.omega.core.datatype.Text;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.AnnotationBuilder;
import it.cnr.ilc.lc.omega.entity.Content;
import it.cnr.ilc.lc.omega.entity.Locus;
import it.cnr.ilc.lc.omega.entity.TextContent;
import it.cnr.ilc.lc.omega.exception.InvalidURIException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import sirius.kernel.di.std.Part;

/**
 *
 * @author simone
 */
public class Abbreviation {

    @Part
    private static ResourceManager resourceManager; //ERROR: l'injection (SIRIUS KERNEL) funziona solo se dichiarata static in quanto richiamata da una new in un metodo static

    private Annotation<Content, AbbreviationType> annotation;

    private <T extends Content> Abbreviation(Locus<T> locus, String expansion, URI uri) throws ManagerAction.ActionException {

        init(locus, expansion, uri);
    }

    public static <T extends Content> Abbreviation of(Locus<T> locus, String expansion) throws ManagerAction.ActionException {
        System.err.println("Abbreviation.of");
        //FIXME Aggiungere URI della annotazione
        return new Abbreviation(locus, expansion, null);
    }

    public static <T extends Content> Abbreviation of(Locus<T> locus, String expansion, URI uri) throws ManagerAction.ActionException {
        System.err.println("Abbreviation.of");
        //FIXME Aggiungere URI della annotazione
        return new Abbreviation(locus, expansion, uri);
    }

    private <T extends Content> void init(Locus<T> locus, String expansion, URI uri) throws ManagerAction.ActionException {
        System.err.println("Abbreviation init() " + resourceManager);
        AbbreviationBuilder ab = new AbbreviationBuilder().abbrevationExpansion(expansion).abbrevation("testo della abbreviazione");

    }

    public static Locus<TextContent> createLocus(int start, int end) throws ManagerAction.ActionException {
        try {
            return resourceManager.createLocus(start, end);
            
            
        } catch (InvalidURIException ex) {
            Logger.getLogger(Abbreviation.class.getName()).log(Level.INFO, null, ex);
        }
        return null;
    }

    public void addLocus(Text text, int start, int end) throws ManagerAction.ActionException {

        resourceManager.updateTextAnnotationLocus(text.getSource(), annotation, start, end);
    }

    public void save() throws ManagerAction.ActionException {

        resourceManager.saveAnnotation(annotation);
    }

}
