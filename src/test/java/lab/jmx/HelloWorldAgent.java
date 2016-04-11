package lab.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

//import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloWorldAgent {

    public static void main(String[] args) throws MalformedObjectNameException,
            NullPointerException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException, IOException {

        int rmiPort = 1099;
        String jmxServerName = "TestJMXServer";

        // jdkfolder/bin/rmiregistry.exe 9999
        Registry registry = LocateRegistry.createRegistry(rmiPort);

        MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
        //MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
/*
      HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        ObjectName adapterName;
        adapterName = new ObjectName(jmxServerName + ":name=" + "htmladapter");
        adapter.setPort(8082);
        adapter.start();*/
/*        mbs.registerMBean(adapter, adapterName);*/

        ObjectName objName = new ObjectName(jmxServerName + ":name=" + "HelloWorld");
        mbs.registerMBean(new HelloWorld(), objName);


        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + jmxServerName);
        System.out.println("JMXServiceURL: " + url.toString());
        JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxConnServer.start();

    }
    public static void main(String args){
        try {
            int rmiPort = 1099;
            String jmxServerName = "TestJMXServer";

            // jdkfolder/bin/rmiregistry.exe 9999
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();//可在jconsole中使用
            HelloWorld helloWorld=new HelloWorld();
            mbs.registerMBean(helloWorld, new ObjectName("MyappMBean:name=helloWorld"));

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + jmxServerName);
            System.out.println("JMXServiceURL: " + url.toString());
            JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
            jmxConnServer.start();


            MBeanServerConnection connection= JMXManager.createMBeanServer("127.0.0.1","1099","","");
            Map map =   JMXManager.getAllAttribute(connection,new ObjectName("MyappMBean:name=helloWorld"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}