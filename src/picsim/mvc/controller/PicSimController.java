package picsim.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import picsim.mvc.model.PicSimModel;
import picsim.mvc.view.PicSimCalculatorView;
import picsim.mvc.view.PicSimTrisView;
import picsim.mvc.view.PicSimView;

public class PicSimController {
	private PicSimView view;
	private PicSimModel model;
	private boolean running;

	public PicSimController(PicSimView view, PicSimModel model) {
		this.view = view;
		this.model = model;
		this.running = false;
		initialize_register_array();
		addListener();
		ReloadGUI();
	}

	private void addListener() {
		view.setNextStepListener(new NextStepListener());
		view.setStartProgramListener(new StartProgramListener());
		view.setPauseListener(new PauseListener());
		view.setSpeichernRegisterListener(new SpeichernRegisterListener());
		view.setOpenFileListener(new OpenFileListener());
		view.setSliderChangeListener(new SliderChangeListener());
		view.setOpenCalculatorListener(new OpenCalculatorListener());
		view.setOpenTrisAListener(new OpenTrisAListener());
		view.setOpenTrisBListener(new OpenTrisBListener());
		view.setOpenTrisCListener(new OpenTrisCListener());
	}

	public boolean get_running() {
		return running;
	}

	public void set_running(boolean s) {

		running = s;
	}

	public int get_Frequency() {
		return model.get_takt();
	}

	/* ARRAY FüR DAS REGISTER */
	public void initialize_register_array() {

		int m;
		for (m = 0; m < 256; m++) {

			model.setRegisterEntry(m, 0);
		}

	}

	public void start_programm(int takt) throws InterruptedException {
		/*
		 * Überprüfung ob Ende des Programms erreicht wird kann am Ende gelöscht
		 * werden !!
		 */// TODO löschen
		if (model.getProgrammCounter() == model.code_list.size()) {
			Thread.sleep(takt);
			model.setProgramCounter(0);
			start_function();
		} else {
			Thread.sleep(takt);
			start_function();
		}
	}

	/*
	 * ######## Führt für jedes in der code_list enthaltene Elemente die
	 * Funktion what_to_do() aus #######
	 */
	public void start_function() throws InterruptedException {
		model.what_to_do(model.code_list.get(model.getProgrammCounter()));
		model.setProgramCounter(model.getProgrammCounter() + 1);
		ReloadGUI();
	}

	public void ReloadGUI() {
		/* Erweiterungen aktualisieren */
		ReloadElements();
		/* aktuell ausgeführten Code markieren */
		view.select_code(model.getProgrammCounter());
		/* W-Register in GUI setzen */
		view.set_W_value("1");
		// frame.lbl_wreg_value.setText(String.valueOf(w_register).toString());
		/* Aktualisieren der Tabelle mit den Werten aus Register_Array */
		ReloadTable();
		/* z überprüfen ob true/false */
		if (model.get_Z() == 1) {

			view.set_Z_value(String.valueOf(model.get_Z()));
		} else {

			view.set_Z_value(String.valueOf(model.get_Z()));
		}

		/* PC setzen in Frame */
		view.set_PC_value(String.valueOf(model.getProgrammCounter()));

		/* C setzen in Frame */
		view.set_C_value(String.valueOf(model.get_C()));

		/* Status setzen */
		view.set_Status_value(String.valueOf(model.get_status()));

		/* DC setzen */
		view.set_DC_value(String.valueOf(model.get_DC()));

	}

	public void ReloadElements() {
		// TODO Elemente aktualisieren LEDs etc
	}

	public void ReloadTable() {
		int m1 = 0, n1 = 0, t1 = 0;
		while (m1 < 256) {
			while (t1 < 8) {
				/* Tabelle bekommt Werte aus Array zugewiesen */
				view.set_TableEntry(model.getRegisterEntry(m1), n1, t1);
				t1++;
				m1++;
			}
			t1 = 0;
			n1++;
		}
	}

