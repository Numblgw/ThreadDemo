package lgw.java_thread_pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName Demo6
 * @Description WorkStealingThreadPool 基本用法演示
 * @Author Numblgw
 * @Date 2019/6/4 10:20
 */
public class Demo6 {
	public static void main(String[] args) {
		ExecutorService service = newWorkStealingPool();
		// 输出处理器的核数（cpuCoreNum）
		System.out.println(Runtime.getRuntime().availableProcessors());
		for(int i = 1 ; i <= 9 ; i++) {
			service.execute(new Inner(i * 1000));
		}
		try {
			// 因为产生的是精灵线程，所以需要阻塞主线程才能看到后台线程的输出。
			System.in.read();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static class Inner implements Runnable {

		private int time;

		public Inner(int time) {
			this.time = time;
		}


		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(time + "..." + Thread.currentThread().getName());
		}
	}
}
