package lgw.java_atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName Demo3
 * @Description LongAdder
 * @Author Numblgw
 * @Date 2019/6/5 10:23
 */
public class Demo3 {
	LongAdder longAdder = new LongAdder();

	public static void main(String[] args) {
		Demo3 demo3 = new Demo3();
		for(int i = 5 ; i > 0 ; i--) {
			new Thread(() -> {
				demo3.longAdder.add(10L);
			}).start();
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(demo3.longAdder.sum());
	}
}
