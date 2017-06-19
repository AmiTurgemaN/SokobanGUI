package sokobanSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

import model.data.level.GeneralLevelLoaderCreator;
import model.data.level.Level;
import model.data.level.LevelCreator;
import model.data.util.Utilities;
import strips.Plannable;
import strips.Strips;

public class SokobanSolver {
	
	private SolverModel sokobanModel;
	private View sokobanView;
	private Plannable plannable;
	private Strips plan;
	private String levelName;

	public SokobanSolver(String levelName) {
		sokobanModel = new SolverModel();
		sokobanView = new SolverView();
		plan = new Strips();
		this.levelName=levelName;
	}
	
	public void loadLevel()
	{
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
	
	public SokobanSolver(Level level) {
		sokobanModel = new SolverModel();
		sokobanView = new SolverView();
		sokobanModel.setLevel(level);
		plannable=sokobanModel.readLevel();
		plan = new Strips();
	}
	
	public void solve(String destinationFile)
	{
		plan.plan(plannable);
		sokobanView.showSolution(plan.getActions(),"Level Files\\"+destinationFile);
	}
	
	public void solve(PrintWriter pw)
	{
		plan.plan(plannable);
		sokobanView.showSolution(plan.getActions(),pw);
	}
	
	public Strips getPlan() {
		return plan;
	}

	public void setPlan(Strips plan) {
		this.plan = plan;
	}
	
	public SolverModel getSokobanModel() {
		return sokobanModel;
	}

	public void setSokobanModel(SolverModel sokobanModel) {
		this.sokobanModel = sokobanModel;
	}

	public View getSokobanView() {
		return sokobanView;
	}

	public void setSokobanView(View sokobanView) {
		this.sokobanView = sokobanView;
	}

	public Plannable getPlannable() {
		return plannable;
	}

	public void setPlannable(Plannable plannable) {
		this.plannable = plannable;
	}
}
