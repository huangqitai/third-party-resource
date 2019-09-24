package com.qitai.arithmeticexercise;

import org.junit.Test;
import java.math.BigInteger;
import java.util.Stack;
import java.util.Vector;

public class PageOne {
    /**
     * 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     */
    @Test
    public void rabbit(){
        for (int i = 1 ; i < 13 ; i++){
            System.out.println(recursion(i));
        }
    }
    int recursion(int i){
        if (i == 1 || i ==2){
            return 1;
        }else return recursion(i-1)+recursion(i-2);
    }

    /**
     * 判断101-200之间有多少个素数，并输出所有素数。
     */
    @Test
    public void prime(){
        for (int i = 101 ; i < 201 ; i++){
            for (int j = 2 ; j < i ; j++){
                if (i%j==0){
                    System.out.println(i+"是素数");break;
                }
            }
        }
    }

    /**
     * 打印出1-1000所有的 "水仙花数"，所谓 "水仙花数"是指一个三位数，其各位数字立方和等于该数本身。
     */
    @Test
    public void  narcissus(){
        int gewei;
        int shiwei;
        int baiwei;
        for (int num = 100 ; num < 1001 ; num++){
            gewei = num%10;
            shiwei = (num%100)/10;
            baiwei = num/100;

            if(num == baiwei*baiwei*baiwei+shiwei*shiwei*shiwei+gewei*gewei*gewei){
                System.out.println(num+"是水仙花");
            }
        }
    }

    /**
     * 将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。
     */
    @Test
    public void resolve(){
        int num = 20;
        String divisor="";
        for (int i = 2 ; i <= num ;  ){
            if (num%i==0&&num>2){
                divisor += i;
                if (i!=num){
                    divisor += "*";
                }

                num = num/i;
            }else i++;
        }
        System.out.println(divisor);
    }

    /**
     * 输入两个正整数m和n，求其最大公约数和最小公倍数
     */

    @Test
    public void gcdLcm(){
        int m = 20;
        int n = 30;
        int min = m >= n ? n : m;
        for(int i = min ; i>0 ; i--){
            if (m%i==0&&n%i==0){
                System.out.println("最大公约数为"+i);
                break;
            }
        }

        int max = m > n ? m : n;

        for (int i = max ; i <= n*m ; i++){
            if (i%n==0&&i%m==0){
                System.out.println("最小公倍数为"+i);
                break;
            }
        }
    }

    /**
     * 输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数
     */
    @Test
    public void count(){
        String string = "wqew145huids7y745..,s@@aa%%";
        int strCount=0;
        int numCount=0;
        int othCount=0;

        for (int i=0;i<string.length();i++){
            char c = string.charAt(i);

            if (('a'<c&&'z'>c)||('A'<c&&'Z'>c)){
                strCount++;
            }else if ('0'<c&&c<'9'){
                numCount++;
            }else othCount++;
        }

        System.out.println("字母个数"+strCount);
        System.out.println("数字个数"+numCount);
        System.out.println("其它个数"+othCount);

    }

    /**
     * 一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在第10次落地时，共经过多少米？第10次反弹多高？
     */
    @Test
    public void fall(){
        int height=100;
        int len=height;
        int tenH;
        for (int i=0;i<11;i++){
            len += height/2;
            height = height/2;
        }
        tenH=height;
        System.out.println("路程"+len+";第十次高度"+tenH);
    }

    /**
     * 二十一位水仙花
     * 二十一太大，等不到结果
     */
    @Test
    public void narcissus21(){
        int size = 5;
        BigInteger powArray[] = new BigInteger[10];
        pow(powArray,size);
        BigInteger i;
        BigInteger y;

        for (i=min21(size);i.compareTo(max21(size))<0;i=i.add(new BigInteger(""+1))){
            y = cubeSum(i,powArray);
            if (y.compareTo(i)==0){
                System.out.println(i+"是水仙花");
            }
        }
    }
    public BigInteger min21(int size){
        String min="1";
        for (int i=0;i<size-1;i++){
            min+="0";
        }
        return new BigInteger(min);
    }
    public BigInteger max21(int size){
        String max="9";
        for (int i=0;i<size-1;i++){
            max+="9";
        }
        return new BigInteger(max);
    }
    public void pow(BigInteger powArray[],int size){

        for (int i=0;i<10;i++){
            powArray[i]=(new BigInteger(""+i)).pow(size);
        }
    }
    public BigInteger cubeSum(BigInteger bigInteger,BigInteger bigIntegers[]){
        String string = bigInteger+"";
        BigInteger bigIntegerSum=new BigInteger(""+0);
        char[] chars = new char[string.length()];
        for (int i=0;i<string.length();i++){
            chars[i] = string.charAt(i);
            for (int j=0;j<bigIntegers.length;j++){
                if ((chars[i]-'0')==j){
                    bigIntegerSum = bigIntegerSum.add(bigIntegers[j]);break;
                }
            }
        }
        return bigIntegerSum;
    }

