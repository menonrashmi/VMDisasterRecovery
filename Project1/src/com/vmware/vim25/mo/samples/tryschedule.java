package com.vmware.vim25.mo.samples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.vmware.vim25.mo.samples.vm.VMSnapshot;

import sun.applet.Main;

public class tryschedule extends TimerTask{

	  private DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");

	  public static void main(String[] args) {
		  System.out.println("Mian");
	   
		  TimerTask task = new tryschedule();

	   Timer timer = new Timer();
	    
	    
	    
	    timer.scheduleAtFixedRate(task,0, 30*60*1000);
	  }

	  private static void Snapshotrefresh() {
          
		  
			try {
				String [] arg_snapshotremove={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","removeall"};
				VMSnapshot.main(arg_snapshotremove);
				  
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			try {
				String [] arg_snapshot={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","create"};
				VMSnapshot.main(arg_snapshot);
				  
			} catch (Exception e) {
				
				e.printStackTrace();
			}
          
      }
  
	  public void run() {
		  //  System.out.println(formatter.format(new Date()));
		  Snapshotrefresh();
		  }

}

