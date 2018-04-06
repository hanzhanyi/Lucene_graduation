package com.forestWolf.luceneGraduation.service.impl;

import com.alibaba.fastjson.JSON;
import com.forestWolf.luceneGraduation.lucene.CropIndex;
import com.forestWolf.luceneGraduation.mapper.CropDetailImgMapper;
import com.forestWolf.luceneGraduation.mapper.CropDetailMapper;
import com.forestWolf.luceneGraduation.mapper.LabelDetailMapper;
import com.forestWolf.luceneGraduation.mapper.LabelMapper;
import com.forestWolf.luceneGraduation.pojo.*;
import com.forestWolf.luceneGraduation.response.ServerResponse;
import com.forestWolf.luceneGraduation.service.InfoService;
import com.forestWolf.luceneGraduation.utils.UniqueIDUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("infoService")
public class InfoServiceImpl implements InfoService {

    private static final String URL_PREFIX = "http://bcch.ahnw.gov.cn/";

    private static final String URL_RIGHT = "http://bcch.ahnw.gov.cn/Right.aspx";

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelDetailMapper labelDetailMapper;

    @Autowired
    private CropDetailImgMapper cropDetailImgMapper;

    @Autowired
    private CropDetailMapper cropDetailMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean getInfoMation() {
        return getLabelInfo(URL_RIGHT);
    }


    private static final String LABEL_VIEW_PRE = "label_view_";
    private static final String LABEL_DETAIL_PRE = "label_detail_";
    private static final String TYPE_VIEW_PRE = "type_view_";
    private static final String TYPE_DETAIL_PRE = "type_detail_";
    @Override
    public List<Label> getLabelView() {
        String labelView =  stringRedisTemplate.opsForValue().get(LABEL_VIEW_PRE);
        if(labelView!=null&&!labelView.isEmpty()){
            System.out.println("命中Redis缓存"+labelView);
            List<Label> labelList = JSON.parseArray(labelView,Label.class);
            return labelList;
        }
        LabelExample labelExample = new LabelExample();
        labelExample.createCriteria();
        List<Label> labelList = labelMapper.selectByExample(labelExample);
        stringRedisTemplate.opsForValue().set(LABEL_VIEW_PRE,JSON.toJSONString(labelList));
        return labelList;
    }

    @Override
    public List<LabelDetail> getLabelDetail(long id) {
        String labelView =  stringRedisTemplate.opsForValue().get(LABEL_DETAIL_PRE+id);
        if(labelView!=null&&!labelView.isEmpty()){
            System.out.println("命中Redis缓存"+labelView);
            List<LabelDetail> labelDetailList = JSON.parseArray(labelView,LabelDetail.class);
            return labelDetailList;
        }

        LabelDetailExample labelDetailExample = new LabelDetailExample();
        labelDetailExample.createCriteria().andLabelIdEqualTo(id);
        List<LabelDetail> labelDetails = labelDetailMapper.selectByExample(labelDetailExample);

        stringRedisTemplate.opsForValue().set(LABEL_DETAIL_PRE+id,JSON.toJSONString(labelDetails));
        return labelDetails;
    }

    @Override
    public List<CropDetail> getTypeView(long id) {
        String labelView =  stringRedisTemplate.opsForValue().get(TYPE_VIEW_PRE+id);
        if(labelView!=null&&!labelView.isEmpty()){
            System.out.println("命中Redis缓存"+labelView);
            List<CropDetail> cropDetailList = JSON.parseArray(labelView,CropDetail.class);
            return cropDetailList;
        }

        CropDetailExample cropDetailExample = new CropDetailExample();
        cropDetailExample.createCriteria().andLabelDetailEqualTo(id);
        List<CropDetail> cropDetailList = cropDetailMapper.selectByExample(cropDetailExample);

        stringRedisTemplate.opsForValue().set(TYPE_VIEW_PRE+id,JSON.toJSONString(cropDetailList));
        return cropDetailList;
    }

    @Override
    public CropDetail getTypeDetail(long id) {
        String labelView =  stringRedisTemplate.opsForValue().get(TYPE_DETAIL_PRE+id);
        if(labelView!=null&&!labelView.isEmpty()){
            System.out.println("命中Redis缓存"+labelView);
            CropDetail cropDetail = JSON.parseObject(labelView,CropDetail.class);
            return cropDetail;
        }

        CropDetailExample cropDetailExample = new CropDetailExample();
        cropDetailExample.createCriteria().andIdEqualTo(id);
        CropDetail cropDetail = null;
        List<CropDetail> cropDetailList = cropDetailMapper.selectByExample(cropDetailExample);
        if (cropDetailList != null && !cropDetailList.isEmpty()) {
            cropDetail = cropDetailList.get(0);
            CropDetailImgExample cropDetailImgExample = new CropDetailImgExample();
            cropDetailImgExample.createCriteria().andCropIdEqualTo(cropDetail.getId());
            List<CropDetailImg> cropDetailImgList = cropDetailImgMapper.selectByExample(cropDetailImgExample);
            cropDetail.setImgList(cropDetailImgList);
        }

        stringRedisTemplate.opsForValue().set(TYPE_DETAIL_PRE+id,JSON.toJSONString(cropDetail));
        return cropDetail;
    }

