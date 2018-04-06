package com.forestWolf.luceneGraduation.service;

import com.forestWolf.luceneGraduation.pojo.CropDetail;
import com.forestWolf.luceneGraduation.pojo.Label;
import com.forestWolf.luceneGraduation.pojo.LabelDetail;
import com.forestWolf.luceneGraduation.response.ServerResponse;

import java.util.List;

public interface InfoService {
    /**
     * 重新抓取信息
     *
     * @return
     */
    boolean getInfoMation();

    List<Label> getLabelView ();

    List<LabelDetail> getLabelDetail(long id);

    List<CropDetail> getTypeView(long id);

    CropDetail getTypeDetail(long id);

    boolean bulidIndex();

    List<CropDetail> searchIndex(String wd);

    List<CropDetail> profSearchIndex(String wd,int no);
}
