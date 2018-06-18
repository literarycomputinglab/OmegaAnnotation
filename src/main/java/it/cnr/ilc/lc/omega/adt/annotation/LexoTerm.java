/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation;

import it.cnr.ilc.lc.omega.adt.annotation.dto.lexo.LeftTextContext;
import it.cnr.ilc.lc.omega.adt.annotation.dto.lexo.RightTextContext;
import it.cnr.ilc.lc.omega.adt.annotation.dto.lexo.TextFragment;
import it.cnr.ilc.lc.omega.adt.annotation.dto.lexo.UriSense;
import it.cnr.ilc.lc.omega.annotation.BaseAnnotation;
import it.cnr.ilc.lc.omega.annotation.LexoAnnotation;
import it.cnr.ilc.lc.omega.annotation.LexoAnnotationBuilder;
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
public class LexoTerm extends ADTAbstractAnnotation {

    private static final Logger log = LogManager.getLogger(LexoTerm.class);

    private LexoAnnotationBuilder lab = new LexoAnnotationBuilder();

    private Annotation<TextContent, LexoAnnotation> annotation;

    private LexoTerm() {
    }

    private LexoTerm(URI uri) throws ManagerAction.ActionException {
        init(uri);
    }

    private void init(URI uri) throws ManagerAction.ActionException {
        log.info("LexoTerm init()");
//        LexoAnnotationBuilder lab = new BaseAnnotationBuilder().URI(uri).text(text);
//        annotation = resourceManager.createAnnotation(
//                BaseAnnotation.class, bab);

        lab.URI(uri);
    }

    @Override
    protected Annotation<TextContent, LexoAnnotation> getAnnotation() {
        return this.annotation;
    }

    public static LexoTerm of(URI uri) throws ManagerAction.ActionException {
        return new LexoTerm(uri);
    }

    /**
     *
     * @param uriSense
     * @param textFragment
     * @param leftTextContext
     * @param rightTextContent
     * @return
     * @throws it.cnr.ilc.lc.omega.core.ManagerAction.ActionException
     * @throws IllegalStateException
     */
    public LexoTerm withParams(UriSense uriSense, TextFragment textFragment,
            LeftTextContext leftTextContext, RightTextContext rightTextContent) throws ManagerAction.ActionException {

        if (lab != null) {
            lab.uriSense(uriSense.getValue())
                    .textFragment(textFragment.getValue())
                    .leftContext(leftTextContext.getValue())
                    .rightContext(rightTextContent.getValue());
        } else {
            log.error("Unable to run withParams() without his builder");
            throw new IllegalStateException("Unable to run withParams() without his builder");
        }
        return this;
    }

    public LexoTerm build() throws ManagerAction.ActionException {
        if (null == annotation) {
            if (lab != null) {
                annotation = resourceManager.createAnnotation(LexoAnnotation.class, lab);
                lab = null;
            } else {
                log.error("Unable to build() LexoTerm since a builder is needed");
                throw new IllegalStateException("Unable to build() LexoTerm since a builder is needed");
            }
        }
        return this;
    }

    @Override
    public void save() throws ManagerAction.ActionException {
        log.info("save()");
        if (null != annotation) {
            resourceManager.saveAnnotation(annotation);
        } else {
            log.error("Unable to save() LexoTerm Annotation because of missing annotation. The build() method must be invoked before!");
            throw new IllegalStateException("Unable to save() LexoTerm Annotation because of missing annotation. The build() method must be invoked before!");
        }
    }

    public void addLocus(Text text, int start, int end) throws ManagerAction.ActionException, InvalidURIException {
        log.info("start " + start + ", end " + end);
        TextLocus locus = resourceManager.createLocus(text.getSource(), start, end, TextContent.class);
        resourceManager.updateAnnotationLocus(locus, annotation, TextContent.class);
///////        resourceManager.updateTextAnnotationLocus(text.getSource(), annotation, start, end);
    }

    @Override
    protected void setAnnotation(Annotation<?, ?> annotation) {
        this.annotation = (Annotation<TextContent, LexoAnnotation>) annotation;
    }

    @Override
    public boolean isRemovable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
