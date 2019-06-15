package lgw.java_singleton;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo1
 * @Description 单例模式的多种实现方式
 * @Author Numblgw
 * @Date 2019/6/3 11:23
 */
public class Demo1 {
	public static void main(String[] args) {

		// 通过打印的对象可以看出，多个线程得到的是不是同一个对象。
		for(int i = 5 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					// T1 线程安全
					// System.out.println(Thread.currentThread().getName() + "..." + T1.getT1());

					// T2 线程不安全
					// System.out.println(Thread.currentThread().getName() + "..." + T2.getT2());

					// T3 线程安全
					// System.out.println(Thread.currentThread().getName() + "..." + T3.getT3());

					// T4 线程安全
					// System.out.println(Thread.currentThread().getName() + "..." + T4.getT4());

					// T5 线程安全
					System.out.println(Thread.currentThread().getName() + "..." + T5.getT5());
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}

/**
 * 饿汉式 实现单例模式
 *
 * 优点  线程安全，多个线程得到的都是同一个对象
 *
 * 缺点  无法实现延迟加载，即没法在需要用到该对象的时候在加载这个对象，只能一开始就加载，占用内存。
 */
class T1 {

	/**
	 * 内部维护一个自身的引用，并且手动初始化。
	 */
	private static T1 t1 = new T1();

	/**
	 * 构造器私有化，不允许外部 new 对象
	 */
	private T1() {

	}

	/**
	 * 提供一个静态的方法返回上面的对象
	 * @return
	 */
	public static T1 getT1() {
		return t1;
	}


}

/**
 * 懒汉式实现多线程不安全的单例模式
 *
 * 优点  可以实现延迟加载。
 *
 * 缺点  线程不安全。
 *
 */
class T2 {
	/**
	 * 不给内部维护的对象初始化
	 */
	private static T2 t2;

	private T2() {

	}

	public static T2 getT2() {
		if(t2 == null) {
			t2 = new T2();
		}
		return t2;
	}
}

/**
 * 懒汉式实现线程安全的单例模式, 使用 synchronized修饰方法实现
 *
 * 优点  可以实现延迟加载，并且线程安全。
 *
 * 缺点  每次获得对象都要先获取锁，影响性能。当 T3 对象已经存在的时候应该不需要获取锁。
 */
class T3 {

	private static T3 t3;

	private T3() {

	}

	public static synchronized T3 getT3() {
		if(t3 == null) {
			t3 = new T3();
		}
		return t3;
	}
}

/**
 * 懒汉式实现单例模式 (double check)
 *
 * 优点  可以实现延迟加载，线程安全
 *
 * 缺点  代码不够优雅（手动滑稽）
 *
 */
class T4 {
	/**
	 * ###############################################
	 * 这里如果不加 volatile 在指令重排序时可能会出现问题。
	 */
	private volatile static T4 t4;

	private T4() {

	}

	public static T4 getT4() {
		// 外层判断是用于  当 T4 对象已经存在的时候，再执行该方法不用获取锁。
		if(t4 == null) {
			synchronized(T4.class) {
				// 内层判断是为了保证多线程环境下只会产生一个对象。
				// 当多个线程都通过了外层判断，等待获取锁时，如果不加内层判断就会产生多个对象。
				if(t4 == null) {
					t4 = new T4();
				}
			}
		}
		return t4;
	}
}

/**
 * 静态内部类实现单例模式
 *
 * 分析
 * 1) 只有在调用 getT5 方法的时候才会加载内部类的信息，并创建外部类对象，所以可以实现延迟加载。
 * 2) 类加载的过程在 jvm 中是自动加锁执行的（为了防止一个类被加载多次），所以 getT5 不用加锁也可以线程安全。
 */
class T5 {
	private T5() {

	}

	private static class Inner {
		public static T5 t5 = new T5();
	}

	public static T5 getT5() {
		return Inner.t5;
	}
}
