package com.xiayu.mapper;

import com.xiayu.entity.AddressEntity;
import com.xiayu.message.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MessageMapper {

    String columnList = " basename,locale_lan,k,v ";

    @Insert("insert into t_message(" +columnList+") values(#{basename},#{locale_lan},#{k},#{v})")
    boolean insertMessage(Message message);

    @Select("select " + columnList + "from t_message where basename = #{basename} and locale_lan = #{language}")
    AddressEntity getMessage(String basename,String language);
}
