package com.lovetotravel.travel;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@SpringBootTest
class TravelserviceApplicationTests {



    @Test
    void contextLoads() throws IOException {

        Process proc;
        try {
            Runtime.getRuntime().exec("conda activate pyspark");
            proc = Runtime.getRuntime().exec("python .\\src\\main\\resources\\static\\hotCity.py");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GB2312"));
            String line = null;
            String result = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}