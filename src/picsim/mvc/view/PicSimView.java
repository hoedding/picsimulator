package picsim.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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

import picsim.mvc.model.PicSimModel;
import sun.misc.IOUtils;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.Choice;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

public class PicSimView extends JFrame {

	private static final long serialVersionUID = -6310686304585590231L;

	private PicSimModel model;

	public JTextField txtSteps;
	public JTextField txtLaufzeit;
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

	private JMenuItem mntmDateiffnen;
	private JMenuItem mntmBeenden;
	private JMenuItem mntmCalculator;

	private DefaultTableModel table_model;
	private JScrollPane scrollpane_code;

	private JList<String> list_code;
	private DefaultListModel<String> listModel;
	private DefaultListModel<Integer> listModelSTACK;

	private BreakpointCellRenderer list_renderer;

	public List<Integer> breakpoint_list = new ArrayList<Integer>();

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

	private JLabel label_PD_value;
	private JLabel label_T0_value;
	private JLabel label_IRP_value;
	private JLabel label_RP1_value;
	private JLabel label_RP0_value;

	private JLabel label_58;
	private JLabel label_57;
	private JLabel label_56;
	private JLabel label_55;
	private JLabel label_54;
	private JLabel label_53;
	private JLabel label_52;
	private JLabel label_51;

	public JPanel panel_portstatus;
	
	private JList listSTACK;
	
	private JLabel lblDisconnected;

	private JComboBox<String> choice;

	private JComboBox<Integer> breakpoints;

	private int indexOfBreakpoint = 0;
	
	private JRadioButton radioButtonComPort;

