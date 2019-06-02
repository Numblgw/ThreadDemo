package lgw.java_atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Demo2
 * @Description 证明atomicXXX类比synchronized更高效,好像证明不了。。。。。。
 * 				############## 疑问标记 ############
 * @Author Numblgw
 * @Date 2019/6/2 13:30
 */
public class Demo2 {
	private int count;

	private AtomicInteger countAtomic = new AtomicInteger(0);

	private synchronized void countDown() {
		System.out.println(Thread.currentThread().getName());
		long start = System.currentTimeMillis();
		for(int i = 1000000 ; i > 0 ; i--) {
			this.count--;
		}
		long end = System.currentTimeMillis();
		System.out.println("synchronized用时" + (end - start));
	}

	private void atomicCountDown() {
		System.out.println(Thread.currentThread().getName());
		long start = System.currentTimeMillis();
		for(int i = 1000000 ; i > 0 ; i--) {
			countAtomic.decrementAndGet();
		}
		long end = System.currentTimeMillis();
		System.out.println("atomic用时" + (end - start));
	}

	public static void main(String[] args) {
		Demo2 demo2 = new Demo2();
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(demo2::countDown).start();
			//new Thread(demo2::atomicCountDown).start();
		}
	}
}
