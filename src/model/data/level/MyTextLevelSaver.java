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
			String [] LevelRows = level.getLevelString().split("\n");
			for(int i=0;i<LevelRows.length;i++)
			{
				for(int j=0;j<LevelRows[i].length();j++)
				{
					w.write(LevelRows[i].charAt(j));
				}
				if(i!=LevelRows.length-1)
					w.write("\r\n");
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
