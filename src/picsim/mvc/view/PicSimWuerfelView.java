package picsim.mvc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class PicSimWuerfelView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9095640580082799139L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PicSimWuerfelView frame = new PicSimWuerfelView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PicSimWuerfelView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 89, 110);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(6, 5, 24, 26);
		contentPane.add(radioButton_1);

		JRadioButton radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(30, 5, 24, 26);
		contentPane.add(radioButton_4);

		JRadioButton radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(54, 5, 24, 26);
		contentPane.add(radioButton_5);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("");
		rdbtnNewRadioButton_1.setBounds(6, 29, 24, 26);
		contentPane.add(rdbtnNewRadioButton_1);

		JRadioButton radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(30, 29, 24, 26);
		contentPane.add(radioButton_3);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		rdbtnNewRadioButton.setBounds(54, 29, 24, 26);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(6, 53, 24, 26);
		contentPane.add(radioButton);

		JRadioButton radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(30, 53, 24, 26);
		contentPane.add(radioButton_2);

		JRadioButton radioButton_8 = new JRadioButton("");
		radioButton_8.setBounds(54, 53, 24, 26);
		contentPane.add(radioButton_8);
	}
}
