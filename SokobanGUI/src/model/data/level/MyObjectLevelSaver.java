package model.data.level;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectLevelSaver extends GeneralLevelSaver {

	public MyObjectLevelSaver(Level level) {
		this.level=level;
	}

	@Override
	public void saveLevel(OutputStream outputStream) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(outputStream);
			out.writeObject(this.level);
			out.close();
		}catch(IOException i) {
			i.printStackTrace();
		}
	}

}
