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
public class AgentZwykly extends Agent {

    public AgentZwykly(int id, Random rand) {
        super(id, 1, rand);
    }

    @Override
    ArrayList<Spotkanie> ustalSpotkania(double prawdSpotkania, int numerDnia, int liczbaDni, ArrayList<Agent> agenci) {
        
        double prawd = rand.nextDouble();
        ArrayList<Spotkanie> Spotkania = new ArrayList<>();
        
        // Jeśli agent zwykły jest chory, to spotyka się dwa razy rzadziej
        if (!this.isZdrowy())
            prawdSpotkania /= 2;
                
        // Losujemy spotkania, dopóki agent chce się spotykać
        while (prawd <= prawdSpotkania) {
            
            HashSet<Integer> kandydaciNaTowarzyszy = new HashSet<>();
            
            // Wybieramy kandydatów na spotkanie tylko spośród sąsiadów agenta
            for (int i: this.sąsiedzi) {
                // ... dodajemy tylko sąsiadów
                if (agenci.get(i-1).isŻywy()) {
                    kandydaciNaTowarzyszy.add(i);
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
