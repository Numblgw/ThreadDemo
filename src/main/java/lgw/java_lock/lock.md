1) reentrantLock 与 synchronized 性能差不多，但是reentrantLock更加灵活，demo1。

2) reentrantLock 和 synchronized 使用方式差不多，reentrantLock 不能自动释放锁，必须手动调用 unlock,一般在finally子句中调用，demo1。

2) reentrantLock  可以使用 condition 条件对象，唤醒在指定条件等待的线程,MyContainer3中使用。

3) reentrantLock 可以使用 tryLock 尝试获取锁，并且可以得到boolean型返回值判断是否获取到锁,还可以指定等待的时间，demo2。

4) reentrantLock 可以使用 lockInterruptibly 获取锁，可以相应中断。不管该线程是否获取到锁都可以中断，中断会释放锁，demo2。