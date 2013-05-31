package com.vmware.vim25.mo.samples;
	import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Folder;

import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineQuickStats;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.samples.vm.VMSnapshot;

	public class Thread_snapshotrefersh  extends TimerTask implements Runnable{
		
		private DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
		static String Tname;
		String ip;
		static String Virtualname;
		
		 Thread_snapshotrefersh() {
		
		}
		
		public Thread_snapshotrefersh(String thname, String ipad,String vmname)
		{
			Tname=thname;
			ip=ipad;
			Virtualname=vmname;
			
		}
		
		  public static void main(String[] args) {
			 
		
		  }

		  public void run() {
			  
			snapshotrefresh snp = new snapshotrefresh(Virtualname, Tname, ip);
			snp.main(null);
			
			 }
	}


