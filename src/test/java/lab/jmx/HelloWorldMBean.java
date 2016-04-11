package lab.jmx;

/**
 * Created by dozen.zhang on 2016/4/11.
 */

public interface HelloWorldMBean {
    public String getName();
    public void setName(String name);
    public void printHello();
    public void printHello(String whoName);
}
