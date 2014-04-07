package picsimulator;

import java.util.ArrayList;
import java.util.List;

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
		case 9:	do_comf(_hex1); break;
		case 3: do_decf(_hex1); break;
		case 11: do_decfsz(_hex1); break;
		case 10: do_incf(_hex1); break;
		case 15: do_incfsz(_hex1); break;
		case 4: do_iorwf(_hex1); break;
		case 8: do_movf(_hex1); break;
		case 13: do_rlf(_hex1); break;
		case 12: do_rrf(_hex1); break;
		case 2: do_subwf(_hex1); break;
		case 14: do_swapf(_hex1); break;
		case 6: do_xorwf(_hex1); break;
		case 57: do_andlw(_hex1); break;
		case 56: do_iorlw(_hex1); break;
		case 58: do_xorlw(_hex1); break;
		
		}
		
		int hex2 = code_as_int & 0b0000000001111111;
		int _hex2 = code_as_int & 0b1111111110000000;
		switch (hex2) {
		case 3: do_clrf(_hex2); break;
		case 2: do_clrw(_hex2); break;
		case 1: do_movwf(_hex2); break;
		case 0: do_nop(); break;
		}
		
		int hex3 = code_as_int & 0b0000001111111111;
		int _hex3 = code_as_int & 0b1111110000000000;
		switch(hex3){
		case 4: do_bcf(_hex3); break;
		case 5: do_bsf(_hex3); break;
		case 6: do_btfsc(_hex3); break;
		case 7: do_btfss(_hex3); break;
		case 12: do_movlw(_hex3); break;
		case 23: do_retlw(_hex3); break;
		}
		
		int hex4 = code_as_int & 0b0000000111111111;
		int _hex4 = code_as_int & 0b1111111000000000;
		switch(hex4){
		case 31: do_addlw(_hex4); break;
		case 30: do_sublw(_hex4); break;
		}
		
		int hex5 = code_as_int & 0b0000011111111111;
		int _hex5 = code_as_int & 0b1111100000000000;
		switch(hex5){
		case 4: do_call(_hex5); break;
		case 5: do_goto(_hex5); break;
		}
		
		int hex6 = code_as_int & 0x0000;
		switch(hex6){
		case 100: do_clrwdt(); break;
		case 9: do_retfie(); break;
		case 8: do_return(); break;
		case 99: do_sleep(); break;
		
		}
		
		
		
	}

	private void do_sleep() {
		// TODO Auto-generated method stub
		
	}

	private void do_return() {
		// TODO Auto-generated method stub
		
	}

	private void do_retfie() {
		// TODO Auto-generated method stub
		
	}

	private void do_clrwdt() {
		// TODO Auto-generated method stub
		
	}

	private void do_goto(int _hex5) {
		// TODO Auto-generated method stub
		
	}

	private void do_call(int _hex5) {
		// TODO Auto-generated method stub
		
	}

	private void do_sublw(int _hex4) {
		// TODO Auto-generated method stub
		
	}

	private void do_addlw(int _hex4) {
		// TODO Auto-generated method stub
		
	}

	private void do_retlw(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_movlw(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_xorlw(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_iorlw(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_andlw(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_btfss(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_btfsc(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_bsf(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_bcf(int _hex3) {
		// TODO Auto-generated method stub
		
	}

	private void do_nop() {
		// TODO Auto-generated method stub
		
	}

	private void do_movwf(int _hex2) {
		// TODO Auto-generated method stub
		
	}

	private void do_xorwf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_swapf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_subwf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_rrf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_rlf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_movf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_iorwf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_incfsz(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_incf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_decfsz(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_decf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_comf(int _hex1) {
		// TODO Auto-generated method stub
		
	}

	private void do_clrw(int _hex2) {
		// TODO Auto-generated method stub
		
	}

	private void do_addwf(int a) {
		// TODO Auto-generated method stub
	}

	private void do_andwf(int a) {
		// TODO Auto-generated method stub
	}	
	private void do_clrf(int a){
		// TODO Auto-generated method stub
	}
}
