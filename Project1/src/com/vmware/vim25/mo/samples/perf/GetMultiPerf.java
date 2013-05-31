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

package com.vmware.vim25.mo.samples.perf;

import java.net.URL;
import java.util.Calendar;

import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */

public class GetMultiPerf 
{
  public static void main(String[] args) throws Exception
  {
	  System.out.println("Step1");
    if(args.length != 4)
    {
      System.out.println("Usage: java GetMultiPerf " 
        + "<url> <username> <password> <vmname>");
      return;
    }

    System.out.println("Step2");
    ServiceInstance si = new ServiceInstance(
      new URL(args[0]), args[1], args[2], true);
    System.out.println("Step3");
    String vmname = args[3];
    VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
      si.getRootFolder()).searchManagedEntity(
        "VirtualMachine", vmname);
    System.out.println("Step4" + vm);
    if(vm == null)
    {
      System.out.println("Virtual Machine " + vmname 
          + " cannot be found.");
      si.getServerConnection().logout();
      return;
    }

    System.out.println("Step5");
    PerformanceManager perfMgr = si.getPerformanceManager();

    int perfInterval = 1800; // 30 minutes for PastWeek
    
    System.out.println("Step6");
    // retrieve all the available perf metrics for vm
    PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(
        vm, null, null, perfInterval);
    System.out.println("Step7");
    Calendar curTime = si.currentTime();
    System.out.println("Step8");
    PerfQuerySpec qSpec = new PerfQuerySpec();
    System.out.println("Step9");
    qSpec.setEntity(vm.getRuntime().getHost());
    System.out.println("Step10");
    //metricIDs must be provided, or InvalidArgumentFault 
    qSpec.setMetricId(pmis);
    System.out.println("Step11");
    qSpec.setFormat("normal"); //optional since it's default
    System.out.println("Step12");
    qSpec.setIntervalId(perfInterval); 
    System.out.println("Step13");
    Calendar startTime = (Calendar) curTime.clone();
    System.out.println("Step14");
    startTime.roll(Calendar.DATE, -4);
    System.out.println("Step15");
    System.out.println("start:" + startTime.getTime());
    System.out.println("Step16");
    qSpec.setStartTime(startTime);
    System.out.println("Step17");
    Calendar endTime = (Calendar) curTime.clone();
    System.out.println("Step18");
    endTime.roll(Calendar.DATE, -3);
    System.out.println("Step19");
    System.out.println("end:" + endTime.getTime());
    System.out.println("Step20");
    qSpec.setEndTime(endTime);
    System.out.println("Step21");
    PerfCompositeMetric pv = perfMgr.queryPerfComposite(qSpec);
    System.out.println("Step22");
    if(pv != null)
    {
      printPerfMetric(pv.getEntity());
      PerfEntityMetricBase[] pembs = pv.getChildEntity();
      for(int i=0; pembs!=null && i< pembs.length; i++)
      {
        printPerfMetric(pembs[i]);
      }
    }
    //si.getServerConnection().logout();
  }

  static void printPerfMetric(PerfEntityMetricBase val)
  {
    String entityDesc = val.getEntity().getType() 
        + ":" + val.getEntity().get_value();
    System.out.println("Entity:" + entityDesc);
    if(val instanceof PerfEntityMetric)
    {
      printPerfMetric((PerfEntityMetric)val);
    }
    else if(val instanceof PerfEntityMetricCSV)
    {
      printPerfMetricCSV((PerfEntityMetricCSV)val);
    }
    else
    {
      System.out.println("UnExpected sub-type of " +
      		"PerfEntityMetricBase.");
    }
  }
  
  static void printPerfMetric(PerfEntityMetric pem)
  {
    PerfMetricSeries[] vals = pem.getValue();
    PerfSampleInfo[]  infos = pem.getSampleInfo();
    
    System.out.println("Sampling Times and Intervales:");
    for(int i=0; infos!=null && i<infos.length; i++)
    {
        System.out.println("sample time: " 
          + infos[i].getTimestamp().getTime());
        System.out.println("sample interval (sec):" 
          + infos[i].getInterval());
    }
    
    System.out.println("\nSample values:");
    for(int j=0; vals!=null && j<vals.length; ++j)
    {
      System.out.println("Perf counter ID:" 
          + vals[j].getId().getCounterId());
      System.out.println("Device instance ID:" 
          + vals[j].getId().getInstance());
      
      if(vals[j] instanceof PerfMetricIntSeries)
      {
        PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
        long[] longs = val.getValue();
        for(int k=0; k<longs.length; k++) 
        {
          System.out.print(longs[k] + " ");
        }
        System.out.println("Total:"+longs.length);
      }
      else if(vals[j] instanceof PerfMetricSeriesCSV)
      { // it is not likely coming here...
        PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
        System.out.println("CSV value:" + val.getValue());
      }
    }
  }
    
  static void printPerfMetricCSV(PerfEntityMetricCSV pems)
  {
    System.out.println("SampleInfoCSV:" 
        + pems.getSampleInfoCSV());
    PerfMetricSeriesCSV[] csvs = pems.getValue();
    for(int i=0; i<csvs.length; i++)
    {
      System.out.println("PerfCounterId:" 
          + csvs[i].getId().getCounterId());
      System.out.println("CSV sample values:" 
          + csvs[i].getValue());
    }
  }
}