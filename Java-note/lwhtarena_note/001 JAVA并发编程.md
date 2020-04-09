# JAVA并发编程

> Java的并发

　对于大部分从事Java开发不久的程序猿来讲（包括LZ），并发一般都是很少接触到的（这里主要以LZ的领域来说，即J2EE），因为现有的框架已经将很多并发的问题给解决了，并给我们这些无脑程序猿创造了一个串行程序的环境。

　　比如，在J2EE领域的servlet规范当中，servlet是单例的，并且非常有可能，甚至可以说一定会被多个线程并发的去访问。因此servlet其实是有并发的安全性问题的，除非你不在servlet当中记录任何状态。但是当前比较火的MVC框架struts2已经帮我们解决了这个问题，尽管Action当中经常会有一些数据或者说状态，但Action在struts2中是非单例的，这相当于每个Action实例都是线程私有的，因此不存在并发问题。

　　有一些情况下，我们可能会接触到并发问题，比如，你需要做一个单例的对象，那么这个对象一般可以被全局访问，因此就可能存在并发的问题。可以这么说，几乎所有采用了单例模式的对象都会涉及到并发的问题，除非这个对象没有任何状态，但是这往往不会出现，因为没有状态的单例对象是没有意义的，它们更好的处理方式应该是一个无实例（即将构造函数私有化）且充满了静态方法的类。

　　很多程序猿在初次意识到并发时，都会采取一个看似万能却并非一定有用的方法，那就是将一个类的所有方法加上synchronized关键字。LZ以前也是这样的，而且还自认为十分高端，现在想想，LZ实在自惭形秽。当时LZ对synchronized的理解，就知道它可以让很多线程一个一个来执行这个方法，至于其它的特性，就不太明白了。

　　其实在大部分时候，一些比较简单的场景中，上面这种无脑做法还是能起到相应的作用的，也就是说，它可以保证安全性与活跃性。但是另外一个特性就无法保证了，那就是性能。无脑的方法同步，有时候会将性能降低数个数量级，可能会使很多线程都在等待，CPU却一直处于1%利用率的情况。

　　通常情况下，对于安全性、活跃性以及性能来说，我们会将性能放在最后一位，引用之前看过的一句经典的话，是用来形容面向对象设计的，即“可复用的前提是可用”。同样的，对于并发的程序来讲，“性能高的前提是程序的执行是正确的”。

> 线程安全

　线程安全这个词汇实在是折磨人，它给人一种错觉，让你仿佛很轻松的理解了它，但实则是一个典型的笑面虎，背后冷不丁就给你一刀，让你血溅职场。

　　我们先来看下这个词语组成的词汇都有哪些，首先后面可以加一“性”字，此为**线程安全性**。另外，如果后面加“类”或者“程序”，就组成了**线程安全类**或者是**线程安全程序**。很显然，线程安全性是类和程序的属性，就像一个类或者程序的其它属性一样，例如扩展性、维护性等等。

　　到现在重点就出来了，到底什么是线程安全性？从字面上看，线程安全性就是一个类或者程序在多线程的环境中运行是安全的。可是这显然是废话，重点还是落在了安全性上面。怎么才能称作是安全的？

　　LZ这里先贴出一个比较官方的解释，接下来再和各位猿友侃侃大山。安全性是指，某个类的行为与其规范完全一致。那么我们现在就可以将整句话连起来了，也就是说，线程安全性就是指，一个类或者程序在多线程的环境下，其行为与规范完全一致的特性。

　　有的猿友可能会说，“我们开发从来都没有规范的，OK？既然如此，何来与规范一致一说？”。是的，只是如果哪位猿友心里冒出这么一句话的话，说明你对这里的“规范”两字理解错误了，这里的规范可不是指的编码规范。LZ举个简单的例子来说明，这个规范的意思是什么。

```Java
public class Region {
    
    private int left;
    
    private int right;
    
    public Region() {
        super();
    }

    public Region(int left, int right) {
        super();
        if (left <= right) {
            this.left = left;
            this.right = right;
        }else {
            this.left = left;
            this.right = right;
        }
    }

    public void setLeft(int left) {
        if (left > right) {
            this.left = right;
        }else {
            this.left = left;
        }
    }

    public void setRight(int right) {
        if (right < left) {
            this.right = left;
        }else {
            this.right = right;
        }
    }
    
    public boolean in(int value){
        return value >= left && value <= right;
    }
    
    public String toString(){
        return "[" + left + "," + right + "]";
    }

}
```
看一下上面这个类，它表示一个整数区间，对于一个区间来讲，我们自然而然的有一些规则，比如区间左边的值必须小于或者等于右边的值。在上面的类当中，我们也在很多地方限制着客户端的输入，试图保持这种规则（但是在多线程环境下，我们这种约束将显得非常薄弱）。

我们说这种规则就是上面提到的规范，也就是说对于Region类来说，始终保持它是一个有效的区间，就是它的规范。因此对于Region类来说，它的线程安全性就是指它可以在多线程的环境下保持它是一个有效的区间（left小于等于right）。对于Region是一个有效的区间这件事来说，其实就相当于在说in方法不能永久返回false。如果我们更加抽象点来说，就是说方法的行为应该与预期的一致。

由此我们可以看出，一个类或程序的规范，就是指它能够始终保持一定的约束条件。比如一个应用类的stop方法，在客户端调用后，必须能够保证应用被正确关闭等等，这些方法的使用说明其实就是一种规范。

> 线程安全类

通过上面的描述，我们知道了线程安全性的定义，或者说，我们已经知道要满足线程安全性需要达到什么要求。那么对于一个类来说，它的线程安全性如果被满足，它就是一个线程安全的类。

对于线程安全的类，我们有一些可描述的规律，接下来LZ就和各位分享一下这些规律，很多时候，它对我们非常有用。

**1、无状态的对象一定是线程安全的。**

这一条规律实在是太有用了，很多时候，我们的代码处于多线程的环境下，而我们往往苦恼于这些代码的安全性。此时，如果你的类是无状态的，那么你就可以高枕无忧的在多线程环境下使用它。

**为什么说无状态的对象一定是线程安全的？**

一个对象如果没有状态，则意味着对象不存在运行时状态的改变，因此无论是单线程还是多线程的情况下，都不会使对象处于不正确的状态。大多数时候，无状态的对象就是一堆代码的持有者而已，它每一个方法的变量都封闭在独立的线程当中，线程相互之间无法共享变量，因此它们也无法互相影响各自的行为。因此，在多线程的环境下，我们首先推荐的就是无状态对象。

下例就是一个无状态对象，它没有任何域，自然也就没有状态。
```Java
public class NonStatusObject{
  
  public void handle(String param){
    System.out.println(param);    
  }    

}
```
**2、不可变对象一定是线程安全的。**

提到不可变对象，总让人不知不觉的想到基本类型的包装类，比如Java当中的String就是典型的不可变对象。不可变对象的不可变性与无状态的对象非常相似，只是无状态对象通过不添加任何状态保持对象在运行时状态的不可变性，而不可变对象则通常通过final域来强制达到这一特性，不过要注意的是，如果final域指向的是可变对象，则该对象依然可能是可变的。

比如一个List的包装类，如果提供了对List的操作，那么既然内部的List是final类型的，该对象依然是可变的，我们看下面的例子。
```Java
import java.util.ArrayList;
import java.util.List;

public class ListWrapper<E> {

    private final List<E> list;
    
    public ListWrapper(){
        list = new ArrayList<E>();
    }
    
    public boolean contains(E e){
        return list.contains(e);
    }
    
    public void add(E e){
        list.add(e);
    }
    
    public void remove(E e){
        list.remove(e);
    }
    
}
```
　这个类其实有时候是有用的，尽管它很简单，但是它可以弥补JDK1.5加入泛型的弊病，比如remove方法的参数是Object。但是很可惜，它唯一的域是final类型的，但却不是不可变的。因为我们提供了add和remove方法，这些方法依然可以改变这个类的状态，因为list的状态就是它的状态。倘若我们在构造函数中加入一些初始化的元素，并且去掉add和remove方法，那么尽管该类引用了可变的非线程安全的类，但它依然是不可变的，也就是说依然是线程安全的。

