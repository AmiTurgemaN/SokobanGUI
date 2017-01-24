package controller.commands;

import model.data.level.Level;
import model.Model;
import view.View;

public class Display extends GeneralCommand {
	private static final long serialVersionUID = 1L;

	public Display(Model model) {
		this.model=model;
	}

	public Display(Model model, View view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		if(this.commandArgs!=null)
		{
			view.displayError("Display command does not require any arguments.");
			return;
		}
		if(this.model.getLevel()==null)
		{
			view.displayError("Level not loaded");
			return;
		}
		
		Level level = model.getLevel();
		view.displayLevel(level);
	}
}
