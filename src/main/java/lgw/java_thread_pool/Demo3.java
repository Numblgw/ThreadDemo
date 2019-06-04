package lgw.java_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;


/**
 * @ClassName Demo3
 * @Description	CacheThreadPool的基本用法
 * @Author Numblgw
 * @Date 2019/6/3 20:41
 */
public class Demo3 {
	public static void main(String[] args) {
		ExecutorService service = newCachedThreadPool();

		// 初始线程池状态，验证初始线程数 0
		System.out.println(service);
		// 启动两个任务
		for(int i = 2 ; i > 0 ; i--) {
			service.submit(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		// 添加两个任务之后线程池的状态，验证当提交任务线程池中没有空闲线程时会创建线程。
		System.out.println(service);

		// 睡 80 秒 使线程池中的线程销毁，验证空闲 60 秒线程自动销毁
		try {
			TimeUnit.SECONDS.sleep(80);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// 打印线程池中的线程注销后线程池的状态。
		System.out.println(service);
	}
}
