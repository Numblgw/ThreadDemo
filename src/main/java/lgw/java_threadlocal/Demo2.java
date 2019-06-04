package lgw.java_threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName Demo2
 * @Description 验证 ThreadLocal 变量在使用线程池时可能存在的内存泄漏问题
 * @Author Numblgw
 * @Date 2019/6/4 10:44
 */
public class Demo2 {
	private ThreadLocal<Integer> count = new ThreadLocal<>();

	public static void main(String[] args) {
		ExecutorService service = newFixedThreadPool(1);
		Demo2 demo2 = new Demo2();
		service.submit(() -> {
			demo2.count.set(100);
			System.out.println(Thread.currentThread().getName() + "将count设置为100");
		});
		service.submit(() -> {
			
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			int c = demo2.count.get();
			System.out.println(Thread.currentThread().getName() + "读取到 " + c);
		});
	}
}
