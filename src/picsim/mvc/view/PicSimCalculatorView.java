package picsim.mvc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PicSimCalculatorView extends JFrame {

	private static final long serialVersionUID = 3134782070485749870L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnHexBinary;
	private JRadioButton rdbtnBinaryHex;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PicSimCalculatorView frame = new PicSimCalculatorView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PicSimCalculatorView() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 224, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEingabe = new JLabel("Eingabe:");
		lblEingabe.setBounds(6, 18, 61, 16);
		contentPane.add(lblEingabe);

		rdbtnHexBinary = new JRadioButton("Hex -> Binary");
		rdbtnHexBinary.setSelected(true);
		buttonGroup.add(rdbtnHexBinary);
		rdbtnHexBinary.setBounds(76, 43, 141, 23);
		contentPane.add(rdbtnHexBinary);

		rdbtnBinaryHex = new JRadioButton("Binary -> Hex");
		buttonGroup.add(rdbtnBinaryHex);
		rdbtnBinaryHex.setBounds(76, 72, 141, 23);
		contentPane.add(rdbtnBinaryHex);

		textField = new JTextField();
		textField.setBounds(79, 12, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblErgebnis = new JLabel("Ergebnis:");
		lblErgebnis.setBounds(6, 139, 61, 16);
		contentPane.add(lblErgebnis);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(79, 140, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnBerechnen = new JButton("Berechnen");
		btnBerechnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnHexBinary.isSelected()) {
					String hexToBin = new BigInteger(textField.getText(), 16)
							.toString(2);
					textField_1.setText(hexToBin);
				}
				if (rdbtnBinaryHex.isSelected()) {
					String binToHex = Long.toHexString(Long.parseLong(
							textField.getText(), 2));
					textField_1.setText(binToHex);
				}
			}
		});
		btnBerechnen.setBounds(86, 99, 117, 29);
		contentPane.add(btnBerechnen);
	}
}