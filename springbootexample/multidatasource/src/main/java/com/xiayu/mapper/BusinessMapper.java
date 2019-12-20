package com.xiayu.mapper;

import com.xiayu.entity.AddressEntity;
import com.xiayu.entity.BusinessEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BusinessMapper {

    String columnList = " ID,BUSINESS_DETAIL ";

    @Insert("insert into t_business(" +columnList+") values(#{id},#{businessDetail})")
    boolean insertBusiness(BusinessEntity businessEntity);

    @Select("select " + columnList + "from t_business where id = #{id}")
    AddressEntity getBusinessById(int id);
}
