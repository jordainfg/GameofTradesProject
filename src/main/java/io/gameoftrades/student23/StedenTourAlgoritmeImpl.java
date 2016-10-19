/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student23;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author JordainGijsbertha
 */
public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {

    Debugger debug;
    private  List<Stad> StedenTourPad ;
    private  SnelstePadAlgoritmeImpl SnelstePimpl;
    private  Map<Stad, Double> map ;
    
    Map<List<Stad>, Double> beginstadmap =new HashMap<>();
     
     
   private  List<Stad> kortsteafstandenstad ;
   private  PadImpl pad;

    public StedenTourAlgoritmeImpl() {
        kortsteafstandenstad = new ArrayList<>();
        map= new HashMap<>();
        SnelstePimpl = new SnelstePadAlgoritmeImpl();
     
    }

    @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> steden) {

        debug.debugSteden(kaart, findNearest(kaart, steden));
        return findNearest(kaart, steden);
    }

    public List<Stad> findNearest(Kaart kaart, List<Stad> list) {

        for (int j = 0; j < list.size(); j++) {
            kortsteafstandenstad.clear();
             List<Stad> lijstvanSteden = new ArrayList(list);
            ListIterator<Stad> iterator = lijstvanSteden.listIterator();
            Stad startstad = lijstvanSteden.get(j);
            kortsteafstandenstad.add(startstad);
        double kosten = 0;
         lijstvanSteden.remove(j);

        while (iterator.hasNext()) {

            for (int i = 0; i < lijstvanSteden.size(); i++) {
                Stad get = lijstvanSteden.get(i);
                pad = SnelstePimpl.aStarAlgoritme(kaart, startstad.getCoordinaat(), get.getCoordinaat());
                double gvalue = pad.getPathGValue();
                map.put(get, gvalue);
             
            }
       
            startstad = MinGvalueCity().getKey();
            kosten = kosten + MinGvalueCity().getValue();
            kortsteafstandenstad.add(startstad);
            lijstvanSteden.remove(startstad);
            map.clear();

            //Print kosten
            if (lijstvanSteden.isEmpty()) {
                List<Stad> bestekorste = kortsteafstandenstad;
                beginstadmap.put(bestekorste,kosten);
                
            }
        }
    }
        System.out.println("Dit is de beste route de beste kosten en  de beste beginstad :"+bestebeginstad().getValue());
        StedenTourPad=bestebeginstad().getKey();
        return StedenTourPad;
    }

    private Entry<Stad, Double> MinGvalueCity() {
        Entry<Stad, Double> min = null;
        for (Entry<Stad, Double> entry : map.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        return min;
    }
   
     private Entry<List<Stad>, Double> bestebeginstad() {
        Entry<List<Stad>, Double> min = null;
        for (Entry<List<Stad>, Double> entry : beginstadmap.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        return min;
    }


    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }
}