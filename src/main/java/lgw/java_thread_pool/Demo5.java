package lgw.java_thread_pool;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName Demo5
 * @Description ScheduledThreadPool 基本用法演示
 * 				当线程池中线程不够用时，不会自动开启新的线程，会将下面的任务推迟。
 * @Author Numblgw
 * @Date 2019/6/4 9:53
 */
public class Demo5 {
	public static void main(String[] args) {
		ScheduledExecutorService service = newScheduledThreadPool(5);
		// 定时执行
		// 参数一 lambda表达式，代表执行的行为。
		// 参数二 第一个任务延迟执行的时间。
		// 参数三 任务间隔的时间。
		// 参数四 时间单位。
		service.scheduleAtFixedRate(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}, 0, 500, TimeUnit.MICROSECONDS);

	}
}
