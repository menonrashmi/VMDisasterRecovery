package com.vmware.vim25.mo.samples;

import java.net.URL;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class Poweron_VM {

	public static void main(String[] args) throws Exception
	
	  {
		  
		System.out.println("VM Powering on");
	    String cloneName = args[3];

	    ServiceInstance si = new ServiceInstance(
	        new URL(args[0]), args[1], args[2], true);
	    		
	  Folder rootFolder = si.getRootFolder();
	  VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
	        rootFolder).searchManagedEntity(
	            "VirtualMachine", cloneName);
	  
	    if(vm==null)
	    {
	      System.out.println("No VM " + cloneName + " found");
	      si.getServerConnection().logout();
	      return;
	    }
	    
	    else
	    	
	    	vm.powerOnVM_Task(null);
	    
}
}