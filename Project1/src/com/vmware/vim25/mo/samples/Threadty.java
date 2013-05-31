package com.vmware.vim25.mo.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.samples.vm.VmNicOp;
import com.vmware.vim25.mo.InventoryNavigator;

public class Threadty extends TimerTask implements Runnable {
	int count;
	String name;
	String add;
	String virtualname;
	VirtualMachine VMachine;
	Threadty() {
	  
	  }
	
	public Threadty(String Threadname,String ipadd, String  vmname,VirtualMachine vm)
	{
		  	count = 0;
			name=Threadname;
			add= ipadd;
			virtualname=vmname;
			VMachine=vm;
	}
	  public void run() {
	   // System.out.println("MyThread starting.");
	    try {
	      do {
	    	  
	    	  //for alarm added on 6th
	    	  		
			    	// VirtualMachinePowerState VMPower=null;
			 		 //VMPower=VMachine.getRuntime().getPowerState(); 
			 		 
			 		 
			 	
			 		ping();
			        count++;
			        if (name.toString().contains("thread1")&& count ==3)
			        {
			        	
			        	//System.out.println("Power state of VM " + VMPower);
			        		failvm(add);
			         
			        }
	        
	      } while (count !=-1);
	    } catch (IOException e) {
			
			e.printStackTrace();
		}
	   
	  }
	  
	  private void failvm(String add2) {
		
			System.out.println("Failing initiated for virtual machine :	 " + virtualname);
			String [] arg_removenic={"https://130.65.157.246/sdk","administrator","12!@qwQW",virtualname,"remove","Network adapter 1" };
	 		try {
	 			System.out.println("Calling remove virtual machine nic!!! ");
				VmNicOp.main(arg_removenic);
				Thread.sleep(5*1000);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	 	
			//Thread.currentThread().stop();
			
	}

	public Boolean ping( ) throws IOException
	    {
			//System.out.println("IP address passed is " + add + name);
			String pingResult = "";

			String pingCmd = "ping " + add;
			Runtime r = Runtime.getRuntime();
			
			Process p = r.exec(pingCmd);

			BufferedReader in = new BufferedReader(new
			InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			//System.out.println(inputLine);
			pingResult += inputLine;
			
			}
			
			
			VirtualMachinePowerState VMPower1=null;
			VMPower1=VMachine.getRuntime().getPowerState();
			//For Alarm
			if (VMPower1.toString().contains("poweredOff"))
			{
				System.out.println("Alarm Triggred" );
			}
			
			
	 		
			if (pingResult.contains("unreachable") && (VMPower1.toString().contentEquals("poweredOn")))
				{
					System.out.println("IP address unreachable " + add + "  Virtual Machine :  " + virtualname);
					
					recovery(add,virtualname,VMachine);
					Thread.currentThread().stop();
					
					return (false);
					
					}
			  else
			      {
					System.out.println("IP address passed reachable " + add + "  Virtual Machine :  " + virtualname);
					//System.out.println("Powerstate" + VMPower);
					return(true);
			       }
	    }

	private void recovery(String add2, String vm, VirtualMachine vmachin) {
		
		
		recovery rec = new recovery();
		try {
			rec.recoveryVM(add,vm,VMachine);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	  
	  
}
