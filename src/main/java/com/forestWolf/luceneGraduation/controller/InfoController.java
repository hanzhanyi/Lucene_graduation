package com.forestWolf.luceneGraduation.controller;

import com.forestWolf.luceneGraduation.pojo.CropDetail;
import com.forestWolf.luceneGraduation.pojo.Label;
import com.forestWolf.luceneGraduation.pojo.LabelDetail;
import com.forestWolf.luceneGraduation.response.ServerResponse;
import com.forestWolf.luceneGraduation.service.InfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/crop")
@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "pythonInfo")
    public ServerResponse<Boolean> pythonInfo() {
        if (infoService.getInfoMation()) {
            return new ServerResponse<Boolean>(0, "success", true);
        } else {
            return new ServerResponse<Boolean>(-1, "failure", false);
        }
    }

    @RequestMapping(value = "label/detail")
    public ServerResponse<List<LabelDetail>> getLabelDetail(@Param(value = "id") Long id) {
        List<LabelDetail> labelList = infoService.getLabelDetail(id);
        if (labelList != null && !labelList.isEmpty()) {
            return new ServerResponse<List<LabelDetail>>(0, "success", labelList);
        } else {
            return new ServerResponse<List<LabelDetail>>(-1, "failure", null);
        }
    }

    @RequestMapping(value = "type/view")
    public ServerResponse<List<CropDetail>> getTypeView(@Param(value = "id") Long id) {
        List<CropDetail> cropDetailList = infoService.getTypeView(id);
        if (cropDetailList != null && !cropDetailList.isEmpty()) {
            return new ServerResponse<List<CropDetail>>(0, "success", cropDetailList);
        } else {
            return new ServerResponse<List<CropDetail>>(-1, "failure", null);
        }
    }

    @RequestMapping(value = "type/detail")
    public ServerResponse<CropDetail> getTypeDetail(@Param(value = "id") Long id) {
        CropDetail cropDetail = infoService.getTypeDetail(id);
        if (cropDetail != null) {
            return new ServerResponse<CropDetail>(0, "success", cropDetail);
        } else {
            return new ServerResponse<CropDetail>(-1, "failure", null);
        }
    }

    @RequestMapping(value = "label/view")
    public ServerResponse<List<Label>> getLabelView() {
        List<Label> labelList = infoService.getLabelView();
        if (labelList != null && !labelList.isEmpty()) {
            return new ServerResponse<List<Label>>(0, "success", labelList);
        } else {
            return new ServerResponse<List<Label>>(-1, "failure", null);
        }
    }


    @RequestMapping(value = "type/buildIndex")
    public ServerResponse<Boolean> buildIndex() {
        if (infoService.bulidIndex()) {
            return new ServerResponse<Boolean>(0, "success", true);
        } else {
            return new ServerResponse<Boolean>(-1, "failure", false);
        }
    }

    @RequestMapping(value = "type/search")
    public ServerResponse<List<CropDetail>> searchIndex(@Param(value = "wd") String wd) {
        ServerResponse<List<CropDetail>> serverResponse = new ServerResponse<List<CropDetail>>();
        List<CropDetail> cropDetailList = infoService.searchIndex(wd);
        if (!cropDetailList.isEmpty()) {
            return new ServerResponse<List<CropDetail>>(0, "success", cropDetailList);
        } else {
            return new ServerResponse<List<CropDetail>>(-1, "failure", null);
        }
    }

    @RequestMapping(value = "type/seniorsearch")
    public ServerResponse<List<CropDetail>> seniorSearchIndex(@Param(value = "wd") String wd) {
        ServerResponse<List<CropDetail>> serverResponse = new ServerResponse<List<CropDetail>>();
        List<CropDetail> cropDetailList = infoService.seniorSearchIndex(wd);
        if (!cropDetailList.isEmpty()) {
            return new ServerResponse<List<CropDetail>>(0, "success", cropDetailList);
        } else {
            return new ServerResponse<List<CropDetail>>(-1, "failure", null);
        }
    }

    @RequestMapping(value = "type/profsearch")
    public ServerResponse<List<CropDetail>> profSearchIndex(@Param(value = "wd") String wd,@Param(value = "type") Integer type) {
        ServerResponse<List<CropDetail>> serverResponse = new ServerResponse<List<CropDetail>>();
        List<CropDetail> cropDetailList = infoService.profSearchIndex(wd,type);
        if (!cropDetailList.isEmpty()) {
            return new ServerResponse<List<CropDetail>>(0, "success", cropDetailList);
        } else {
            return new ServerResponse<List<CropDetail>>(-1, "failure", null);
        }
    }



    @RequestMapping(value = "test")
    public ServerResponse<List<Label>> test() {
        stringRedisTemplate.opsForValue().set("test1","hanzhanyi");
        System.out.println(stringRedisTemplate.opsForValue().get("test1"));
        List<Label> labelList = infoService.getLabelView();
        if (labelList != null && !labelList.isEmpty()) {
            return new ServerResponse<List<Label>>(0, "success", labelList);
        } else {
            return new ServerResponse<List<Label>>(-1, "failure", null);
        }
    }

}