    /**
     *
     * 下面代码模拟了一套扑克牌（初始排序A~K，共13张）的操作过程。
     * 操作过程是：
     * 手里拿着这套扑克牌，从前面拿一张放在后面，再从前面拿一张放桌子上，再从前面拿一张放在后面，....
     * 如此循环操作，直到剩下最后一张牌也放在桌子上。
     * 下面代码的目的就是为了求出最后桌上的牌的顺序。
     * 初始的排列如果是A,2,3...K，则最后桌上的顺序为：
     * [2, 4, 6, 8, 10, Q, A, 5, 9, K, 7, 3, J]
     */
    @Test
    public void playCard(){
        String s[] = new String[13];
        int index=0;
        String strs[] = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
           while (strs.length>0){
               strs = firstToEnd(strs);
               strs = pullFirst(strs,s,index);
               index++;
           }

        for (int i=0;i<s.length;i++){
            System.out.print(s[i]+"     ");
        }
    }
    public String[] pullFirst(String[] strs,String[] s,int index){
        String str = strs[0];
        String strsCopy[] = new String[strs.length-1];
        for (int i=0 ;i<strs.length-1;i++){

            strsCopy[i] = strs[i+1];
        }
        strs = strsCopy;
        s[index] = str;
        return strs;
    }
    public String[] firstToEnd(String[] strs){
        String str= strs[0];
        for (int i=0 ;i<strs.length-1;i++){

            strs[i] = strs[i+1];
        }
        strs[strs.length-1] = str;
        return strs;
    }

    /**
     * 下面的代码用于判断一个串中的括号是否匹配 所谓匹配是指不同类型的括号必须左右呼应，可以相互包含，但不能交叉
     * 例如：
     * ..(..[..]..).. 是允许的
     * ..(...[...)....].... 是禁止的
     */
    @Test
    public void testGoodBracket(){
        System.out.println(isGoodBracket("...(..[.)..].{.(..).}..."));
        System.out.println(isGoodBracket("...(..[...].(.).).{.(..).}..."));
        System.out.println(isGoodBracket(".....[...].(.).){.(..).}..."));
        System.out.println(isGoodBracket("...(..[...].(.).){.(..)...."));
    }

    private boolean isGoodBracket(String s) {
        Stack<Character> characterStack = new Stack();
        for (int i=0 ;i<s.length();i++){
            if(s.charAt(i)=='('){
                characterStack.push(')');
            }
            if(s.charAt(i)=='{'){
                characterStack.push('}');
            }
            if(s.charAt(i)=='['){
                characterStack.push(']');
            }
            if (s.charAt(i)==')'||s.charAt(i)=='}'||s.charAt(i)==']'){
                if (characterStack.size()==0) return false;
                if (characterStack.pop()!=s.charAt(i)) return false;//最后面添加进栈中的反括号必须和第一个出现的反括号一样，同样的，pop删除了顶元素之后，新的顶元素必须和下一次出现的反括号一样
            }
        }
        if (characterStack.size()!=0){
            return false;
        }
        return true;
    }

    /**
     *
     * 看这个算式：
     * ☆☆☆ + ☆☆☆ = ☆☆☆
     * 如果每个五角星代表 1 ~ 9 的不同的数字。
     * 这个算式有多少种可能的正确填写方法？
     * 173 + 286 = 459
     * 295 + 173 = 468
     * 173 + 295 = 468
     * 183 + 492 = 675
     * 以上都是正确的填写法！
     * 注意：
     * 111 + 222 = 333 是错误的填写法！
     * 因为每个数字必须是不同的！
     * 也就是说：1~9中的所有数字，每个必须出现且仅出现一次！
     * 注意：
     * 不包括数字“0”！
     * 注意：
     * 满足加法交换率的式子算两种不同的答案。
     * 所以答案肯定是个偶数！
     */

    public int count=0;
    @Test
    public void exhaustionTest(){
        Vector<Character> ac = new Vector<>();
        Vector<Character> rc = new Vector<>();
        for (int i=1 ; i<10;i++){
            ac.add((char)(i+'0'));
        }
        exhaustion(ac,rc);
        System.out.println(count);
    }
    public void exhaustion(Vector<Character> ac,Vector<Character> rc){

        if (ac.size()==0){
            int a = (rc.elementAt(0)-'0')*100+(rc.elementAt(1)-'0')*10+(rc.elementAt(2)-'0');
            int b = (rc.elementAt(3)-'0')*100+(rc.elementAt(4)-'0')*10+(rc.elementAt(5)-'0');
            int c = (rc.elementAt(6)-'0')*100+(rc.elementAt(7)-'0')*10+(rc.elementAt(8)-'0');
            if (a+b==c){
                count++;
                //System.out.println(a+"+"+b+"="+c);
            }
        }else{
            for (int i=0;i<ac.size();i++){
                rc.add(ac.elementAt(i));
                ac.remove(i);
                exhaustion(ac,rc);
                ac.add(i,rc.elementAt(rc.size()-1));
                rc.remove(rc.size()-1);
            }
        }
    }

    private int countPc=0;
    @Test
    public void pcTest(){
        Vector<Character> ac = new Vector<>();
        Vector<Character> rc = new Vector<>();
        for (int i=1 ; i<5;i++){
            ac.add((char)(i+'0'));
        }
        pc(ac,rc);
        System.out.println(countPc);
    }
    public void pc(Vector<Character> ac, Vector<Character> rc){

        if (ac.size()==1){
            int a = (rc.elementAt(0)-'0')*100+(rc.elementAt(1)-'0')*10+(rc.elementAt(2)-'0');
            System.out.println(a);
            countPc++;
        }
        else{
            for (int i=0;i<ac.size();i++){
                rc.add(ac.elementAt(i));
                ac.remove(i);
                pc(ac,rc);
                ac.add(i,rc.elementAt(rc.size()-1));
                rc.remove(rc.size()-1);
            }
        }

    }

    /**
     * 求1+2!+3!+...+20!的和
     */
    @Test
    public void recursiveTest(){
        System.out.println(recursive(5));
    }
    private int recursive(int num){
        if (num!=1){
            return num*recursive(num-1);
        }
        else return 1;
    }
}
