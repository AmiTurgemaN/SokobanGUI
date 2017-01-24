package view;

import java.io.File;

import javafx.stage.FileChooser;

public class MainWindowController {

	public void openFile()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Open level file");
		//fc.setInitialDirectory(new File("./Level Files"));
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"Level files", "*.txt", "*.obj", "*.xml");

		fc.getExtensionFilters().add(fileExtensions);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
			System.err.println(chosen.getName());
	}

}