**3、除了以上两种对象，我们通常都需要使用加锁机制来保证对象的线程安全性。**

　　这一条基本上道出了大部分的情况，很多时候，我们无法将一个可能处于多线程环境的对象设计成以上两种，这时就需要我们进行合适的加锁机制来保证它的线程安全性。通常情况下，我们希望一个对象是无状态的或者不可变的，这可以大大降低程序的复杂性，请尽量这么做。

> 加锁机制（何时加锁）

既然有时候我们必须使用锁机制来保证类的线程安全性，那么我们最关心的就是两件事，第一件是何时加锁，第二件是如何加锁。

　　关于**何时加锁**这个问题，我们主要关注以下几点来决定，这些内容都是并发的精髓。

**1、原子性**

　　原子性，我们通俗的理解就是，一个操作要么就做完，要么就没开始，不存在做了一部分的情况，那么这个操作就具有原子性。这个简单的理解其实有一个重大漏洞，那就是这个操作是针对什么层次来说的，这将直接影响我们的判断。比如下面这个被用的烂透了的例子，万年的自增。
```Java
//    i++;
```
博客园的大神们不让LZ直接输入i++，因此这里加了个注释符号（这算不算一个bug，0.0）。i++这个操作，从编程语言的层次来讲，它是一个原子操作，因为它只有一句代码，如果你去调试这行代码，它一定无法执行一半或一部分。但是如果从汇编语言的层次来讲，它就不是一个原子操作，因为它有好几条指令（看过计算机原理系列的猿友应该非常清楚），既然有好几条指令，那么就意味着i++这个操作在汇编层次，可以存在做了一部分的情况。

　　对于原子性的层次定义，一般应该以CPU提供的指令集为准，至少我们认为，一个指令是无法拆分的操作。从这个角度来看，我们Java当中大部分看似原子性的操作，其实都不是原子操作，比如刚才提到的自增、赋值操作等等。如果在并发环境中，一个操作无法保证其原子性，可能就需要进行加锁操作。

**1.1、竞态条件**

　　上面已经简单的提了一下原子性的概念，接下来，我们再来看一个和原子性密切相关的概念——竞态条件。竞态条件的含义是，操作的正确性要取决于多线程之间指令执行的顺序。

　　看了上面的定义，大部分猿友估计会唏嘘不已，因为多线程之间指令执行的顺序完全是不定的。如果我们考虑一个多线程程序可能的指令执行顺序，或许会得到10种、100种甚至更多种可能，而我们的程序可能在其中几种情况下执行是正确的，也就是说，我们的程序正确的概率可能为1/10、1/100甚至1/1000000。

　　惊呆了，这是中彩票的概率吧？

　　我们可以这么去想，当你中了500万的彩票时，你的程序或许就能正确执行了。程序的正确性完全取决于“运气”，这就是典型的竞态条件。比如下面这个更典型的单例模式当中经常出现的方式。
```Java
public static SingletonObject getInstance(){
    if (instance == null) {
        instance = new SingletonObject();
    }
    return instance;
}
```
这里就出现了竞态条件，因为instance是否为单例，取决于指令执行的顺序。举一个极端的例子，假设10个线程同时运行这个方法，如果这10个线程每一个都判断完instance是否为null之后挂起，那这10个线程在再次被唤醒时都将会去执行new的操作，我们假设每个线程的new和return操作都会一起执行完，然后才把CPU让给其它线程。最终的结果会是，这10个线程得到了10个不一样的实例。各位猿友可以执行一下下面这个简单的测试程序，它将开启100个线程同时执行getInstance方法。
```Java
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonObject {

    private static SingletonObject instance;
    
    private SingletonObject(){}
    
    public static SingletonObject getInstance(){
        if (instance == null) {
            instance = new SingletonObject();
        }
        return instance;
    }
    
    public static void main(String[] args) throws InterruptedException {
        int threadCounts = 100;
        int testCounts = 10000;
        for (int i = 0; i < testCounts; i++) {
            test(threadCounts);
        }
    }
    
    public static void test(int threadCounts) throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch startFlag = new CountDownLatch(1);
        final CountDownLatch counter = new CountDownLatch(threadCounts);
        final Set<String> instanceSet = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < threadCounts; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        startFlag.await();
                    } catch (InterruptedException e) {}
                    instanceSet.add(SingletonObject.getInstance().toString());
                    counter.countDown();
                }
            });
        }
        startFlag.countDown();
        counter.await();
　　　　 SingletonObject.instance = null;
        if (instanceSet.size() > 1) {
            System.out.print("{");
            for (String instance : instanceSet) {
                System.out.print("[" + instance + "]");
            }
            System.out.println("}");
        }
        executorService.shutdown();
    }
    
}
```
以上的测试共执行1万次，这是为了加大出错几率。基本上，你总能看到以下这样的输出。
```Java
{[SingletonObject@16930e2][SingletonObject@7259da]}
```
　　这说明在一次测试中，生成了两个SingletonObject对象（可能会有更多，LZ运行了一小会就见到一次14个的）。可以看出，并不是这10000次测试都会出错，相对来说，出错的概率还是非常小的。这正是竞态条件的发生形式，在一定的指令执行序列下，程序就会出错，比如单例模式实际上变成了非单例的情况。

**1.2、复合操作**

　　顾名思义，复合操作就是非原子性的操作，两者具有互斥性，也就是说，一个操作要么属于原子操作，要么属于复合操作。上面的if块就是一个典型的复合操作，根据某一个变量的值，决定下一步的行为。通常情况下，使用同步关键字（synchronized）可以使得复合操作变成原子操作，但我们往往更推荐使用现有的类库去实现原子性。

　　比如一个并发的计数器，就可以写成如下形式。
```Java
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter {

    private final AtomicInteger count = new AtomicInteger(0);
    
    public int getCount(){
        return count.get();
    }
    
    public int increment(){
        return count.incrementAndGet();
    }
    
    public int decrement(){
        return count.decrementAndGet();
    }
    
}
```
　这里我们使用现有的线程安全类来实现一个并发计数器，这省去了我们很多工作，比如自增并返回、递减并返回这些复合操作（实际上AtomicInteger提供了很多常用的复合操作，并保证原子性）。这样做的好处是，不容易出错，性能可能更高（比如ConcurrentHashMap），分析起来更简单。实际上，我们包装了一个线程安全的类，使之成为了另外一个线程安全的类。

**2、可见性**

　　可见性这玩意实在是太奇葩了，以至于亮瞎了LZ的一双氪金人眼。为了把可见性写的更神秘一点，LZ先给出一个简单的例子。
```Java
public class Integer {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
```
　这个类是Java类库中Integer类的伪劣产品，各位猿友想象一下，它是一个线程安全的类吗？（JDK中的Integer是不可变对象，因此是线程安全的）

　　乍一看好像是的，因为这个类太简单了，而且没有竞态条件（当前的行为不受之前状态的影响）。但是很抱歉，这个类依然不是线程安全的。原因就是因为它的可见性不能保证，因此在多线程环境下，如果一个线程设置了value的值为100，那么另外一个线程或许会看不到100这个值。

　　为何会这样呢？

　　我们依然回想一下计算机原理当中的内容，在计算机原理当中我们曾经无数次的接触过寄存器与存储器，在汇编级别的代码当中，我们会发现，很多变量的赋值是不会反应到存储器当中的，它们有时候一直存在于寄存器当中。这样一来，可见性就好解释了，有时候一个线程A去读取一个变量，这时候它会瞄准存储器的某一个位置进行读取操作，它或许会期待另外一个线程B去改变存储器的值，但事实往往是，另外一个线程B只是把值隐藏在了寄存器，而导致线程A永远看不到这个更新后的值。

　　还有另外一种情况是，编译器会将现有的程序进行乱序重组，或许表面看起来，我们是先给一个变量赋值，然后又在另外一个线程去读取它，但事实可能是我们先去读取了这个变量，然后才进行的赋值。

　　不管是哪种情况，一旦牵扯到可见性，就说明程序的行为是不可预见的。换句话说，我们的程序如果想要正确的运行，和中彩票是一个概念，需要一定的概率才能发生，这当然是我们不能容忍的。

