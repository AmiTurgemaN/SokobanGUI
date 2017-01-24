package controller.commands;

import model.Model;
import view.View;

public class Exit extends GeneralCommand {

	private static final long serialVersionUID = 1L;
	
	public Exit(Model model, View view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		if(this.commandArgs!=null)
		{
			view.displayError("Exit command does not require any arguments.");
			return;
		}
		this.view.exit("Exiting");
	}
}
