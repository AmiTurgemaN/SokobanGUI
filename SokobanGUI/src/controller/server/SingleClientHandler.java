package controller.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import controller.SokobanController;
import model.SokobanModel;
import view.CLI;

public class SingleClientHandler implements ClientHandler {

	private InputStream is;
	private OutputStream os;
	
	public SingleClientHandler(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	@Override
	public void handleClinet(InputStream inFromClient, OutputStream outToClient) {
		SokobanModel model = new SokobanModel();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter writer = new PrintWriter(outToClient);
		CLI view = new CLI(reader, writer, "exit");
		
		SokobanController controller = new SokobanController(model, view);
		model.addObserver(controller);
		view.addObserver(controller);
		
		view.start();
	}

}
