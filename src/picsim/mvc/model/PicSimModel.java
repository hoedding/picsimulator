package picsim.mvc.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PicSimModel {

	public int w_register = 0b0;
	public int sprung;
	private int PC = 0;

	private Deque<Integer> STACK = new ArrayDeque<Integer>();
	public int[] register_array = new int[256];
	public List<Integer> code_list = new ArrayList<Integer>();
	private int takt;
	private String path_of_programfile;
	private String path_of_registerfile;
	
	private int portA;
	private int portB;

	public PicSimModel() {
		// Konstruktor
	}

	// Stack löschen
	public void CleanStack() {
		STACK.clear();
	}

	// WReg löschen
	public void CleanWReg() {
		w_register = 0;
	}

	public int getPortA() {
		return portA;
	}

	public void setPortA(int portA) {
		this.portA = portA;
	}


	public int getPortB() {
		return portB;
	}
	public void setPortB(int portB) {
		this.portB = portB;
	}

	// Getter Setter Programmfile
	public String getPath_of_programfile() {
		return path_of_programfile;
	}

	public void setPath_of_programfile(String path_of_programfile) {
		this.path_of_programfile = path_of_programfile;
	}

	// Getter Setter Registerfile
	public String getPath_of_registerfile() {
		return path_of_registerfile;
	}

	public void setPath_of_registerfile(String path_of_registerfile) {
		this.path_of_registerfile = path_of_registerfile;
	}

	public void setProgramCounter(int i) {
		PC = i;
	}

	// Getter ProgramCounter
	public int getProgrammCounter() {
		return PC;
	}

	// Getter Setter Takt
	public void set_takt(int takt) {
		this.takt = takt;
	}

	public int get_takt() {
		return takt;
	}

	// Getter Status
	public int get_status() {
		return getRegisterEntry(3);

	}

	/* Register schreiben */
	public void setRegisterEntry(int index, int value) {
		if (is_bit_set(5, 3)) {
			
			// Wenn das Bit für Bankumschaltung gesetzt ist
			
			if (index == 0) {
				value = value & 0b11111111;
				register_array[register_array[4] + 128] = value;

			} else {
				value = value & 0b11111111;
				register_array[index + 128] = value;
			}
		} else {
			if (index == 0) {
				value = value & 0b11111111;
				register_array[register_array[4]] = value;

			} else {
				value = value & 0b11111111;
				register_array[index] = value;
			}
		}
	}
	
	private void setRegisterEntryOneBit(int index, int value){
		if (index == 0) {
			value = value & 0b11111111;
			register_array[register_array[4]] = value;

		} else {
			value = value & 0b11111111;
			register_array[index] = value;
		}
	}

	/* Eintrag aus Register holen */
	public int getRegisterEntry(int index) {
		if (index == 0) {
			return register_array[register_array[4]];
		} else {
			return register_array[index];
		}
	}

	/* Model zurücksetzen */
	public void reset_model() {
		int m;
		for (m = 0; m < 256; m++) {

			register_array[m] = 0;
		}
		w_register = 0;
		STACK.clear();
		sprung = 0;
		PC = 0;
		code_list.clear();
		path_of_programfile = "";
		path_of_registerfile = "";
		takt = 4000;
	}

	/* LISTE MIT EINZELNEN CODE-ELEMENTEN */
	public void setCode(int _code) {
		code_list.add(_code);
	}

	/*
	 * Löscht alles außer die relevanten 16 Bit und speichert den Programmcode
	 * über setCode() in code_list
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
	 * Tatsächliche Pic-Befehle Hier findet die Auswahl der Befehle nach
	 * Bitfolge statt
	 */
	public void what_to_do(int code) throws InterruptedException {
		int code_as_int = code;

		int hex16 = code_as_int & 0b1111111111111111;
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

		int hex10 = code_as_int & 0b1111110000000000;
		int _hex10 = code_as_int & 0b0000001111111111;
		switch (hex10) {
		case 4096:
			do_bcf(_hex10);
			break;
		case 5120:
			do_bsf(_hex10);
			break;
		case 6144:
			do_btfsc(_hex10);
			break;
		case 7168:
			do_btfss(_hex10);
			break;
		case 12288:
			do_movlw(_hex10);
			break;
		case 13312:
			do_retlw(_hex10);
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

	}

	/*
	 * ############################### * Befehlscode nach Datenblatt * *
	 * #############################
	 */

	private void do_sleep() {
		// TODO clear bit PD
		// TODO clear bit TO
		// TODO clear bit Watchdog
		// TODO processor is put to sleep
		// TODO oscillator stopped
		// Section 14.8 for more info

	}

	private void do_return() throws InterruptedException {

		int adress = STACK.pop();

		setProgramCounter(adress);

	}

	private void do_retfie() {

		int adress = STACK.pop();

		setProgramCounter(adress);
		// Global Interrupt auf enable
		set_Bit(8, 8);

	}

	private void do_clrwdt() {

		// TODO Clear WatchDog Timer

	}

	private void do_goto(int _hex5) {

		sprung = getProgrammCounter();
		setProgramCounter(_hex5 - 1);

	}

	private void do_call(int _hex5) {

		STACK.add(getProgrammCounter());
		setProgramCounter(_hex5 - 1);

	}

	private void do_sublw(int _hex4) {

		int temp = _hex4 & 0b011111111;
		int value = temp - w_register;
		if (value > 255) {
			set_C(true);
		}
		if (value == 0) {
			set_Z(true);
		}
		w_register = value;

	}

	private void do_addlw(int _hex4) {

		int temp = _hex4 & 0b011111111;
		int value = temp + w_register;
		if (value > 255) {
			set_C(true);
		}
		if (value == 0) {
			set_Z(true);
		}
		w_register = value;

	}

	private void do_retlw(int _hex3) {

		int temp = _hex3 & 0b0011111111;
		w_register = temp;
		int adress = STACK.pop();
		setProgramCounter(adress);

	}

	private void do_movlw(int _hex3) {

		int value = _hex3 & 0b0011111111;
		w_register = value;

	}

	private void do_xorlw(int _hex1) {

		int value = w_register ^ _hex1;

		if (value == 0) {
			set_Z(true);
		}
		w_register = value;

	}

	private void do_iorlw(int _hex1) {

		int value = w_register | _hex1;

		if (value == 0) {
			set_Z(true);
		}
		w_register = value;

	}

	private void do_andlw(int _hex1) {

		int value = w_register & _hex1;

		if (value == 0) {
			set_Z(true);
		}
		w_register = value;

	}

	private void do_btfss(int _hex3) {

		int adress = _hex3 & 0b0001111111;
		int bit = _hex3 & 0b1110000000;
		if (is_bit_set(bit, adress)) {
			setProgramCounter(getProgrammCounter() + 1);
		}

	}

	private void do_btfsc(int _hex3) {

		int adress = _hex3 & 0b0001111111;
		int bit = _hex3 & 0b1110000000;
		if (!is_bit_set(bit, adress)) {
			setProgramCounter(getProgrammCounter() + 1);
		}

	}

	private void do_bsf(int _hex3) {
		System.out.println("bsf");
		int bit = (_hex3 & 0b1110000000) / 128;
		
		int adress = _hex3 & 0b0001111111;
		System.out.println(bit + ". bit an der "+ adress + ". adresse");
		set_Bit(bit, adress);

	}

	private void do_bcf(int _hex3) {
		System.out.println("bcf");
		int bit = (_hex3 & 0b1110000000) / 128;
		int adress = _hex3 & 0b0001111111;
		System.out.println(bit + ". bit an der "+ adress + ". adresse");
		clear_Bit(bit, adress);

	}

	private void do_nop() {

	}

	private void do_movwf(int _hex2) {

		setRegisterEntry(_hex2, w_register);

	}

	private void do_xorwf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);
		int result = w_register | value;
		if (d == 0) {
			w_register = result;
		} else {
			setRegisterEntry(adress, result);
		}

		if (result == 0) {
			set_Z(true);
		}
	}

	private void do_swapf(int _hex1) {

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

	}

	private void do_subwf(int _hex) {

		int d = _hex & 0b10000000;
		int adress = _hex & 0b01111111;
		int value = getRegisterEntry(adress) - w_register;

		if (d == 0) {
			w_register = value;
		} else {
			setRegisterEntry(adress, value);
		}
		if (value > 255) {
			set_C(true);
		}
		if (value == 0) {
			set_Z(true);
		}
	}

	private void do_rrf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);

		if (get_C() == 1) {
			value += 256;
		}

		if ((value & 0b000000001) == 1) {
			set_C(true);
			value = value & 0b111111110;
		}

		value = value >> 1;

		if (d == 0) {

			w_register = value;

		} else {

			setRegisterEntry(adress, value);
		}

	}

	private void do_rlf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);

		value = value << 1;

		if (get_C() == 1) {
			value += 1;
		}

		if ((value & 0b100000000) == 256) {
			set_C(true);
			value = value & 0b011111111;
		}

		if (d == 0) {

			w_register = value;

		} else {

			setRegisterEntry(adress, value);
		}

	}

	private void do_movf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress);
		if (d == 0) {
			w_register = value;
		}
		if (value == 0) {
			set_Z(true);
		}

	}

	private void do_iorwf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = getRegisterEntry(adress) | w_register;

		if (d == 0) {

			w_register = value;

		}

		else {
			setRegisterEntry(adress, value);
		}
		if (value == 0) {
			set_Z(true);
		}

	}

	private void do_incfsz(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int decr = getRegisterEntry(adress) + 1;

		if (d == 128) {
			setRegisterEntry(adress, decr);
		} else {

			w_register = decr;
		}
		if (getRegisterEntry(adress) == 0) {
			setProgramCounter(getProgrammCounter() + 1);
		}

	}

	private void do_incf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		if (d == 0) {
			w_register = getRegisterEntry(adress) + 1;
			set_Z(true);
		} else {
			setRegisterEntry(adress, (getRegisterEntry(adress) + 1));
		}

	}

	private void do_decfsz(int _hex1) {

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

	}

	private void do_decf(int adress) {
		int value = (getRegisterEntry(adress) - 1);
		setRegisterEntry(adress, value);

	}

	private void do_comf(int _hex1) {

		int d = _hex1 & 0b10000000;
		int adress = _hex1 & 0b01111111;
		int value = ~getRegisterEntry(adress);
		if (d == 0) {
			w_register = value;
		} else {
			setRegisterEntry(adress, value);
		}
		if (value == 0) {
			set_Z(true);
		}

	}

	private void do_clrw(int _hex2) {

		w_register = 0b0;
		set_Z(true);

	}

	private void do_addwf(int a) {

		int d = a & 0b10000000;
		int adress = a & 0b01111111;
		int value = getRegisterEntry(adress);
		int result = w_register + value;
		if (d == 0) {
			w_register = result;
		} else {
			setRegisterEntry(adress, result);
		}
		if (result > 255) {
			set_C(true);
		}
		if (result == 0) {
			set_Z(true);
		}

	}

	private void do_andwf(int a) {

		int d = a & 0b10000000;
		int adress = a & 0b01111111;
		int value = getRegisterEntry(adress);
		int result = w_register + value;
		if (d == 0) {
			w_register = result;
		} else {
			setRegisterEntry(adress, result);
		}
		if (result == 0) {
			set_Z(true);
		}

	}

	private void do_clrf(int a) {

		setRegisterEntry(a, 0);
		set_Z(true);

	}

	/* Getter und Setter für C,DC,Z */
	public int get_C() {
		if (is_bit_set(1, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	public void set_C(boolean s) {
		if (s = true) {
			set_Bit(1, 3);
		} else {
			clear_Bit(1, 3);
		}
	}

	public int get_DC() {
		if (is_bit_set(2, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	public void set_DC(boolean s) {
		if (s = true) {
			set_Bit(2, 3);
		} else {
			clear_Bit(2, 3);
		}
	}

	public int get_Z() {
		if (is_bit_set(3, 3)) {
			return 1;
		} else {
			return 0;
		}
	}

	public void set_Z(boolean s) {
		if (s = true) {
			set_Bit(3, 3);
		} else {
			clear_Bit(3, 3);
		}
	}

	/*
	 * Veränderung von Daten direkt in der Tabelle werden hier ins Register
	 * geschrieben
	 */
	public void write_table_to_register(int xColumn, int yRow, String value) {

		int adress = (yRow * 8) + xColumn + 1;
		setRegisterEntry(adress, (Integer.parseInt(value)));

	}

	/* Einzelnes Bit in bestimmter Speicherstelle setzen */
	public void set_Bit(int position, int adress) {
		switch (position) {
		case 0: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00000001));
			break;
		}
		case 1: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00000010));
			break;
		}
		case 2: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00000100));
			break;
		}
		case 3: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00001000));
			break;
		}
		case 4: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00010000));
			break;
		}
		case 5: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b00100000));
			break;
		}
		case 6: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b01000000));
			break;
		}
		case 7: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) | 0b10000000));
			break;
		}
		default: {
		}
		}
	}

	/* Einzelnes Bit in bestimmter Speicherstelle löschen */
	public void clear_Bit(int position, int adress) {
		switch (position) {
		case 0: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00000001));
			break;
		}
		case 1: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00000010));
			break;
		}
		case 2: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00000100));
			break;
		}
		case 3: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00001000));
			break;
		}
		case 4: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00010000));
			break;
		}
		case 5: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b00100000));
			break;
		}
		case 6: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b01000000));
			break;
		}
		case 7: {
			setRegisterEntryOneBit(adress, (getRegisterEntry(adress) & 0b10000000));
			break;
		}
		default: {
		}
		}
	}

	/* Überprüfung ob einzlenes Bit gesetzt ist */
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
