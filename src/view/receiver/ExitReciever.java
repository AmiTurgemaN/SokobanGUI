package view.receiver;

import java.io.PrintWriter;

import common.GeneralReceiver;

public class ExitReciever extends GeneralReceiver {

	private String exitString;
	private PrintWriter out;
	
	public ExitReciever(String exitString,PrintWriter out) {
		this.exitString = exitString;
		this.out=out;
	}
	
	public String getExitString() {
		return exitString;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public void setExitString(String exitString) {
		this.exitString = exitString;
	}

	@Override
	public void action() {
		String dotsString = "......";
		out.print(exitString);
		out.flush();
		for(int i=0;i<dotsString.length();i++)
		{
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.print(dotsString.charAt(0));
			out.flush();
		}
	}

}
