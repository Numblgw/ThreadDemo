package lgw.java_synchronized;

/**
 * @ClassName Demo7
 * @Description 探究子类同步方法调用父类同步方法时，是不是只获取了子类对象的锁。
 * @Author Numblgw
 * @Date 2019/6/2 12:15
 */
public class Demo7 {
	// 父类同步方法
	public synchronized void runParent() {
		// 子类对象调用父类方法是，this还是指向子类对象，所以这两个方法用的是同一把锁（this对象锁）
		System.out.println(this);
	}

	public static void main(String[] args) {
		Sub sub = new Sub();
		new Thread(sub::runChild).start();
	}
}

class Sub extends Demo7 {
	// 子类同步方法
	public synchronized void runChild() {
		System.out.println(this);
		// 子类同步方法调用父类同步方法
		super.runParent();
	}
}
