/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import it.cnr.ilc.lc.omega.entity.Source;
import it.cnr.ilc.lc.omega.entity.TextContent;

/**
 *
 * @author simone
 */
public final class WorkSource implements DTOValue<Source<TextContent>>{

    
    Source<TextContent> source;
    
    WorkSource() {
    }
 
    @Override
    public Source<TextContent> getValue() {
        return source;
    }

    @Override
    public <K extends DTOValue<Source<TextContent>>> K withValue(Source<TextContent> t) {
        this.source = t;
        return (K) this;
    }
    
}
