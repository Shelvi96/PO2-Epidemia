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
public abstract class Agent {
    
    private final int id;
    private final int idTyp; // 1 - zwykły, 2 - towarzyski    
    private boolean zdrowy;
    private boolean odporny;
    private boolean żywy;
    ArrayList<Integer> sąsiedzi;
    Random rand;

    public Agent(int id, int idTyp, Random rand) {
        
        this.id = id;
        this.idTyp = idTyp;
        this.zdrowy = true;
        this.odporny = false;
        this.żywy = true;
        this.sąsiedzi = new ArrayList<>();
        this.rand = rand;
        
    }
    
    abstract ArrayList<Spotkanie> ustalSpotkania (double prawdSpotkania, int numerDnia, int liczbaDni, ArrayList<Agent> agenci, int seed);

    public void dodajSąsiada (int s) {
        sąsiedzi.add(s);
    }
    
    public boolean maSąsiada (int s) {
        return sąsiedzi.contains(s);
    }
    
    public boolean maSąsiadów () {
        return !sąsiedzi.isEmpty();
    }
    
    public void wypiszSąsiadów (PrintWriter writer) {
        
        for (Integer i: sąsiedzi) {
            writer.print(i + " ");
        }
        writer.println("");
        
    }
    
    public int getId () {
        return this.id;
    }
    
    public int getIdTyp () {
        return this.idTyp;
    }

    public boolean isZdrowy() {
        return zdrowy;
    }

    public void setZdrowy(boolean zdrowy) {
        this.zdrowy = zdrowy;
    }
    
    public boolean isOdporny() {
        return odporny;
    }

    public void setOdporny(boolean odporny) {
        this.odporny = odporny;
    }

    public boolean isŻywy() {
        return żywy;
    }

    public void setŻywy(boolean żywy) {
        this.żywy = żywy;
    }

    public ArrayList<Integer> getSąsiedzi() {
        return sąsiedzi;
    }

    public void setSąsiedzi(ArrayList<Integer> sąsiedzi) {
        this.sąsiedzi = sąsiedzi;
    }
    
}
