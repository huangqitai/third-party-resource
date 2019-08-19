package com.qitai.designmode.adapter;
/**
 *适配器类，为了即实现继承而来的方法也实现接口的方法
 * 只需要重写接口独有的方法，与类中同名的方法不需要重写，不会报错
 */
public class TargetImplSourEx extends Source implements TargetInterface {
    @Override
    public void method2() {
        System.out.println("执行接口方法method2");
    }
}
