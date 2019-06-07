1) 对于基本类型的数据的操作，java中都有相关的原子性操作类来实现，例如
AtomicInteger,AtomicBoolean,AtomicLong....

2) 这些类中的每一个方法都是使用底层指令实现的原子性操作，可以实现无锁同步。

3) 它只能保证每一个方法的执行是原子性的，不能保证多个方法连续执行是原子性
的,demo1证明。

4) AtomicXXX类可以保证可见性，它给内部维护的变量加了volatile关键字。

5) AtomicXXX 类是在底层使用了 CAS 乐观锁的方式，当并发量很高的时候会造成
多次更新失败影响性能。这时可以使用 LongAdder类操作long型的数据，Demo3演
示基本用法。