　　因此，我们必须保证一个对象的可见性，否则在共享一个对象时，就会非常的危险。对于上面这个简单的整数类，我们只要给get/set方法加上synchronized关键字，就可以保证它的可见性。这是由于synchronized关键字不仅保证了同步机制，更重要的是禁用了乱序重组以及保证了值对存储器的写入，这样就可以保证可见性。

> 加锁机制（如何加锁）

　上面主要回答了各位我们应该在**何时加锁**，看似很复杂，但其实更难的还是在**如何加锁**的问题上。因为如果不考虑简单性或者性能等一些问题，给一个类的全部方法加上synchronized关键字就可以确保这个类的线程安全性。但是很显然，这种做法很多时候是不可取的，除非你想收到上级的“夸奖”。

　　如果一个多线程环境下的类无法做成无状态或者是不可变对象，那么我们就只能尝试去做一些同步机制，来保证它的线程安全性，或者说保证它可以正常工作。这个问题很难一概而论，不过在绝大多数情况下，我们秉持这样一个原则去进行同步，那就是**总是用同一个锁去保护需要协变的状态。**

　　这一句话显然无法概括所有加锁的情况，但是却是LZ个人感觉能解决大部分问题的方法。接下来LZ就举一个简单的例子，比如上面的区间类，它当中就有一些明显的协变状态（协变状态是LZ个人起的名字，意思是想指那些需要相互协助变化的状态）。我们接下来就尝试将上面的区间类变成线程安全的类。
```Java
public class Region {
    
    private int left;
    
    private int right;
    
    public Region() {
        super();
    }

    public Region(int left, int right) {
        super();
        if (left <= right) {
            this.left = left;
            this.right = right;
        }else {
            this.left = left;
            this.right = right;
        }
    }

    public synchronized void setLeft(int left) {
        if (left > right) {
            this.left = right;
        }else {
            this.left = left;
        }
    }

    public synchronized void setRight(int right) {
        if (right < left) {
            this.right = left;
        }else {
            this.right = right;
        }
    }
    
    public synchronized boolean in(int value){
        return value >= left && value <= right;
    }
    
    public String toString(){
        return "[" + left + "," + right + "]";
    }

}
```
　方法非常简单，我们只是简单的给三个方法加上了synchronized关键字，但不可否认的是，它现在已经是一个线程安全的类（我们对toString的显示要求不高，因此不进行同步）。这个类当中很显然left和right变量是一组协变状态，它们两个之间需要相互协助的变化，而不可以单独进行改变。

　　其实在现实当中，这样的协变状态有很多。比如我们常用的ArrayList，它当中就有一个Object数组和一个size标识，这两个状态很明显是需要协变的，一旦object数组有所变化，size就要跟随着变化，这样的话在多线程当中使用时，就需要将二者使用同一个锁进行同步（一般情况下，我们会使用当前对象充当这个锁，即this关键字）。

　　如果一个方法当中，并不全是协变状态，我们就可以进行局部同步（使用synchronized同步块），这样就可以减少性能的损失，但也要保证一定的简单性，否则的话，这段程序维护起来会非常头疼。

　　接下来，我们看一个简单的例子，我们给区间类加一些输出语句，来显示同步块的使用。
```Java
public class Region {
    
    private int left;
    
    private int right;
    
    public Region() {
        super();
    }

    public Region(int left, int right) {
        super();
        if (left <= right) {
            this.left = left;
            this.right = right;
        }else {
            this.left = left;
            this.right = right;
        }
    }

    public void setLeft(int left) {
        System.out.println("before setLeft:" + toString());
        synchronized (this) {
            if (left > right) {
                this.left = right;
            }else {
                this.left = left;
            }
        }
        System.out.println("after setLeft:" + toString());
    }

    public void setRight(int right) {
        System.out.println("before setRight:" + toString());
        synchronized (this) {
            if (right < left) {
                this.right = left;
            }else {
                this.right = right;
            }
        }
        System.out.println("after setRight:" + toString());
    }
    
    public synchronized boolean in(int value){
        return value >= left && value <= right;
    }
    
    public String toString(){
        return "[" + left + "," + right + "]";
    }

}
```
　这里我们为了尽可能的保证程序的性能，所以使用了同步块，在进行输出语句的调用时，并不会将当前对象锁定。众所周知，JAVA在I/O方面的处理是比较慢的，因此在同步的语句当中，我们应当尽量的将I/O语句移出同步块（当然还包括其它的一些处理较慢的语句）。

　　这里LZ再举一个非常常见的例子，就是对于循环一个列表的处理，以下这段代码节选自JDK1.6当中Observable类（观察者模式当中的被观察者父类）。
```Java
public void notifyObservers(Object arg) {
    /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

    synchronized (this) {
        /* We don't want the Observer doing callbacks into
         * arbitrary code while holding its own Monitor.
         * The code where we extract each Observable from 
         * the Vector and store the state of the Observer
         * needs synchronization, but notifying observers
         * does not (should not).  The worst result of any 
         * potential race-condition here is that:
         * 1) a newly-added Observer will miss a
         *   notification in progress
         * 2) a recently unregistered Observer will be
         *   wrongly notified when it doesn't care
         */
        if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }
```
可以看到，这个方法的任务是通知所有的观察者，也就是说，需要循环obs这个list列表，并挨个调用update方法。但是这里并没有直接循环obs这个列表，而是使用了一个临时变量arrLocal，并获取到obs的一个快照（snapshot）进行循环。这就是为了保证同步的情况下，尽量的提高性能，因为update方法当中可能会有一些很占用时间的操作，这样的话，如果我们直接对obs循环期间进行同步，那么就可能会导致被观察者被锁定相当长的一段时间。

总结:		

&emsp;&emsp;并发算是编程当中的一个高级课题，所以难度可能会较高。但话说回来，只要你在做Java Web，就一定离不开并发。所以看似高级课题的并发，其实一直都与你日夜相伴。从某种意义上来讲，真正要入门web的前提，就是搞清楚并发的相关内容，因为在运维的过程中，往往代码中出现的bug都是非常简单的，而难的地方，就是一些并发所带来的偶然性问题，这就需要你对并发有一定深入的了解才能发现问题的所在。

> 并发编程的简单分类

并发常见的编程场景，一句话概括就是，需要协调多个线程之间的协作，已保证程序按照自己原本的意愿执行。那么究竟应该如何协调多个线程？

这个问题比较宽泛，一般情况下，我们按照方式的纬度去简单区分，有以下两种方式：

<font size=3 face="黑体" color=red>
1，第一种是利用JVM的内部机制。

2，第二种是利用JVM外部的机制，比如JDK或者一些类库。</font>

第一种方式一般是通过synchronized关键字等方式去实现，第二种则一般是使用JDK当中的类去手动实现。两种方式十分相似，他们的区别有点类似于C/C++和Java的垃圾搜集方式的区别，C/C++手动释放内存的方式更加灵活和高效，而Java自动垃圾搜集的方式则更加安全和方便。

并发一直被认为是编程当中的高级特性，也是很多大公司在面试的时候都比较在意的部分，因此掌握好并发的简单技巧，还是能够让自己的技术沉淀有质的飞跃的。

> 详解JVM内部机制——同步篇

JVM有很多内部同步机制，这在有的时候是非常值得我们去使用和学习的，接下来咱们就一起看看，JVM到底提供了哪些内部的同步方式。

**1、 static的强制同步机制**

static这个关键字相信大家都不陌生，不过它附带的同步机制估计是很多猿友都不知道的。例如下面这个简单的类。
```Java
public class Static {
 
     private static String someField1 = someMethod1();
     
     private static String someField2;
     
     static {
         someField2 = someMethod2();
     }
     
}
```
首先上面这一段代码在编译以后会变成下面这个样子，这点各位可以使用反编译工具去验证。
```Java
public class Static {

    private static String someField1;
    
    private static String someField2;
    
    static {
        someField1 = someMethod1();
        someField2 = someMethod2();
    }
    
}
```
不过在JVM真正执行这段代码的时候，其实它又变成了下面这个样子。
```Java
public class Static {

    private static String someField1;

    private static String someField2;

    private static volatile boolean isCinitMethodInvoked = false;

    static {
        synchronized (Static.class) {
            if (!isCinitMethodInvoked) {
                someField1 = someMethod1();
                someField2 = someMethod2();
                isCinitMethodInvoked = true;
            }
        }
    }

}
```
也就是说在实际执行一个类的静态初始化代码块时，虚拟机内部其实对其进行了同步，这就保证了无论多少个线程同时加载一个类，静态块中的代码执行且只执行一次。这点在单例模式当中得到了有效的应用，各位猿友有兴趣的可以去翻看LZ之前的单例模式博文。

