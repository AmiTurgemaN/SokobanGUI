package model.data.level;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class MyXMLLevelSaver extends GeneralLevelSaver {

	
	public MyXMLLevelSaver(Level level) {
		this.level=level;
	}
	
	@Override
	public void saveLevel(OutputStream outputStream) {
		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(outputStream));
		encoder.writeObject(level);
		encoder.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
