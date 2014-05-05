package picsim.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import picsim.mvc.view.PicSimView;

public class PicSimController {
	private PicSimView view;
	private PicSimModel model;
	private boolean running;

	public PicSimController(PicSimView view, PicSimModel model) {
		this.view = view;
		this.model = model;
		this.running = false;
		model.reset_model();
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
		view.setChangePortABit0(new ChangePortABit0());
		view.setChangePortABit1(new ChangePortABit1());
		view.setChangePortABit2(new ChangePortABit2());
		view.setChangePortABit3(new ChangePortABit3());
		view.setChangePortABit4(new ChangePortABit4());
		view.setChangePortABit5(new ChangePortABit5());
		view.setChangePortABit6(new ChangePortABit6());
		view.setChangePortABit7(new ChangePortABit7());
		view.setChangePortBBit0(new ChangePortBBit0());
		view.setChangePortBBit1(new ChangePortBBit1());
		view.setChangePortBBit2(new ChangePortBBit2());
		view.setChangePortBBit3(new ChangePortBBit3());
		view.setChangePortBBit4(new ChangePortBBit4());
		view.setChangePortBBit5(new ChangePortBBit5());
		view.setChangePortBBit6(new ChangePortBBit6());
		view.setChangePortBBit7(new ChangePortBBit7());
		view.setChangeTableEntryListener(new ChangeTableEntryListener());
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

	/* ARRAY FÜR DAS REGISTER */

	public void start_programm(int takt) throws InterruptedException {
		/*
		 * Überprüfung ob Ende des Programms erreicht wird kann am Ende
		 * gelöscht werden !!
		 */// TODO l��schen
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
	 * ######## F��hrt f��r jedes in der code_list enthaltene Elemente die
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
		/* aktuell ausgef��hrten Code markieren */
		view.select_code(model.getProgrammCounter());
		view.set_W_value(String.valueOf(model.w_register));

		/* Aktualisieren der Tabelle mit den Werten aus Register_Array */
		ReloadTable();

		/* Status setzen */
		view.set_Status_value(model.get_status());
		view.set_Intcon_value(model.get_intcon());
		/* Ports aktualisieren */
		view.setPortALabels(model.getRegisterEntry(5));
		view.setPortBLabels(model.getRegisterEntry(6));
		view.setTrisALabels(model.getRegisterEntry(0x85));
		view.setTrisBLabels(model.getRegisterEntry(0x86));

		/* Wuerfel setzen */
		view.setWuerfel(false, false, false, false, false, false, false, false,
				false);

		/* Laufzeit aktualisieren */
		view.txtLaufzeit.setText(String.valueOf(model.getRunningTime()));

		/* Programmschritte aktualisieren */
		view.txtSteps.setText(String.valueOf(model.getSteps()));
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
			model.setStartTime(System.currentTimeMillis());
			start();

			set_running(true);
			view.setVisibilityButtons(false, true, true);
			Thread t1 = new Thread(new PicSimControllerThread(this));

			t1.start();

		} else {
			view.set_ErrorMsgs("Kein Programm geöffnet.");
			set_running(false);
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

		running = false;
		view.clear_ListModel();
		model.reset_model();
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
						/* Zeile f��r Zeile wird in TextPane eingef��gt */

						view.addElementListModel(zeile);

					} catch (Exception e1) {
						System.out.println(e1);
					}
				}
				view.setVisibilityButtons(false, false, false);
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
		/* Ausw��hlen der Datei */
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
						/* Array wird mit Werten aus Dokument gef��llt */
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

	class ChangeTableEntryListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				writeTableToRegister();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void writeToRegister(int adress, int value) {
		model.setRegisterEntry(adress, value);
		ReloadGUI();

	}

