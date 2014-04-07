package picsimulator;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Simulator_Logik {
	public MainFrame frame;
	public String befehlscode;
	public String speicherstelle;

	public int w_register = 0b00001010;
	public int fsr = 0b00000000;
	public int pcl = 0b00000000;
	public int pclath = 0b00000000;
	// public int PC = 0b00000000;
	public int status = 0b00000000;
	public int irp = 0b00000000;
	public int rp1 = 0b00000000;
	public int rp0 = 0b00000000;
	public int t0 = 0b00000000;
	public int pd = 0b00000000;
	public int z = 0b00000000;
	public int dc = 0b00000000;
	public int c = 0b00000000;
	public int option = 0b00000000;
	public int rbp = 0b00000000;
	public int intEdg = 0b00000000;
	public int tocs = 0b00000000;
	public int tose = 0b00000000;
	public int psa = 0b00000000;
	public int ps2 = 0b00000000;
	public int ps1 = 0b00000000;
	public int ps0 = 0b00000000;
	public int gie = 0b00000000;
	public int pie = 0b00000000;
	public int t0ie = 0b00000000;
	public int inte = 0b00000000;
	public int rbie = 0b00000000;
	public int t0if = 0b00000000;
	public int intf = 0b00000000;
	public int rbif = 0b00000000;
	/* Das Ding enthält nur noch den Befehlscode */
	public List<String> list_analyzed_code;
	
	int[] register_array = new int[256];

	public Simulator_Logik(MainFrame _frame) {
		frame = _frame;
		list_analyzed_code = new ArrayList<>();
	}

	public void doSth() {
		frame.txtProgrammCounter.setText("text");
	}

	public void analyze_code(String _codezeile) {
		String codezeile = _codezeile;

		StringBuffer buffer = new StringBuffer(codezeile);
		int i;
		for (i = 0; i < 5; i++) {
			buffer.deleteCharAt(0);
		}

		String s = "";
		for (i = 0; i < 4; i++) {
			s = s + buffer.charAt(i);
		}
		list_analyzed_code.add(s);

	}

	public void start_programm() {
		int i;
		
		for (i = 0; i < list_analyzed_code.size(); i++) {
			what_to_do(list_analyzed_code.get(i));
		}
		
	}

	public void what_to_do(String code) {
		int code_as_int = Integer.parseInt(code);
		int hex1 = code_as_int & 0x00ff;
		int _hex1 = code_as_int & 0xff00;
		switch (hex1) {
		case 7: do_addwf(_hex1); break;
		case 5:	do_andwf(_hex1); break;
		case 1:	do_clrf(_hex1); break;
				default: break;
		}
		
	}

	private void do_addwf(int a) {
		String add1 = c + d;
		int a = Integer.parseInt(add1, 2) + w_register;
		String output = String.valueOf(a);
		frame.lbl_wreg_value.setText(output);
		w_register = a;
	}

	private void do_andwf(int a) {
		String add1 = c + d;
		int a = Integer.parseInt(add1, 2);
		int b = a & w_register;
		String output = String.valueOf(b);
		System.out.println(output);
		frame.lbl_wreg_value.setText(output);
	}	
	private void do_clrf(int a){
		String add1 = c + d;
		add1 = add1.substring(1);
		System.out.println(add1);
		int a = Integer.parseInt(add1,2);
		String b = Integer.toHexString(a);
		System.out.println(a);
		
	}
}
