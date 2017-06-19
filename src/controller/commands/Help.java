package controller.commands;

import model.Model;
import view.View;

public class Help extends GeneralCommand {
	private static final long serialVersionUID = 1L;

	public Help(Model model) {
		this.model=model;
	}

	public Help(Model model, View view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		if(this.commandArgs!=null)
		{
			view.displayError("Help command does not require any arguments.");
			return;
		}
		view.displayMessage("The commands are:\n"
				+ "Display\n"
				+ "Exit\n"
				+ "Levels\n"
				+ "Solve\n"
				+ "Solve <File Name>\n"
				+ "Load <File Name>\n"
				+ "Move <Direction>\n"
				+ "Save <File Name>");
	}
}
