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

	public int status = 0b00000000;
	public int irp = 0b00000000;
	public int rp1 = 0b00000000;
	public int rp0 = 0b00000000;
	public int t0 = 0b00000000;
	public int pd = 0b00000000;
	public boolean z = false;
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
	public int sprung;
	private int PC = 0;

	public List<Integer> STACK = new ArrayList<Integer>();

	public int[] register_array = new int[256];
	private List<Code> code_list = new ArrayList<Code>();

	public MainFrame_Logik logik;

	public Simulator_Logik(MainFrame _frame, MainFrame_Logik _logik) {
		frame = _frame;
		logik = _logik;
		initialize_register_array();
	}

	/* Getter Setter für Programm counter */
	public void setProgramCounter(int i) {
		PC = i;
	}

	public int getProgrammCounter() {
		return PC;
	}

	/* ######## ARRAY FüR DAS REGISTER ####### */
	public void initialize_register_array() {
		register_array = new int[256];
		int m;
		for (m = 0; m < 256; m++) {
			register_array[m] = 0;
		}
	}

	public void setRegisterEntry(int index, int value) {
		register_array[index] = value;
	}

	public int getRegisterEntry(int index) {
		return register_array[index];
	}

	/* ######## LISTE MIT EINZELNEN CODE-ELEMENTEN ####### */
	public void setCode(int _code) {
		Code code_element = new Code();
		code_element.code = _code;
		code_element.line_number = code_list.size() + 1;
		code_list.add(code_element);
	}

	/*
	 * ######## Löscht alles außer die relevanten 16 Bit und speichert den
	 * Programmcode über setCode() in code_list ########
	 */
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
		int code = Integer.parseInt(s, 16);
		setCode(code);
	}

	/*
	 * ######## Führt für jedes in der code_list enthaltene Elemente die
	 * Funktion what_to_do() aus #######
	 */
	public void start_programm() throws InterruptedException {
		if (getProgrammCounter() == code_list.size()) {
			gui_aktualisieren();
			Thread.sleep(500);
			setProgramCounter(0);
			count_se_pr0gram();
		} else {
			gui_aktualisieren();
			Thread.sleep(500);
			count_se_pr0gram();
		}
	}

	public void count_se_pr0gram() throws InterruptedException {

		System.out.println(code_list.get(getProgrammCounter()).code);
		what_to_do(code_list.get(getProgrammCounter()).code);
		setProgramCounter(getProgrammCounter()+1);;
		
	}

	/* ######## Tatsächliche Pic-Befehle ####### */
	public void what_to_do(int code) throws InterruptedException {
		int code_as_int = code;

		int hex5 = code_as_int & 0b1111100000000000;
		int _hex5 = code_as_int & 0b0000011111111111;
		switch (hex5) {
		case 8192:
			do_call(_hex5);
			break;
		case 10240:
			do_goto(_hex5);
			break;
		}

		int hex6 = code_as_int & 0b1111110000000000;
		int _hex6 = code_as_int & 0b0000001111111111;
		switch (hex6) {
		case 4096:
			do_bcf(_hex6);
			break;
		case 5120:
			do_bsf(_hex6);
			break;
		case 6144:
			do_btfsc(_hex6);
			break;
		case 7168:
			do_btfss(_hex6);
			break;
		case 12288:
			do_movlw(_hex6);
			break;
		case 13312:
			do_retlw(_hex6);
			break;
		}

		int hex7 = code_as_int & 0b1111111000000000;
		int _hex7 = code_as_int & 0b0000000111111111;
		switch (hex7) {
		case 15872:
			do_addlw(_hex7);
			break;
		case 15360:
			do_sublw(_hex7);
			break;
		}

		int hex8 = code_as_int & 0b1111111100000000;
		int _hex8 = code_as_int & 0b0000000011111111;
		switch (hex8) {
		case 1792:
			do_addwf(_hex8);
			break;
		case 1280:
			do_andwf(_hex8);
			break;
		case 2304:
			do_comf(_hex8);
			break;
		case 768:
			do_decf(_hex8);
			break;
		case 2816:
			do_decfsz(_hex8);
			break;
		case 2560:
			do_incf(_hex8);
			break;
		case 3840:
			do_incfsz(_hex8);
			break;
		case 1024:
			do_iorwf(_hex8);
			break;
		case 2048:
			do_movf(_hex8);
			break;
		case 3328:
			do_rlf(_hex8);
			break;
		case 3072:
			do_rrf(_hex8);
			break;
		case 512:
			do_subwf(_hex8);
			break;
		case 3584:
			do_swapf(_hex8);
			break;
		case 1536:
			do_xorwf(_hex8);
			break;
		case 14592:
			do_andlw(_hex8);
			break;
		case 14336:
			do_iorlw(_hex8);
			break;
		case 14848:
			do_xorlw(_hex8);
			break;

		}

		int hex16 = code_as_int & 0b0000000000000000;
		switch (hex16) {
		case 100:
			do_clrwdt();
			break;
		case 9:
			do_retfie();
			break;
		case 8:
			do_return();
			break;
		case 99:
			do_sleep();
			break;
		}

		int hex9 = code_as_int & 0b1111111110000000;
		int _hex9 = code_as_int & 0b0000000001111111;
		switch (hex9) {
		case 384:
			do_clrf(_hex9);
			break;
		case 256:
			do_clrw(_hex9);
			break;
		case 128:
			do_movwf(_hex9);
			break;
		case 0:
			do_nop();
			break;
		}

	}

	private void do_sleep() {
		// TODO standby mode ??

	}

	private void do_return() throws InterruptedException {
		setProgramCounter(sprung);
		
	}

	private void do_retfie() {
		// TODO Return from Interrupt

	}

	private void do_clrwdt() {
		// TODO Clear WatchDog Timer

	}

	private void do_goto(int _hex5) {
		sprung = getProgrammCounter();
		System.out.println(getProgrammCounter());
		setProgramCounter(_hex5);
		System.out.println(getProgrammCounter());
	}

	private void do_call(int _hex5) {
		// TODO Auto-generated method stub
		System.out.println("call");

	}

	private void do_sublw(int _hex4) {

		int temp = _hex4 & 0b011111111;
		w_register = temp - w_register;

		gui_aktualisieren();

	}

	private void do_addlw(int _hex4) {

		int temp = _hex4 & 0b011111111;
		w_register = temp + w_register;

		gui_aktualisieren();

	}

	private void do_retlw(int _hex3) {

		int temp = _hex3 & 0b0011111111;
		w_register = temp;

		setProgramCounter(STACK.get(STACK.size() - 1));

		gui_aktualisieren();
	}

	private void do_movlw(int _hex3) {

		int temp = _hex3 & 0b0011111111;
		w_register = temp;

		gui_aktualisieren();
	}

	private void do_xorlw(int _hex1) {

		w_register = w_register ^ _hex1;

		gui_aktualisieren();

	}

	private void do_iorlw(int _hex1) {

		w_register = w_register | _hex1;

		gui_aktualisieren();

	}

	private void do_andlw(int _hex1) {

		w_register = w_register & _hex1;

		gui_aktualisieren();

	}

	private void do_btfss(int _hex3) {
		if (register_array[_hex3] > 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();
	}

	private void do_btfsc(int _hex3) {
		if (register_array[_hex3] == 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();

	}

	private void do_bsf(int _hex3) {
		register_array[_hex3] = 1;
		gui_aktualisieren();

	}

	private void do_bcf(int _hex3) {
		register_array[_hex3] = 0;
		gui_aktualisieren();

	}

	private void do_nop() {

		gui_aktualisieren();

	}

	private void do_movwf(int _hex2) {
		register_array[_hex2] = w_register;
		gui_aktualisieren();

	}

	private void do_xorwf(int _hex1) {

		int temp = _hex1 & 0b10000000;
		int _temp = _hex1 & 0b01111111;

		if (temp == 0) {
			w_register = w_register | _temp;
		} else {
			register_array[_temp] = w_register | _temp;
		}

		gui_aktualisieren();

	}

	private void do_swapf(int _hex1) {

		int temp = _hex1 & 0b10000000;
		int _temp = _hex1 & 0b01111111;

		int nibble1 = (register_array[_temp] & 0b00001111) * 16;
		int nibble2 = (register_array[_temp] & 0b11110000) / 16;

		int ergebnis = nibble1 + nibble2;

		if (temp == 0) {

			w_register = ergebnis;

		} else {
			register_array[_temp] = ergebnis;
		}

		gui_aktualisieren();

	}

	private void do_subwf(int _hex1) {
		register_array[_hex1] = register_array[_hex1] - w_register;
		gui_aktualisieren();
	}

	private void do_rrf(int _hex1) {

		int temp = _hex1 & 0b10000000;

		gui_aktualisieren();
	}

	private void do_rlf(int _hex1) {
		// TODO Auto-generated method stub

		gui_aktualisieren();

	}

	private void do_movf(int _hex1) {
		int temp = _hex1 & 0b10000000;
		int _temp = _hex1 & 0b01111111;
		if (temp == 1) {
			register_array[_temp] = _temp;
		} else {
			w_register = _hex1;
		}
		gui_aktualisieren();
	}

	private void do_iorwf(int _hex1) {
		// TODO Auto-generated method stub

		gui_aktualisieren();

	}

	private void do_incfsz(int _hex1) {
		// TODO wie funktioniert incfsz

		gui_aktualisieren();
	}

	private void do_incf(int _hex1) {
		register_array[_hex1] = register_array[_hex1] + 1;

	}

	private void do_decfsz(int _hex1) {
		int temp = register_array[_hex1] - 1;
		register_array[_hex1] = temp;
		if (temp == 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		// TODO wie funktioniert decfsz

		gui_aktualisieren();
	}

	private void do_decf(int _hex1) {
		register_array[_hex1] = register_array[_hex1] - 1;
		change_z();
		gui_aktualisieren();
	}

	private void do_comf(int _hex1) {
		// TODO was ist complement ?

		gui_aktualisieren();

	}

	private void do_clrw(int _hex2) {
		w_register = 0b0;
		change_z();
		gui_aktualisieren();

	}

	private void do_addwf(int a) {
		w_register = w_register + a;
		change_z();
		gui_aktualisieren();
	}

	private void do_andwf(int a) {
		w_register = w_register & a;
		change_z();
		gui_aktualisieren();
	}

	private void do_clrf(int a) {
		register_array[a] = 0b0;
		change_z();
		gui_aktualisieren();
	}
	public void gui_aktualisieren(){
		/*W-Register in GUI setzen*/
	frame.lbl_wreg_value.setText(String.valueOf(w_register).toString());
	 	/*Aktualisieren der Tabelle mit den Werten aus Register_Array*/
	tabelle_aktualisieren();
		/*z überprüfen ob true/false*/
	if(z=true){frame.label_z_value.setText("1");}else{frame.label_z_value.setText("0");}
		/*Aktuellen Code in Liste markieren*/
	frame.txtProgrammCounter.setText(String.valueOf(PC+1).toString());
	}
		
	public void tabelle_aktualisieren(){
	
			int m1 = 0, n1 = 0, t1 = 0;
			while (m1 < 256) {
				while (t1 < 8) {
					/* Tabelle bekommt Werte aus Array zugewiesen */
					
					frame.table_model.setValueAt(register_array[m1],
							n1, t1);
					t1++;
					m1++;
				}
				t1 = 0;
				n1++;
		}
		/* Aktuellen Code in Liste markieren */
		frame.txtProgrammCounter.setText(String.valueOf(PC).toString());
	}

	public void tabelle_aktualisieren() {

		int m1 = 0, n1 = 0, t1 = 0;
		while (m1 < 256) {
			while (t1 < 8) {
				/* Tabelle bekommt Werte aus Array zugewiesen */

				frame.table_model.setValueAt(register_array[m1], n1, t1);
				t1++;
				m1++;
			}
			t1 = 0;
			n1++;
		}
	}

	public void change_z() {
		if (z = true) {
			z = false;
		} else {
			if (z = false) {
				z = true;
			}
		}
	}
	public void write_to_register(int adress, int value){
		if(adress==0){
			adress = 4;
		} 
		register_array[adress]=value;
	}
	
	public int get_C(){
		int _temp = register_array[3] & 0b00000001;
		return _temp;
	}
}