**2、synchronized的同步机制**

synchronized是JVM提供的同步机制，它可以修饰方法或者代码块。此外，在修饰代码块的时候，synchronized可以指定锁定的对象，比如常用的有this，类字面常量等。在使用synchronized的时候，通常情况下，我们会针对特定的属性进行锁定，有时也会专门建立一个加锁对象。

直接给方法加synchronized关键字，或者使用this，类字面常量作为锁的方式比较常用，也比较简单，这里就不再举例了。我们来看看对某一属性进行锁定的方式，如下。
```Java
public class Synchronized {

    private List<String> someFields;
    
    public void add(String someText) {
        //some code
        synchronized (someFields) {
            someFields.add(someText);
        }
        //some code
    }
    
    public Object[] getSomeFields() {
        //some code
        synchronized (someFields) {
            return someFields.toArray();
        }
    }
    
}
```
　这种方式一般要优于使用this或者类字面常量进行锁定的方式，因为synchronized修饰的非静态成员方法默认是使用的this进行锁定，而synchronized修饰的静态成员方法默认是使用的类字面常量进行的锁定，因此如果直接在synchronized代码块中使用this或者类字面常量，可能会不经意的与synchronized方法产生互斥。通常情况下，使用属性进行加锁，能够更加有效的提高并发度，从而在保证程序正确的前提下尽可能的提高性能。

　　再来看一段比较特殊的代码，如果猿友们经常看JDK源码或者一些优秀的开源框架源码的话，或许会见过这种方式。

```Java
public class Synchronized {
    
    private Object lock = new Object();

    private List<String> someFields1;
    private List<String> someFields2;
    
    public void add(String someText) {
        //some code
        synchronized (lock) {
            someFields1.add(someText);
            someFields2.add(someText);
        }
        //some code
    }
    
    public Object[] getSomeFields() {
        //some code
        Object[] objects1 = null;
        Object[] objects2 = null;
        synchronized (lock) {
            objects1 = someFields1.toArray();
            objects2 = someFields2.toArray();
        }
        Object[] objects = new Object[someFields1.size() + someFields2.size()];
        System.arraycopy(objects1, 0, objects, 0, objects1.length);
        System.arraycopy(objects2, 0, objects, objects1.length, objects2.length);
        return objects;
    }
    
}
```
　lock是一个专门用于监控的对象，它没有任何实际意义，只是为了与synchronized配合，完成对两个属性的统一锁定。当然，一般情况下，也可以使用this代替lock，这其实没有什么死的规定，完全可以按照实际情况而定。还有一种比较不推荐的方式，就是下面这种。
```Java
public class Synchronized {
    
    private List<String> someFields1;
    private List<String> someFields2;
    
    public void add(String someText) {
        //some code
        synchronized (someFields1) {
            synchronized (someFields2) {
                someFields1.add(someText);
                someFields2.add(someText);
            }
        }
        //some code
    }
    
    public Object[] getSomeFields() {
        //some code
        Object[] objects1 = null;
        Object[] objects2 = null;
        synchronized (someFields1) {
            synchronized (someFields2) {
                objects1 = someFields1.toArray();
                objects2 = someFields2.toArray();
            }
        }
        Object[] objects = new Object[someFields1.size() + someFields2.size()];
        System.arraycopy(objects1, 0, objects, 0, objects1.length);
        System.arraycopy(objects2, 0, objects, objects1.length, objects2.length);
        return objects;
    }
    
}
```
　这种加锁方式比较挑战人的细心程度，万一哪个不小心把顺序搞错了，就可能造成死锁。因此如果你非要使用这种方式，请做好被你的上司行刑的准备。

> 详解JVM外部机制——同步篇

与JVM内部的同步机制对应的，就是外部的同步机制，也可以叫做编程式的同步机制。接下来，咱们就看看一些常用的外部同步方法。

　　**ReentrantLock（可重入的锁）**

　　ReentrantLock是JDK并发包中locks当中的一个类，专门用于弥补synchronized关键字的一些不足。接下来咱们就看一下synchronized关键字都有哪些不足，接着咱们再尝试使用ReentrantLock去解决这些问题。

　　**1）synchronized关键字同步的时候，等待的线程将无法控制，只能死等。**

　　解决方式：ReentrantLock可以使用tryLock(timeout, unit)方法去控制等待获得锁的时间，也可以使用无参数的tryLock方法立即返回，这就避免了死锁出现的可能性。

　　**2）synchronized关键字同步的时候，不保证公平性，因此会有线程插队的现象。**

　　解决方式：ReentrantLock可以使用构造方法ReentrantLock(fair)来强制使用公平模式，这样就可以保证线程获得锁的顺序是按照等待的顺序进行的，而synchronized进行同步的时候，是默认非公平模式的，但JVM可以很好的保证线程不被饿死。

　　ReentrantLock有这样一些优点，当然也有不足的地方。最主要不足的一点，就是ReentrantLock需要开发人员手动释放锁，并且必须在finally块中释放。

　　下面给出两个简单的ReentrantLock例子，请各位猿友收看。
```Java
public class Lock {

    private ReentrantLock nonfairLock = new ReentrantLock();

    private ReentrantLock fairLock = new ReentrantLock(true);

    private List<String> someFields;

    public void add(String someText) {
        // 等待获得锁，与synchronized类似
        nonfairLock.lock();
        try {
            someFields.add(someText);
        } finally {
            // finally中释放锁是无论如何都不能忘的
            nonfairLock.unlock();
        }
    }

    public void addTimeout(String someText) {
        // 尝试获取，如果10秒没有获取到则立即返回
        try {
            if (!fairLock.tryLock(10, TimeUnit.SECONDS)) {
                return;
            }
        } catch (InterruptedException e) {
            return;
        }
        try {
            someFields.add(someText);
        } finally {
            // finally中释放锁是无论如何都不能忘的
            fairLock.unlock();
        }
    }

}
```
　以上主要展示了ReentrantLock的基本用法和限时的等待，接下来咱们来看看当需要锁定多个对象的时候，ReentrantLock是如何使用的。从以下代码可以看出，用法与上面的synchronized中的方式非常相似。
```Java
package concurrent;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zuoxiaolong
 *
 */
public class ReentrantLockTest {
    
    private ReentrantLock lock = new ReentrantLock();

    private List<String> someFields1;
    private List<String> someFields2;
    
    public void add(String someText) {
        //some code
        lock.lock();
        try {
            someFields1.add(someText);
            someFields2.add(someText);
        } finally {
            lock.unlock();
        }
        //some code
    }
    
    public Object[] getSomeFields() {
        //some code
        Object[] objects1 = null;
        Object[] objects2 = null;
        lock.lock();
        try {
            objects1 = someFields1.toArray();
            objects2 = someFields2.toArray();
        } finally {
            lock.unlock();
        }
        Object[] objects = new Object[someFields1.size() + someFields2.size()];
        System.arraycopy(objects1, 0, objects, 0, objects1.length);
        System.arraycopy(objects2, 0, objects, objects1.length, objects2.length);
        return objects;
    }
    
}
```

> 详解JVM内部机制——条件等待篇

刚才已经讨论过JVM内部同步的机制，接下来咱们一起看一下JVM内部的条件等待机制。Java当中的类有一个共同的父类Object，而在Object中，有一个wait的本地方法，这是一个神奇的方法。

　　它可以用来协调线程之间的协作，使用方式也比较简单，看一下下面这个例子，你就基本入门了哦。

