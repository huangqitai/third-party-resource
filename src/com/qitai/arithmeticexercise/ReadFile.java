package com.qitai.arithmeticexercise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadFile {

    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        StringBuffer sb = new StringBuffer();


        if (file.isFile() && file.exists()) { //判断文件是否存在
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ReadFile.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while (true) {
                try {
                    if (!((byteread = in.read(tempbytes)) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  System.out.write(tempbytes, 0, byteread);
                String str = new String(tempbytes, 0, byteread);
                sb.append(str);
            }
        } else {
            System.out.println("找不到指定的文件，请确认文件路径是否正确");
        }
    }

        private static void showAvailableBytes(InputStream in) {
            try {
                System.out.println("当前字节输入流中的字节数为 {} "+in.available());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {

            //String fileName = "D:/file/1.json";
            //  String fileName = "D:/file/8.json";
            //readFileByBytes(fileName);

            String gid = getXmlString();
            String [] arrayGid = gid.split(",");
            System.out.println(arrayGid.length);
        }

    public static String getXmlString() {
        String xmlString;
        byte[] strBuffer = null;
        int flen = 0;
        File xmlfile = new File("C:/Users/86190/Desktop/11.txt");
        try {
            InputStream in = new FileInputStream(xmlfile);
            flen = (int)xmlfile.length();
            strBuffer = new byte[flen];
            in.read(strBuffer, 0, flen);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlString = new String(strBuffer); //构建String时，可用byte[]类型，

        return xmlString;
    }
}
