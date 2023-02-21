package com.metoo.nspm.core.utils;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringUtils {


    /**
     * 1：判断字符是否为数字或字母
     * @return
     */

    public static void main(String[] args) {
        String value = "f1234";
        String value1 = "abasd";
        // 方式一：
        boolean flag = isInteger(value);
        boolean flag1 = isInteger(value1);
        System.out.println(flag);
        System.out.println(flag1);

        boolean isDigitInt = isDigit(value);
        boolean isDigitStr = isDigit(value1);
        System.out.println(isDigitInt);
        System.out.println(isDigitStr);

    }

    // 方式一：
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Test
    public void idd(){
        String value = "ewa";
        System.out.println(value.charAt(0));
    }

    // 方式二：Character
    public static boolean isDigit(String value){

        for (int i = value.length(); --i >= 0;){
            System.out.println(value.charAt(i));
            if (!Character.isDigit(value.charAt(i))){

                return false;
            }

        }
        return true;
    }

    public static String getStr(String s) {
        if (s == null) {
            return "";
        }
        if (s.isEmpty()) {
            return s;
        }
        return s.trim();
    }

    /**
     * 测试指定字符串出现的位置
     */
    public static void acquireCharacterPosition() {
        int i = acquireCharacterPosition("00", ":", 1);
        System.out.println(i);
    }

    /**
     * 获取指定字符第N次出现的位置
     * @return
     */
    public static int acquireCharacterPosition(String param, String symbol, int num){
        if(param.contains(symbol) && param.indexOf(":") != -1){
            Pattern pattern = Pattern.compile(symbol);
            Matcher findMatcher = pattern.matcher(param);
            List<Integer> list = new ArrayList();
            while(findMatcher.find()) {
                list.add(findMatcher.start());
            }
            return list.get(num - 1);
        }
        return -1;
    }

    @Test
    public void t(){
        System.out.println("输出整型数组:");
        Integer[] integerArray = { 1, 2, 3, 4, 5, 6 };
        for (Integer element : integerArray){
            System.out.printf("%s ", element);
            System.out.println();
        }

    }
    static  class MainClass{
        public static void main(String[] args) {
            int nDisks = 4;
            doTowers(nDisks, 'A', 'B', 'C');
        }
        public static void doTowers(int topN, char from, char inter, char to) {
            if (topN == 1){
                System.out.println("Disk 1 from "
                        + from + " to " + to);
            }else {
                doTowers(topN - 1, from, to, inter);
                System.out.println("Disk "
                        + topN + " from " + from + " to " + to);
                doTowers(topN - 1, inter, from, to);
            }
        }
    }

}
