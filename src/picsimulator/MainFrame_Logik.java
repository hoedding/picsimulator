package picsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame_Logik {
	private MainFrame frame;
	private Simulator_Logik simulator;

	public MainFrame_Logik(MainFrame _frame, Simulator_Logik _simulator) {
		frame = _frame;
		simulator = _simulator;
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
				frame.path_of_registerfile = chooser.getSelectedFile()
						.getAbsolutePath();
				in = new BufferedReader(new FileReader(
						frame.path_of_registerfile));
				zeile = in.readLine();
				String[] splitResult = zeile.split(";");

				int m,  s = 0;
				for (m = 0; m < 256; m++) {
					{
						/* Array wird mit Werten aus Dokument gefüllt */
						simulator.register_array[m] = Integer.parseInt(splitResult[s]);
						s++;
					}
				}
				
				simulator.tabelle_aktualisieren();
				/*int m1 = 0, n1 = 0, t1 = 0;
				while (m1 < 256) {
					while (t1 < 8) {
						/* Tabelle bekommt Werte aus Array zugewiesen */
					/*	frame.table_model.setValueAt(simulator.register_array[m1],
								n1, t1);
						t1++;
						m1++;
					}
					t1 = 0;
					n1++;
				}*/

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void filter_code() {
		/* Programmcode nach relevanten Zeilen filtern */
		int i;
		for (i = 0; i < frame.listModel.size(); i++) {
			String temp = frame.listModel.elementAt(i);
			if (temp.startsWith("     ")) {
				frame.listModel.remove(i);
				i--;
			}
		}
	}

	@SuppressWarnings("resource")
	public void load_code() {
		/* Auswählen der Datei */
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Lst File", "lst");
		chooser.setFileFilter(filter);
		/* Was wurde angeklickt -> rueckgabewert */
		int rueckgabeWert = chooser.showOpenDialog(null);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			try {
				/* Datei einlesen mit Buffered Reader */
				if(!frame.listModel.isEmpty())
				{
					frame.listModel.clear();
				}
				BufferedReader in;
				String zeile = null;
				frame.path_of_code = chooser.getSelectedFile()
						.getAbsolutePath();
				in = new BufferedReader(new FileReader(frame.path_of_code));

				while ((zeile = in.readLine()) != null) {
					try {
						/* Zeile für Zeile wird in TextPane eingefügt */
						/*
						 * doc.getLength() bedeutet letzte Zeile, also wird
						 * alles am Ende eingefügt
						 */
						frame.listModel.addElement(zeile);
					} catch (Exception e1) {
						System.out.println(e1);
					}
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void set_cpu_frequency() {
		int int_value_of_slider = frame.slider.getValue();
		StringBuilder sb = new StringBuilder();
		sb.append(int_value_of_slider);
		String str_value_of_slider = sb.toString();
		frame.txtTakt.setText(str_value_of_slider);
	}

	public void reload_original_code(String path_of_code) {
		frame.listModel.removeAllElements();
		BufferedReader in;
		String zeile = null;

		try {
			in = new BufferedReader(new FileReader(path_of_code));

			while ((zeile = in.readLine()) != null) {

				/* Zeile für Zeile wird in TextPane eingefügt */
				/*
				 * doc.getLength() bedeutet letzte Zeile, also wird alles am
				 * Ende eingefügt
				 */
				frame.listModel.addElement(zeile);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private frame_trisA trisa;
	public void open_trisA() {
		
		if(trisa==null){
		 trisa = new frame_trisA(frame);
		trisa.setVisible(true);
		}
	}
	private frame_trisB trisb;
	public void open_trisB() {
		
		if(trisb==null){
		 trisb = new frame_trisB(frame);
		trisb.setVisible(true);
		}
	}
	private frame_trisC trisc;
	public void open_trisC() {
		
		if(trisc==null){
		 trisc = new frame_trisC(frame);
		trisc.setVisible(true);
		}
	}
	
}
