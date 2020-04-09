package com.qitai.regular;

import org.junit.Test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
    /**
     * 验证非法字符
     */
    public static final String REGEX_STR_LEGAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 验证手机号
     */
    public static final String REGEX_MOBILE = "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";

    /**
     * 验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 验证身份证
     */
    private static final String REGEX_ID_CARD = "/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/";
    /**
     * 验证台湾身份证
     */
    private static final String REGEX_Taiwan_CARD = "/^[A-Z][0-9]{9}$/";
    /**
     * 验证香港身份证
     */
    private static final String REGEX_Hongkong_CARD = "/^[A-Z][0-9]{6}\\([0-9A]\\)$/";
    /**
     * 验证澳门身份证
     */
    private static final String REGEX_Macao_CARD = "/^[157][0-9]{6}\\([0-9]\\)$/";

    /**
     * 验证银行卡号
     */
    public static final String REGEX_BANK_CARD = "^(\\d{16}|\\d{19})$";

    /**
     * 验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 验证用户名
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 非法字符验证
     * @param str
     * @return
     */
    public static boolean strLegal(String str) {
        return Pattern.matches(REGEX_STR_LEGAL, str);
    }


    @Test
    public void regularTest(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入需要验证的字符串：");

            String line = scanner.next();
            System.out.println(line);
    }

    @Test
    public void test(){
        String url = "http://localhost:8080/ http://127.0.0.1:8080/  http://127.0.0.2:8080/  http://bdc.huizhou.gov.cn/";
        String[] urls = url.split(" ");
        Pattern p = Pattern.compile("((http|https)://(www.)?(\\w+(\\.)?)+:\\d+)|((http|https)://(www.)?(\\w+(\\.)?)+)");
        Pattern p1 = Pattern.compile("((http|https)://(www.)?(\\w+(\\.)?)+:\\d+)|((http|https)://(www.)?(\\w+(\\.)?)+)");
        Pattern p2 = Pattern.compile("/(www.)?(\\w+(\\.)?)+");
        Matcher m = p.matcher(url);
        String urlRex="((";
        while(m.find()) {

            urlRex += m.group(0)+")|(";
        }
        urlRex = urlRex.substring(0,urlRex.length()-2);
        urlRex+=")/.*";
        System.out.println(urlRex);
        System.out.println(Pattern.matches(urlRex,"http://127.0.0.1:8080/mainWeb/xx"));
        System.out.println(Pattern.matches(urlRex,"http://127.0.0.3:8080/mainWeb/xx"));
        System.out.println(Pattern.matches(urlRex,"http://bdc.huizhou.gov.cn/mainWeb/xx"));
        System.out.println(Pattern.matches(urlRex,"http://localhost:8080/"));
    }

    @Test
    public void regularTest1(){
        String url = "http://192.168.10.95:8089/mainWeb/index/xx";
        String reUrl = "^((http|https)://)((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(:\\d{1,}/)(mainWeb|pubWeb)(/.*)";
        System.out.println(Pattern.matches(reUrl,url));
        System.out.println(Pattern.matches("<[a-zA-Z]{1,}>","<title>"));
    }

    @Test
    public void regularTest3(){
        String url = "http://localhost:8080/gdbdcWebService/doc/forward";
        String reUrl = "(/doc/)(.*)";
        Pattern pattern = Pattern.compile(reUrl);
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()){
            System.out.println(matcher.group());
        }

        url = "/doc/forward";
        System.out.println(url.split("(/doc/)")[1]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入需要验证的字符串：");
        while (true){
            String line = scanner.next();
            if (line.equals("exit"))break;
            System.out.println(line+"是否是手机号:"+isMobile(line));
            System.out.println(line+"是否是身份证:"+isIDCard(line));
            System.out.println(line+"是否是邮箱:"+isEmail(line));
        }
    }
    @Test
    public void test1(){
        String url = null;
        System.out.println("true".equals(url));
        System.out.println(url.equals("true"));
    }

    @Test
    public void test4(){
        System.out.println(true||false);
    }
}