	class NextStepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			run_one_function();
		}
	}

	public void run_one_function() {

		Thread t1 = new Thread(new PicSimControllerThread_Once(this));
		t1.start();
	}

	private void start() {
		filter_code();
		int i;
		for (i = 0; i < view.getListModelSize(); i++) {
			model.analyze_code(view.getElementListModel(i));
		}
	}

	public void filter_code() {
		/* Programmcode nach relevanten Zeilen filtern */
		int i;
		for (i = 0; i < view.getListModelSize(); i++) {
			String temp = view.getElementListModel(i);
			if (temp.startsWith("     ")) {
				view.remove_ElementListModel(i);
				i--;
			}
		}
	}

	class StartProgramListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			run_all_functions();

		}
	}

	public void run_all_functions() {

		if (view.getListModelSize() > 0) {

			start();

			if (view.getListModelSize() != 0) {
				set_running(true);
				view.setVisibilityButtons(false, true, true);
				Thread t1 = new Thread(new PicSimControllerThread(this));

				t1.start();

			} else {
				view.set_ErrorMsgs("Kein Programm geöffnet.");
				set_running(false);
			}

		}
	}

	class PauseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			set_running(false);

		}

	}

	class SpeichernRegisterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			int i, m;
			for (i = 0; i <= 31; i++) {
				for (m = 0; m <= 7; m++) {
					int adress = i * 8 + m;

					String entry = view.get_TableEntry(i, m);
					int value;
					try {
						value = Integer.parseInt(entry);
						model.setRegisterEntry(adress, value);

					} catch (NumberFormatException e1) {

						e1.printStackTrace();
					}

				}
			}

		}
	}

	class SliderChangeListener implements ChangeListener {
		// TODO Slider
		@Override
		public void stateChanged(ChangeEvent e) {
			model.set_takt(view.get_Frequency());
			view.set_Frequency(model.get_takt());
		}

	}

	class OpenFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loadFile();
		}

	}

	@SuppressWarnings("resource")
	private void loadFile() {
		initialize_register_array();
		model.CleanStack();
		model.setProgramCounter(0);
		model.CleanWReg();
		view.clear_ListModel();
		ReloadGUI();

		/* Auswählen der Datei */
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Lst File", "lst");
		chooser.setFileFilter(filter);
		/* Was wurde angeklickt -> rueckgabewert */
		int rueckgabeWert = chooser.showOpenDialog(null);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			try {

				/* Datei einlesen mit Buffered Reader */

				BufferedReader in;
				String zeile = null;
				model.setPath_of_programfile(chooser.getSelectedFile()
						.getAbsolutePath());
				in = new BufferedReader(new FileReader(
						model.getPath_of_programfile()));

				while ((zeile = in.readLine()) != null) {
					try {
						/* Zeile für Zeile wird in TextPane eingefügt */

						view.addElementListModel(zeile);

					} catch (Exception e1) {
						System.out.println(e1);
					}
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (view.getListModelSize() == 0) {
			view.set_ErrorMsgs("Kein Programm enthalten.");
		}

	}

	class OpenCalculatorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PicSimCalculatorView calc = new PicSimCalculatorView();
			calc.setVisible(true);

		}

	}

	class RegisterLadenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			register_load();

		}

	}

	@SuppressWarnings("resource")
	public void register_load() {
		/* Auswählen der Datei */
		JFileChooser chooser = new JFileChooser();
		/* Was wurde angeklickt -> rueckgabewert */
		int rueckgabeWert = chooser.showOpenDialog(null);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			try {
				/* Datei einlesen mit Buffered Reader */
				BufferedReader in;
				String zeile = null;
				model.setPath_of_registerfile(chooser.getSelectedFile()
						.getAbsolutePath());
				in = new BufferedReader(new FileReader(
						model.getPath_of_registerfile()));
				zeile = in.readLine();
				String[] splitResult = zeile.split(";");

				int m, s = 0;
				for (m = 0; m < 256; m++) {
					{
						/* Array wird mit Werten aus Dokument gefüllt */
						model.setRegisterEntry(m,
								Integer.parseInt(splitResult[s]));

						s++;
					}
				}

				ReloadGUI();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	class OpenWuerfelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class OpenTrisAListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			load_TRIS("A");

		}

	}

	class OpenTrisBListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			load_TRIS("B");

		}

	}

	class OpenTrisCListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			load_TRIS("C");

		}

	}

	public void load_TRIS(String name) {
		PicSimTrisView tris = new PicSimTrisView(this, name);
		tris.setVisible(true);
	}

	class ChangeTableEntryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void writeToRegister(int adress, int value) {
		model.setRegisterEntry(adress, value);
		ReloadGUI();

	}

}
