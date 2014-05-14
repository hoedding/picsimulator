package picsim.mvc.controller;

public class PicSimControllerThread_Once implements Runnable {
	private PicSimController controller;

	public PicSimControllerThread_Once(PicSimController controller) {
		this.controller = controller;

	}

	@Override
	public void run() {

		try {

			controller.start_programm(controller.get_Frequency() / 10);
		controller.chooseMode();
			controller.countSteps();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
