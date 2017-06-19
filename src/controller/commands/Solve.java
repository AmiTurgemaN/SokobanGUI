package controller.commands;

import model.Model;
import sokobanSolver.SokobanSolver;
import view.View;

public class Solve extends GeneralCommand {
	private static final long serialVersionUID = 1L;

	public Solve(Model model) {
		this.model=model;
	}

	public Solve(Model model, View view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		if(this.commandArgs!=null)
		{
			if(this.model.getLevel()==null)
			{
				view.displayError("Level not loaded");
				return;
			}
			if(!this.model.getLevel().isCompleted())
			{
				SokobanSolver sokobanSolver = new SokobanSolver(this.model.getLevel());
				sokobanSolver.solve(this.commandArgs);
			}
			else
			{
				view.displayError("Level already completed!");
			}

		}
		else
		{
			if(this.model.getLevel()==null)
			{
				view.displayError("Level not loaded");
				return;
			}
			else if(!this.model.getLevel().isCompleted())
			{
				SokobanSolver sokobanSolver = new SokobanSolver(this.model.getLevel());
				sokobanSolver.solve(this.view.getOut());
			}
			else
			{
				view.displayError("Level already completed!");
			}
		}
	}
}
