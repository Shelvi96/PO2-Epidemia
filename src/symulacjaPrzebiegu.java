/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Epidemia;

import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author konrad
 */
public class symulacjaPrzebiegu {

    Random rand;
    private final obslugaWejscia input;
    private final GrafSpolecznosci g;
    private int numerDnia;
    PriorityQueue<Spotkanie> kalendarzSpotkań;
    
    private int ileZdrowych;
    private int ileOdpornych;
    private int ileChorych;
    
    public symulacjaPrzebiegu(obslugaWejscia input, GrafSpolecznosci g, Random rand) {
        
        this.rand = rand;
        this.input = input;
        this.g = g;
        this.numerDnia = 1;
        this.kalendarzSpotkań = new PriorityQueue<>(new SpotkaniaComparator());
        
    }
    
    public void symulujPrzebieg (PrintWriter writer) {
        
        ileZdrowych = input.getLiczbaAgentów() - 1;
        ileChorych = 1;
        ileOdpornych = 0;
                
        writer.println("");
        writer.println("# liczność w kolejnych dniach");
        writer.println(ileZdrowych + " " + ileChorych + " " + ileOdpornych);
        
        int liczbaDni = input.getLiczbaDni();
        
        while (numerDnia <= liczbaDni) {
            
            ArrayList<Agent> agenci = g.getWierzchołki();
            double prawd;
            
            // uśmiercamy lub leczymy agentów
            for (Agent a: agenci) {

                prawd = rand.nextDouble();

                // Jeśli agent jest żywy i chory to może umrzeć
                if (a.isŻywy() && !a.isZdrowy() && prawd <= input.getŚmiertelność()) {
                    a.setŻywy(false);
                    ileChorych--;
                }

                prawd = rand.nextDouble();
                
                // Jeśli agent jest żywy i chory to może wyzdrowieć. Nabiera wówczas odporności.
                if (a.isŻywy() && !a.isZdrowy() && prawd <= input.getPrawdWyzdrowienia()) {
                    a.setZdrowy(true);
                    a.setOdporny(true);
                    ileChorych--;
                    ileOdpornych++;
                }
                
            }

            // ustalamy spotkania
            for (Agent a: agenci) {
                if (a.isŻywy() && a.maSąsiadów()) {
                    ArrayList<Spotkanie> spotkania = a.ustalSpotkania(input.getPrawdSpotkania(), numerDnia, input.getLiczbaDni(), agenci);
                    kalendarzSpotkań.addAll(spotkania);
                }
            }

            // odbywamy zaplanowane spotkania
            Spotkanie s = kalendarzSpotkań.peek();
            
            while (!kalendarzSpotkań.isEmpty() && s.getDataSpotkania() == numerDnia) {
                
                kalendarzSpotkań.poll();
                
                int a1 = s.getOsoba();
                int a2 = s.getTowarzysz();
                
                // Jeśli w między czasie agenci nie zostali pokonani przez epidemię, to spotkanie odbywa się
                if (agenci.get(a1-1).isŻywy() && agenci.get(a2-1).isŻywy()) {
                    
                    prawd = rand.nextDouble();
                    // Agent a1 zaraża nieodpornego agenta a2                
                    if (agenci.get(a2-1).isZdrowy() && !agenci.get(a1-1).isZdrowy() && !agenci.get(a2-1).isOdporny() && prawd <= input.getPrawdZarażenia()) {
                        
                        agenci.get(a2-1).setZdrowy(false);
                        ileZdrowych--;
                        ileChorych++;
                        
                    }

                    prawd = rand.nextDouble();
                    // Agent a2 zaraża nieodpornego agenta a1
                    if (agenci.get(a1-1).isZdrowy() && !agenci.get(a2-1).isZdrowy() && !agenci.get(a1-1).isOdporny() && prawd <= input.getPrawdZarażenia()) {
                        
                        agenci.get(a1-1).setZdrowy(false);
                        ileZdrowych--;
                        ileChorych++;
                    }
                }
                
                s = kalendarzSpotkań.peek();
                
            }
            
            writer.println(ileZdrowych + " " + ileChorych + " " + ileOdpornych);
            
            numerDnia++;
            
        }
        
    }
    
}
