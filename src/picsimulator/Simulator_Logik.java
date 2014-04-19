package picsimulator;

import java.util.ArrayList;
import java.util.List;

public class Simulator_Logik {
	public MainFrame frame;

	public int w_register = 0b0;

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
			Thread.sleep(100);
			setProgramCounter(0);
			count_se_pr0gram();
		} else {
			gui_aktualisieren();
			Thread.sleep(100);
			count_se_pr0gram();
		}
	}

	public void count_se_pr0gram() throws InterruptedException {

		what_to_do(code_list.get(getProgrammCounter()).code);
		setProgramCounter(getProgrammCounter() + 1);
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
		// TODO clear bit PD
		// TODO clear bit TO
		// TODO clear bit Watchdog
		// TODO processor is put to sleep
		// TODO oscillator stopped
		// Section 14.8 for more info

		System.out.println("SLEEP");

	}

	private void do_return() throws InterruptedException {
		System.out.println("RETURN");
		int adress = STACK.get(STACK.size() - 1);
		STACK.remove(STACK.size() - 1);
		setProgramCounter(adress);
		gui_aktualisieren();
	}

	private void do_retfie() {
		System.out.println("RETFIE");
		int adress = STACK.get(STACK.size() - 1);
		STACK.remove(STACK.size() - 1);
		setProgramCounter(adress);
		// Global Interrupt auf enable
		set_Bit(8, 8);
		gui_aktualisieren();
	}

	private void do_clrwdt() {
		System.out.println("CLRWDT");
		// TODO Clear WatchDog Timer
		change_Z();
		gui_aktualisieren();

	}

	private void do_goto(int _hex5) {
		System.out.println("GOTO: " + _hex5 + "-1");
		sprung = getProgrammCounter();
		setProgramCounter(_hex5 - 1);
		gui_aktualisieren();
	}

	private void do_call(int _hex5) {
		System.out.println("CALL: " + _hex5);
		STACK.add(getProgrammCounter());
		setProgramCounter(_hex5 - 1);
		gui_aktualisieren();
	}

	private void do_sublw(int _hex4) {
		System.out.println("SUBLW");
		int temp = _hex4 & 0b011111111;
		w_register = temp - w_register;
		change_Z();
		change_C();
		change_DC();
		gui_aktualisieren();

	}

	private void do_addlw(int _hex4) {
		System.out.println("ADDLW");
		int temp = _hex4 & 0b011111111;
		w_register = temp + w_register;
		change_Z();
		change_C();
		change_DC();
		gui_aktualisieren();

	}

	private void do_retlw(int _hex3) {
		System.out.println("RETLW");
		int temp = _hex3 & 0b0011111111;
		w_register = temp;
		setProgramCounter(STACK.get(STACK.size() - 1));
		gui_aktualisieren();
	}

	private void do_movlw(int _hex3) {
		System.out.println("MOVLW");
		int value = _hex3 & 0b0011111111;
		w_register = value;

		gui_aktualisieren();
	}

	private void do_xorlw(int _hex1) {
		System.out.println("XORLW");
		w_register = w_register ^ _hex1;
		change_Z();
		gui_aktualisieren();

	}

	private void do_iorlw(int _hex1) {
		System.out.println("IORLW");
		w_register = w_register | _hex1;
		change_Z();
		gui_aktualisieren();

	}

	private void do_andlw(int _hex1) {
		System.out.println("ANDLW");
		w_register = w_register & _hex1;
		change_Z();
		gui_aktualisieren();

	}

	private void do_btfss(int _hex3) {
		System.out.println("BTFSS");
		int adress = _hex3 & 0b0001111111;
		int bit = _hex3 & 0b1110000000;
		if (is_bit_set(bit, adress)) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();
	}

	private void do_btfsc(int _hex3) {
		System.out.println("BTFSC");
		int adress = _hex3 & 0b0001111111;
		int bit = _hex3 & 0b1110000000;
		if (!is_bit_set(bit, adress)) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();

	}

	private void do_bsf(int _hex3) {
		int bit = _hex3 & 0b1110000000;
		int adress = _hex3 & 0b0001111111;
		set_Bit(bit, adress);
		gui_aktualisieren();
	}

	private void do_bcf(int _hex3) {
		int bit = _hex3 & 0b1110000000;
		int adress = _hex3 & 0b0001111111;
		clear_Bit(bit, adress);
		gui_aktualisieren();
	}

	private void do_nop() {
		System.out.println("NOP");
	}

	private void do_movwf(int _hex2) {
		System.out.println("MOVWF");
		setRegisterEntry(_hex2, w_register);
		gui_aktualisieren();
	}

	private void do_xorwf(int _hex1) {
		System.out.println("XORWF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);

		if (d == 0) {
			w_register = w_register | value;
		} else {
			setRegisterEntry(adress, (w_register | value));
		}
		change_Z();
		gui_aktualisieren();

	}

	private void do_swapf(int _hex1) {
		System.out.println("SWAPF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;

		int nibble1 = (getRegisterEntry(adress) & 0b00001111) * 16;
		int nibble2 = (getRegisterEntry(adress) & 0b11110000) / 16;

		int ergebnis = nibble1 + nibble2;

		if (d == 0) {

			w_register = ergebnis;

		} else {
			setRegisterEntry(adress, ergebnis);
			}

		gui_aktualisieren();

	}

	private void do_subwf(int adress) {
		System.out.println("SUBWF");
		setRegisterEntry(adress, (getRegisterEntry(adress) - w_register));
		change_Z();
		change_C();
		change_DC();
		gui_aktualisieren();
	}

	private void do_rrf(int _hex1) {
		System.out.println("RRF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);

		if (get_C() == 1) {
			value += 256;
		}

		if ((value & 0b000000001) == 1) {
			change_C();
			value = value & 0b111111110;
		}

		value = value >> 1;

		if (d == 0) {

			w_register = value;

		} else {

			setRegisterEntry(adress, value);
		}

		gui_aktualisieren();
	}

	private void do_rlf(int _hex1) {
		System.out.println("RLF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);

		value = value << 1;

		if (get_C() == 1) {
			value += 1;
		}

		if ((value & 0b100000000) == 256) {
			change_C();
			value = value & 0b011111111;
		}

		if (d == 0) {

			w_register = value;

		} else {

			setRegisterEntry(adress, value);
		}

		gui_aktualisieren();

	}

	private void do_movf(int _hex1) {
		System.out.println("MOVF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;

		if (d == 0) {
			w_register = getRegisterEntry(adress);
		} 
		change_Z();
		gui_aktualisieren();
	}

	private void do_iorwf(int _hex1) {
		System.out.println("IORWF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress) | w_register;

		if (d == 0) {

			w_register = value;

		}

		else {
			setRegisterEntry(adress, value);
		}
		change_Z();
		gui_aktualisieren();

	}

	private void do_incfsz(int _hex1) {
		System.out.println("INCFSZ");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int decr = getRegisterEntry(adress) + 1;

		if (d == 128) {
			setRegisterEntry(adress, decr);
		} else {

			w_register = decr;
		}
		if (decr == 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();
	}

	private void do_incf(int _hex1) {
		System.out.println("INCF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		if (d == 0) {
			w_register = getRegisterEntry(adress) + 1;
		} else {
			setRegisterEntry(adress, (getRegisterEntry(adress)+1));
					}
		change_Z();
		gui_aktualisieren();
	}

	private void do_decfsz(int _hex1) {

		System.out.println("DECFSZ");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int decr = getRegisterEntry(adress) - 1;

		if (d == 128) {
			setRegisterEntry(adress, decr);
		} else {

			w_register = decr;
		}
		if (decr == 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}
		gui_aktualisieren();
	}

	private void do_decf(int adress) {
		System.out.println("DECF");
		setRegisterEntry(adress, (getRegisterEntry(adress)-1));
		change_Z();
		gui_aktualisieren();
	}

	private void do_comf(int _hex1) {
		System.out.println("COMF");
		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = ~getRegisterEntry(adress);
		if (d == 0) {
			w_register = value;
		} else {
			setRegisterEntry(adress, value);
		}
		change_Z();
		gui_aktualisieren();

	}

	private void do_clrw(int _hex2) {
		System.out.println("CLRW");
		w_register = 0b0;
		change_Z();
		gui_aktualisieren();

	}

	private void do_addwf(int a) {
		System.out.println("ADDWF");
		int d = a & 0b10000000;
		int adress = a & 0b01111111;
		int value = getRegisterEntry(adress);
		if (d == 0) {
			w_register = w_register + value;
		} else {
			setRegisterEntry(adress, (w_register + value));
		}
		change_Z();
		change_DC();
		change_C();
		gui_aktualisieren();
	}

	private void do_andwf(int a) {
		System.out.println("ANDWF");
		int d = a & 0b10000000;
		int adress = a & 0b01111111;
		int value = getRegisterEntry(adress);
		if (d == 0) {
			w_register = w_register + value;
		} else {
			setRegisterEntry(adress, (w_register + value));
		}
		change_Z();
		gui_aktualisieren();
	}

	private void do_clrf(int a) {
		System.out.println("CLRF");
		register_array[a - 1] = 0b0;
		change_Z();
		gui_aktualisieren();
	}

	public void gui_aktualisieren() {
		/* aktuell ausgeführten Code markieren */
		frame.list_code.setSelectedIndex(PC);
		/* Erweiterungen aktualisieren */
		elements_aktualisieren();
		/* W-Register in GUI setzen */
		frame.lbl_wreg_value.setText(String.valueOf(w_register).toString());
		/* Aktualisieren der Tabelle mit den Werten aus Register_Array */
		tabelle_aktualisieren();
		/* z überprüfen ob true/false */
		if (get_Z() == 1) {
			frame.label_z_value.setText("1");
		} else {
			frame.label_z_value.setText("0");
		}

		/* PC setzen in Frame */
		frame.lbl_PC_value.setText(String.valueOf(PC + 1).toString());

		/* C setzen in Frame */
		frame.label_C_value.setText(String.valueOf(get_C()).toString());

		/* Status setzen */
		frame.lbl_Status_value.setText(String.valueOf(register_array[3])
				.toString());

		/* DC setzen */
		frame.label_DC_value.setText(String.valueOf(get_DC()).toString());
	}

	public void elements_aktualisieren() {

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
		/* Aktuellen Code in Liste markieren */

	}

	public void write_to_register(int adress, int value) {
		if (adress == 0) {
			adress = 4;
		}
		setRegisterEntry(adress, value);
		gui_aktualisieren();
	}

	public int get_C() {
		if (is_bit_set(1, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	public void change_C() {
		if (get_C() == 1) {
			clear_Bit(1, 3);
		} else {
			set_Bit(1, 3);
		}
	}

	public int get_DC() {
		if (is_bit_set(2, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	private void change_DC() {
		if (get_C() == 1) {
			clear_Bit(2, 3);
		} else {
			set_Bit(2, 3);
		}
	}

	public int get_Z() {
		if (is_bit_set(3, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	public void change_Z() {
		if (get_C() == 1) {
			clear_Bit(3, 3);
		} else {
			set_Bit(3, 3);
		}
	}

	public void write_table_to_register(int xColumn, int yRow, String value) {

		int adress = (yRow * 8) + xColumn + 1;
		setRegisterEntry(adress, (Integer.parseInt(value)));
		System.out.println("value " + value);

	}

	public void set_Bit(int position, int adress) {
		switch (position) {
		case 0: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00000001));
			break;
		}
		case 1: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00000010));
			break;
		}
		case 2: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00000100));
			break;
		}
		case 3: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00001000));
			break;
		}
		case 4: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00010000));
			break;
		}
		case 5: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b00100000));
			break;
		}
		case 6: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b01000000));
			break;
		}
		case 7: {
			setRegisterEntry(adress, (getRegisterEntry(adress)|0b10000000));
			break;
		}
		default: {
		}
		}
	}

	public void clear_Bit(int position, int adress) {
		switch (position) {
		case 0: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00000001));
			break;
		}
		case 1: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00000010));
			break;
		}
		case 2: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00000100));
			break;
		}
		case 3: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00001000));
			break;
		}
		case 4: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00010000));
			break;
		}
		case 5: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b00100000));
			break;
		}
		case 6: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b01000000));
			break;
		}
		case 7: {
			setRegisterEntry(adress, (getRegisterEntry(adress) & 0b10000000));
			break;
		}
		default: {
		}
		}
	}

	public boolean is_bit_set(int position, int adress) {
		switch (position) {
		case 0: {
			int d = getRegisterEntry(adress) & 0b00000001;
			if (d == 1) {
				return true;
			} else {
				return false;
			}
		}
		case 1: {
			int d = getRegisterEntry(adress) & 0b00000010;
			if (d == 2) {
				return true;
			} else {
				return false;
			}
		}
		case 2: {
			int d = getRegisterEntry(adress) & 0b00000100;
			if (d == 4) {
				return true;
			} else {
				return false;
			}
		}
		case 3: {
			int d = getRegisterEntry(adress) & 0b00001000;
			if (d == 8) {
				return true;
			} else {
				return false;
			}
		}
		case 4: {
			int d = getRegisterEntry(adress) & 0b00010000;
			if (d == 16) {
				return true;
			} else {
				return false;
			}
		}
		case 5: {
			int d = getRegisterEntry(adress) & 0b00100000;
			if (d == 32) {
				return true;
			} else {
				return false;
			}
		}
		case 6: {
			int d = getRegisterEntry(adress) & 0b01000000;
			if (d == 64) {
				return true;
			} else {
				return false;
			}
		}
		case 7: {
			int d = getRegisterEntry(adress) & 0b10000000;
			if (d == 128) {
				return true;
			} else {
				return false;
			}
		}
		default:
			return false;
		}
	}

}
