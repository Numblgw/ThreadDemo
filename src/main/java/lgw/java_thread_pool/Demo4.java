package lgw.java_thread_pool;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName Demo4
 * @Description SingleThreadPool 的基本用法演示
 * @Author Numblgw
 * @Date 2019/6/3 20:54
 */
public class Demo4 {
	public static void main(String[] args) {
		ExecutorService service = newSingleThreadExecutor();
		for(int i = 5 ; i > 0 ; i--) {
			// 五个任务都是由一个线程执行,任务再多也不会创建新的线程。
			service.submit(() -> System.out.println(Thread.currentThread().getName()));
		}
	}
}
