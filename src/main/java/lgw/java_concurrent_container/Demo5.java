package lgw.java_concurrent_container;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName Demo5
 * @Description 不同的 List 在并发环境下的效率测试
 * @Author Numblgw
 * @Date 2019/6/3 15:24
 */
public class Demo5 {

	private static List<Integer> list =

			// 效率比较高，但是并发环境下会出现问题
			// new ArrayList<>();

			// 线程安全的 ArrayList,所有方法全部加锁。
			new Vector<>();

			// 写时复制，在进行修改或者新增操作时，会先将原内容copy，在copy中修改，
			// 然后让引用指向copy。读操作不用加锁效率极高，但修改和写入效率极低。并发安全。
			// new CopyOnWriteArrayList<>();

	public static void main(String[] args) {
		Thread[] threads = new Thread[100];
		CountDownLatch latch = new CountDownLatch(100);
		Random random = new Random();
		for(int i = 0 ; i < threads.length ; i++) {
			threads[i] = new Thread(() -> {
				for(int j = 1000 ; j > 0 ; j--) {
					list.add(j + random.nextInt(10000));
				}
				latch.countDown();
			});
		}
		long start = System.currentTimeMillis();
		Arrays.asList(threads).stream().forEach(Thread::start);

		try {
			latch.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println(list.size());
		System.out.println(end - start);

	}
}
