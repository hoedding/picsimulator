package picsimulator;

public class Thread_Logik implements Runnable
{ public MainFrame frame;
public Simulator_Logik simulator;
public MainFrame_Logik logik;
public Thread_Logik(MainFrame frame, Simulator_Logik simulator, MainFrame_Logik logik) {
	this.frame = frame;
	this.simulator = simulator;
	this.logik = logik;
		// TODO Auto-generated constructor stub
	}

@Override public void run()
  {
	

	try {
		while(frame.oliver){
		simulator.start_programm();
		}
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

  }
}