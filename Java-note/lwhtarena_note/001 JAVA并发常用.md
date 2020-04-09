# JAVA 并发常用工具

### java并发之TimeUnit理解

**TimeUnit** 是`java.util.concurrent`包下面的一个类，TimeUnit提供了可读性更好的线程暂停操作，通常用来替换`Thread.sleep()`，**在很长一段时间里Thread的sleep()方法作为暂停线程的标准方式**。

事实上sleep方法本身也很常用而且出现在很多面试中。如果你已经使用过Thread.sleep()，当然我确信你这样做过，那么你一定熟知它是一个静态方法，暂停线程时它不会释放锁，该方法会抛出InterrupttedException异常（如果有线程中断了当前线程）。但是我们很多人并没有注意的一个潜在的问题就是它的可读性。Thread.sleep()是一个重载方法，可以接收长整型毫秒和长整型的纳秒参数，这样对程序员造成的一个问题就是很难知道到底当前线程是睡眠了多少秒、分、小时或者天。

```java
Thread.sleep(2400000);   //可读性差
     ↓
Thread.sleep(4*60*1000);   //可读性稍微好一点
     ↓
/**
 * DAYS 天
 * HOURS 时
 * MINUTES 分
 * SECONDS 秒
 * MILLISECONDS 毫秒
 * NANOSECONDS 纳秒
 */
TimeUnit.MINUTES.sleep(4);  // sleeping for 4分钟，更加清晰

//TimeUnit还提供了便捷方法用于把时间转换成不同单位
TimeUnit.SECONDS.toMillis(44);  //将返回44,000

//关于秒的常用方法 
TimeUnit.SECONDS.toMillis(1) 1秒转换为毫秒数 
TimeUnit.SECONDS.toMinutes(60) 60秒转换为分钟数 
TimeUnit.SECONDS.sleep(5) 线程休眠5秒 
TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES) 1分钟转换为秒数 

//TimeUnit.DAYS 日的工具类 
//TimeUnit.HOURS 时的工具类 
//TimeUnit.MINUTES 分的工具类 
//TimeUnit.SECONDS 秒的工具类 
//TimeUnit.MILLISECONDS 毫秒的工具类
```