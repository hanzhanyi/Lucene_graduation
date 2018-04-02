package com.forestWolf.luceneGraduation;

import com.alibaba.fastjson.JSON;
import com.forestWolf.luceneGraduation.service.InfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
         map1.put("key1", "One");
         map1.put("key2", "Two");

         Map<String, Object> map2 = new HashMap<String, Object>();
         map2.put("key1", "Three");
         map2.put("key2", "Four");

         list.add(map1);
         list.add(map2);

         String listJson = JSON.toJSONString(list);
    }
}
