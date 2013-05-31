package com.vmware.vim25.mo.samples;

import com.vmware.vim25.mo.VirtualMachine;
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

import java.util.*;
import java.io.*;
import java.net.URL;
import static java.util.concurrent.TimeUnit.*;

public class Failover {

	/*
	//Clone
	CloneVM.main(url,"administrator","12!@qwQW","VM_Ubuntu_1","Clone");

		System.out.println("cloning");
		String [] arg={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","Clone1"};
		CloneVM.main(arg);

	//	Migrate VM
	
		String [] arg_migrate={"https://130.65.157.246/sdk","Administrator","12!@qwQW","Clone1","130.65.157.86"};
		MigrateVM.main(arg_migrate);

		//Power Migrated VM
		
			VirtualMachine vm1 = (VirtualMachine) new InventoryNavigator(
			      si.getRootFolder()).searchManagedEntity(
			        "VirtualMachine", "Clone1");
		
			vm1.powerOnVM_Task(null);
		
	//Alarm
	
	String [] arg_alarm={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1"};
	CreateVmAlarm.main(arg_alarm);
	
	
	//Print alarm

	String[] arg_alarmprint={"https://130.65.157.246/sdk","administrator","12!@qwQW"};
	PrintAlarmManager.main(arg_alarmprint);
	
	
	//printElementDescription
	//PowerStateAlarm 
	System.out.println("Powerstate Alarm");
	
	String [] arg_powerstatealarm={"https://130.65.157.246/sdk","administrator","12!@qwQW","VM_Ubuntu_1","VmPowerStateAlarm"};
	VMPowerStateAlarm.main(arg_powerstatealarm);
	

*/
}