    @Override
    public boolean bulidIndex() {
        CropIndex cropIndex = new CropIndex();
        CropDetailExample cropDetailExample = new CropDetailExample();
        cropDetailExample.createCriteria();
        try {
            cropIndex.deleteIndexAll();
            int count = cropDetailMapper.countByExample(cropDetailExample);
            for (int i = 0; i < count; i += 100) {
                CropDetailExamplePage cropDetailExamplePage = new CropDetailExamplePage();
                cropDetailExamplePage.setOrderByClause("create_time desc");
                cropDetailExamplePage.setPageNo(i);
                cropDetailExamplePage.setPageSize(100);
                cropDetailExamplePage.createCriteria();
                List<CropDetail> cropDetailList = cropDetailMapper.selectByExampleByPage(cropDetailExamplePage);
                for (CropDetail cropDetail : cropDetailList) {
                    cropIndex.addIndex(cropDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("建立索引失败");
            return false;
        }
        System.out.println("建立索引成功");
        return true;
    }

    @Override
    public List<CropDetail> searchIndex(String wd) {
        CropIndex cropIndex = new CropIndex();

        List<CropDetail> cropDetailList = null;
        try {
            cropDetailList = cropIndex.searchCrop(wd);
        } catch (Exception e) {
            System.out.println("查询成功");
            e.printStackTrace();
        }
        return cropDetailList;
    }
    @Override
    public List<CropDetail> profSearchIndex(String wd,int type) {
        CropIndex cropIndex = new CropIndex();

        List<CropDetail> cropDetailList = null;
        try {
            cropDetailList = cropIndex.profSearchCrop(wd,type);
        } catch (Exception e) {
            System.out.println("查询成功");
            e.printStackTrace();
        }
        return cropDetailList;
    }

    /**
     * 爬取label表数据
     *
     * @param Url
     */
    public boolean getLabelInfo(String Url) {
        try {
            Document doc = Jsoup.connect(Url).get();
            Elements elements = doc.select("table");
            Element element = elements.get(0);
            Elements trs = element.select("tr");
            for (Element tr : trs) {
                Element td = tr.child(0);
                Elements strongs = td.select("strong");
                Element strong = strongs.get(0);
                LabelExample labelExample = new LabelExample();
                labelExample.createCriteria();
                //拼装Label数据
                Label label = new Label();
                label.setId(UniqueIDUtil.getUniqueID());
                label.setCreateTime(new Date());
                label.setUpdateTime(new Date());
                label.setTypeName(strong.text().trim());
                labelMapper.insertSelective(label);
                //爬取labelDetail数据
                Elements hrefs = td.select("a[href]");
                for (Element href : hrefs) {
                    String hrefUrl = href.attr("href");
                    String labelDetailName = href.text().trim();
                    //插入labelDetail数据
                    LabelDetail labelDetail = new LabelDetail();
                    labelDetail.setId(UniqueIDUtil.getUniqueID());
                    labelDetail.setLabelId(label.getId());
                    labelDetail.setCreateTime(new Date());
                    labelDetail.setUpdateTime(new Date());
                    labelDetail.setLabelName(labelDetailName);
                    labelDetailMapper.insertSelective(labelDetail);

                    getLabelDetail(labelDetail, URL_PREFIX + hrefUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void getLabelDetail(LabelDetail labelDetail, String URL) {
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements strongs = doc.select("strong");
            //病害数据
            Element td = strongs.get(0).parent().parent();
            Elements hrefs = td.select("a[href]");
            for (Element href : hrefs) {
                String detailURl = URL_PREFIX + href.attr("href");
                getCropDetail(labelDetail, detailURl, 1L);
            }
            //虫害数据
            Element td2 = strongs.get(1).parent().parent();
            Elements hrefs2 = td2.select("a[href]");
            for (Element href : hrefs2) {
                String detailURl = URL_PREFIX + href.attr("href");
                getCropDetail(labelDetail, detailURl, 2L);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCropDetail(LabelDetail labelDetail, String URL, long cropType) {
        try {
            Document doc = Jsoup.connect(URL).get();

            CropDetail cropDetail = new CropDetail();
            cropDetail.setId(UniqueIDUtil.getUniqueID());
            cropDetail.setLabelDetail(labelDetail.getId());
            cropDetail.setCropName(doc.select("#lblNameZHCN").text());
            cropDetail.setEnglishName(doc.select("#lblNameEng").text());
            cropDetail.setAnotherName(doc.select("#lblSynonyms").text());
            cropDetail.setIntroduction(doc.select("#lblIntroduction").text());
            cropDetail.setDamageSym(doc.select("#lblDamageSym").text());
            cropDetail.setPathogen(doc.select("#lblPathogen").text().replace("?", "c"));
            cropDetail.setCycle(doc.select("#lblCycle").text());
            cropDetail.setOccurrenceFactor(doc.select("#lblOFactor").text());
            cropDetail.setHandleMethod(doc.select("#lblCMethod").text());
            cropDetail.setMorphology(doc.select("#lblMorphology").text());
            cropDetail.setHabits(doc.select("#lblHabits").text());
            cropDetail.setCreateTime(new Date());
            cropDetail.setUpdateTime(new Date());
            cropDetail.setCropType(cropType);
            cropDetail.setCropImg(cropDetail.getId());
            cropDetailMapper.insertSelective(cropDetail);
            //插入图片
            Elements elements = doc.select("img[src^=UploadFiles]");
            for (Element element : elements) {
                CropDetailImg cropDetailImg = new CropDetailImg();
                cropDetailImg.setId(UniqueIDUtil.getUniqueID());
                cropDetailImg.setCreateTime(new Date());
                cropDetailImg.setUpdateTime(new Date());
                cropDetailImg.setCropId(cropDetail.getId());
                cropDetailImg.setPicUrl(URL_PREFIX + element.attr("src"));
                cropDetailImgMapper.insertSelective(cropDetailImg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
