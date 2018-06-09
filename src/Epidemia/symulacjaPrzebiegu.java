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

    private final obsługaWejścia input;
    private final GrafSpołeczności g;
    private int numerDnia;
    PriorityQueue<Spotkanie> kalendarzSpotkań;
    Random rand = new Random();
    
    private int ileZdrowych;
    private int ileOdpornych;
    private int ileChorych;
    
    public symulacjaPrzebiegu(obsługaWejścia input, GrafSpołeczności g) {
        
        this.input = input;
        this.g = g;
        this.numerDnia = 1;
        kalendarzSpotkań = new PriorityQueue<>(new SpotkaniaComparator());
        
    }
    
    public void symulujPrzebieg (PrintWriter writer) {
        
        ileZdrowych = input.getLiczbaAgentów() - 1;
        ileChorych = 1;
        ileOdpornych = 0;
                
        writer.println("");
        writer.println("# liczność w kolejnych dniach");
        writer.println(ileZdrowych + " " + ileChorych + " " + ileOdpornych);
        
        while (numerDnia <= input.getLiczbaDni()) {
            
            ArrayList<Wierzchołek> agenci = g.getWierzchołki();
            double prawd;
            
            // uśmiercamy lub leczymy agentów
            for (Wierzchołek w: agenci) {

                prawd = rand.nextDouble();

                // Jeśli agent jest żywy i chory to może umrzeć
                if (w.isŻywy() && !w.isZdrowy() && prawd <= input.getŚmiertelność()) {
                    w.setŻywy(false);
                    ileChorych--;
                }

                prawd = rand.nextDouble();
                
                // Jeśli agent jest żywy i chory to może wyzdrowieć. Nabiera wówczas odporności.
                if (w.isŻywy() && !w.isZdrowy() && prawd <= input.getPrawdWyzdrowienia()) {
                    w.setZdrowy(true);
                    w.setOdporny(true);
                    ileChorych--;
                    ileOdpornych++;
                }
                
            }
            
            // ustalamy spotkania
            for (Wierzchołek w: agenci) {
                
                if (w.isŻywy() && w.maSąsiadów()) {
                    
                    double prawdSpotkania = input.getPrawdSpotkania();
                    
                    if (!w.isZdrowy() && w.getIdTyp() == 1)
                        prawdSpotkania /= 2;
                    
                    prawd = rand.nextDouble();
                    
                    while (prawd <= prawdSpotkania) {
                        
                        Spotkanie s = new Spotkanie(w, numerDnia, input.getLiczbaDni(), agenci);
                        
                        // Jeśli udało się znaleźć towarzysza (łapię tu błąd z funkcji losujTowarzysza)
                        if (s.getTowarzysz() != -1) {
                            kalendarzSpotkań.add(new Spotkanie(w, numerDnia, input.getLiczbaDni(), agenci));
                        }
                        
                        prawd = rand.nextDouble();
                                
                    }
                    
                }
                
            }
            
            // odbywamy zaplanowane spotkania
            Spotkanie s = kalendarzSpotkań.peek();
            
            while (!kalendarzSpotkań.isEmpty() && s.getDataSpotkania() == numerDnia) {
                
                kalendarzSpotkań.poll();
                
                int a1 = s.getOsoba();
                int a2 = s.getTowarzysz();
                
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
