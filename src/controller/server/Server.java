package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;


public class Server extends Observable {
	private int port;
	private ClientHandler ch;


	public Server(int port,ClientHandler ch) {
		this.port=port;
		this.ch=ch;
	}

	public void start()
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					runServer();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@SuppressWarnings("resource")
	protected void runServer() throws Exception{
		ServerSocket server = new ServerSocket(port);
		try
		{
			System.out.println("Waiting for user to connect");
				Socket aClient=server.accept();
				try {
					System.out.println("User Connected");
					ch.handleClinet(aClient.getInputStream(), aClient.getOutputStream(),"exit");
					System.out.println("User Disconnected");
					aClient.close();
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		catch(SocketTimeoutException e)
		{
			e.printStackTrace();
		}
	}
}
