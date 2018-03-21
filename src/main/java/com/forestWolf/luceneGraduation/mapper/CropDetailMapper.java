package com.forestWolf.luceneGraduation.mapper;

import com.forestWolf.luceneGraduation.pojo.CropDetail;
import com.forestWolf.luceneGraduation.pojo.CropDetailExample;
import java.util.List;

import com.forestWolf.luceneGraduation.pojo.CropDetailExamplePage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CropDetailMapper {
    int countByExample(CropDetailExample example);

    int deleteByExample(CropDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CropDetail record);

    int insertSelective(CropDetail record);

    List<CropDetail> selectByExample(CropDetailExample example);

    List<CropDetail> selectByExampleByPage(CropDetailExamplePage example);

    CropDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CropDetail record, @Param("example") CropDetailExample example);

    int updateByExample(@Param("record") CropDetail record, @Param("example") CropDetailExample example);

    int updateByPrimaryKeySelective(CropDetail record);

    int updateByPrimaryKey(CropDetail record);
}