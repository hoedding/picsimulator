package picsimulator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frame_trisA extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private MainFrame frame;

	public frame_trisA(final MainFrame frame) {
		this.frame = frame;
		setTitle("TRIS A");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 205, 115);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(6, 5, 24, 26);
		contentPane.add(radioButton_1);

		final JRadioButton radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(30, 5, 24, 26);
		contentPane.add(radioButton_2);

		final JRadioButton radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(54, 5, 24, 26);
		contentPane.add(radioButton_3);

		final JRadioButton radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(78, 5, 24, 26);
		contentPane.add(radioButton_4);

		final JRadioButton radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(102, 5, 24, 26);
		contentPane.add(radioButton_5);

		final JRadioButton radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(126, 5, 24, 26);
		contentPane.add(radioButton_6);

		final JRadioButton radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(150, 5, 24, 26);
		contentPane.add(radioButton_7);

		final JRadioButton radioButton_8 = new JRadioButton("");
		radioButton_8.setBounds(174, 5, 24, 26);
		contentPane.add(radioButton_8);
		
		textField = new JTextField();
		textField.setBounds(119, 31, 86, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSpeicherRa = new JLabel("Speicher RA (HEX)");
		lblSpeicherRa.setBounds(6, 37, 111, 16);
		contentPane.add(lblSpeicherRa);
		
		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int trisA;
				if(radioButton_1.isSelected()){trisA=0b10000000;}else{trisA=0b0;}
				if(radioButton_2.isSelected()){trisA=trisA+0b1000000;}else{trisA=trisA+0b0;}
				if(radioButton_3.isSelected()){trisA=trisA+0b100000;}else{trisA=trisA+0b0;}
				if(radioButton_4.isSelected()){trisA=trisA+0b10000;}else{trisA=trisA+0b0;}
				if(radioButton_5.isSelected()){trisA=trisA+0b1000;}else{trisA=trisA+0b0;}
				if(radioButton_6.isSelected()){trisA=trisA+0b100;}else{trisA=trisA+0b0;}
				if(radioButton_7.isSelected()){trisA=trisA+0b10;}else{trisA=trisA+0b0;}
				if(radioButton_8.isSelected()){trisA=trisA+0b1;}else{trisA=trisA+0b0;}
				System.out.println(trisA);
				if(textField.getText()==""){
									
				}else {
					frame.add_to_register(Integer.parseInt(textField.getText()), trisA);
				}
			}
		});
		btnSpeichern.setBounds(45, 64, 117, 29);
		contentPane.add(btnSpeichern);
	}
}