	class ChangePortABit0 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(0);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit1 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(1);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit2 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(2);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit3 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(3);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit4 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(4);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit5 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(5);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit6 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(6);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortABit7 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortA(7);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit0 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(0);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit1 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(1);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit2 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(2);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit3 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(3);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit4 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(4);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit5 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(5);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit6 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(6);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ChangePortBBit7 implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			// TODO Auto-generated method stub
			changeTheRegisterFromPortB(7);
			System.out.println("klick!!b7");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private void changeTheRegisterFromPortA(int ID) {
		switch (ID) {
		case 0: {
			int temp = view.getValuePortA() & 0b00000001;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 1);
			} else {
				model.setPortA(model.getPortA() - 1);
			}
			break;
		}
		case 1: {
			int temp = view.getValuePortA() & 0b00000010;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 2);
			} else {
				model.setPortA(model.getPortA() - 2);
			}
			break;
		}
		case 2: {
			int temp = view.getValuePortA() & 0b00000100;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 4);
			} else {
				model.setPortA(model.getPortA() - 4);
			}
			break;
		}
		case 3: {
			int temp = view.getValuePortA() & 0b00001000;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 8);
			} else {
				model.setPortA(model.getPortA() - 8);
			}
			break;
		}
		case 4: {
			int temp = view.getValuePortA() & 0b00010000;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 16);
			} else {
				model.setPortA(model.getPortA() - 16);
			}
			break;
		}
		case 5: {
			int temp = view.getValuePortA() & 0b00100000;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 32);
			} else {
				model.setPortA(model.getPortA() - 32);
			}
			break;
		}
		case 6: {
			int temp = view.getValuePortA() & 0b01000000;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 64);
			} else {
				model.setPortA(model.getPortA() - 64);
			}
			break;
		}
		case 7: {
			int temp = view.getValuePortA() & 0b10000000;
			if (temp == 0) {
				model.setPortA(model.getPortA() + 128);
			} else {
				model.setPortA(model.getPortA() - 128);
			}
			break;
		}
		default: {
			break;
		}
		}
		model.register_array[5] = model.getPortA();
		ReloadGUI();
	}

	private void changeTheRegisterFromPortB(int ID) {
		switch (ID) {
		case 0: {
			int temp = view.getValuePortB() & 0b00000001;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 1);
			} else {
				model.setPortB(model.getPortB() - 1);
			}
			break;
		}
		case 1: {
			int temp = view.getValuePortB() & 0b00000010;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 2);
			} else {
				model.setPortB(model.getPortB() - 2);
			}
			break;
		}
		case 2: {
			int temp = view.getValuePortB() & 0b00000100;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 4);
			} else {
				model.setPortB(model.getPortB() - 4);
			}
			break;
		}
		case 3: {
			int temp = view.getValuePortB() & 0b00001000;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 8);
			} else {
				model.setPortB(model.getPortB() - 8);
			}
			break;
		}
		case 4: {
			int temp = view.getValuePortB() & 0b00010000;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 16);
			} else {
				model.setPortB(model.getPortB() - 16);
			}
			break;
		}
		case 5: {
			int temp = view.getValuePortB() & 0b00100000;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 32);
			} else {
				model.setPortB(model.getPortB() - 32);
			}
			break;
		}
		case 6: {
			int temp = view.getValuePortB() & 0b01000000;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 64);
			} else {
				model.setPortB(model.getPortB() - 64);
			}
			break;
		}
		case 7: {
			int temp = view.getValuePortB() & 0b10000000;
			if (temp == 0) {
				model.setPortB(model.getPortB() + 128);
			} else {
				model.setPortB(model.getPortB() - 128);
			}
			break;
		}
		default: {
			break;
		}
		}
		model.register_array[6] = model.getPortB();
		ReloadGUI();
	}

	public void setTime() {
		model.setRunningTime((System.currentTimeMillis())
				- (model.getStartTime()));
		System.out.println("start" + model.getStartTime());
		System.out.println("running: " + model.getRunningTime());
	}

	public void countSteps() {
		model.setSteps();

		System.out.println(model.getSteps());
	}

	public void writeTableToRegister() {
		if (running == false) {
			int i, m;
			for (i = 0; i <= 31; i++) {
				for (m = 0; m <= 7; m++) {
					int adress = i * 8 + m;
					// TODO bisher nur decimal möglich
					String temp = String.valueOf(view.get_TableEntry(i, m));
					int value = Integer.parseInt(temp);
					if (model.getRegisterEntry(adress) != value) {

						model.register_array[adress] = value;
						System.out.println("adresse: " + adress
								+ " neuer wert: " + value);
					}
				}
			}
		} else {
			view.set_ErrorMsgs("Direkte Registeränderung nicht während der Laufzeit möglich.");
		}
	}

}
