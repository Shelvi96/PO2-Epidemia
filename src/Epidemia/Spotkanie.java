/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Epidemia;

import java.util.*;

/**
 *
 * @author konrad
 */
public class Spotkanie {
    
    private final int osoba; 
    private final int towarzysz;
    private final int dataSpotkania;
    Random rand = new Random();

    public Spotkanie(Wierzchołek w, int bieżącyDzień, int liczbaDni, ArrayList<Wierzchołek> agenci) {
        
        this.osoba = w.getId();
        this.towarzysz = losujTowarzysza(w, agenci);
        this.dataSpotkania = losujDatę(bieżącyDzień, liczbaDni);
        
    }
    
    private int losujTowarzysza(Wierzchołek w, ArrayList<Wierzchołek> agenci) {

        // Wrzucamy kandydatów na towarzyszy do seta, by dostać zestaw bez powtórzeń
        HashSet<Integer> kandydaciNaTowarzysza = new HashSet<>();
        
        // Jeśli agent jest zwykły
        if ( w.getIdTyp() == 1 || (w.getIdTyp() == 2 && !agenci.get(w.getId()-1).isZdrowy())) {
            
            for (int i: w.getSąsiedzi()) {
                // Jeśli agent jeszcze żyje, to może być towarzyszem
                if (agenci.get(i-1).isŻywy()) {
                    kandydaciNaTowarzysza.add(i);
                }
                
            }
            
        }
        // Jeśli agent jest towarzyski
        else {
            
            for (int i: w.getSąsiedzi()) {
                // Przechodzimy po wszystkich sąsiadach
                for (int j: agenci.get(i-1).getSąsiedzi()) {
                    // Jeśli agent jeszcze żyje, to może być towarzyszem
                    if (agenci.get(j-1).isŻywy()) {
                        kandydaciNaTowarzysza.add(j);
                    }
                    
                }
                // Jeśli agent jeszcze żyje, to może być towarzyszem
                if (agenci.get(i-1).isŻywy()) {
                    kandydaciNaTowarzysza.add(i);
                }
                
            }
            
        }
        
        ArrayList<Integer> kandydaciBezPowtórzeń = new ArrayList<>();
        kandydaciBezPowtórzeń.addAll(kandydaciNaTowarzysza);
        
        // Jeśli są jacyś kandydaci na towarzysza, to losujemy jednego z nich
        if (!kandydaciBezPowtórzeń.isEmpty()) {
            
            int numerSzczęśliwca = rand.nextInt(kandydaciBezPowtórzeń.size());
        
            return kandydaciBezPowtórzeń.get(numerSzczęśliwca);
            
        }
        // Jak nie ma żadnych, to zajmiemy się tym później
        return -1;
        
    }
    
    private int losujDatę (int bieżącyDzień, int liczbaDni) {
        int ret = rand.nextInt(liczbaDni - bieżącyDzień + 1) + bieżącyDzień;
        return ret;
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
