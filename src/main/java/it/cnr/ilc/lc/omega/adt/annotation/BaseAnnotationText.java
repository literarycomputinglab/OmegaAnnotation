/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.annotation.BaseAnnotation;
import it.cnr.ilc.lc.omega.annotation.BaseAnnotationBuilder;
import it.cnr.ilc.lc.omega.core.ManagerAction;
import it.cnr.ilc.lc.omega.core.datatype.ADTAbstractAnnotation;
import it.cnr.ilc.lc.omega.core.datatype.Text;
import it.cnr.ilc.lc.omega.entity.Annotation;
import it.cnr.ilc.lc.omega.entity.TextContent;
import it.cnr.ilc.lc.omega.entity.TextLocus;
import it.cnr.ilc.lc.omega.exception.InvalidURIException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author simone
 */
public final class BaseAnnotationText extends ADTAbstractAnnotation {

    private static final Logger log = LogManager.getLogger(BaseAnnotationText.class);

    private Annotation<TextContent, BaseAnnotation> annotation;

    private BaseAnnotationText(String text, URI uri) throws ManagerAction.ActionException {

        init(text, uri);
    }

    public static BaseAnnotationText of(URI uri, String text) throws ManagerAction.ActionException {
        log.info("text=(" + ((text != null) ? text.length() : "null") + ") uri=(" + uri + ")");
        return new BaseAnnotationText(text, uri);
    }

    public static BaseAnnotationText load(URI uri) throws ManagerAction.ActionException {

        Annotation<TextContent, BaseAnnotation> baseAnnotation
                = (Annotation<TextContent, BaseAnnotation>) resourceManager.loadAnnotation(uri, TextContent.class);

        if (null != baseAnnotation) {
            return new BaseAnnotationText(baseAnnotation);
        } else {
            throw new ManagerAction.ActionException(new NullPointerException("Base Annotation loaded is null!"));
        }
    }

    private BaseAnnotationText(Annotation<TextContent, BaseAnnotation> baseAnnotation) {
        this.annotation = baseAnnotation;
    }

    private void init(String text, URI uri) throws ManagerAction.ActionException {

        log.info("text=(" + ((text != null) ? text.length() : "null") + ") uri=(" + uri + ")");

        BaseAnnotationBuilder bab = new BaseAnnotationBuilder().URI(uri).text(text);
        annotation = resourceManager.createAnnotation(
                BaseAnnotation.class, bab);

    }

    public void addLocus(Text text, int start, int end) throws ManagerAction.ActionException, InvalidURIException {
        log.info("start " + start + ", end " + end);
        TextLocus locus = resourceManager.createLocus(text.getSource(), start, end, TextContent.class);
        resourceManager.updateAnnotationLocus(locus, annotation, TextContent.class);
///////        resourceManager.updateTextAnnotationLocus(text.getSource(), annotation, start, end);
    }

    @Override
    public void save() throws ManagerAction.ActionException {
        log.info("save()");
        this.annotation = resourceManager.saveAnnotation(annotation);
    }

    @Override
    protected Annotation<TextContent, BaseAnnotation> getAnnotation() {
        return this.annotation;
    }

    @Override
    protected void setAnnotation(Annotation<?, ?> annotation) {
        this.annotation = (Annotation<TextContent, BaseAnnotation>) annotation;
    }

    @Override
    public boolean isRemovable() throws ManagerAction.ActionException {
        return !resourceManager.isTargetOfRelation(annotation);
    }
}
