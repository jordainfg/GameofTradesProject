  package io.gameoftrades.ui;

import io.gameoftrades.student23.HandelaarImpl;

/**
 * Toont de visuele gebruikersinterface.
 * 
 * Let op: dit werkt alleen als je de WereldLader hebt geimplementeerd (Anders krijg je een NullPointerException).
 */
public class StudentUI {
        
	public static void main(String[] args) {
            MainGui.toon(new HandelaarImpl(), "/kaarten/voorbeeld-kaart.txt");
            
            //WereldLaderImpl test = new WereldLaderImpl();
            
            
            //test.MapTesting("/kaarten/westeros-kaart.txt");
            
            
	}
	
}