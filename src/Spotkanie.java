/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Epidemia;


/**
 *
 * @author konrad
 */
public class Spotkanie {
    
    private final int osoba; 
    private final int towarzysz;
    private final int dataSpotkania;

    public Spotkanie(int agent, int towarzysz, int dataSpotkania) {
        
        this.osoba = agent;
        this.towarzysz = towarzysz;
        this.dataSpotkania = dataSpotkania;
        
    }

    public int getOsoba() {
        return osoba;
    }

    public int getTowarzysz() {
        return towarzysz;
    }

    public int getDataSpotkania() {
        return dataSpotkania;
    }
  
}
