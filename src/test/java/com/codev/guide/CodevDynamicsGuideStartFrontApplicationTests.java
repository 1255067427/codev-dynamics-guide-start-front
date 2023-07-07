package com.codev.guide;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class EmployeeManagementSystemApplicationTests {

    @Test
    void contextLoads() throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

        String time = "08:31:38";
        String time1 = "08:00:24";
        Date parse = sdf1.parse(time);
        Date parse1 = sdf1.parse(time1);

        long l = parse.getTime() - parse1.getTime();
        Date date = new Date(l);

        System.out.println(sdf1.format(date));
    }

}