```Java
public class ObjectWait {

    private volatile static boolean lock;

    public static void main(String[] args) throws InterruptedException {
        final Object object = new Object();

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("等待被通知！");
                try {
                    synchronized (object) {
                        while (!lock) {
                            object.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("已被通知");
            }
        });
        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("马上开始通知！");
                synchronized (object) {
                    object.notify();
                    lock = true;
                }
                System.out.println("已通知");
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(100000);
    }
}
```
这是一个最基本的例子，我们使用一个线程在object对象上等待另外一个线程的通知，当另外一个线程通知了以后，等待的线程将会继续运行。其实初次接触这个东西，是不是感觉很有意思呢。

　　wait一般情况下最常用的场景是构造一个花销非常大的对象的时候，比如JDK动态代理在生成代理类的时候就使用了这种方式。JDK6在生成一个代理类之前，会先检测一个是否正在生成中的标识，如果正在生成的话，JDK6就会在对象上等待，直到正在生成的代理类生成完毕，然后直接从缓存中获取。

　　这里需要提醒大家的一点是，wait,notify和notifyAll方法在使用前，必须获取到当前对象的锁，否则会告诉你非法的监控状态异常。还有一点，则是如果有多个线程在wait等待，那么调用notify会随机通知其中一个线程，而不会按照顺序通知。换句话说，notify的通知机制是非公平的，notify并不保证先调用wait方法的线程优先被唤醒。notifyAll方法则不存在这个问题，它将通知所有处于wait等待的线程。

> 详解JVM外部机制——条件等待篇

上面咱们已经看过JVM自带的条件控制机制，是使用的本地方法wait实现的。那么在JDK的类库中，也有这样的一个类Condition，来弥补wait方法本身的不足。与之前一样，说到这里，咱们就来谈谈wait到底有哪些不足。

　　**1）wait方法当使用带参数的方法wait(timeout)或者wait(timeout,nanos)时，无法反馈究竟是被唤醒还是到达了等待时间，大部分时候，我们会使用循环（就像上面的例子一样）来检测是否达到了条件。**

　　解决方式：Condition可以使用返回值标识是否达到了超时时间。

　　**2）由于wait,notify,notifyAll方法都需要获得当前对象的锁，因此当出现多个条件等待时，则需要依次获得多个对象的锁，这是非常恶心麻烦且繁琐的事情。**

　　解决方式：Condition之需要获得Lock的锁即可，一个Lock可以拥有多个条件。

　　第一个问题比较好理解，只是Condition的await方法多了一个返回参数boolean去标识究竟是被唤醒还是超时。但是第二个问题比较繁琐一些，因此这里给出一个简单的示例，如下。
```Java
package concurrent;

/**
 * @author zuoxiaolong
 *
 */
public class ObjectWait {

    public static void main(String[] args) throws InterruptedException {
        final Object object1 = new Object();
        final Object object2 = new Object();
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("等待object1被通知！");
                    synchronized (object1) {
                        object1.wait();
                    }
                    System.out.println("object1已被通知，马上开始通知object2！");
                    synchronized (object2) {
                        object2.notify();
                    }
                    System.out.println("通知object2完毕！");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("马上开始通知object1！");
                    synchronized (object1) {
                        object1.notify();
                    }
                    System.out.println("通知object1完毕，等待object2被通知！");
                    synchronized (object2) {
                        object2.wait();
                    }
                    System.out.println("object2已被通知！");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }

}
```
这是一个多条件的示例。基本逻辑是，线程1先等待线程2通知，然后线程2再等待线程1通知。请记住，这是两个不同的条件。可以看到，如果使用wait的话，必须两次获得两个锁，一不小心可能还会出现死锁。接下来，咱们看看Condition实现一样的功能是怎么实现的。

```Java
package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zuoxiaolong
 *
 */
public class ConditionTest {

    private static ReentrantLock lock = new ReentrantLock();
    
    public static void main(String[] args) throws InterruptedException {
        final Condition condition1 = lock.newCondition();
        final Condition condition2 = lock.newCondition();
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    System.out.println("等待condition1被通知！");
                    condition1.await();
                    System.out.println("condition1已被通知，马上开始通知condition2！");
                    condition2.signal();
                    System.out.println("通知condition2完毕！");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    System.out.println("马上开始通知condition1！");
                    condition1.signal();
                    System.out.println("通知condition1完毕，等待condition2被通知！");
                    condition2.await();
                    System.out.println("condition2已被通知！");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }

}
```

可以看到，我们只需要获取lock一次就可以了，在内部咱们可以使用两个或多个条件而不再需要多次获得锁。这种方式会更加直观，大大增加程序的可读性。

> 详解JVM外部机制——线程协作篇

JDK当中除了以上的ReentrantLock和Condition之外，还有很多帮助猿友们协调线程的工具类。接下来咱们就一一混个脸熟。

**1、CountDownLatch**

　　这个类是为了帮助猿友们方便的实现一个这样的场景，就是某一个线程需要等待其它若干个线程完成某件事以后才能继续进行。比如下面的这个程序。

```Java
package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author zuoxiaolong
 *
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int number = i + 1;
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    System.out.println("执行任务[" + number + "]");
                    countDownLatch.countDown();
                    System.out.println("完成任务[" + number + "]");
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        System.out.println("主线程开始等待...");
        countDownLatch.await();
        System.out.println("主线程执行完毕...");
    }
    
}
```

这个程序的主线程会等待CountDownLatch进行10次countDown方法的调用才会继续执行。我们可以从打印的结果上看出来，尽管有的时候完成任务的打印会出现在主线程执行完毕之后，但这只是因为countDown已经执行完毕，主线程的打印语句先一步执行而已。

**2、CyclicBarrier**

这个类是为了帮助猿友们方便的实现多个线程一起启动的场景，就像赛跑一样，只要大家都准备好了，那就开始一起冲。比如下面这个程序，所有的线程都准备好了，才会一起开始执行。
```Java
package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zuoxiaolong
 *
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            final int number = i + 1;
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    System.out.println("等待执行任务[" + number + "]");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                    } catch (BrokenBarrierException e) {
                    }
                    System.out.println("开始执行任务[" + number + "]");
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
    
}
```
**3，Semaphore**

　　这个类是为了帮助猿友们方便的实现控制数量的场景，可以是线程数量或者任务数量等等。来看看下面这段简单的代码。
```Java
package concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zuoxiaolong
 *
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(10);
        final AtomicInteger number = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    try {
                        semaphore.acquire();
                        number.incrementAndGet();
                    } catch (InterruptedException e) {}
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Thread.sleep(10000);
        System.out.println("共" + number.get() + "个线程获得到信号");
        System.exit(0);
    }
    
}
```
从结果上可以看出，LZ设定了总数为10，却开了100个线程，但是最终只有10个线程获取到了信号量，如果这10个线程不主动调用release方法的话，那么其余90个线程将一起挂死。

**4、Exchanger**

这个类是为了帮助猿友们方便的实现两个线程交换数据的场景，使用起来非常简单，看看下面这段代码。

```Java
package concurrent;

import java.util.concurrent.Exchanger;

/**
 * @author zuoxiaolong
 *
 */
public class ExchangerTest {

    public static void main(String[] args) throws InterruptedException {
        final Exchanger<String> exchanger = new Exchanger<String>();
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("线程1等待接受");
                    String content = exchanger.exchange("thread1");
                    System.out.println("线程1收到的为：" + content);
                } catch (InterruptedException e) {}
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("线程2等待接受并沉睡3秒");
                    Thread.sleep(3000);
                    String content = exchanger.exchange("thread2");
                    System.out.println("线程2收到的为：" + content);
                } catch (InterruptedException e) {}
            }
        });
        thread1.start();
        thread2.start();
    }
    
}
```

两个线程在只有一个线程调用exchange方法的时候调用方会被挂起，当都调用完毕时，双方会交换数据。在任何一方没调用exchange之前，线程都会处于挂起状态。

++++++++++++++++++++++++++++++++++++++++++++++++
Java并发编程有多难？这几个核心技术你掌握了吗？

1、Java线程
2、线程模型
3、Java线程池
4、Future(各种Future)
5、Fork/Join框架
6、volatile
7、CAS（原子操作）
8、AQS（并发同步框架）
9、synchronized（同步锁）
10、并发队列（阻塞队列）


一、学习多线程/线程池的最佳途径：掌握这50道面试题，面试就稳了（10道）
1) 什么是线程？

