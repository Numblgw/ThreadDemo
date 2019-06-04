1) **基本概念**  —— ThreadLocal 代表线程局部变量，可以理解为每个线程都会创建一个自己的变量，
不能和其他线程共享，加 volatile 也没有用，demo1演示基本用法。

2) **内存泄漏** —— 在使用线程池的时候，ThreadLocal 会存在内存泄漏的问题，因为线程池中的线
程在执行玩任务后不会销毁，当该线程在执行新的任务的时候还是可以读取到上个任
务给 ThreadLocal 变量设置的值，demo2演示内存泄漏。