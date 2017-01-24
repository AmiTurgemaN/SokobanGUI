package model.data.level;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyTextLevelLoader extends GeneralLevelLoader {

	@Override
	public Level loadLevel(InputStream inputStream) {
		try(Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inputStream))))
		{
		String inputStreamString = scanner.useDelimiter("\\A").next();
		scanner.close();
		Level level = new Level(inputStreamString);
		return level;
		}
	}

}
