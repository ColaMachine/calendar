import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class test {
	/**
	 * 说明:
	 * @param args
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年12月15日下午11:15:17
	 */
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task(1);
        Future<Integer> result = executor.submit(task);
        executor.shutdown();
         
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
         
        System.out.println("主线程在执行任务");
         
        try {
        
            System.out.println("task运行结果"+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
         
        System.out.println("所有任务执行完毕");
    }
}
class Task implements Callable<Integer>{
    private int i;
    private int complete;
    private int succ;
    Task(int j){
      
    }
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        for(int i=0;i<100;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            complete = i;
        }
        return complete;
    }
}