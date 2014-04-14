package picsimulator;

public class Thread_Logik_Once implements Runnable {
	public MainFrame frame;
	public Simulator_Logik simulator;
	public MainFrame_Logik logik;

	public Thread_Logik_Once(MainFrame frame, Simulator_Logik simulator,
			MainFrame_Logik logik) {
		this.frame = frame;
		this.simulator = simulator;
		this.logik = logik;
		// TODO Auto-generated constructor stub
	}

	public void run() {

		try {

			simulator.start_programm();

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}