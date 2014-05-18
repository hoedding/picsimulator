package picsim.mvc.main;

import picsim.mvc.controller.PicSimController;
import picsim.mvc.model.PicSimModel;
import picsim.mvc.model.serial.PicSimSerialController;
import picsim.mvc.view.PicSimView;

public class Main {
	static PicSimController controller;
	static PicSimModel model;
	static PicSimView view;
	
	/**
     * Diese Klasse wird nur dazu benutzt alle nötigen
     * Komponenten zu Initialisieren und die erste
     * View anzuzeigen
     */
	public static void main(String[] args) {
		model = new PicSimModel();
		view = new PicSimView();
		 controller = new PicSimController(view, model);
		 
		view.setVisible(true);
		
	}

}
