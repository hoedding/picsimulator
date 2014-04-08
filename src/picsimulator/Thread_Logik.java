package picsimulator;

public class Thread_Logik implements Runnable
{ public MainFrame frame;
public Simulator_Logik simulator;
public MainFrame_Logik logik;
public Thread_Logik(MainFrame _frame, Simulator_Logik _simulator, MainFrame_Logik _logik) {
	frame = _frame;
	simulator = _simulator;
	logik = _logik;
		// TODO Auto-generated constructor stub
	}

@Override public void run()
  { int i;
	logik.filter_code();
	for(i=0;i<frame.listModel.size();i++){
		simulator.analyze_code(frame.listModel.elementAt(i));
		}
	try {
		simulator.start_programm();
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

  }
}