package lgw.java_thread_pool;

import java.util.concurrent.*;

/**
 * @ClassName Demo2
 * @Description Future 和 FutureTask 的基本使用
 * @Author Numblgw
 * @Date 2019/6/3 20:10
 */
public class Demo2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {

		// ###演示 FutureTask
		FutureTask<String> futureTask = new FutureTask<>(() -> {
			TimeUnit.MILLISECONDS.sleep(500);
			return "Numb";
		});
		new Thread(futureTask).start();
		// get() 方法会阻塞，等待任务执行完。
		System.out.println(futureTask.get());

		// ###演示 Future
		ExecutorService service = Executors.newFixedThreadPool(5);
		Future<String> future = service.submit(() -> {
			TimeUnit.MILLISECONDS.sleep(500);
			return "lgw";
		});
		// false
		System.out.println(future.isDone());
		// 阻塞，并等待该任务执行。
		System.out.println(future.get());
		// ture
		System.out.println(future.isDone());
	}
}
