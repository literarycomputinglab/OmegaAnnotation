/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.annotation;

import it.cnr.ilc.lc.omega.entity.AbstractAnnotationBuilder;

/**
 *
 * @author simone
 */
public class AbbreviationBuilder extends AbstractAnnotationBuilder<AbbreviationType> {

    //abbreviazione sciolta
    private String abbrevationExpansion;

    //abbreviazione cosi' come compare nel testo
    private String abbrevation;

    public AbbreviationBuilder abbrevationExpansion(String abbrevationExpansion) {
        this.abbrevationExpansion = abbrevationExpansion;
        return this;
    }

    public AbbreviationBuilder abbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
        return this;
    }

    @Override
    public AbbreviationType build(AbbreviationType extension) {

        extension.setAbbrevation(abbrevation);
        extension.setAbbrevationExpansion(abbrevationExpansion);

        return extension;
    }

}
