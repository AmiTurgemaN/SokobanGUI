package sokobanSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import model.data.level.GeneralLevelLoaderCreator;
import model.data.level.LevelCreator;
import model.data.util.Utilities;
import strips.Plannable;
import strips.Strips;

public class SokobanSolver {
	
	private SolverModel sokobanModel;
	private View sokobanView;
	private Plannable plannable;
	private String destinationFile;
	
	public SokobanSolver(String levelName,String fileName) {
		destinationFile = fileName;
		sokobanModel = new SolverModel();
		sokobanView = new SolverView();
		try {
			LevelCreator lc = new LevelCreator(new FileInputStream("Hash Maps/saveHashMap.obj"),new FileInputStream("Hash Maps/loadHashMap.obj"));
			GeneralLevelLoaderCreator gllc = lc.getLoadHashMap().get(Utilities.getExtension(levelName));
			InputStream is;
			is = new FileInputStream("Level Files/"+levelName);
			sokobanModel.loadLevel(gllc.create(), is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		plannable=sokobanModel.readLevel();
	}
	
	public void solve()
	{
		Strips plan=new Strips();
		plan.plan(plannable);
		sokobanView.showSolution(plan.getActions(),destinationFile);
	}
}
