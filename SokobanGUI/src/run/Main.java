//package run;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//
//import controller.SokobanController;
//import controller.server.Server;
//import controller.server.SingleClientHandler;
//import model.SokobanModel;
//import view.CLI;
//
//public class Main {
//
//	public static void main(String[] args) {
//		
//		SokobanModel model = new SokobanModel();
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter writer = new PrintWriter(System.out);
//		CLI view = new CLI(reader, writer, "exit");
//		
//		SokobanController controller = new SokobanController(model, view);
//		model.addObserver(controller);
//		view.addObserver(controller);
//		
//		view.start();
//		
//		/*Server server = new Server(1234, new SingleClientHandler(System.in, System.out));
//		server.start();
//		server.stop();*/
//	}
//
//}
