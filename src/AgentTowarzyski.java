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
public class AgentTowarzyski extends Agent {

    public AgentTowarzyski(int id, Random rand) {
        super(id, 2, rand);
    }
    
    @Override
    ArrayList<Spotkanie> ustalSpotkania(double prawdSpotkania, int numerDnia, int liczbaDni, ArrayList<Agent> agenci) {
        
        double prawd = rand.nextDouble();
        ArrayList<Spotkanie> Spotkania = new ArrayList<>();
        
        // Losujemy spotkania, dopóki agent chce się spotykać
        while (prawd <= prawdSpotkania) {
            
            HashSet<Integer> kandydaciNaTowarzyszy = new HashSet<>();
            
            // Gdy agent jest towarzystki i zdrowy ...
            if (this.isZdrowy()) {
                for (int i: this.sąsiedzi) {
                    // ... dodajemy sąsiadów obecnego sąsiada ...
                    for (int j: agenci.get(i-1).getSąsiedzi()) {
                        if (agenci.get(j-1).isŻywy()) {
                            kandydaciNaTowarzyszy.add(j);
                        }
                    }
                    // ... oraz samego sąsiada.
                    if (agenci.get(i-1).isŻywy()) {
                        kandydaciNaTowarzyszy.add(i);
                    }
                    
                }
            }
            // Gdy zaś agent jest towarzyski i chory ...
            else {
                for (int i: this.sąsiedzi) {
                    // ... dodajemy tylko sąsiadów
                    if (agenci.get(i-1).isŻywy()) {
                        kandydaciNaTowarzyszy.add(i);
                    }
                }
            }
            
            ArrayList<Integer> kandydaci = new ArrayList<>();
            kandydaci.addAll(kandydaciNaTowarzyszy);
            
            int numerSzczęśliwca = rand.nextInt(kandydaci.size());
            int dzieńSpotkania = rand.nextInt(liczbaDni - numerDnia + 1) + numerDnia;
            
            Spotkania.add(new Spotkanie(this.getId(), kandydaci.get(numerSzczęśliwca), dzieńSpotkania));
            
            prawd = rand.nextDouble();
            
        }
        
        return Spotkania;
        
    }
    
}
