
import Epidemia.GrafSpołeczności;
import Epidemia.obsługaWejścia;
import Epidemia.symulacjaPrzebiegu;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author konrad
 */
public class SymulatorEpidemii {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Wczytuję pliki
        obsługaWejścia input = new obsługaWejścia("default.properties", "simulation-conf.xml");
        
        final int liczbaKrawędzi = input.getLiczbaAgentów() * input.getŚrZnajomych() / 2;
        
        Random rand = new Random(input.getSeed());
        
        // Generuję graf
        GrafSpołeczności g = new GrafSpołeczności(liczbaKrawędzi, input.getLiczbaAgentów(), input.getPrawdTowarzyski(), input.getSeed(), rand); 
                
        try {
            
            PrintWriter writer = new PrintWriter(input.getPlikZRaportem());
            
            input.wypiszPlikKonfiguracyjny(writer);
            writer.println("");
            g.wypiszAgentów(writer);
            writer.println("");
            g.wypiszGraf(writer);

            // Tworzę symulację dla zadanej konfiguracji i wygenerowanego grafu
            symulacjaPrzebiegu s = new symulacjaPrzebiegu(input, g, rand);
            s.symulujPrzebieg(writer);
            
            writer.close();
            
        } catch (FileNotFoundException ex) {
            
            System.err.println("Niedozwolona wartość " + input.getPlikZRaportem() + " dla klucza plikZRaportem");
            System.exit(1);
            
        }
        
    }
}
