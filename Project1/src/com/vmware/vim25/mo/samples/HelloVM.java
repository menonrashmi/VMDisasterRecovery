/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vim25.mo.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import org.tempuri.Service;
import org.tempuri.ServiceSoap;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import com.vmware.vim25.mo.samples.alarm.CreateVmAlarm;
import com.vmware.vim25.mo.samples.alarm.PrintAlarmManager;
import com.vmware.vim25.mo.samples.perf.GetMultiPerf;
import com.vmware.vim25.mo.samples.perf.PrintPerfMgr;
import com.vmware.vim25.mo.samples.storage.SearchDatastore;
import com.vmware.vim25.mo.samples.vm.CloneVM;
import com.vmware.vim25.mo.samples.vm.MigrateVM;
import com.vmware.vim25.mo.samples.vm.VMSnapshot;
import com.vmware.vim25.mo.samples.vm.VmNicOp;
import com.vmware.vim25.mo.samples.vm.VmRename;
import com.vmware.vim25.mo.samples.vm.VmStartupOption;
import com.vmware.vim25.mo.util.CommandLineParser;
import com.vmware.vim25.mo.util.OptionSpec;
import com.vmware.vim25.mo.util.PropertyCollectorUtil;
import com.vmware.vim25.Action;
import com.vmware.vim25.AlarmAction;
import com.vmware.vim25.AlarmSetting;
import com.vmware.vim25.AlarmSpec;
import com.vmware.vim25.AlarmTriggeringAction;
import com.vmware.vim25.GroupAlarmAction;
import com.vmware.vim25.MethodAction;
import com.vmware.vim25.MethodActionArgument;
import com.vmware.vim25.SendEmailAction;
import com.vmware.vim25.StateAlarmExpression;
import com.vmware.vim25.StateAlarmOperator;
import com.vmware.vim25.mo.AlarmManager;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import org.tempuri.* ;
import com.vmware.vim25.Action;
import com.vmware.vim25.AlarmAction;
import com.vmware.vim25.AlarmSetting;
import com.vmware.vim25.AlarmSpec;
import com.vmware.vim25.AlarmTriggeringAction;
import com.vmware.vim25.GroupAlarmAction;
import com.vmware.vim25.HostListSummaryQuickStats;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.MethodAction;
import com.vmware.vim25.MethodActionArgument;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.ObjectUpdate;
import com.vmware.vim25.PropertyChange;
import com.vmware.vim25.PropertyChangeOp;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertyFilterUpdate;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.SendEmailAction;
import com.vmware.vim25.StateAlarmExpression;
import com.vmware.vim25.StateAlarmOperator;
import com.vmware.vim25.UpdateSet;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineQuickStats;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.*;
import com.vmware.vim25.mo.util.*;
import com.vmware.vim25.ws.*;
import com.vmware.vim25.GuestDiskInfo;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.GuestNicInfo;

import java.util.*;
import java.io.*;
import java.net.URL;
import static java.util.concurrent.TimeUnit.*;

