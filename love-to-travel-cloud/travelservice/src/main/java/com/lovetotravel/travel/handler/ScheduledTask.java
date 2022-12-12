package com.lovetotravel.travel.handler;

import com.lovetotravel.travel.utils.PythonUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScheduledTask {

    @Scheduled(cron = "* * 1 * * ?")
    public void updateHotCity() {
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("================================");

        String filename = "hotCity.py";

        PythonUtil pythonUtil = new PythonUtil();
        String result = pythonUtil.execPythonFile(filename);
        System.out.println("result = " + result);
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("================================");
    }
}
