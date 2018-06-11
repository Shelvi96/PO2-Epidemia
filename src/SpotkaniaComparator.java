/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Epidemia;

import java.util.Comparator;

/**
 *
 * @author konrad
 */
public class SpotkaniaComparator implements Comparator<Spotkanie> {

    @Override
    // Ustawiamy spotkania w kolejności odbycia
    public int compare(Spotkanie s1, Spotkanie s2) {
        return s1.getDataSpotkania() - s2.getDataSpotkania();
    }
}

