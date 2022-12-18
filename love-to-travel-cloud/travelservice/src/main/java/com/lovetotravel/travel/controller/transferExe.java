package com.lovetotravel.travel.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/*
 * 运行可执行文件：.exe
 * 当要执行一个本地机器上的可执行文件时，
 * 可以使用java.lang包中的Runtime类，首先使用Runtime类，首先
 * 使用Runtime类声明一个对象
 *｛
 *    Runtime sc =Runtime.getRuntime();
 *   sc可以调用exec(String command) 方法打开本地湖区上的可执行文件或执行一个操作。
 * ｝
 */
public class transferExe {

    public static void main(String[] args) {
        //需要传入的参数（ps: 多个参数用空格隔开）
//        String paras = "81";
        //调用的exe可执行文件（ps: 调用可执行文件和参数拼接必须要用空格隔开）
//        String cmd = "C:\\Users\\Lenovo\\dist\\main_simulateRequest.exe";

        String cmd = "C:\\Users\\Lenovo\\dist\\hotnote.exe";
        openExe(cmd);

    }

    public static void openExe(String cmd) {
        BufferedReader br = null;
        BufferedReader brError = null;

        try {
            //执行exe  cmd可以为字符串(exe存放路径)也可为数组，调用exe时需要传入参数时，可以传数组调用(参数有顺序要求)
            Process p = Runtime.getRuntime().exec(cmd);
            String line = null;
            //获得子进程的输入流。
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            //获得子进程的错误流。
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
                //输出exe输出的信息以及错误信息
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}