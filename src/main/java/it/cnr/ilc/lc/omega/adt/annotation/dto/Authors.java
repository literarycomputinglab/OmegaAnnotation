/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.omega.adt.annotation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author simone
 */
public final class Authors implements DTOValue<List<String>> {

    String[] authors;

    Authors() {
    }

    @Override
    @JsonIgnore
    public List<String> getValue() {
        return Arrays.asList(authors);
    }

    /**
     * 
     * @param <K>
     * @param t rappresenta la lista dei nome degli autori. Ogni elemento deve essere della forma "nome,cognome" es. angelo mario,del grosso 
     * @return 
     */
    @Override
    public <K extends DTOValue<List<String>>> K withValue(List<String> t) {
        authors = t.toArray(new String[0]);
        return (K) this;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    
 
}
