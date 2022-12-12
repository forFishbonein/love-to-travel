package com.lovetotravel.travel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonUtil {


    public String execPythonFile(String filename) {

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("cmd /C python .\\love-to-travel-cloud\\travelservice\\src\\main\\resources\\static\\" + filename);// 执行py文件
//            proc = Runtime.getRuntime().exec("cmd /C dir");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GB2312"));
            String line = null;
            String result = null;
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
            in.close();
            proc.waitFor();
            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }


}
