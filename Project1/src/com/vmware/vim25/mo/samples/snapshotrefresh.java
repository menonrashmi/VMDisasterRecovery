package com.vmware.vim25.mo.samples;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.vmware.vim25.WaitOptions;
import com.vmware.vim25.mo.samples.vm.VMSnapshot;
import java.rmi.RemoteException;
public class snapshotrefresh extends TimerTask {

	private DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
	static String virtualmachine;
	static String tname;
	static String Virtual_add;
	static boolean reachable;
	
	  public snapshotrefresh(String virtualname, String threadname, String ipadd) {
		virtualmachine=virtualname;
		tname=threadname;
		Virtual_add=ipadd;
	}

	public static void main(String[] args) {
		 
		  
		  TimerTask task = new snapshotrefresh(virtualmachine,tname,Virtual_add);
		  Timer timer = new Timer();
		  timer.scheduleAtFixedRate(task,0, 30*60*1000);
		 
	  }

	  private static void Snapshotrefresh()  {
        
		 
			try {
				
				InetAddress inet = InetAddress.getByName(Virtual_add);
				
				//System.out.println("Sending Ping Request to " + inet);
				reachable=inet.isReachable(8000);
				
				if (reachable==true) 
				{
				
				String [] arg_snapshotremove={"https://130.65.157.246/sdk","administrator","12!@qwQW",virtualmachine,"removeall"};
				VMSnapshot.main(arg_snapshotremove);
				
				String [] arg_snapshot={"https://130.65.157.246/sdk","administrator","12!@qwQW",virtualmachine,"create"};
				
				
				VMSnapshot.main(arg_snapshot);
			
				}
				else
				{
					Thread.sleep(5*60*1000);
					System.out.println("Host not reachable");
				}
				
			 
			} catch(Exception e) { 
		        System.out.println("Error: Remote Exception.");
			}
	  }

	  public void run() {
		 
		 
		
			Snapshotrefresh();
		
	}

}