线程是操作系统能够进行运算调度的最小单位，它被包含在进程之中，是进程中的实际运作单位。程序员可以通过它进行多处理器编程，你可以使用多线程对运算密集型任务提速。比如，如果一个线程完成一个任务要100毫秒，那么用十个线程完成改任务只需10毫秒。Java在语言层面对多线程提供了卓越的支持，它也是一个很好的卖点。

2) 线程和进程有什么区别？

线程是进程的子集，一个进程可以有很多线程，每条线程并行执行不同的任务。不同的进程使用不同的内存空间，而所有的线程共享一片相同的内存空间。别把它和栈内存搞混，每个线程都拥有单独的栈内存用来存储本地数据。

3) 如何在Java中实现线程？

在语言层面有两种方式。java.lang.Thread 类的实例就是一个线程但是它需要调用java.lang.Runnable接口来执行，由于线程类本身就是调用的Runnable接口所以你可以继承java.lang.Thread 类或者直接调用Runnable接口来重写run()方法实现线程。

4) 用Runnable还是Thread？

这个问题是上题的后续，大家都知道我们可以通过继承Thread类或者调用Runnable接口来实现线程，问题是，那个方法更好呢？什么情况下使用它？这个问题很容易回答，如果你知道Java不支持类的多重继承，但允许你调用多个接口。所以如果你要继承其他类，当然是调用Runnable接口好了。

6) Thread 类中的start() 和 run() 方法有什么区别？

这个问题经常被问到，但还是能从此区分出面试者对Java线程模型的理解程度。start()方法被用来启动新创建的线程，而且start()内部调用了run()方法，这和直接调用run()方法的效果不一样。当你调用run()方法的时候，只会是在原来的线程中调用，没有新的线程启动，start()方法才会启动新线程。

7) Java中Runnable和Callable有什么不同？

Runnable和Callable都代表那些要在不同的线程中执行的任务。Runnable从JDK1.0开始就有了，Callable是在JDK1.5增加的。它们的主要区别是Callable的 call() 方法可以返回值和抛出异常，而Runnable的run()方法没有这些功能。Callable可以返回装载有计算结果的Future对象。

8) Java中CyclicBarrier 和 CountDownLatch有什么不同？

CyclicBarrier 和 CountDownLatch 都可以用来让一组线程等待其它线程。与 CyclicBarrier 不同的是，CountdownLatch 不能重新使用。

9) Java内存模型是什么？

Java内存模型规定和指引Java程序在不同的内存架构、CPU和操作系统间有确定性地行为。它在多线程的情况下尤其重要。Java内存模型对一个线程所做的变动能被其它线程可见提供了保证，它们之间是先行发生关系。这个关系定义了一些规则让程序员在并发编程时思路更清晰。比如，先行发生关系确保了：

线程内的代码能够按先后顺序执行，这被称为程序次序规则。
对于同一个锁，一个解锁操作一定要发生在时间上后发生的另一个锁定操作之前，也叫做管程锁定规则。
前一个对volatile的写操作在后一个volatile的读操作之前，也叫volatile变量规则。

一个线程内的任何操作必需在这个线程的start()调用之后，也叫作线程启动规则。
一个线程的所有操作都会在线程终止之前，线程终止规则。
一个对象的终结操作必需在这个对象构造完成之后，也叫对象终结规则。
可传递性
我强烈建议大家阅读《Java并发编程实践》第十六章来加深对Java内存模型的理解。

10) Java中的volatile 变量是什么？

volatile是一个特殊的修饰符，只有成员变量才能使用它。在Java并发程序缺少同步类的情况下，多线程对成员变量的操作对其它线程是透明的。volatile变量可以保证下一个读取操作会在前一个写操作之后发生，就是上一题的volatile变量规则。

11) 什么是线程安全？Vector是一个线程安全类吗？

如果你的代码所在的进程中有多个线程在同时运行，而这些线程可能会同时运行这段代码。如果每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。一个线程安全的计数器类的同一个实例对象在被多个线程使用的情况下也不会出现计算失误。很显然你可以将集合类分成两组，线程安全和非线程安全的。Vector 是用同步方法来实现线程安全的, 而和它相似的ArrayList不是线程安全的。

本篇从最简单的定义开始：什么是线程？线程和进程有什么区别？怎么创建？？属于新手级别

二、这50道面试官最喜欢问的关于多线程/线程池的面试题，你都会了吗（10道）

稍微深入，如什么是ThreadLocal变量？，属于新手进阶

12) Java中如何停止一个线程？

Java提供了很丰富的API但没有为停止线程提供API。JDK 1.0本来有一些像stop(), suspend() 和 resume()的控制方法但是由于潜在的死锁威胁因此在后续的JDK版本中他们被弃用了，之后Java API的设计者就没有提供一个兼容且线程安全的方法来停止一个线程。当run() 或者 call() 方法执行完的时候线程会自动结束,如果要手动结束一个线程，你可以用volatile 布尔变量来退出run()方法的循环或者是取消任务来中断线程。

13） 如何在两个线程间共享数据？

你可以通过共享对象来实现这个目的，或者是使用像阻塞队列这样并发的数据结构。这篇教程《Java线程间通信》(涉及到在两个线程间共享对象)用wait和notify方法实现了生产者消费者模型。

14) Java中notify 和 notifyAll有什么区别？

这又是一个刁钻的问题，因为多线程可以等待单监控锁，Java API 的设计人员提供了一些方法当等待条件改变的时候通知它们，但是这些方法没有完全实现。notify()方法不能唤醒某个具体的线程，所以只有一个线程在等待的时候它才有用武之地。而notifyAll()唤醒所有线程并允许他们争夺锁确保了至少有一个线程能继续运行。

15) 什么是FutureTask？

在Java并发程序中FutureTask表示一个可以取消的异步运算。它有启动和取消运算、查询运算是否完成和取回运算结果等方法。只有当运算完成的时候结果才能取回，如果运算尚未完成get方法将会阻塞。一个FutureTask对象可以对调用了Callable和Runnable的对象进行包装，由于FutureTask也是调用了Runnable接口所以它可以提交给Executor来执行。

16) 一个线程运行时发生异常会怎样？

这是我在一次面试中遇到的一个很刁钻的Java面试题, 简单的说，如果异常没有被捕获该线程将会停止执行。Thread.UncaughtExceptionHandler是用于处理未捕获异常造成线程突然中断情况的一个内嵌接口。当一个未捕获异常将造成线程中断的时候JVM会使用Thread.getUncaughtExceptionHandler()来查询线程的UncaughtExceptionHandler并将线程和异常作为参数传递给handler的uncaughtException()方法进行处理。

17) 为什么wait和notify方法要在同步块中调用？

主要是因为Java API强制要求这样做，如果你不这么做，你的代码会抛出IllegalMonitorStateException异常。还有一个原因是为了避免wait和notify之间产生竞态条件。

18) 什么是ThreadLocal变量？

ThreadLocal是Java里一种特殊的变量。每个线程都有一个ThreadLocal就是每个线程都拥有了自己独立的一个变量，竞争条件被彻底消除了。它是为创建代价高昂的对象获取线程安全的好方法，比如你可以用ThreadLocal让SimpleDateFormat变成线程安全的，因为那个类创建代价高昂且每次调用都需要创建不同的实例所以不值得在局部范围使用它，如果为每个线程提供一个自己独有的变量拷贝，将大大提高效率。首先，通过复用减少了代价高昂的对象的创建个数。其次，你在没有使用高代价的同步或者不变性的情况下获得了线程安全。线程局部变量的另一个不错的例子是ThreadLocalRandom类，它在多线程环境中减少了创建代价高昂的Random对象的个数。

19) Java中什么是竞态条件？ 举个例子说明。

竞态条件会导致程序在并发情况下出现一些bugs。多线程对一些资源的竞争的时候就会产生竞态条件，如果首先要执行的程序竞争失败排到后面执行了，那么整个程序就会出现一些不确定的bugs。这种bugs很难发现而且会重复出现，因为线程间的随机竞争。一个例子就是无序处理。

20) Java中interrupted 和 isInterruptedd方法的区别？

