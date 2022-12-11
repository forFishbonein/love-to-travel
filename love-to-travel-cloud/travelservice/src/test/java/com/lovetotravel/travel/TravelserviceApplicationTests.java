package com.lovetotravel.travel;

import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

@SpringBootTest
class TravelserviceApplicationTests {



    @Test
    void contextLoads() throws IOException {

        Process proc;
        try {
            Runtime.getRuntime().exec("conda activate pyspark");
            proc = Runtime.getRuntime().exec("python .\\src\\main\\resources\\static\\hot_city.py");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
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