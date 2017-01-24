package controller.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	void handleClinet(InputStream inFromClient,OutputStream outToClient);
}
