package picsim.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class PicSimView extends JFrame {

	private static final long serialVersionUID = -6310686304585590231L;
	private JTextField txtZeit;
	private JTextField txtLaufzeit;
	private JTextField txtTakt;

	private JLabel lbl_wreg_value;
	private JLabel lbl_C_value;
	private JLabel lbl_Status_value;
	private JLabel lbl_PC_value;
	private JLabel lbl_DC_value;
	private JLabel lblErrorMsgs;
	private JLabel lbl_Z_value;

	private JSlider slider;

	private JTable table;
	private JButton btnRegisterSpeichern;
	private JButton btnWeiter;
	private JButton btnPause;
	private JButton btnStart;

	private JButton btnRegisterLaden;

	private JRadioButton rdbtnWrfel;
	private JRadioButton rdbtnTrisA;
	private JRadioButton rdbtnTrisB;
	private JRadioButton rdbtnTrisC;

	private JMenuItem mntmDateiffnen;
	private JMenuItem mntmCalculator;

	private DefaultTableModel table_model;
	private JScrollPane scrollpane_code;

	private JList<String> list_code;
	private DefaultListModel<String> listModel;

	/* LABEL TRIS A und PORT A */
	private JLabel lblI;
	private JLabel lblI_1;
	private JLabel lblI_2;
	private JLabel lblI_3;
	private JLabel lblI_4;
	private JLabel lblI_5;
	private JLabel lblI_6;
	private JLabel lblI_7;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel label_15;
	private JLabel label_16;
	private JLabel label_17;
	private JLabel label_18;
	private JLabel label_19;
	private JLabel label_20;

	/* Label TRIS B und PORT B */
	private JLabel label_28;
	private JLabel label_29;
	private JLabel label_30;
	private JLabel label_31;
	private JLabel label_32;
	private JLabel label_33;
	private JLabel label_34;
	private JLabel label_35;
	private JLabel label_38;
	private JLabel label_39;
	private JLabel label_40;
	private JLabel label_41;
	private JLabel label_42;
	private JLabel label_43;
	private JLabel label_44;
	private JLabel label_45;

	private JLabel label_47;
	private JLabel label_23;

	public PicSimView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1112, 599);
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

		mntmCalculator = new JMenuItem("Calculator");

		mnTools.add(mntmCalculator);

		JMenu mnHelp = new JMenu("Hilfe");
		menuBar.add(mnHelp);

		JMenuItem mntmDoku = new JMenuItem("Dokumentation");
		mnHelp.add(mntmDoku);

		final SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.RED);
		StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);

		btnStart = new JButton("Start");
		btnStart.setBounds(10, 34, 91, 29);
		contentPane.add(btnStart);

		btnPause = new JButton("Pause");
		btnPause.setBounds(10, 61, 91, 29);
		btnPause.setEnabled(false);
		contentPane.add(btnPause);

		txtZeit = new JTextField();
		txtZeit.setEditable(false);
		txtZeit.setText("Zeit");
		txtZeit.setBounds(205, 28, 88, 28);
		contentPane.add(txtZeit);
		txtZeit.setColumns(10);

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
		txtTakt = new JTextField();
		txtTakt.setText("4000");
		txtTakt.setEditable(false);

		txtTakt.setBounds(205, 145, 88, 28);
		contentPane.add(txtTakt);
		txtTakt.setColumns(10);

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
		scrollpane_code.setBounds(10, 356, 781, 215);
		contentPane.add(scrollpane_code);

		String[] titles = new String[] { "00", "01", "02", "03", "04", "05",
				"06", "07" };
		table_model = new DefaultTableModel(titles, 32);
		table = new JTable(table_model);

		table.setToolTipText("Zweifaches Dr��cken von 'Enter' ��bertr��gt den eingetragenen Zahlenwert direkt in den Speicher. ");

		JScrollPane scrollpane_table = new JScrollPane(table);

		scrollpane_table.setBounds(803, 33, 301, 538);

		mntmDateiffnen = new JMenuItem("Datei \u00F6ffnen");
		mnDatei.add(mntmDateiffnen);

		contentPane.add(scrollpane_table, BorderLayout.CENTER);

		btnRegisterLaden = new JButton("Register laden");
		btnRegisterLaden.setBounds(653, 34, 141, 29);
		contentPane.add(btnRegisterLaden);

		btnRegisterSpeichern = new JButton("Register speichern");
		btnRegisterSpeichern.setBounds(653, 61, 141, 29);
		contentPane.add(btnRegisterSpeichern);

		rdbtnTrisA = new JRadioButton("TRIS A");
		rdbtnTrisA.setBounds(305, 34, 77, 23);
		contentPane.add(rdbtnTrisA);

		rdbtnTrisB = new JRadioButton("TRIS B");
		rdbtnTrisB.setBounds(305, 62, 77, 23);
		contentPane.add(rdbtnTrisB);

		rdbtnTrisC = new JRadioButton("TRIS C");
		rdbtnTrisC.setBounds(305, 90, 77, 23);
		contentPane.add(rdbtnTrisC);

		rdbtnWrfel = new JRadioButton("W��rfel");

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

		lbl_PC_value = new JLabel("0000");
		lbl_PC_value.setBounds(495, 112, 46, 14);
		contentPane.add(lbl_PC_value);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(422, 135, 46, 14);
		contentPane.add(lblStatus);

		lbl_Status_value = new JLabel("00");
		lbl_Status_value.setBounds(495, 135, 46, 14);
		contentPane.add(lbl_Status_value);

		JLabel lblZ = new JLabel("Z");
		lblZ.setBounds(530, 39, 20, 16);
		contentPane.add(lblZ);

		lbl_Z_value = new JLabel("00");
		lbl_Z_value.setBounds(556, 39, 37, 16);
		contentPane.add(lbl_Z_value);

		btnWeiter = new JButton("Weiter");
		btnWeiter.setBounds(10, 89, 91, 29);
		btnWeiter.setEnabled(false);
		contentPane.add(btnWeiter);

		JLabel lblC = new JLabel("C");
		lblC.setBounds(530, 61, 61, 16);
		contentPane.add(lblC);

		lbl_C_value = new JLabel("00");
		lbl_C_value.setBounds(556, 61, 61, 16);
		contentPane.add(lbl_C_value);

		JLabel lblDc = new JLabel("DC");
		lblDc.setBounds(530, 74, 61, 16);
		contentPane.add(lblDc);

		lbl_DC_value = new JLabel("00");
		lbl_DC_value.setBounds(556, 74, 61, 16);
		contentPane.add(lbl_DC_value);

		lblErrorMsgs = new JLabel("Error.");
		lblErrorMsgs.setVisible(false);
		lblErrorMsgs.setIcon(new ImageIcon(PicSimView.class
				.getResource("/javax/swing/plaf/metal/icons/Warn.gif")));
		lblErrorMsgs.setBounds(10, 315, 233, 29);
		contentPane.add(lblErrorMsgs);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(609, 105, 188, 108);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 194, 29);
		panel_4.add(panel);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblRA = new JLabel("RA");
		panel.add(lblRA);

		JLabel label_1 = new JLabel("");
		panel.add(label_1);

		JLabel lblNewLabel_1 = new JLabel("7");
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("6");
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("5");
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("4");
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("3");
		panel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("2");
		panel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("1");
		panel.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("0");
		panel.add(lblNewLabel_8);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 29, 194, 29);
		panel_4.add(panel_1);
		panel_1.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_1.setBackground(UIManager.getColor("Button.background"));
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblTrisa = new JLabel("TA");
		panel_1.add(lblTrisa);

		JLabel label_12 = new JLabel("");
		panel_1.add(label_12);

		lblI = new JLabel("i");
		panel_1.add(lblI);

		lblI_1 = new JLabel("i");
		panel_1.add(lblI_1);

		lblI_2 = new JLabel("i");
		panel_1.add(lblI_2);

		lblI_3 = new JLabel("i");
		panel_1.add(lblI_3);

		lblI_4 = new JLabel("i");
		panel_1.add(lblI_4);

		lblI_5 = new JLabel("i");
		panel_1.add(lblI_5);

		lblI_6 = new JLabel("i");
		panel_1.add(lblI_6);

		lblI_7 = new JLabel("i");
		panel_1.add(lblI_7);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 56, 194, 29);
		panel_4.add(panel_2);
		panel_2.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_2.setBackground(SystemColor.window);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblPin = new JLabel("P");
		panel_2.add(lblPin);

		JLabel label_21 = new JLabel("");
		panel_2.add(label_21);

		label_13 = new JLabel("0");
		panel_2.add(label_13);

		label_14 = new JLabel("0");
		panel_2.add(label_14);

		label_15 = new JLabel("0");
		panel_2.add(label_15);

		label_16 = new JLabel("0");
		panel_2.add(label_16);

		label_17 = new JLabel("0");
		panel_2.add(label_17);

		label_18 = new JLabel("0");
		panel_2.add(label_18);

		label_19 = new JLabel("0");
		panel_2.add(label_19);

		label_20 = new JLabel("0");
		panel_2.add(label_20);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(39, 84, 155, 29);
		panel_4.add(panel_3);
		panel_3.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_3.setBackground(SystemColor.window);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel label_5 = new JLabel("");
		panel_3.add(label_5);

		label_23 = new JLabel("0");
		panel_3.add(label_23);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(609, 236, 182, 108);
		contentPane.add(panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(0, 0, 194, 29);
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblRb = new JLabel("RB");
		panel_6.add(lblRb);

		JLabel label_6 = new JLabel("");
		panel_6.add(label_6);

		JLabel label_7 = new JLabel("7");
		panel_6.add(label_7);

		JLabel label_8 = new JLabel("6");
		panel_6.add(label_8);

		JLabel label_9 = new JLabel("5");
		panel_6.add(label_9);

		JLabel label_10 = new JLabel("4");
		panel_6.add(label_10);

		JLabel label_11 = new JLabel("3");
		panel_6.add(label_11);

		JLabel label_22 = new JLabel("2");
		panel_6.add(label_22);

		JLabel label_24 = new JLabel("1");
		panel_6.add(label_24);

		JLabel label_25 = new JLabel("0");
		panel_6.add(label_25);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_7.setBackground(SystemColor.window);
		panel_7.setBounds(0, 29, 194, 29);
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblTb = new JLabel("TB");
		panel_7.add(lblTb);

		JLabel label_27 = new JLabel("");
		panel_7.add(label_27);

		label_28 = new JLabel("i");
		panel_7.add(label_28);

		label_29 = new JLabel("i");
		panel_7.add(label_29);

		label_30 = new JLabel("i");
		panel_7.add(label_30);

		label_31 = new JLabel("i");
		panel_7.add(label_31);

		label_32 = new JLabel("i");
		panel_7.add(label_32);

		label_33 = new JLabel("i");
		panel_7.add(label_33);

		label_34 = new JLabel("i");
		panel_7.add(label_34);

		label_35 = new JLabel("i");
		panel_7.add(label_35);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_8.setBackground(SystemColor.window);
		panel_8.setBounds(0, 56, 194, 29);
		panel_5.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel label_36 = new JLabel("P");
		panel_8.add(label_36);

		JLabel label_37 = new JLabel("");
		panel_8.add(label_37);

		label_38 = new JLabel("0");
		panel_8.add(label_38);

		label_39 = new JLabel("0");
		panel_8.add(label_39);

		label_40 = new JLabel("0");
		panel_8.add(label_40);

		label_41 = new JLabel("0");
		panel_8.add(label_41);

		label_42 = new JLabel("0");
		panel_8.add(label_42);

		label_43 = new JLabel("0");
		panel_8.add(label_43);

		label_44 = new JLabel("0");
		panel_8.add(label_44);

		label_45 = new JLabel("0");
		panel_8.add(label_45);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_9.setBackground(SystemColor.window);
		panel_9.setBounds(39, 84, 155, 29);
		panel_5.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel label_46 = new JLabel("");
		panel_9.add(label_46);

		label_47 = new JLabel("0");
		panel_9.add(label_47);
	}

	public void set_W_value(String s) {
		lbl_wreg_value.setText(s);
	}

	public void set_Z_value(String s) {
		lbl_Z_value.setText(s);
	}

	public void set_C_value(String s) {
		lbl_C_value.setText(s);
	}

	public void set_Status_value(String s) {
		lbl_Status_value.setText(s);
	}

	public void set_PC_value(String s) {
		lbl_PC_value.setText(s);
	}

	public void set_DC_value(String s) {
		lbl_DC_value.setText(s);
	}

	public void set_ErrorMsgs(String error) {
		lblErrorMsgs.setVisible(true);
	}

	public void select_code(int index) {
		list_code.setSelectedIndex(index);
	}

	public int getListModelSize() {
		return listModel.size();
	}

	public String getElementListModel(int at) {
		return listModel.elementAt(at);
	}

	public void addElementListModel(String value) {
		listModel.addElement(value);
	}

	public void clear_ListModel() {
		listModel.removeAllElements();
	}

	public void remove_ElementListModel(int index) {
		listModel.remove(index);
	}

	public void set_TableEntry(int aValue, int row, int column) {
		table_model.setValueAt(aValue, row, column);
	}

	public String get_TableEntry(int row, int column) {
		return String.valueOf(table.getValueAt(row, column));
	}

	public void setVisibilityButtons(boolean error, boolean next, boolean pause) {
		lblErrorMsgs.setVisible(error);
		btnPause.setEnabled(pause);
		btnWeiter.setEnabled(next);
	}

	public void set_Frequency(int i) {
		txtTakt.setText(String.valueOf(i));
	}

	public int get_Frequency() {
		return slider.getValue();
	}

	public void setStartProgramListener(ActionListener l) {
		btnStart.addActionListener(l);
	}

	public void setNextStepListener(ActionListener l) {
		btnWeiter.addActionListener(l);
	}

	public void setPauseListener(ActionListener l) {
		btnPause.addActionListener(l);
	}

	public void setSpeichernRegisterListener(ActionListener l) {
		btnRegisterSpeichern.addActionListener(l);
	}

	public void setSliderChangeListener(ChangeListener l) {
		slider.addChangeListener(l);
	}

	public void setOpenFileListener(ActionListener l) {
		mntmDateiffnen.addActionListener(l);

	}

	public void setOpenCalculatorListener(ActionListener l) {
		mntmCalculator.addActionListener(l);
	}

	public void setRegisterLadenListener(ActionListener l) {
		btnRegisterLaden.addActionListener(l);
	}

	public void setOpenWuerfelListener(ActionListener l) {
		rdbtnWrfel.addActionListener(l);
	}

	public void setOpenTrisAListener(ActionListener l) {
		rdbtnTrisA.addActionListener(l);
	}

	public void setOpenTrisBListener(ActionListener l) {
		rdbtnTrisB.addActionListener(l);
	}

	public void setOpenTrisCListener(ActionListener l) {
		rdbtnTrisC.addActionListener(l);
	}

	public void setChangeTableEntryListener(KeyAdapter l) {
		table.addKeyListener(l);
	}

	public void setChangePortABit0(MouseListener l) {
		label_20.addMouseListener(l);
	}

	public void setChangePortABit1(MouseListener l) {
		label_19.addMouseListener(l);
	}

	public void setChangePortABit2(MouseListener l) {
		label_18.addMouseListener(l);
	}

	public void setChangePortABit3(MouseListener l) {
		label_17.addMouseListener(l);
	}

	public void setChangePortABit4(MouseListener l) {
		label_16.addMouseListener(l);
	}

	public void setChangePortABit5(MouseListener l) {
		label_15.addMouseListener(l);
	}

	public void setChangePortABit6(MouseListener l) {
		label_14.addMouseListener(l);
	}

	public void setChangePortABit7(MouseListener l) {
		label_13.addMouseListener(l);
	}

	// PortB

	public void setChangePortBBit0(MouseListener l) {
		label_45.addMouseListener(l);
	}

	public void setChangePortBBit1(MouseListener l) {
		label_44.addMouseListener(l);
	}

	public void setChangePortBBit2(MouseListener l) {
		label_43.addMouseListener(l);
	}

	public void setChangePortBBit3(MouseListener l) {
		label_42.addMouseListener(l);
	}

	public void setChangePortBBit4(MouseListener l) {
		label_41.addMouseListener(l);
	}

	public void setChangePortBBit5(MouseListener l) {
		label_40.addMouseListener(l);
	}

	public void setChangePortBBit6(MouseListener l) {
		label_39.addMouseListener(l);
	}

	public void setChangePortBBit7(MouseListener l) {
		label_38.addMouseListener(l);
	}

	public void setTrisALabels(int t) {
		t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			lblI_7.setText("i");
		} else {
			lblI_7.setText("o");
		}
		if (b > 0) {
			lblI_6.setText("i");
		} else {
			lblI_6.setText("o");
		}
		if (c > 0) {
			lblI_5.setText("i");
		} else {
			lblI_5.setText("o");
		}
		if (d > 0) {
			lblI_4.setText("i");
		} else {
			lblI_4.setText("o");
		}
		if (e > 0) {
			lblI_3.setText("i");
		} else {
			lblI_3.setText("o");
		}
		if (f > 0) {
			lblI_2.setText("i");
		} else {
			lblI_2.setText("o");
		}
		if (g > 0) {
			lblI_1.setText("i");
		} else {
			lblI_1.setText("o");
		}
		if (h > 0) {
			lblI.setText("i");
		} else {
			lblI.setText("o");
		}
	}

	public void setPortALabels(int t) {
		t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			label_20.setText("1");
		} else {
			label_20.setText("0");
		}
		if (b > 0) {
			label_19.setText("1");
		} else {
			label_19.setText("0");
		}
		if (c > 0) {
			label_18.setText("1");
		} else {
			label_18.setText("0");
		}
		if (d > 0) {
			label_17.setText("1");
		} else {
			label_17.setText("0");
		}
		if (e > 0) {
			label_16.setText("1");
		} else {
			label_16.setText("0");
		}
		if (f > 0) {
			label_15.setText("1");
		} else {
			label_15.setText("0");
		}
		if (g > 0) {
			label_14.setText("1");
		} else {
			label_14.setText("0");
		}
		if (h > 0) {
			label_13.setText("1");
		} else {
			label_13.setText("0");
		}
		label_23.setText(String.valueOf(t));
	}

	public void setTrisBLabels(int t) {
		t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			label_35.setText("i");
		} else {
			label_35.setText("o");
		}
		if (b > 0) {
			label_34.setText("i");
		} else {
			label_34.setText("o");
		}
		if (c > 0) {
			label_33.setText("i");
		} else {
			label_33.setText("o");
		}
		if (d > 0) {
			label_32.setText("i");
		} else {
			label_32.setText("o");
		}
		if (e > 0) {
			label_31.setText("i");
		} else {
			label_31.setText("o");
		}
		if (f > 0) {
			label_30.setText("i");
		} else {
			label_30.setText("o");
		}
		if (g > 0) {
			label_29.setText("i");
		} else {
			label_29.setText("o");
		}
		if (h > 0) {
			label_28.setText("i");
		} else {
			label_28.setText("o");
		}
	}

	public void setPortBLabels(int t) {
		t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			label_45.setText("1");
		} else {
			label_45.setText("0");
		}
		if (b > 0) {
			label_44.setText("1");
		} else {
			label_44.setText("0");
		}
		if (c > 0) {
			label_43.setText("1");
		} else {
			label_43.setText("0");
		}
		if (d > 0) {
			label_42.setText("1");
		} else {
			label_42.setText("0");
		}
		if (e > 0) {
			label_41.setText("1");
		} else {
			label_41.setText("0");
		}
		if (f > 0) {
			label_40.setText("1");
		} else {
			label_40.setText("0");
		}
		if (g > 0) {
			label_39.setText("1");
		} else {
			label_39.setText("0");
		}
		if (h > 0) {
			label_38.setText("1");
		} else {
			label_38.setText("0");
		}
		label_47.setText(String.valueOf(t));
	}

	public int getValuePortA() {

		String value = label_20.getText() + label_19.getText()
				+ label_18.getText() + label_17.getText() + label_16.getText()
				+ label_15.getText() + label_14.getText() + label_13.getText();

		int binary = Integer.parseInt(value, 2);

		return binary;
	}

	public int getValuePortB() {

		String value = label_45.getText() + label_44.getText()
				+ label_43.getText() + label_42.getText() + label_41.getText()
				+ label_40.getText() + label_39.getText() + label_38.getText();
		
		int binary = Integer.parseInt(value, 2);

		return binary;

	}
}
