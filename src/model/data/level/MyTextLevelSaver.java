package model.data.level;

import java.io.OutputStream;
import java.io.PrintWriter;

public class MyTextLevelSaver extends GeneralLevelSaver {

	public MyTextLevelSaver(Level level) {
		this.level=level;
	}
	
	@Override
	public void saveLevel(OutputStream outputStream) {
		try {
			PrintWriter w = new PrintWriter(outputStream);
			w.write(level.getLevelString());
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
