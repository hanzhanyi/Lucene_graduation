package com.forestWolf.luceneGraduation.mapper;

import com.forestWolf.luceneGraduation.pojo.CropDetailImg;
import com.forestWolf.luceneGraduation.pojo.CropDetailImgExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CropDetailImgMapper {
    int countByExample(CropDetailImgExample example);

    int deleteByExample(CropDetailImgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CropDetailImg record);

    int insertSelective(CropDetailImg record);

    List<CropDetailImg> selectByExample(CropDetailImgExample example);

    CropDetailImg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CropDetailImg record, @Param("example") CropDetailImgExample example);

    int updateByExample(@Param("record") CropDetailImg record, @Param("example") CropDetailImgExample example);

    int updateByPrimaryKeySelective(CropDetailImg record);

    int updateByPrimaryKey(CropDetailImg record);
}