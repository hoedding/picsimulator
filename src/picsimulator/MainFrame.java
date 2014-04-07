package picsimulator;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Label;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtZeit;
	public JTextField txtProgrammCounter;
	public JTextField txtLaufzeit;
	public JTextField txtTakt;
	public JTextField txtwreg;
	public DefaultTableModel table_model;
	public JList<String> list_code;
	public JScrollPane scrollpane_code;
	public DefaultListModel<String> listModel;
	public String path_of_code;
	public String path_of_registerfile;
	public JSlider slider;
	public static MainFrame_Logik logik;
	public static Simulator_Logik simulator;
	public static frame_trisA trisA;
	public static frame_led led;
	public static frame_calculator calculator;
	public JLabel lbl_wreg_value;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainFrame frame = new MainFrame();
					
					simulator = new Simulator_Logik(frame);
					logik = new MainFrame_Logik(frame,simulator);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1112, 497);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1112, 22);
		contentPane.add(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmDateiExportieren = new JMenuItem("Datei exportieren");
		mnDatei.add(mntmDateiExportieren);

		JMenu mnErweiterungen = new JMenu("Erweiterungen");
		menuBar.add(mnErweiterungen);

		JMenuItem mntmsegment = new JMenuItem("7-Segment");
		mnErweiterungen.add(mntmsegment);

		JMenuItem mntmLed = new JMenuItem("LED");
		mnErweiterungen.add(mntmLed);

		JMenuItem mntmSchalter = new JMenuItem("Schalter");
		mnErweiterungen.add(mntmSchalter);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmCalculator = new JMenuItem("Calculator");
		mntmCalculator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame_calculator calculator = new frame_calculator();
				calculator.setVisible(true);
			}
		});
		mnTools.add(mntmCalculator);

		final SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.RED);
		StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long time = -System.currentTimeMillis();
				//TODO Testfunktionen
				
				int i;
				for(i=0;i<listModel.size();i++){
					simulator.analyze_code(listModel.elementAt(i));
					}
				
				simulator.start_programm();
				/*for(i=0;i<simulator.list_analyzed_code.size();i++){
					System.out.println(simulator.list_analyzed_code.get(i));
				}*/
				long new_time = time + System.currentTimeMillis();
				txtLaufzeit.setText(new_time + "ms");

			}
		});
		btnStart.setBounds(10, 34, 91, 29);
		contentPane.add(btnStart);

		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(10, 61, 91, 29);
		contentPane.add(btnStop);

		txtZeit = new JTextField();
		txtZeit.setEditable(false);
		txtZeit.setText("Zeit");
		txtZeit.setBounds(205, 28, 88, 28);
		contentPane.add(txtZeit);
		txtZeit.setColumns(10);

		txtProgrammCounter = new JTextField();
		txtProgrammCounter.setEditable(false);
		txtProgrammCounter.setText("Programm Counter");
		txtProgrammCounter.setBounds(205, 68, 88, 28);
		contentPane.add(txtProgrammCounter);
		txtProgrammCounter.setColumns(10);

		txtLaufzeit = new JTextField();
		txtLaufzeit.setEditable(false);
		txtLaufzeit.setText("Laufzeit");
		txtLaufzeit.setBounds(205, 105, 88, 28);
		contentPane.add(txtLaufzeit);
		txtLaufzeit.setColumns(10);

		slider = new JSlider();

		slider.setMaximum(10000);
		slider.setValue(4000);
		slider.setBounds(205, 164, 88, 29);
		contentPane.add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				logik.set_cpu_frequency();
			}
		});

		txtTakt = new JTextField();
		txtTakt.setText("Takt");
		txtTakt.setEditable(false);

		txtTakt.setBounds(205, 145, 88, 28);
		contentPane.add(txtTakt);
		txtTakt.setColumns(10);

		Label label_1 = new Label("PC:");
		label_1.setBounds(119, 79, 66, 17);
		contentPane.add(label_1);

		Label label = new Label("Zeit:");
		label.setBounds(119, 39, 66, 17);
		contentPane.add(label);

		Label label_2 = new Label("Laufzeit:");
		label_2.setBounds(119, 116, 66, 17);
		contentPane.add(label_2);

		Label label_3 = new Label("Takt:");
		label_3.setForeground(Color.BLACK);
		label_3.setBounds(119, 156, 66, 17);
		contentPane.add(label_3);

		 listModel = new DefaultListModel<String>();
		list_code = new JList<String>(listModel);

		scrollpane_code = new JScrollPane(list_code);
		scrollpane_code.setBounds(10, 246, 781, 215);
		contentPane.add(scrollpane_code);

		String[] titles = new String[] { "00", "01", "02", "03", "04", "05",
				"06", "07" };
		 table_model = new DefaultTableModel(titles, 32);
		JTable table = new JTable(table_model);
		JScrollPane scrollpane_table = new JScrollPane(table);
		scrollpane_table.setBounds(803, 33, 301, 428);
		int m, i = 0;
		/* Beim initialisieren wird das Register mit 00 gefüllt */
		for (i = 0; i <= 31; i++) {
			for (m = 0; m <= 7; m++) {
				table_model.setValueAt("00", i, m);

			}
		}

		JMenuItem mntmDateiffnen = new JMenuItem("Datei \u00F6ffnen");
		mntmDateiffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.load_code();
			}
		});
		mnDatei.add(mntmDateiffnen);

		contentPane.add(scrollpane_table, BorderLayout.CENTER);

		JButton btnFilterCode = new JButton("Filter Code");
		btnFilterCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.filter_code();
			}
		});
		btnFilterCode
				.setToolTipText("Nur noch relevanten Programmcode anzeigen");
		btnFilterCode.setBounds(6, 212, 117, 29);
		contentPane.add(btnFilterCode);

		JButton btnRegisterLaden = new JButton("Register laden");
		btnRegisterLaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.register_load();
			}
		});
		btnRegisterLaden.setBounds(677, 34, 117, 29);
		contentPane.add(btnRegisterLaden);

		JButton btnRegisterSpeichern = new JButton("Register speichern");
		btnRegisterSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.register_safe();
			}
		});
		btnRegisterSpeichern.setBounds(677, 61, 117, 29);
		contentPane.add(btnRegisterSpeichern);

		JButton btnReloadOriginal = new JButton("Original");
		btnReloadOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.reload_original_code(path_of_code);
			}
		});
		btnReloadOriginal.setBounds(119, 212, 117, 29);
		contentPane.add(btnReloadOriginal);

		JRadioButton rdbtnTrisA = new JRadioButton("TRIS A");
		rdbtnTrisA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logik.open_trisA();
			}
		});
		rdbtnTrisA.setBounds(305, 34, 77, 23);
		contentPane.add(rdbtnTrisA);

		JRadioButton rdbtnTrisB = new JRadioButton("TRIS B");
		rdbtnTrisB.setBounds(305, 62, 77, 23);
		contentPane.add(rdbtnTrisB);

		JRadioButton rdbtnTrisC = new JRadioButton("TRIS C");
		rdbtnTrisC.setBounds(305, 90, 77, 23);
		contentPane.add(rdbtnTrisC);

		JRadioButton rdbtnWrfel = new JRadioButton("Würfel");
		rdbtnWrfel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame_led led = new frame_led();
				led.setVisible(true);
			}
		});
		rdbtnWrfel.setBounds(305, 116, 98, 23);
		contentPane.add(rdbtnWrfel);
		
		JLabel lblWreg = new JLabel("W-Reg");
		lblWreg.setBounds(422, 39, 61, 16);
		contentPane.add(lblWreg);
		
		 lbl_wreg_value = new JLabel("00");
		lbl_wreg_value.setBounds(495, 39, 61, 16);
		contentPane.add(lbl_wreg_value);
		
		JLabel lblFSR = new JLabel("FSR");
		lblFSR.setBounds(422, 61, 46, 14);
		contentPane.add(lblFSR);
		
		JLabel lbl_FRS_value = new JLabel("00");
		lbl_FRS_value.setBounds(495, 61, 46, 14);
		contentPane.add(lbl_FRS_value);
		
		JLabel lblPcl = new JLabel("PCL");
		lblPcl.setBounds(422, 76, 46, 14);
		contentPane.add(lblPcl);
		
		JLabel lbl_PCL_value = new JLabel("00");
		lbl_PCL_value.setBounds(495, 76, 46, 14);
		contentPane.add(lbl_PCL_value);
		
		JLabel lblPclath = new JLabel("PCLATH");
		lblPclath.setBounds(422, 94, 46, 14);
		contentPane.add(lblPclath);
		
		JLabel lbl_PCLATH_value = new JLabel("00");
		lbl_PCLATH_value.setBounds(495, 94, 46, 14);
		contentPane.add(lbl_PCLATH_value);
		
		JLabel lblPc = new JLabel("PC");
		lblPc.setBounds(422, 112, 46, 14);
		contentPane.add(lblPc);
		
		JLabel lbl_PC_value = new JLabel("0000");
		lbl_PC_value.setBounds(495, 112, 46, 14);
		contentPane.add(lbl_PC_value);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(422, 135, 46, 14);
		contentPane.add(lblStatus);
		
		JLabel lbl_Status_value = new JLabel("00");
		lbl_Status_value.setBounds(495, 135, 46, 14);
		contentPane.add(lbl_Status_value);

	}
}
