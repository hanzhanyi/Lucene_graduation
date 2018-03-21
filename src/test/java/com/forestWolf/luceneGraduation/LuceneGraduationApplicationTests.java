package com.forestWolf.luceneGraduation;

import com.forestWolf.luceneGraduation.service.InfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneGraduationApplicationTests {
    @Resource
    private InfoService infoService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getLabelInfoTest() {
//        infoService.getInfoMation(null);
    }
}
