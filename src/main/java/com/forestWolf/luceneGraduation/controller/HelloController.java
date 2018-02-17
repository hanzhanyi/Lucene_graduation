package com.forestWolf.luceneGraduation.controller;

import com.forestWolf.luceneGraduation.mapper.TestMapper;
import com.forestWolf.luceneGraduation.pojo.Test;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by hzy on 2018-1-3.
 */
@RestController
public class HelloController {
    public final String BINGHAI_URL= "http://bcch.ahnw.gov.cn/searchResult.aspx?type=%B2%A1%BA%A6&context=";
    public final String CHONGHAI_URL = "http://bcch.ahnw.gov.cn/searchResult.aspx?type=%B3%E6%BA%A6&context=";
    public final String CAOHAI_URL = "http://bcch.ahnw.gov.cn/searchResult.aspx?type=%B2%DD%BA%A6&context=";
    @Autowired
    private TestMapper testMapper ;

    @RequestMapping("hello")
    public String HelloWorld(){
        return "hello";
    }
    @RequestMapping(value = "hello1")
    public String HelloWorld2(){
        Test test = new Test();
        test.setId(UUID.randomUUID().toString());
        testMapper.insertSelective(test);
        return "hello1";
    }
}
