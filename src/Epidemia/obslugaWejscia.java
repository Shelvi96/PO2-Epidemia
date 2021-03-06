/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Epidemia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 *
 * @author konrad
 */
public class obslugaWejscia {
    
    private final Properties defaultProps;
    private final Properties simulationConf;
    private final String[] klucze;
    
    private final long seed;
    private final int liczbaAgentów;
    private final double prawdTowarzyski;
    private final double prawdSpotkania;
    private final double prawdZarażenia;
    private final double prawdWyzdrowienia;
    private final double śmiertelność;
    private final int liczbaDni;
    private final int śrZnajomych;
    private final String plikZRaportem;

    public obslugaWejscia() {
        
        this.defaultProps = new Properties();
        this.simulationConf = new Properties();
        this.klucze = new String[]{"seed", "liczbaAgentów", "prawdTowarzyski", "prawdSpotkania", "prawdZarażenia", "prawdWyzdrowienia",
            "śmiertelność", "liczbaDni", "śrZnajomych", "plikZRaportem"};
        
        wczytajPliki();
        
        sprawdźPoprawnośćPlikuProps(defaultProps);
        sprawdźPoprawnośćPlikuConf(simulationConf);
        
        // UWAGA! Poprawność ścieżki plikZRaportem zostanie sprawdzana przy tworzeniu raportu.
        
        this.seed = Long.parseLong(simulationConf.getProperty("seed", defaultProps.getProperty("seed")));
        this.liczbaAgentów = Integer.parseInt(simulationConf.getProperty("liczbaAgentów", defaultProps.getProperty("liczbaAgentów")));
        this.prawdTowarzyski = Double.parseDouble(simulationConf.getProperty("prawdTowarzyski", defaultProps.getProperty("prawdTowarzyski")));
        this.prawdSpotkania = Double.parseDouble(simulationConf.getProperty("prawdSpotkania", defaultProps.getProperty("prawdSpotkania")));
        this.prawdZarażenia = Double.parseDouble(simulationConf.getProperty("prawdZarażenia", defaultProps.getProperty("prawdZarażenia")));
        this.prawdWyzdrowienia = Double.parseDouble(simulationConf.getProperty("prawdWyzdrowienia", defaultProps.getProperty("prawdWyzdrowienia")));
        this.śmiertelność = Double.parseDouble(simulationConf.getProperty("śmiertelność", defaultProps.getProperty("śmiertelność")));
        this.liczbaDni = Integer.parseInt(simulationConf.getProperty("liczbaDni", defaultProps.getProperty("liczbaDni")));
        this.śrZnajomych = Integer.parseInt(simulationConf.getProperty("śrZnajomych", defaultProps.getProperty("śrZnajomych")));
        this.plikZRaportem = simulationConf.getProperty("plikZRaportem", defaultProps.getProperty("plikZRaportem"));
        
    }

    private void wczytajPliki () {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ");
        String appConfigPath = rootPath + "default.properties";
        
        try (FileInputStream stream = new FileInputStream(appConfigPath);
            Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
            defaultProps.load(reader);
        } catch (MalformedInputException e) {
            System.err.println("default.properties nie jest tekstowy");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Brak pliku default.properties");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Błąd pliku default.properties");
            System.exit(1);
        }
        
        rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ");
        String customConfigPath = rootPath + "simulation-conf.xml";
        
