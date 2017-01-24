package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

public class Server extends Observable{
	private int port;
	private ClientHandler ch;
	private volatile boolean stop;

	public Server(int port,ClientHandler ch) {
		this.port=port;
		this.ch=ch;
		this.stop=false;
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

	protected void runServer() throws Exception{
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop)
		{
			try
			{
				Socket aClient=server.accept();
				try {
					ch.handleClinet(aClient.getInputStream(), aClient.getOutputStream());
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					aClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			catch(SocketTimeoutException e)
			{
				e.printStackTrace();
			}
		}
		server.close();
	}

	public void stop()
	{
		this.stop=true;
	}
}
