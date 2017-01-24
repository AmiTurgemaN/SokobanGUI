package controller.commands;

import java.io.Serializable;

import model.Model;
import view.View;

public abstract class GeneralCommand implements Command,Serializable {

	private static final long serialVersionUID = 1L;
	protected String commandArgs;
	transient protected View view;
	transient protected Model model;
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getCommandArgs() {
		return commandArgs;
	}

	public void setCommandArgs(String commandArgs) {
		this.commandArgs = commandArgs;
	}

	@Override
	public abstract void execute();

}
