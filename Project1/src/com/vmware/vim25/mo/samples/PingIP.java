package com.vmware.vim25.mo.samples;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class PingIP extends TimerTask implements Runnable  {
	
Thread runner;
String host;

public PingIP()
{
	
}
	
public PingIP(String Threadname,String ipadd)
{
	runner=new Thread(this,Threadname);
	host=ipadd;
	runner.start();
	
}
	@Override
	public void run() {
		try {
			System.out.println("Pinging");
			ping();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static void main(String[] args) {
		 
		  
		  TimerTask task = new PingIP();
		  Timer timer = new Timer();
		  timer.scheduleAtFixedRate(task,0, 1000);
		 
	  }
	 
	public Boolean ping( ) throws IOException
    {
		
		  
		System.out.println("IP address passed is " + host);
		String pingResult = "";

		String pingCmd = "ping " + host;
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(pingCmd);

		BufferedReader in = new BufferedReader(new
		InputStreamReader(p.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		//System.out.println(inputLine);
		pingResult += inputLine;
		
		}
		
		if (pingResult.contains("Received = 0") || pingResult.contains("unreachable"))
				return (false);
		else
				return(true);
    }
	


}