interrupted() 和 isInterrupted()的主要区别是前者会将中断状态清除而后者不会。Java多线程的中断机制是用内部标识来实现的，调用Thread.interrupt()来中断一个线程就会设置中断标识为true。当中断线程调用静态方法Thread.interrupted()来检查中断状态时，中断状态会被清零。而非静态方法isInterrupted()用来查询其它线程的中断状态且不会改变中断状态标识。简单的说就是任何抛出InterruptedException异常的方法都会将中断状态清零。无论如何，一个线程的中断状态有有可能被其它线程调用中断来改变。

21) 为什么wait, notify 和 notifyAll这些方法不在thread类里面？

这是个设计相关的问题，它考察的是面试者对现有系统和一些普遍存在但看起来不合理的事物的看法。回答这些问题的时候，你要说明为什么把这些方法放在Object类里是有意义的，还有不把它放在Thread类里的原因。一个很明显的原因是JAVA提供的锁是对象级的而不是线程级的，每个对象都有锁，通过线程获得。如果线程需要等待某些锁那么调用对象中的wait()方法就有意义了。如果wait()方法定义在Thread类中，线程正在等待的是哪个锁就不明显了。简单的说，由于wait，notify和notifyAll都是锁级别的操作，所以把他们定义在Object类中因为锁属于对象。

三、怎样快速高效学习多线程/线程池？跟着我做完这50道面试题就够了（15道）

中级难度，如什么是线程池？如何避免死锁？你如何在Java中获取线程堆栈？等问题。

22) Thread类中的yield方法有什么作用？

Yield方法可以暂停当前正在执行的线程对象，让其它有相同优先级的线程执行。它是一个静态方法而且只保证当前线程放弃CPU占用而不能保证使其它线程一定能占用CPU，执行yield()的线程有可能在进入到暂停状态后马上又被执行。

23） Java中堆和栈有什么不同？

为什么把这个问题归类在多线程和并发面试题里？因为栈是一块和线程紧密相关的内存区域。每个线程都有自己的栈内存，用于存储本地变量，方法参数和栈调用，一个线程中存储的变量对其它线程是不可见的。而堆是所有线程共享的一片公用内存区域。对象都在堆里创建，为了提升效率线程会从堆中弄一个缓存到自己的栈，如果多个线程使用该变量就可能引发问题，这时volatile 变量就可以发挥作用了，它要求线程从主存中读取变量的值。

24) JVM中哪个参数是用来控制线程的栈堆栈小的

这个问题很简单， -Xss参数用来控制线程的堆栈大小。

25） 什么是线程池？ 为什么要使用它？

创建线程要花费昂贵的资源和时间，如果任务来了才创建线程那么响应时间会变长，而且一个进程能创建的线程数有限。为了避免这些问题，在程序启动的时候就创建若干线程来响应处理，它们被称为线程池，里面的线程叫工作线程。从JDK1.5开始，Java API提供了Executor框架让你可以创建不同的线程池。比如单线程池，每次处理一个任务；数目固定的线程池或者是缓存线程池（一个适合很多生存期短的任务的程序的可扩展线程池）。需要详细了解的先收藏本文后看这篇：这么说吧，java线程池的实现原理其实很简单

26） Java中ConcurrentHashMap的并发度是什么？

ConcurrentHashMap把实际map划分成若干部分来实现它的可扩展性和线程安全。这种划分是使用并发度获得的，它是ConcurrentHashMap类构造函数的一个可选参数，默认值为16，这样在多线程情况下就能避免争用。

27) Java中的同步集合与并发集合有什么区别？

同步集合与并发集合都为多线程和并发提供了合适的线程安全的集合，不过并发集合的可扩展性更高。在Java1.5之前程序员们只有同步集合来用且在多线程并发的时候会导致争用，阻碍了系统的扩展性。Java5介绍了并发集合像ConcurrentHashMap，不仅提供线程安全还用锁分离和内部分区等现代技术提高了可扩展性。

28） 怎么检测一个线程是否拥有锁？

我一直不知道我们竟然可以检测一个线程是否拥有锁，直到我参加了一次电话面试。在java.lang.Thread中有一个方法叫holdsLock()，它返回true如果当且仅当当前线程拥有某个具体对象的锁。

29） 如何写代码来解决生产者消费者问题？

在现实中你解决的许多线程问题都属于生产者消费者模型，就是一个线程生产任务供其它线程进行消费，你必须知道怎么进行线程间通信来解决这个问题。比较低级的办法是用wait和notify来解决这个问题，比较赞的办法是用Semaphore 或者 BlockingQueue来实现生产者消费者模型。

30) 为什么你应该在循环中检查等待条件?

处于等待状态的线程可能会收到错误警报和伪唤醒，如果不在循环中检查等待条件，程序就会在没有满足结束条件的情况下退出。因此，当一个等待线程醒来时，不能认为它原来的等待状态仍然是有效的，在notify()方法调用之后和等待线程醒来之前这段时间它可能会改变。这就是在循环中使用wait()方法效果更好的原因，你可以在Eclipse中创建模板调用wait和notify试一试。如果你想了解更多关于这个问题的内容，我推荐你阅读《Effective Java》这本书中的线程和同步章节。

31） Java中Semaphore是什么？

Java中的Semaphore是一种新的同步类，它是一个计数信号。从概念上讲，从概念上讲，信号量维护了一个许可集合。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。每个 release()添加一个许可，从而可能释放一个正在阻塞的获取者。但是，不使用实际的许可对象，Semaphore只对可用许可的号码进行计数，并采取相应的行动。信号量常常用于多线程的代码中，比如数据库连接池。

32） 如何避免死锁？
死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。这是一个严重的问题，因为死锁会让你的程序挂起无法完成任务，死锁的发生必须满足以下四个条件：

互斥条件：一个资源每次只能被一个进程使用。
请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
不剥夺条件：进程已获得的资源，在末使用完之前，不能强行剥夺。
循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。
避免死锁最简单的方法就是阻止循环等待条件，将系统中所有的资源设置标志位、排序，规定所有的进程申请资源必须以一定的顺序（升序或降序）做操作来避免死锁。

33） 有三个线程T1，T2，T3，怎么确保它们按顺序执行？

在多线程中有多种方法让线程按特定顺序执行，你可以用线程类的join()方法在一个线程中启动另一个线程，另外一个线程完成该线程继续执行。为了确保三个线程的顺序你应该先启动最后一个(T3调用T2，T2调用T1)，这样T1就会先完成而T3最后完成。

34) Java中活锁和死锁有什么区别？

这是上题的扩展，活锁和死锁类似，不同之处在于处于活锁的线程或进程的状态是不断改变的，活锁可以认为是一种特殊的饥饿。一个现实的活锁例子是两个人在狭小的走廊碰到，两个人都试着避让对方好让彼此通过，但是因为避让的方向都一样导致最后谁都不能通过走廊。简单的说就是，活锁和死锁的主要区别是前者进程的状态可以改变但是却不能继续执行。

35） Java中synchronized 和 ReentrantLock 有什么不同？

Java在过去很长一段时间只能通过synchronized关键字来实现互斥，它有一些缺点。比如你不能扩展锁之外的方法或者块边界，尝试获取锁时不能中途取消等。Java 5 通过Lock接口提供了更复杂的控制来解决这些问题。 ReentrantLock 类实现了 Lock，它拥有与 synchronized 相同的并发性和内存语义且它还具有可扩展性。

36) 你如何在Java中获取线程堆栈？

对于不同的操作系统，有多种方法来获得Java进程的线程堆栈。当你获取线程堆栈时，JVM会把所有线程的状态存到日志文件或者输出到控制台。在Windows你可以使用Ctrl + Break组合键来获取线程堆栈，Linux下用kill -3命令。你也可以用jstack这个工具来获取，它对线程id进行操作，你可以用jps这个工具找到id。

四、java程序员必备的五十道多线程/线程池的面试题宝典（完结版）（15道）

高阶难度，如如果你提交任务时，线程池队列已满。会时发会生什么？等。
35) 如何强制启动一个线程？

这个问题就像是如何强制进行Java垃圾回收，目前还没有方法，虽然你可以使用System.gc()来进行垃圾回收，但是不保证能成功。在Java里面没有办法强制启动一个线程，它是被线程调度器控制着且Java没有公布相关的API。

