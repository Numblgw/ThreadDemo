package lgw.java_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 导入 java.util.concurrent.Executors 类中的多个静态方法
 */
import static java.util.concurrent.Executors.*;


/**
 * @ClassName Demo1
 * @Description 线程池的基本用法
 * @Author Numblgw
 * @Date 2019/6/3 19:29
 */
public class Demo1 {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 创建一个有5个线程的线程池， FixedThreadPool 固定数量的线程池
		 */
		ExecutorService service = newFixedThreadPool(5);
		for(int i = 6 ; i > 0 ; i--) {
			service.execute(() -> {

				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(Thread.currentThread().getName());
			});
		}

		System.out.println(service);

		// 关闭线程池，会先等待任务执行完毕。
		service.shutdown();

		// 是否已终止
		System.out.println(service.isTerminated());

		// 是否正在关闭
		System.out.println(service.isShutdown());

		System.out.println(service);

		TimeUnit.SECONDS.sleep(5);

		System.out.println(service.isTerminated());

		System.out.println(service.isShutdown());

		System.out.println(service);
	}
}