        try (FileInputStream stream = new FileInputStream(customConfigPath)) {
            simulationConf.loadFromXML(stream);
        } catch (InvalidPropertiesFormatException e) {
            System.err.println("simulation-conf.xml nie jest XML");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Brak pliku simulation-conf.xml");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Błąd pliku simulation-conf.xml");
            System.exit(1);
        }
        
    }
    
    private void sprawdźPoprawnośćPlikuProps(Properties props) {
        
        for(String klucz: klucze) {

            if(props.getProperty(klucz) == null) {
                System.err.println("Brak klucza " + klucz);
                System.exit(1);
            }
            else if(props.getProperty(klucz).equals("")) {
                System.err.println("Brak wartości dla klucza " + klucz);
                System.exit(1);
            }
            else {
                sprawdźPoprawnośćKlucza(props, klucz);
                sprawdźZakresKlucza(props, klucz);
            }
            
        }
           
    }
    
    private void sprawdźPoprawnośćPlikuConf(Properties conf) {

        for(String klucz: klucze) {
            // Jeśli klucz istnieje, to sprawdzam czy nadana wartość jest prawidłowa
            if(conf.getProperty(klucz) != null) {
                sprawdźPoprawnośćKlucza(conf, klucz);
                sprawdźZakresKlucza(conf, klucz);
            }
        }
        
    }
    
    private void sprawdźPoprawnośćKlucza (Properties props, String klucz) {
        
        if (klucz.equals("seed")) {
            
            try {
                Long.parseLong(props.getProperty(klucz));
            }
            catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        else if (klucz.equals("liczbaAgentów") || klucz.equals("liczbaDni") || klucz.equals("śrZnajomych")) {
            
            try {
                Integer.parseInt(props.getProperty(klucz));
            }
            catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        else if (!klucz.equals("plikZRaportem")) {

            try {
                Double.parseDouble(props.getProperty(klucz));
            }
            catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        
    }
    
    private void sprawdźZakresKlucza (Properties props, String klucz) {
        
        if (klucz.equals("liczbaAgentów")) {
                            
            int wart =  Integer.parseInt(props.getProperty(klucz));

            if (wart < 1 || wart > 1000000) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        else if ( (klucz.equals("prawdTowarzyski") || klucz.equals("prawdSpotkania") || klucz.equals("prawdZarażenia") ||
                klucz.equals("prawdWyzdrowienia") || klucz.equals("śmiertelność"))) {
            
            double wart =  Double.parseDouble(props.getProperty(klucz));
            
            if (wart < 0 || wart > 1 || (klucz.equals("prawdSpotkania") && wart == 1)) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        else if (klucz.equals("liczbaDni")) {
            
            int wart =  Integer.parseInt(props.getProperty(klucz));
            
            if (wart < 0 || wart > 1000) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
        else if (klucz.equals("śrZnajomych")) {
            
            int wart =  Integer.parseInt(props.getProperty(klucz));
            int lAgent = Integer.parseInt(props.getProperty("liczbaAgentów", defaultProps.getProperty("liczbaAgentów")));
            
            if (wart < 0 || wart > lAgent - 1) {
                System.err.println("Niedozwolona wartość " + props.getProperty(klucz) + " dla klucza " + klucz);
                System.exit(1);
            }
            
        }
    }
    
    public void wypiszPlikKonfiguracyjny (PrintWriter writer) {
        
        writer.println("seed=" + seed);
        writer.println("liczbaAgentów=" + liczbaAgentów);
        writer.println("prawdTowarzyski=" + prawdTowarzyski);
        writer.println("prawdSpotkania=" + prawdSpotkania);
        writer.println("prawdZarażenia=" + prawdZarażenia);
        writer.println("prawdWyzdrowienia=" + prawdWyzdrowienia);
        writer.println("śmiertelność=" + śmiertelność);
        writer.println("liczbaDni=" + liczbaDni);
        writer.println("śrZnajomych: " + śrZnajomych);
        writer.println("plikZRaportem=" + plikZRaportem);
        
    }
    
    public long getSeed () {
        return this.seed;
    }
    
    public int getLiczbaAgentów () {
        return this.liczbaAgentów;
    }
    
    public double getPrawdTowarzyski () {
        return this.prawdTowarzyski;
    }
    
    public double getPrawdSpotkania () {
        return this.prawdSpotkania;
    }
    
    public double getPrawdZarażenia () {
        return this.prawdZarażenia;
    }
    
    public double getPrawdWyzdrowienia () {
        return this.prawdWyzdrowienia;
    }
    
    public double getŚmiertelność () {
        return this.śmiertelność;
    }
    
    public int getLiczbaDni () {
        return this.liczbaDni;
    }
    
    public int getŚrZnajomych () {
        return this.śrZnajomych;
    }
    
    public String getPlikZRaportem () {
        return this.plikZRaportem;
    }
    
}
