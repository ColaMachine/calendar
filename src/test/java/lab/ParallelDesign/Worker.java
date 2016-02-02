/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年1月4日
 * 文件说明: 
 */
package lab.ParallelDesign;

import java.util.Map;
import java.util.Queue;

public class Worker implements Runnable{
    protected Queue<Object> workQueue;
    protected Map<String,Object> resultMap;
    protected void setWorkQueue(Queue<Object> workQueue){
        this.workQueue=workQueue;
    }
    public void setResultMap(Map<String,Object> resultMap){
        this.resultMap =resultMap;
    }
    public Object handle(Object input){
        return input;
    }
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     * @author colamachine
     */
    @Override
    public void run() {
        while (true){
            Object input =workQueue.poll();
            System.out.println(input);
            if(input ==null)break;
            Object re=handle(input);
            resultMap.put(Integer.toString(input.hashCode()),re);
            
        }
        // TODO Auto-generated method stub
        
    }
    
}