36） 单例模式的双检锁是什么？

这个问题在Java面试中经常被问到，但是面试官对回答此问题的满意度仅为50%。一半的人写不出双检锁还有一半的人说不出它的隐患和Java1.5是如何对它修正的。它其实是一个用来创建线程安全的单例的老方法，当单例实例第一次被创建时它试图用单个锁进行性能优化，但是由于太过于复杂在JDK1.4中它是失败的，我个人也不喜欢它。无论如何，即便你也不喜欢它但是还是要了解一下，因为它经常被问到。

37） Java中Semaphore是什么？

Java中的Semaphore是一种新的同步类，它是一个计数信号。从概念上讲，从概念上讲，信号量维护了一个许可集合。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。每个 release()添加一个许可，从而可能释放一个正在阻塞的获取者。但是，不使用实际的许可对象，Semaphore只对可用许可的号码进行计数，并采取相应的行动。信号量常常用于多线程的代码中，比如数据库连接池。

38) 如果同步块内的线程抛出异常会发生什么？

这个问题坑了很多Java程序员，若你能想到锁是否释放这条线索来回答还有点希望答对。无论你的同步块是正常还是异常退出的，里面的线程都会释放锁，所以对比锁接口我更喜欢同步块，因为它不用我花费精力去释放锁，该功能可以在finally block里释放锁实现。

39) Swing API中那些方法是线程安全的？

这个问题又提到了swing和线程安全，虽然组件不是线程安全的但是有一些方法是可以被多线程安全调用的，比如repaint(), revalidate()。 JTextComponent的setText()方法和JTextArea的insert() 和 append() 方法也是线程安全的。

40) Java线程池中submit() 和 execute()方法有什么区别？

两个方法都可以向线程池提交任务，execute()方法的返回类型是void，它定义在Executor接口中, 而submit()方法可以返回持有计算结果的Future对象，它定义在ExecutorService接口中，它扩展了Executor接口，其它线程池类像ThreadPoolExecutor和ScheduledThreadPoolExecutor都有这些方法。

41） Java多线程中调用wait() 和 sleep()方法有什么不同？

Java程序中wait 和 sleep都会造成某种形式的暂停，它们可以满足不同的需要。wait()方法用于线程间通信，如果等待条件为真且其它线程被唤醒时它会释放锁，而sleep()方法仅仅释放CPU资源或者让当前线程停止执行一段时间，但不会释放锁。

42) 什么是阻塞式方法？

阻塞式方法是指程序会一直等待该方法完成期间不做其他事情，ServerSocket的accept()方法就是一直等待客户端连接。这里的阻塞是指调用结果返回之前，当前线程会被挂起，直到得到结果之后才会返回。此外，还有异步和非阻塞式方法在任务完成前就返回。

43) 如何在Java中创建Immutable对象？

这个问题看起来和多线程没什么关系， 但不变性有助于简化已经很复杂的并发程序。Immutable对象可以在没有同步的情况下共享，降低了对该对象进行并发访问时的同步化开销。可是Java没有@Immutable这个注解符，要创建不可变类，要实现下面几个步骤：通过构造方法初始化所有成员、对变量不要提供setter方法、将所有的成员声明为私有的，这样就不允许直接访问这些成员、在getter方法中，不要直接返回对象本身，而是克隆对象，并返回对象的拷贝。

44）如果你提交任务时，线程池队列已满。会时发会生什么？

这个问题问得很狡猾，许多程序员会认为该任务会阻塞直到线程池队列有空位。事实上如果一个任务不能被调度执行那么ThreadPoolExecutor’s submit()方法将会抛出一个RejectedExecutionException异常。

java程序员必备的五十道多线程/线程池的面试题宝典（完结版）

45) 多线程中的忙循环是什么?

忙循环就是程序员用循环让一个线程等待，不像传统方法wait(), sleep() 或 yield() 它们都放弃了CPU控制，而忙循环不会放弃CPU，它就是在运行一个空循环。这么做的目的是为了保留CPU缓存，在多核系统中，一个等待线程醒来的时候可能会在另一个内核运行，这样会重建缓存。为了避免重建缓存和减少等待重建的时间就可以使用它了。

46 Java中的fork join框架是什么？

fork join框架是JDK7中出现的一款高效的工具，Java开发人员可以通过它充分利用现代服务器上的多处理器。它是专门为了那些可以递归划分成许多子模块设计的，目的是将所有可用的处理能力用来提升程序的性能。fork join框架一个巨大的优势是它使用了工作窃取算法，可以完成更多任务的工作线程可以从其它线程中窃取任务来执行。

47） 如何在Java中创建线程安全的Singleton？

这是上面那个问题的后续，如果你不喜欢双检锁而面试官问了创建Singleton类的替代方法，你可以利用JVM的类加载和静态变量初始化特征来创建Singleton实例，或者是利用枚举类型来创建Singleton，我很喜欢用这种方法。

48） Java中invokeAndWait 和 invokeLater有什么区别？

这两个方法是Swing API 提供给Java开发者用来从当前线程而不是事件派发线程更新GUI组件用的。InvokeAndWait()同步更新GUI组件，比如一个进度条，一旦进度更新了，进度条也要做出相应改变。如果进度被多个线程跟踪，那么就调用invokeAndWait()方法请求事件派发线程对组件进行相应更新。而invokeLater()方法是异步调用更新组件的。

49）volatile 变量和 atomic 变量有什么不同？

这是个有趣的问题。首先，volatile 变量和 atomic 变量看起来很像，但功能却不一样。Volatile变量可以确保先行关系，即写操作会发生在后续的读操作之前, 但它并不能保证原子性。例如用volatile修饰count变量那么 count++ 操作就不是原子性的。而AtomicInteger类提供的atomic方法可以让这种操作具有原子性如getAndIncrement()方法会原子性的进行增量操作把当前值加一，其它数据类型和引用变量也可以进行相似操作。

50) Swing是线程安全的吗？ 为什么？

你可以很肯定的给出回答，Swing不是线程安全的，但是你应该解释这么回答的原因即便面试官没有问你为什么。当我们说swing不是线程安全的常常提到它的组件，这些组件不能在多线程中进行修改，所有对GUI组件的更新都要在AWT线程中完成，而Swing提供了同步和异步两种回调方法来进行更新。

51） Java中的ReadWriteLock是什么？

一般而言，读写锁是用来提升并发程序性能的锁分离技术的成果。Java中的ReadWriteLock是Java 5 中新增的一个接口，一个ReadWriteLock维护一对关联的锁，一个用于只读操作一个用于写。在没有写线程的情况下一个读锁可能会同时被多个读线程持有。写锁是独占的，你可以使用JDK中的ReentrantReadWriteLock来实现这个规则，它最多支持65535个写锁和65535个读锁。

52) 写出3条你遵循的多线程最佳实践

这种问题我最喜欢了，我相信你在写并发代码来提升性能的时候也会遵循某些最佳实践。以下三条最佳实践我觉得大多数Java程序员都应该遵循：

给你的线程起个有意义的名字。
这样可以方便找bug或追踪。OrderProcessor, QuoteProcessor or TradeProcessor 这种名字比 Thread-1. Thread-2 and Thread-3 好多了，给线程起一个和它要完成的任务相关的名字，所有的主要框架甚至JDK都遵循这个最佳实践。
避免锁定和缩小同步的范围
锁花费的代价高昂且上下文切换更耗费时间空间，试试最低限度的使用同步和锁，缩小临界区。因此相对于同步方法我更喜欢同步块，它给我拥有对锁的绝对控制权。
多用同步类少用wait 和 notify
首先，CountDownLatch, Semaphore, CyclicBarrier 和 Exchanger 这些同步类简化了编码操作，而用wait和notify很难实现对复杂控制流的控制。其次，这些类是由最好的企业编写和维护在后续的JDK中它们还会不断优化和完善，使用这些更高等级的同步工具你的程序可以不费吹灰之力获得优化。
多用并发集合少用同步集合
这是另外一个容易遵循且受益巨大的最佳实践，并发集合比同步集合的可扩展性更好，所以在并发编程时使用并发集合效果更好。如果下一次你需要用到map，你应该首先想到用ConcurrentHashMap。


