package com.forestWolf.luceneGraduation.mapper;

import com.forestWolf.luceneGraduation.pojo.LabelDetail;
import com.forestWolf.luceneGraduation.pojo.LabelDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface LabelDetailMapper {
    int countByExample(LabelDetailExample example);

    int deleteByExample(LabelDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LabelDetail record);

    int insertSelective(LabelDetail record);

    List<LabelDetail> selectByExample(LabelDetailExample example);

    LabelDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LabelDetail record, @Param("example") LabelDetailExample example);

    int updateByExample(@Param("record") LabelDetail record, @Param("example") LabelDetailExample example);

    int updateByPrimaryKeySelective(LabelDetail record);

    int updateByPrimaryKey(LabelDetail record);
}