package picsim.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

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

	public PicSimView() {
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
		scrollpane_code.setBounds(10, 246, 781, 215);
		contentPane.add(scrollpane_code);

		String[] titles = new String[] { "00", "01", "02", "03", "04", "05",
				"06", "07" };
		table_model = new DefaultTableModel(titles, 32);
		table = new JTable(table_model);

		table.setToolTipText("Zweifaches Drücken von 'Enter' überträgt den eingetragenen Zahlenwert direkt in den Speicher. ");

		JScrollPane scrollpane_table = new JScrollPane(table);

		scrollpane_table.setBounds(803, 33, 301, 428);

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

		rdbtnWrfel = new JRadioButton("Würfel");

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
		lblErrorMsgs.setBounds(555, 212, 233, 29);
		contentPane.add(lblErrorMsgs);
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

}
