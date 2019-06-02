package lgw.java_atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Demo1
 * @Description 验证多个原子性方法连续调用	并不是原子性的
 * @Author Numblgw
 * @Date 2019/6/2 13:11
 */
public class Demo1 {
	private AtomicInteger count = new AtomicInteger(10);

	private void countDown() {
		while(count.get() > 0) {
			// 判断完之后睡一秒在执行，可能会出现 在 count 等于 n 时 有大于 n 个线程判断成功，这时在进行下面的自减操作就会出现负数
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(count.decrementAndGet());
		}
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		for(int i = 5 ; i > 0 ; i--) {
			new Thread(demo1::countDown).start();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
