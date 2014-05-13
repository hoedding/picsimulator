package picsim.mvc.model.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Enumeration;

import picsim.mvc.model.PicSimModel;

public class PicSimSerialConnection {

	private CommPortIdentifier portId;
	private SerialPort port;
	private OutputStreamWriter out;
	private InputStreamReader in;
	private char CR = '\r';
	private char LF = '\n';
	private PicSimModel model;

	public PicSimSerialConnection(PicSimModel model) {
		this.model = model;
	}

	// Startpunkt für die Verbindung
	public boolean open(String comportUsed) {

		try {

			Enumeration portList;
			String defaultPort;
			// Differenzierung nach Betriebssystem
			String osname = System.getProperty("os.name", "").toLowerCase();
			if (osname.startsWith("windows")) {
				// windows
				defaultPort = "COM5";
			} else if (osname.startsWith("linux")) {
				// linux
				defaultPort = "/dev/ttyS0";
			} else if (osname.startsWith("mac")) {
				// mac
				defaultPort = "/dev/tty.usbserial-FTH92HF9";
			} else {
				System.out
						.println("Sorry, your operating system is not supported");

			}

			portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (portId.getName().equals(comportUsed)) {
						System.out.println("Found port: " + comportUsed);
						System.out.println(portId.getName());
						break;
					}
				}
			}

			this.port = (SerialPort) this.portId.open("PICSIM", 2000);

			port.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			in = new InputStreamReader(port.getInputStream());

			out = new OutputStreamWriter(port.getOutputStream());

			System.err.println("Port Open: Success");

			System.err.println("Send: Success");

			return true;
		}

		catch (Exception e) {
			System.err.println("Port Open:Failed");
			System.out.println("Port is already used from other application,");
			System.out
					.println("Close that application and re-run this application");
			
			return false;
		}
	}

	public void sendRS232() throws Exception {
		String p_a = encodeData(model.getPortA());
		String t_a = encodeData(model.getRegisterEntry(0x85));
		String p_b = encodeData(model.getPortB());
		String t_b = encodeData(model.getRegisterEntry(0x86));
		String send = t_a + p_a + t_b + p_b;
		System.out.println("send:" + send);
		write(send + CR);
	}

	public void write(String s) throws Exception {

		out.write(s);
		out.flush();
	}

	private void write(byte s) throws Exception {
		out.write(s);
		out.flush();
	}

	public void write(int s) throws Exception {
		out.write(s);
		out.flush();
	}

	public String read() throws Exception {
		int n, i;
		char c;
		String answer = new String("");

		// TODO code auf website war fehlerhaft, for war abgeschnitten
		for (i = 0; i < 500; i++) {

			while (in.ready()) {
				n = in.read();
				if (n != -1) {
					c = (char) n;
					answer = answer + c;
					Thread.sleep(1);
				} else
					break;
			}
			delay(1);
		}

		return answer;
	}

	private void delay(int a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
		}
	}

	public void close() throws Exception {
		try {
			port.close();
			System.err.println("close port");
		} catch (Exception e) {
			System.err.println("Error: close port failed: " + e);
		}
	}

	private String encodeData(int b) {
		char c1 = (char) (0x30 + ((b & 0xF0) >> 4));
		char c2 = (char) (0x30 + (b & 0x0F));

		return "" + c1 + c2;
	}

	private void decodeData() {

	}

}
