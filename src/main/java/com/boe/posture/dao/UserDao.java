package com.boe.posture.dao;


import com.boe.posture.domain.AssessmentResultBO;
import com.boe.posture.domain.UserInfoBO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserDao {

    //查询列表
    List<UserInfoBO> getList(String code);
    //查询详情
    AssessmentResultBO getInfo(Integer id);

    void addInfo(Map<String, Object> map);
}