public class HelloVM 
{
	
	
	public static void main(String[] args) throws Exception
	{
		
		long start = System.currentTimeMillis();
		URL url = new URL("https://130.65.157.246/sdk");
		ServiceInstance si = new ServiceInstance(url, "administrator", "12!@qwQW", true);
		
		long end = System.currentTimeMillis();
		//System.out.println("time taken:" + (end-start));
		Folder rootFolder = si.getRootFolder();
		
		
		String name = rootFolder.getName();
		//System.out.println("root:" + name);
		
		ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
		if(mes==null || mes.length ==0)
		{
			return;
		}
		//Commented by rashmi on 7th april
		//VirtualMachine vm = (VirtualMachine) mes[0];
		
		VirtualMachine vm = (VirtualMachine) mes[2]; 
		VirtualMachineConfigInfo vminfo = vm.getConfig();
		VirtualMachineCapability vmc = vm.getCapability();
		vm.getResourcePool();
		
		VirtualMachineSummary vms= vm.getSummary();
		
		//---Statistic
		for (int i = 0; i < mes.length; i++) 
		{
			
			VirtualMachine vm1 = (VirtualMachine) mes[i];	
			//writeVmInfo(vm);
			
			System.out.println("---------------------Statistics---------------");
			 
			GuestInfo guestInfo = vm1.getGuest();
			System.out.println("----Vitual Machine---- :" + vm1.getName());
			System.out.println("	        Os Info : " + guestInfo.guestFullName );
			System.out.println("	     IP Address : " + guestInfo.ipAddress );
			System.out.println("	   Tools Status : " + guestInfo.toolsStatus );
			System.out.println("	  Tools version : " + guestInfo.toolsVersion );
			
			if ( guestInfo.getToolsStatus().toString() != "toolsNotRunning" ) {
				System.out.println("	    Screen size : " + guestInfo.getScreen().getWidth() + "x" + guestInfo.getScreen().getHeight() );
			}			
			
			
			System.out.println("----Memory Statistic----");
			VirtualMachineSummary summary = vm1.getSummary();
			VirtualMachineQuickStats quickStats = summary.getQuickStats();
			System.out.println("	Memory Usage : " +quickStats.getGuestMemoryUsage());
			System.out.println("	Uptime : " + quickStats.uptimeSeconds + " seconds");
			System.out.println("	CPU usage : " + quickStats.getOverallCpuUsage() + " Mhtz");
				
			System.out.println("----NIC Statistic----");

			GuestNicInfo[] nicInfo = guestInfo.getNet();
			if ( nicInfo != null && nicInfo.length > 0 ) 
			{ 
				GuestNicInfo firstNic = nicInfo[0];

				System.out.println("	 Nic0 Connected : " + firstNic.connected );
				//System.out.println("	        Nic0 IP : " + firstNic.getIpAddress()[0] );
				System.out.println("	  Nic0 Mac Addr : " + firstNic.macAddress );
				System.out.println("	   Nic0 Network : " + firstNic.network );
				
			}
	
		
		System.out.println("---- Hardware Statistic----");
		
			GuestDiskInfo[] diskInfo = guestInfo.getDisk();
			if ( diskInfo != null && diskInfo.length > 0 ) { 
				GuestDiskInfo firstDisk = diskInfo[0];

			System.out.println("	  Disk0 capcity : " + firstDisk.capacity + " MB");
			System.out.println("	     Disk0 Free : " + firstDisk.capacity + " Free");
			System.out.println("	      Disk Path : " + firstDisk.diskPath );
			}
			
			
					//Hardware config info
			VirtualMachineConfigInfo config = vm.getConfig();
			VirtualHardware hw = config.getHardware();
			System.out.println("Memory in MB: " + hw.getMemoryMB());
			System.out.println("# of CPUs: " + hw.getNumCPU());
			VirtualDevice[] devices = hw.getDevice();
			
			VirtualDevice device = devices[i];
			Description deviceInfo = device.getDeviceInfo();
			System.out.println("Device (" + device.getKey() + "): " + deviceInfo.getLabel() + " : " + deviceInfo.getSummary());
			System.out.println("------------------------------------------------------------------");
			
			
		}
		
	
		
		//--
		
		
		//Alarm
		//String [] arg_alarm={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_3"};
		//CreateVmAlarm.main(arg_alarm);
				
		//to ping  VMs
		
		Thread threadty= new Thread(new Threadty("thread1","130.65.157.110","VM_Ubuntu_3",vm));
		threadty.start();
		
		//Thread threadty1= new Thread(new Threadty("thread2","130.65.157.88","VM_Ubuntu_2",vm));
		//threadty1.start();
		
		//Thread threadty2= new Thread(new Threadty("thread3","130.65.157.232","VM_Ubuntu_3",vm));
		//threadty2.start();
		
		
		//Thread threadty4= new Thread(new Thread_snapshotrefersh("thread5", "130.65.157.238", "VM_Ubuntu_3"));
		//threadty4.start();
		
		/*
		
		Thread threadty3= new Thread(new Thread_snapshotrefersh("thread4", "130.65.157.64", "VM_Ubuntu_1"));
		threadty3.start();
		
		Thread threadty4= new Thread(new Thread_snapshotrefersh("thread5", "130.65.157.207", "VM_Ubuntu_2"));
		threadty4.start();
		
		Thread threadty5= new Thread(new Thread_snapshotrefersh("thread5", "130.65.157.232", "VM_Ubuntu_3"));
		threadty5.start();
		*/
		
		//do i need to creat thread for refreshing snapshot-done
		//How to check hearbeat of host before migrating-code done,run n check it
		//How to check heartbeat of VM before refreshing snapshot
		//
		//show statistics once when started to run and once after recovery
		//Alarm should be triggered off when vm is power off...But if vm is powered off then it should not goto recovery
		
		
		//Snapshot referesh
		//snapshotrefresh.main(null);
		
		//tryschedule.main(null);
		
		//Statistics
		
	//	String[] arg_getperf={"https://130.65.157.246/sdk","administrator","12!@qwQW","Clone1"};
		//GetMultiPerf.main(arg_getperf);
		
		//String[] arg_printperf={"https://130.65.157.246/sdk","administrator","12!@qwQW"};
		//PrintPerfMgr.main(arg_printperf);
		
		/*
		System.out.println("Hello " + vm.getName());
		System.out.println("GuestOS: " + vminfo.getGuestFullName());
		System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());
*/
				
				
				//String [] arg_snapshotrevert={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","revert"};
				//VMSnapshot.main(arg_snapshotrevert);
			
		
				//Remove NIC -in vcenter it will not show ip address and not respond to the ping after removing the nic
		  
		 		//String [] arg_removenic={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","remove","Network adapter 1" };
		 		//VmNicOp.main(arg_removenic);
		 	
		 	
		
						
				//  si.getServerConnection().logout();
				
}
}


	
