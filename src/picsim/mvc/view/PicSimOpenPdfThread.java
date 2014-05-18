package picsim.mvc.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class PicSimOpenPdfThread implements Runnable {

	@Override
	public void run() {
		
		try {
			String path = System.getProperty("user.dir");
			File pdfFile = new File(path+"\\doku_picsim.pdf");
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("AWT Desktop is not supported!");
				}
			} else {
				System.out.println("File does not exist");
			}
			System.out.println("DONE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
