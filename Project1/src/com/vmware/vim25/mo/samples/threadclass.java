package com.vmware.vim25.mo.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import sun.awt.windows.ThemeReader;

import com.vmware.vim25.mo.samples.vm.VMSnapshot;

public class threadclass  extends TimerTask implements Runnable{

	
	Thread runner;
	static String host;
	static String threadname;
	
	public threadclass() {
	}
	
	public  threadclass(String thname, String ipadd) throws IOException{
		//runner=new Thread(this,thname);
		host=ipadd;
		runner = new Thread(this, thname); // (1) Create a new thread.
		System.out.println(runner.getName());
		
		runner.start(); // (2) Start the thread.
	}
	
	@Override
	public void run() {
		
			//Display info about this particular thread
			System.out.println(Thread.currentThread());
			try {
				Ping();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	  public void main() throws InterruptedException {
		 
		  TimerTask task = new threadclass();
		  Timer timer = new Timer();
		  timer.scheduleAtFixedRate(task,0, 10*1000);
		
		  
		 
	  }

	  private static void Ping() throws IOException {
      
		 // String host="192.168.1.101";
		  String result;
		  System.out.println("IP address passed is " + host + threadname);
			String pingResult = "";
			String pingCmd = "ping " + host;
						
			}
  

	  private static void failover() {
		System.out.println("Starting failover");
	}

	
	}

	
	