	public PicSimView() {
		setTitle("Simulator PIC12F84");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1028, 613);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1112, 22);
		contentPane.add(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		mntmCalculator = new JMenuItem("Calculator");

		mnTools.add(mntmCalculator);

		JMenu mnHelp = new JMenu("Hilfe");
		menuBar.add(mnHelp);

		JMenuItem mntmDoku = new JMenuItem("Dokumentation");
		mntmDoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
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

		txtSteps = new JTextField();
		txtSteps.setEditable(false);
		txtSteps.setText("0");
		txtSteps.setBounds(205, 28, 88, 28);
		contentPane.add(txtSteps);
		txtSteps.setColumns(10);

		txtLaufzeit = new JTextField();
		txtLaufzeit.setEditable(false);
		txtLaufzeit.setText("0");
		txtLaufzeit.setBounds(205, 61, 88, 28);
		contentPane.add(txtLaufzeit);
		txtLaufzeit.setColumns(10);

		slider = new JSlider();
		slider.setMaximum(10000);
		slider.setValue(4000);
		slider.setBounds(205, 120, 88, 29);
		contentPane.add(slider);
		txtTakt = new JTextField();
		txtTakt.setText("4000");
		txtTakt.setEditable(false);

		txtTakt.setBounds(205, 101, 88, 28);
		contentPane.add(txtTakt);
		txtTakt.setColumns(10);

		Label label = new Label("Schritte: ");
		label.setBounds(119, 39, 66, 17);
		contentPane.add(label);

		Label label_2 = new Label("Laufzeit:");
		label_2.setBounds(119, 72, 66, 17);
		contentPane.add(label_2);

		Label label_3 = new Label("Takt:");
		label_3.setForeground(Color.BLACK);
		label_3.setBounds(119, 112, 66, 17);
		contentPane.add(label_3);

		listModel = new DefaultListModel<String>();
		list_code = new JList<String>(listModel);

		list_code.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					int temp = list_code.getSelectedIndex();

					if (breakpoint_list.contains(temp)) {
						breakpoint_list.remove(breakpoint_list.indexOf(temp));
						updateBPCombobox();

					} else {

						breakpoint_list.add(temp);
						updateBPCombobox();

					}
				}
			}
		});

		list_renderer = new BreakpointCellRenderer(list_code);

		list_code.setCellRenderer(list_renderer);
		
		

		scrollpane_code = new JScrollPane(list_code);
		scrollpane_code.setBounds(10, 356, 687, 215);
		contentPane.add(scrollpane_code);

		String[] titles = new String[] { "00", "01", "02", "03", "04", "05",
				"06", "07" };
		table_model = new DefaultTableModel(titles, 32);
		table = new JTable(table_model);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		table.setToolTipText("Zweifaches Dr�cken von 'Enter' �berträgt den Wert direkt ins Register. ");

		JScrollPane scrollpane_table = new JScrollPane(table);

		scrollpane_table.setBounds(707, 34, 301, 538);

		mntmDateiffnen = new JMenuItem("Datei \u00F6ffnen");
		mnDatei.add(mntmDateiffnen);
		
		mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnDatei.add(mntmBeenden);

		contentPane.add(scrollpane_table, BorderLayout.CENTER);

		btnRegisterLaden = new JButton("Register laden");
		btnRegisterLaden.setBounds(555, 39, 141, 29);
		contentPane.add(btnRegisterLaden);

		btnRegisterSpeichern = new JButton("Register speichern");
		btnRegisterSpeichern.setBounds(555, 66, 141, 29);
		contentPane.add(btnRegisterSpeichern);

		btnWeiter = new JButton("Weiter");
		btnWeiter.setBounds(10, 89, 91, 29);
		btnWeiter.setEnabled(false);
		contentPane.add(btnWeiter);

		lblErrorMsgs = new JLabel("Error.");
		lblErrorMsgs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblErrorMsgs.setVisible(false);
			}
		});
		lblErrorMsgs.setVisible(false);
		lblErrorMsgs.setIcon(new ImageIcon(PicSimView.class
				.getResource("/javax/swing/plaf/metal/icons/Warn.gif")));
		lblErrorMsgs.setBounds(10, 315, 458, 29);
		contentPane.add(lblErrorMsgs);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(501, 106, 194, 108);
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
		panel_3.setBounds(39, 84, 155, 24);
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
		panel_5.setBounds(501, 237, 194, 108);
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
		panel_9.setBounds(39, 84, 155, 24);
		panel_5.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel label_46 = new JLabel("");
		panel_9.add(label_46);

		label_47 = new JLabel("0");
		panel_9.add(label_47);

		JPanel panel_11 = new JPanel();
		panel_11.setBounds(10, 163, 366, 147);
		contentPane.add(panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		gbl_panel_11.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_11.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_11.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panel_11.setLayout(gbl_panel_11);

		JLabel lblWreg = new JLabel("W-Reg");
		GridBagConstraints gbc_lblWreg = new GridBagConstraints();
		gbc_lblWreg.insets = new Insets(0, 0, 5, 5);
		gbc_lblWreg.gridx = 0;
		gbc_lblWreg.gridy = 0;
		panel_11.add(lblWreg, gbc_lblWreg);

		lbl_wreg_value = new JLabel("00");
		GridBagConstraints gbc_lbl_wreg_value = new GridBagConstraints();
		gbc_lbl_wreg_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_wreg_value.gridx = 1;
		gbc_lbl_wreg_value.gridy = 0;
		panel_11.add(lbl_wreg_value, gbc_lbl_wreg_value);

		JLabel lblIrp = new JLabel("IRP");
		GridBagConstraints gbc_lblIrp = new GridBagConstraints();
		gbc_lblIrp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIrp.gridx = 4;
		gbc_lblIrp.gridy = 0;
		panel_11.add(lblIrp, gbc_lblIrp);

		JLabel lblRp_1 = new JLabel("RP1");
		GridBagConstraints gbc_lblRp_1 = new GridBagConstraints();
		gbc_lblRp_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRp_1.gridx = 5;
		gbc_lblRp_1.gridy = 0;
		panel_11.add(lblRp_1, gbc_lblRp_1);

		JLabel lblRp = new JLabel("RP0");
		GridBagConstraints gbc_lblRp = new GridBagConstraints();
		gbc_lblRp.insets = new Insets(0, 0, 5, 5);
		gbc_lblRp.gridx = 6;
		gbc_lblRp.gridy = 0;
		panel_11.add(lblRp, gbc_lblRp);

		JLabel lblTo = new JLabel("T0");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 7;
		gbc_lblTo.gridy = 0;
		panel_11.add(lblTo, gbc_lblTo);

		JLabel lblPd = new JLabel("PD");
		GridBagConstraints gbc_lblPd = new GridBagConstraints();
		gbc_lblPd.insets = new Insets(0, 0, 5, 5);
		gbc_lblPd.gridx = 8;
		gbc_lblPd.gridy = 0;
		panel_11.add(lblPd, gbc_lblPd);

		JLabel lblZ = new JLabel("Z");
		GridBagConstraints gbc_lblZ = new GridBagConstraints();
		gbc_lblZ.insets = new Insets(0, 0, 5, 5);
		gbc_lblZ.gridx = 9;
		gbc_lblZ.gridy = 0;
		panel_11.add(lblZ, gbc_lblZ);

		JLabel lblDc = new JLabel("DC");
		GridBagConstraints gbc_lblDc = new GridBagConstraints();
		gbc_lblDc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDc.gridx = 10;
		gbc_lblDc.gridy = 0;
		panel_11.add(lblDc, gbc_lblDc);

		JLabel lblC = new JLabel("C");
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 0);
		gbc_lblC.gridx = 11;
		gbc_lblC.gridy = 0;
		panel_11.add(lblC, gbc_lblC);

		JLabel lblFSR = new JLabel("FSR");
		GridBagConstraints gbc_lblFSR = new GridBagConstraints();
		gbc_lblFSR.insets = new Insets(0, 0, 5, 5);
		gbc_lblFSR.gridx = 0;
		gbc_lblFSR.gridy = 1;
		panel_11.add(lblFSR, gbc_lblFSR);

		JLabel lbl_FRS_value = new JLabel("00");
		GridBagConstraints gbc_lbl_FRS_value = new GridBagConstraints();
		gbc_lbl_FRS_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_FRS_value.gridx = 1;
		gbc_lbl_FRS_value.gridy = 1;
		panel_11.add(lbl_FRS_value, gbc_lbl_FRS_value);

		label_IRP_value = new JLabel("0");
		GridBagConstraints gbc_label_50 = new GridBagConstraints();
		gbc_label_50.insets = new Insets(0, 0, 5, 5);
		gbc_label_50.gridx = 4;
		gbc_label_50.gridy = 1;
		panel_11.add(label_IRP_value, gbc_label_50);

		label_RP1_value = new JLabel("0");
		GridBagConstraints gbc_label_49 = new GridBagConstraints();
		gbc_label_49.insets = new Insets(0, 0, 5, 5);
		gbc_label_49.gridx = 5;
		gbc_label_49.gridy = 1;
		panel_11.add(label_RP1_value, gbc_label_49);

		label_RP0_value = new JLabel("0");
		GridBagConstraints gbc_label_48 = new GridBagConstraints();
		gbc_label_48.insets = new Insets(0, 0, 5, 5);
		gbc_label_48.gridx = 6;
		gbc_label_48.gridy = 1;
		panel_11.add(label_RP0_value, gbc_label_48);

		label_T0_value = new JLabel("0");
		GridBagConstraints gbc_label_26 = new GridBagConstraints();
		gbc_label_26.insets = new Insets(0, 0, 5, 5);
		gbc_label_26.gridx = 7;
		gbc_label_26.gridy = 1;
		panel_11.add(label_T0_value, gbc_label_26);

		label_PD_value = new JLabel("0");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 8;
		gbc_label_4.gridy = 1;
		panel_11.add(label_PD_value, gbc_label_4);

		lbl_Z_value = new JLabel("00");
		GridBagConstraints gbc_lbl_Z_value = new GridBagConstraints();
		gbc_lbl_Z_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Z_value.gridx = 9;
		gbc_lbl_Z_value.gridy = 1;
		panel_11.add(lbl_Z_value, gbc_lbl_Z_value);

		lbl_DC_value = new JLabel("00");
		GridBagConstraints gbc_lbl_DC_value = new GridBagConstraints();
		gbc_lbl_DC_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_DC_value.gridx = 10;
		gbc_lbl_DC_value.gridy = 1;
		panel_11.add(lbl_DC_value, gbc_lbl_DC_value);

		lbl_C_value = new JLabel("00");
		GridBagConstraints gbc_lbl_C_value = new GridBagConstraints();
		gbc_lbl_C_value.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_C_value.gridx = 11;
		gbc_lbl_C_value.gridy = 1;
		panel_11.add(lbl_C_value, gbc_lbl_C_value);

		JLabel lblPcl = new JLabel("PCL");
		GridBagConstraints gbc_lblPcl = new GridBagConstraints();
		gbc_lblPcl.insets = new Insets(0, 0, 5, 5);
		gbc_lblPcl.gridx = 0;
		gbc_lblPcl.gridy = 2;
		panel_11.add(lblPcl, gbc_lblPcl);

		JLabel lbl_PCL_value = new JLabel("00");
		GridBagConstraints gbc_lbl_PCL_value = new GridBagConstraints();
		gbc_lbl_PCL_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_PCL_value.gridx = 1;
		gbc_lbl_PCL_value.gridy = 2;
		panel_11.add(lbl_PCL_value, gbc_lbl_PCL_value);

		JLabel lblGie = new JLabel("GIE");
		GridBagConstraints gbc_lblGie = new GridBagConstraints();
		gbc_lblGie.insets = new Insets(0, 0, 5, 5);
		gbc_lblGie.gridx = 4;
		gbc_lblGie.gridy = 2;
		panel_11.add(lblGie, gbc_lblGie);

		JLabel lblPie = new JLabel("PIE");
		GridBagConstraints gbc_lblPie = new GridBagConstraints();
		gbc_lblPie.insets = new Insets(0, 0, 5, 5);
		gbc_lblPie.gridx = 5;
		gbc_lblPie.gridy = 2;
		panel_11.add(lblPie, gbc_lblPie);

		JLabel lblTie = new JLabel("T0IE");
		GridBagConstraints gbc_lblTie = new GridBagConstraints();
		gbc_lblTie.insets = new Insets(0, 0, 5, 5);
		gbc_lblTie.gridx = 6;
		gbc_lblTie.gridy = 2;
		panel_11.add(lblTie, gbc_lblTie);

		JLabel lblInte = new JLabel("INTE");
		GridBagConstraints gbc_lblInte = new GridBagConstraints();
		gbc_lblInte.insets = new Insets(0, 0, 5, 5);
		gbc_lblInte.gridx = 7;
		gbc_lblInte.gridy = 2;
		panel_11.add(lblInte, gbc_lblInte);

		JLabel lblRbie = new JLabel("RBIE");
		GridBagConstraints gbc_lblRbie = new GridBagConstraints();
		gbc_lblRbie.insets = new Insets(0, 0, 5, 5);
		gbc_lblRbie.gridx = 8;
		gbc_lblRbie.gridy = 2;
		panel_11.add(lblRbie, gbc_lblRbie);

		JLabel lblTif = new JLabel("T0IF");
		GridBagConstraints gbc_lblTif = new GridBagConstraints();
		gbc_lblTif.insets = new Insets(0, 0, 5, 5);
		gbc_lblTif.gridx = 9;
		gbc_lblTif.gridy = 2;
		panel_11.add(lblTif, gbc_lblTif);

		JLabel lblIntf = new JLabel("INTF");
		GridBagConstraints gbc_lblIntf = new GridBagConstraints();
		gbc_lblIntf.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntf.gridx = 10;
		gbc_lblIntf.gridy = 2;
		panel_11.add(lblIntf, gbc_lblIntf);

		JLabel lblRbif = new JLabel("RBIF");
		GridBagConstraints gbc_lblRbif = new GridBagConstraints();
		gbc_lblRbif.insets = new Insets(0, 0, 5, 0);
		gbc_lblRbif.gridx = 11;
		gbc_lblRbif.gridy = 2;
		panel_11.add(lblRbif, gbc_lblRbif);

		JLabel lblPclath = new JLabel("PCLATH");
		GridBagConstraints gbc_lblPclath = new GridBagConstraints();
		gbc_lblPclath.insets = new Insets(0, 0, 5, 5);
		gbc_lblPclath.gridx = 0;
		gbc_lblPclath.gridy = 3;
		panel_11.add(lblPclath, gbc_lblPclath);

		JLabel lbl_PCLATH_value = new JLabel("00");
		GridBagConstraints gbc_lbl_PCLATH_value = new GridBagConstraints();
		gbc_lbl_PCLATH_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_PCLATH_value.gridx = 1;
		gbc_lbl_PCLATH_value.gridy = 3;
		panel_11.add(lbl_PCLATH_value, gbc_lbl_PCLATH_value);

		label_51 = new JLabel("0");
		GridBagConstraints gbc_label_51 = new GridBagConstraints();
		gbc_label_51.insets = new Insets(0, 0, 5, 5);
		gbc_label_51.gridx = 4;
		gbc_label_51.gridy = 3;
		panel_11.add(label_51, gbc_label_51);

		label_52 = new JLabel("0");
		GridBagConstraints gbc_label_52 = new GridBagConstraints();
		gbc_label_52.insets = new Insets(0, 0, 5, 5);
		gbc_label_52.gridx = 5;
		gbc_label_52.gridy = 3;
		panel_11.add(label_52, gbc_label_52);

		label_53 = new JLabel("0");
		GridBagConstraints gbc_label_53 = new GridBagConstraints();
		gbc_label_53.insets = new Insets(0, 0, 5, 5);
		gbc_label_53.gridx = 6;
		gbc_label_53.gridy = 3;
		panel_11.add(label_53, gbc_label_53);

		label_54 = new JLabel("0");
		GridBagConstraints gbc_label_54 = new GridBagConstraints();
		gbc_label_54.insets = new Insets(0, 0, 5, 5);
		gbc_label_54.gridx = 7;
		gbc_label_54.gridy = 3;
		panel_11.add(label_54, gbc_label_54);

		label_55 = new JLabel("0");
		GridBagConstraints gbc_label_55 = new GridBagConstraints();
		gbc_label_55.insets = new Insets(0, 0, 5, 5);
		gbc_label_55.gridx = 8;
		gbc_label_55.gridy = 3;
		panel_11.add(label_55, gbc_label_55);

		label_56 = new JLabel("0");
		GridBagConstraints gbc_label_56 = new GridBagConstraints();
		gbc_label_56.insets = new Insets(0, 0, 5, 5);
		gbc_label_56.gridx = 9;
		gbc_label_56.gridy = 3;
		panel_11.add(label_56, gbc_label_56);

		label_57 = new JLabel("0");
		GridBagConstraints gbc_label_57 = new GridBagConstraints();
		gbc_label_57.insets = new Insets(0, 0, 5, 5);
		gbc_label_57.gridx = 10;
		gbc_label_57.gridy = 3;
		panel_11.add(label_57, gbc_label_57);

		label_58 = new JLabel("0");
		GridBagConstraints gbc_label_58 = new GridBagConstraints();
		gbc_label_58.insets = new Insets(0, 0, 5, 0);
		gbc_label_58.gridx = 11;
		gbc_label_58.gridy = 3;
		panel_11.add(label_58, gbc_label_58);

		JLabel lblPc = new JLabel("PC");
		GridBagConstraints gbc_lblPc = new GridBagConstraints();
		gbc_lblPc.insets = new Insets(0, 0, 5, 5);
		gbc_lblPc.gridx = 0;
		gbc_lblPc.gridy = 4;
		panel_11.add(lblPc, gbc_lblPc);

		lbl_PC_value = new JLabel("0000");
		GridBagConstraints gbc_lbl_PC_value = new GridBagConstraints();
		gbc_lbl_PC_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_PC_value.gridx = 1;
		gbc_lbl_PC_value.gridy = 4;
		panel_11.add(lbl_PC_value, gbc_lbl_PC_value);

		JLabel lblStatus = new JLabel("Status");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 5;
		panel_11.add(lblStatus, gbc_lblStatus);

		lbl_Status_value = new JLabel("00");
		GridBagConstraints gbc_lbl_Status_value = new GridBagConstraints();
		gbc_lbl_Status_value.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Status_value.gridx = 1;
		gbc_lbl_Status_value.gridy = 5;
		panel_11.add(lbl_Status_value, gbc_lbl_Status_value);

		JLabel lblOption = new JLabel("Option");
		GridBagConstraints gbc_lblOption = new GridBagConstraints();
		gbc_lblOption.insets = new Insets(0, 0, 0, 5);
		gbc_lblOption.gridx = 0;
		gbc_lblOption.gridy = 6;
		panel_11.add(lblOption, gbc_lblOption);

		JLabel label_59 = new JLabel("00");
		GridBagConstraints gbc_label_59 = new GridBagConstraints();
		gbc_label_59.insets = new Insets(0, 0, 0, 5);
		gbc_label_59.gridx = 1;
		gbc_label_59.gridy = 6;
		panel_11.add(label_59, gbc_label_59);

		JLabel lblSerielleVerbindung = new JLabel("Serielle Verbindung:");
		lblSerielleVerbindung.setBounds(10, 148, 124, 14);
		contentPane.add(lblSerielleVerbindung);

		lblDisconnected = new JLabel("disconnected");
		lblDisconnected.setForeground(Color.RED);
		lblDisconnected.setBounds(144, 148, 78, 14);
		contentPane.add(lblDisconnected);

		choice = new JComboBox<String>();
		choice.setBounds(347, 102, 98, 20);
		contentPane.add(choice);

		breakpoints = new JComboBox<Integer>();
		breakpoints.setToolTipText("shows selected Breakpoints!");
		breakpoints.setBounds(335, 58, 89, 20);
		contentPane.add(breakpoints);

		JLabel lblBreakpoints = new JLabel("Selected Breakpoints");
		lblBreakpoints.setBounds(335, 41, 133, 14);
		contentPane.add(lblBreakpoints);
		
		listModelSTACK = new DefaultListModel<Integer>();
		 listSTACK = new JList<Integer>(listModelSTACK);
		 listSTACK.setBorder(new LineBorder(new Color(0, 0, 0)));
		 listSTACK.setBackground(UIManager.getColor("Button.background"));
		listSTACK.setBounds(386, 180, 105, 130);
		contentPane.add(listSTACK);
		
		JLabel lblStack = new JLabel("Stack");
		lblStack.setBounds(387, 163, 46, 14);
		contentPane.add(lblStack);
		
		 radioButtonComPort = new JRadioButton("");
		radioButtonComPort.setBounds(320, 101, 21, 23);
		contentPane.add(radioButtonComPort);
		
		 panel_portstatus = new JPanel();
		panel_portstatus.setBounds(447, 101, 21, 22);
		contentPane.add(panel_portstatus);
	}

	public boolean portComCheck(){
		if(radioButtonComPort.isSelected()){
			return true;
		} else {
			return false;
		}
	}
	
	public void stackAdd(int m){
		listModelSTACK.addElement(m);
	}
	
	public void stackClear(){
		listModelSTACK.removeAllElements();
	}
	
	public void initializeComMenu(String port) {
		choice.addItem(port);
	}

	public String selectedComPort() {
		return (String) choice.getSelectedItem();

	}

	public void updateBPCombobox() {
		breakpoints.setModel(new DefaultComboBoxModel<Integer>(breakpoint_list
				.toArray(new Integer[0])));
		list_renderer.setThings(breakpoint_list);
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

	public void set_Status_value(int s) {

		lbl_Status_value.setText(String.valueOf(s));
		if ((s & 0b00000001) == 1) {
			lbl_C_value.setText("1");
		} else {
			lbl_C_value.setText("0");
		}
		if ((s & 0b00000010) == 2) {
			lbl_DC_value.setText("1");
		} else {
			lbl_DC_value.setText("0");
		}
		if ((s & 0b00000100) == 4) {
			lbl_Z_value.setText("1");
		} else {
			lbl_Z_value.setText("0");
		}
		if ((s & 0b00001000) == 8) {
			label_PD_value.setText("1");
		} else {
			label_PD_value.setText("0");
		}
		if ((s & 0b00010000) == 16) {
			label_T0_value.setText("1");
		} else {
			label_T0_value.setText("0");
		}
		if ((s & 0b00100000) == 32) {
			label_RP0_value.setText("1");
		} else {
			label_RP0_value.setText("0");
		}
		if ((s & 0b01000000) == 64) {
			label_RP1_value.setText("1");
		} else {
			label_RP1_value.setText("0");
		}
		if ((s & 0b10000000) == 128) {
			label_IRP_value.setText("1");
		} else {
			label_IRP_value.setText("0");
		}

	}

	public void set_Intcon_value(int s) {
		if ((s & 0b00000001) == 1) {
			label_58.setText("1");
		} else {
			label_58.setText("0");
		}
		if ((s & 0b00000010) == 2) {
			label_57.setText("1");
		} else {
			label_57.setText("0");
		}
		if ((s & 0b00000100) == 4) {
			label_56.setText("1");
		} else {
			label_56.setText("0");
		}
		if ((s & 0b00001000) == 8) {
			label_55.setText("1");
		} else {
			label_55.setText("0");
		}
		if ((s & 0b00010000) == 16) {
			label_54.setText("1");
		} else {
			label_54.setText("0");
		}
		if ((s & 0b00100000) == 32) {
			label_53.setText("1");
		} else {
			label_53.setText("0");
		}
		if ((s & 0b01000000) == 64) {
			label_52.setText("1");
		} else {
			label_52.setText("0");
		}
		if ((s & 0b10000000) == 128) {
			label_51.setText("1");
		} else {
			label_51.setText("0");
		}

	}

	public void set_PC_value(String s) {
		lbl_PC_value.setText(s);
	}

	public void set_DC_value(String s) {
		lbl_DC_value.setText(s);
	}

	public void set_ErrorMsgs(String error) {
		lblErrorMsgs.setText(error);
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

	public int getSelectedLineNumb() {
		return list_code.getSelectedIndex();
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

	public void setChangeTableEntryListener(KeyListener l) {
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

	public void setComPortChange(ActionListener l) {
		choice.addActionListener(l);
	}

	public void setTrisALabels(int t) {
		// t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			lblI_7.setText("I");
		} else {
			lblI_7.setText("O");
		}
		if (b > 0) {
			lblI_6.setText("I");
		} else {
			lblI_6.setText("O");
		}
		if (c > 0) {
			lblI_5.setText("I");
		} else {
			lblI_5.setText("O");
		}
		if (d > 0) {
			lblI_4.setText("I");
		} else {
			lblI_4.setText("O");
		}
		if (e > 0) {
			lblI_3.setText("I");
		} else {
			lblI_3.setText("O");
		}
		if (f > 0) {
			lblI_2.setText("I");
		} else {
			lblI_2.setText("O");
		}
		if (g > 0) {
			lblI_1.setText("I");
		} else {
			lblI_1.setText("O");
		}
		if (h > 0) {
			lblI.setText("I");
		} else {
			lblI.setText("O");
		}
	}

	public void setPortALabels(int t) {
		// t = t & 0b11111111;
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
		// t = t & 0b11111111;
		int a = t & 0b00000001;
		int b = t & 0b00000010;
		int c = t & 0b00000100;
		int d = t & 0b00001000;
		int e = t & 0b00010000;
		int f = t & 0b00100000;
		int g = t & 0b01000000;
		int h = t & 0b10000000;
		if (a > 0) {
			label_35.setText("I");
		} else {
			label_35.setText("O");
		}
		if (b > 0) {
			label_34.setText("I");
		} else {
			label_34.setText("O");
		}
		if (c > 0) {
			label_33.setText("I");
		} else {
			label_33.setText("O");
		}
		if (d > 0) {
			label_32.setText("I");
		} else {
			label_32.setText("O");
		}
		if (e > 0) {
			label_31.setText("I");
		} else {
			label_31.setText("O");
		}
		if (f > 0) {
			label_30.setText("I");
		} else {
			label_30.setText("O");
		}
		if (g > 0) {
			label_29.setText("I");
		} else {
			label_29.setText("O");
		}
		if (h > 0) {
			label_28.setText("I");
		} else {
			label_28.setText("O");
		}
	}

	public void setPortBLabels(int t) {
		// TODO t = t & 0b11111111;
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
		String value = "";
		value = label_13.getText() + label_14.getText() + label_15.getText()
				+ label_16.getText() + label_17.getText() + label_18.getText()
				+ label_19.getText() + label_20.getText();

		int binary = 0;
		binary = Integer.parseInt(value, 2);
		System.out.println("binary: " + binary);
		return binary;
	}

	public int getValuePortB() {
		String value = "";
		value = label_38.getText() + label_39.getText() + label_40.getText()
				+ label_41.getText() + label_42.getText() + label_43.getText()
				+ label_44.getText() + label_45.getText();

		int binary = 0;
		binary = Integer.parseInt(value, 2);
		System.out.println("binary: " + binary);
		return binary;

	}

	/*public void setWuerfel(boolean a11, boolean a12, boolean a13, boolean a21,
			boolean a22, boolean a23, boolean a31, boolean a32, boolean a33) {
		if (a11) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a12) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a13) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a21) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a22) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a23) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a31) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a32) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
		if (a33) {
			radioButton.setSelected(true);
		} else {
			radioButton.setSelected(false);
		}
	}*/

	/*public int getAdressOfWuerfel() {
		String temp = textField.getText();
		int result = Integer.parseInt(temp);
		return result;
	}*/

	public void setSerialConnected() {
		lblDisconnected.setText("connected");
		lblDisconnected.setForeground(Color.black);
	}

	public void setSerialDisconnected() {
		lblDisconnected.setText("disconnected");
		lblDisconnected.setForeground(Color.red);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
