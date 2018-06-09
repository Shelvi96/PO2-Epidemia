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
    
    private ArrayList<Wierzchołek> wierzchołki;
    private final int liczbaKrawędzi;
    private final int liczbaAgentów;
    private final double prawdTowarzyski;
    Random rand = new Random();

    public GrafSpołeczności(int liczbaKrawędzi, int liczbaAgentów, double prawdTowarzyski) {
        
        this.liczbaKrawędzi = liczbaKrawędzi;
        this.liczbaAgentów = liczbaAgentów;
        this.prawdTowarzyski = prawdTowarzyski;
        this.wierzchołki = wygenerujGraf();
        
    }
    
    private ArrayList<Wierzchołek> wygenerujGraf () {
        
        wierzchołki = new ArrayList<>();
        
        stwórzAgentów();
        
        int licznik = 0;
        
        while (licznik < liczbaKrawędzi) {
        
            int n = rand.nextInt(liczbaAgentów) + 1;
            int m = rand.nextInt(liczbaAgentów) + 1;
            
            // Krawędź nie może prowadzić z wierzchołka do niego samego
            while (n == m)
                m = rand.nextInt(liczbaAgentów) + 1;
            
            Wierzchołek w1 = wierzchołki.get(n-1);
            Wierzchołek w2 = wierzchołki.get(m-1);
            
            // Jeśli jeszcze nie dodaliśmy takiej krawędzi do grafu, to dodajemy ją
            if (!w1.maSąsiada(m) && !w2.maSąsiada(n)) {
                w1.dodajSąsiada(m);
                w2.dodajSąsiada(n);
                licznik++;
            }
            
        }
        
        zaraźPechowca();
        
        return wierzchołki;
        
    }
    
    private void stwórzAgentów () {
        
        for (int i = 1; i <= liczbaAgentów; ++i) {
            wierzchołki.add(new Wierzchołek(i, prawdTowarzyski));
        }
        
    }
    
    private void zaraźPechowca () {
        
        int n = rand.nextInt(liczbaAgentów) + 1;
        wierzchołki.get(n-1).setZdrowy(false);
        
    }
    
    public void wypiszAgentów (PrintWriter writer) {
        
        writer.println("# agenci jako: id typ lub id* typ dla chorego");
        
        for (Wierzchołek w: wierzchołki) {
            
            writer.print(w.getId());
            
            if (!w.isZdrowy())
                writer.print("*");
            writer.print(" ");
            
            if (w.getIdTyp() == 1)
                writer.println("zwykły");
            else
                writer.println("towarzyski");
            
        }
        
    }
    
    public void wypiszGraf (PrintWriter writer) {
        
        writer.println("# graf");
        
        for (Wierzchołek w: wierzchołki) {
            
            writer.print(w.getId() + " ");
            w.wypiszSąsiadów(writer);
            
        }
    }
    
    public ArrayList<Wierzchołek> getWierzchołki () {
        return wierzchołki;
    }
    
}
