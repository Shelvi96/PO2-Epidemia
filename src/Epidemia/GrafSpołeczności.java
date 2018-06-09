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
public class GrafSpołeczności {
    
    Random rand;
    private final int liczbaKrawędzi;
    private final int liczbaAgentów;
    private final double prawdTowarzyski;
    private ArrayList<Agent> wierzchołki;

    public GrafSpołeczności(int liczbaKrawędzi, int liczbaAgentów, double prawdTowarzyski, int seed, Random rand) {
        
        this.rand = rand;
        this.liczbaKrawędzi = liczbaKrawędzi;
        this.liczbaAgentów = liczbaAgentów;
        this.prawdTowarzyski = prawdTowarzyski;
        this.wierzchołki = wygenerujGraf();
        
    }
    
    private ArrayList<Agent> wygenerujGraf () {
        
        wierzchołki = new ArrayList<>();
        
        stwórzAgentów();
        
        int licznik = 0;
        
        while (licznik < liczbaKrawędzi) {
        
            int n = rand.nextInt(liczbaAgentów) + 1;
            int m = rand.nextInt(liczbaAgentów) + 1;
            
            // Krawędź nie może prowadzić z wierzchołka do niego samego
            while (n == m)
                m = rand.nextInt(liczbaAgentów) + 1;
            
            Agent a1 = wierzchołki.get(n-1);
            Agent a2 = wierzchołki.get(m-1);
            
            // Jeśli jeszcze nie dodaliśmy takiej krawędzi do grafu, to dodajemy ją
            if (!a1.maSąsiada(m) && !a2.maSąsiada(n)) {
                a1.dodajSąsiada(m);
                a2.dodajSąsiada(n);
                licznik++;
            }
            
        }
        
        zaraźPechowca();
        
        return wierzchołki;
        
    }
    
    private void stwórzAgentów () {
        
        for (int i = 1; i <= liczbaAgentów; ++i) {
            
            double prawd = rand.nextDouble();
            
            if (prawd <= prawdTowarzyski){
                wierzchołki.add(new AgentTowarzyski(i, rand));
            }
            else {
                wierzchołki.add(new AgentZwykły(i, rand));
            }
            
        }
        
    }
    
    private void zaraźPechowca () {
        
        int n = rand.nextInt(liczbaAgentów) + 1;
        wierzchołki.get(n-1).setZdrowy(false);
        
    }
    
    public void wypiszAgentów (PrintWriter writer) {
        
        writer.println("# agenci jako: id typ lub id* typ dla chorego");
        
        for (Agent a: wierzchołki) {
            
            writer.print(a.getId());
            
            if (!a.isZdrowy())
                writer.print("*");
            writer.print(" ");
            
            if (a.getIdTyp() == 1)
                writer.println("zwykły");
            else
                writer.println("towarzyski");
            
        }
        
    }
    
    public void wypiszGraf (PrintWriter writer) {
        
        writer.println("# graf");
        
        for (Agent a: wierzchołki) {
            
            writer.print(a.getId() + " ");
            a.wypiszSąsiadów(writer);
            
        }
    }
    
    public ArrayList<Agent> getWierzchołki () {
        return wierzchołki;
    }
    
}
