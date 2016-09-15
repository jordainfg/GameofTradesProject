package io.gameoftrades.student23;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WereldLaderImpl implements WereldLader {

    private Wereld world;
    private Terrein[][] terrein; 

    @Override
    public Wereld laad(String resource) {
        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //

        Scanner in = new Scanner(this.getClass().getResourceAsStream(resource));

        String[] Line = in.nextLine().split(",");

        int w = Integer.parseInt(Line[0].trim());
        int h = Integer.parseInt(Line[1].trim());
        LinkedList<String> terreinLines = new LinkedList();

        for (int i = 0; i < h; i++) {
            terreinLines.add(in.nextLine().trim());
            if (terreinLines.get(i).matches(".*\\d.*")) {
                throw new IllegalArgumentException("Invalid Terrein value");
            }
        }

        int numberCity = Integer.parseInt(in.nextLine().trim());

        LinkedList<String> cityLines = new LinkedList();
        String[] cityName = new String[numberCity];
        int[] cityX = new int[numberCity];
        int[] cityY = new int[numberCity];

        for (int i = 0; i < numberCity; i++) {
            String cityLine = in.nextLine().trim();
            cityLines.add(cityLine);

            for (String city : cityLines) {
                String[] tempCity = city.split(",");
                cityX[i] = Integer.parseInt(tempCity[0]);
                cityY[i] = Integer.parseInt(tempCity[1]);
                cityName[i] = tempCity[2];
            }
        }

        int numberTrades = Integer.parseInt(in.nextLine().trim());

        LinkedList<String> tradesLines = new LinkedList();
        String[] tradeCityName = new String[numberTrades];
        String[] tradeType = new String[numberTrades];
        String[] tradeGoods = new String[numberTrades];
        int[] tradePrice = new int[numberTrades];

        for (int i = 0; i < numberTrades; i++) {
            tradesLines.add(in.nextLine().trim());

            for (String trades : tradesLines) {
                String[] tempTrades = trades.split(",");
                tradeCityName[i] = tempTrades[0];
                if (tempTrades[1].equals("VRAAGT") || tempTrades[1].equals("BIEDT")) {

                    tradeType[i] = tempTrades[1];
                } else {
                    throw new IllegalArgumentException("Invalid HandelsType value");
                }
                tradeGoods[i] = tempTrades[2];
                tradePrice[i] = Integer.parseInt(tempTrades[3]);

            }
        }

        List<Stad> steden = new ArrayList();
        List<Handel> handels = new ArrayList();

        //Coordinaat coordinaat = null;
        for (int i = 0; i < numberCity; i++) {
            if (cityX[i] == 0 && cityY[i] == 0) {
                throw new IllegalArgumentException("Invalid coordinate for city " + cityName[i]);
            } else {
                steden.add(new Stad(Coordinaat.op(cityX[i], cityY[i]), cityName[i]));
            }
        }

        for (int i = 0; i < numberTrades; i++) {
            handels.add(new Handel(GetCity(tradeCityName[i], steden), HandelType.valueOf(tradeType[i]), new Handelswaar(tradeGoods[i]), tradePrice[i]));
        }

        Kaart kaart = new Kaart(w, h);
        Markt markt = new Markt(handels);
        terrein = new Terrein[w][h];

        String[][] mapCharacters = new String[w][h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                String[] temp = terreinLines.get(i).split("");
                if (temp.length < w) {
                    throw new IllegalArgumentException("Invalid width value");
                } else {
                    mapCharacters[j][i] = temp[j];
                }
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                terrein[j][i] = new Terrein(kaart, Coordinaat.op(j, i), TerreinType.valueOf(getTerreinType(mapCharacters[j][i])));
            }
        }

        world = new Wereld(kaart, steden, markt);

        return world;
    }

    public void MapTesting(String resource) {
        Scanner in = new Scanner(this.getClass().getResourceAsStream(resource));

        while (in.hasNext()) {
            String temp = in.nextLine();
            System.out.println(temp);
        }

        /*
        String[] Line = in.nextLine().split(",");
        
        int w = Integer.parseInt(Line[0]);
        int h = Integer.parseInt(Line[1]);
        LinkedList<String> terreinLines = new LinkedList();
        
        for (int i = 0; i < h; i++) {
                System.out.println(in.next());
                terreinLines.add(in.next());
        }
        
        int numberCity = Integer.parseInt(in.next());
        
        LinkedList<String> cityLines = new LinkedList();
        String[] cityName = new String[numberCity];
        int[] cityX = new int[numberCity];
        int[] cityY = new int[numberCity];
        
        for (int i = 0; i < numberCity; i++) {
                cityLines.add(in.next());
                
                for (String city : cityLines){
                    String[] tempCity = city.split(",");
                    cityX[i] = Integer.parseInt(tempCity[0]);
                    cityY[i] = Integer.parseInt(tempCity[1]);
                    cityName[i] = tempCity[2];
                }
        }
        
        int numberTrades = Integer.parseInt(in.next());
        
        LinkedList<String> tradesLines = new LinkedList();
        String[] tradeCityName = new String[numberTrades]; 
        String[] tradeType = new String[numberTrades]; 
        String[] tradeGoods = new String[numberTrades]; 
        int[] tradePrice = new int[numberTrades];
        
        for (int i = 0; i < numberTrades; i++) {
                tradesLines.add(in.next());
                
                for (String trades : tradesLines){
                    String[] tempTrades = trades.split(",");
                    tradeCityName[i] = tempTrades[0];
                    tradeType[i] = tempTrades[1];
                    tradeGoods[i] = tempTrades[2];
                    tradePrice[i] = Integer.parseInt(tempTrades[3]);
                }
        }
        
        
        Kaart kaart = new Kaart(w,h);
        List<Stad> steden = new ArrayList();
        List<Handel> handels = new ArrayList();
        
        //Coordinaat coordinaat = null;
        
        for (int i = 0; i < numberCity; i++) {
            steden.add(new Stad(Coordinaat.op(cityX[i], cityY[i]),cityName[i]));
        }
        
        for (int i = 0; i < numberTrades; i++) {
            handels.add(new Handel(GetCity(tradeCityName[i],steden), HandelType.valueOf(tradeType[i]), new Handelswaar(tradeGoods[i]), tradePrice[i]));
        }
        
        
        Markt markt = new Markt(handels);
        
        Wereld world = new Wereld(kaart, steden, markt);*/
    }

    private Stad GetCity(String cityName, List<Stad> steden) {
        for (int j = 0; j < steden.size(); j++) {
            if (steden.get(j).getNaam().equals(cityName)) {
                return steden.get(j);
            }
        }

        return null;
    }

    private String getTerreinType(String mapChar) {
        String terreinType = "";

        switch (mapChar) {
            case "Z":
                terreinType = "ZEE";
                break;
            case "R":
                terreinType = "BERG";
                break;
            case "B":
                terreinType = "BOS";
                break;
            case "G":
                terreinType = "GRASLAND";
                break;
            case "S":
                terreinType = "STAD";
                break;
        }

        return terreinType;
    }
    public Terrein[][] getTerrein(){
        return terrein; 
    }

}
