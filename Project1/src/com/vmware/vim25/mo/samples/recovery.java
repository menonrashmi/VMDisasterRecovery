package com.vmware.vim25.mo.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.vmware.vim25.VirtualMachineMovePriority;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.samples.VMConstants;
import java.net.URL;

import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.samples.VMConstants;
import com.vmware.vim25.mo.samples.alarm.CreateVmAlarm;
import com.vmware.vim25.mo.samples.vm.CloneVM;
import com.vmware.vim25.mo.samples.vm.MigrateVM;
import com.vmware.vim25.mo.samples.vm.VMpowerOps;
import com.vmware.vim25.mo.samples.VMConstants;
public class recovery {

	public void recoveryVM(String ipadd, String vmname,VirtualMachine Vmachine) throws Exception
	{
		String virtualname=vmname;
		String Clonename= virtualname +"_clone";
		VirtualMachine VMachine=Vmachine;
		
		System.out.println("Recovery Started for	 " + ipadd + "	Virtual Machine :	" + vmname);
		 
		System.out.println("Cloning for virtual machine :" + vmname + " started!" );
		
		String [] arg={"https://130.65.157.246/sdk","administrator","12!@qwQW",virtualname,Clonename};
		CloneVM.main(arg);
		
		//Migrate VM
		//Added on 6th 
			//Check if host is alive
				String pingResult = "";
				String IP=VMConstants.migrate_host;
				
				
				String pingCmd = "ping " + IP;
				Runtime r = Runtime.getRuntime();
				Process p = r.exec(pingCmd);
		
				BufferedReader in = new BufferedReader(new
				InputStreamReader(p.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
				
				pingResult += inputLine;
		
				if (pingResult.contains("Received = 0") || pingResult.contains("unreachable"))
				{
					//System.out.println("IP address unreachable :" + IP );
					System.out.println("Host not alive for migration");		
					
					Thread.currentThread().stop();
					
					}
			else
			{
				//Migrate
					System.out.println("Migration started for cloned virtual machine :" + Clonename + " !!!");
					String [] arg_migrate={"https://130.65.157.246/sdk","Administrator","12!@qwQW",Clonename,VMConstants.migrate_host};
					//String [] arg_migrate={"https://130.65.157.246/sdk","Administrator","12!@qwQW",Clonename,VMConstants.migrate_host,Migrate_Resourcepool};
					MigrateVM.main(arg_migrate);
					//Thread.sleep(20*1000);
					//Power Migrated VM
					System.out.println("Powering on migrated cloned virtual machine :" + Clonename + " !!!");
					String [] arg_vmpower={"https://130.65.157.246/sdk","Administrator","12!@qwQW",Clonename};
					Poweron_VM.main(arg_vmpower);
					
					
					//VMachine.migrateVM_Task(Migration_RP, VMConstants.migrate_host,VirtualMachineMovePriority.highPriority, 
					         //VirtualMachinePowerState.poweredOff)
					//vm1.powerOnVM_Task(null);		
					
					
					//Power off
					 System.out.println("Failed Virtual Machine Powering off :" + vmname + " !!!");
					//String [] arg_vmpoweroff={"https://130.65.157.246/sdk","Administrator","12!@qwQW",virtualname,"poweroff"};
					//VMpowerOps.main(arg_vmpoweroff);
					//System.out.println("Virtual machine to be powered of :" + vmname);
					 
					 Vmachine.powerOffVM_Task();
					//Thread.sleep(10*1000);
					
					
					//System.out.println("Alarm has been triggered!!!!");
					
					 //Remove VM
					System.out.println("Removing Failed Virtual Machine " + vmname + " !!!");
					VMachine.destroy_Task();
					
					Thread.currentThread().stop();
					
			}
			
	}

	}
}



