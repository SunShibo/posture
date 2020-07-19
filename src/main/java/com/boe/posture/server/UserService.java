package com.boe.posture.server;

import com.boe.posture.dao.UserDao;
import com.boe.posture.domain.AssessmentResultBO;
import com.boe.posture.domain.UserInfoBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    @Resource
    private UserDao userDao;

    //查询列表
    public List<UserInfoBO> getList(String userKey) {
        List<UserInfoBO> list = userDao.getList(userKey);
        return list;
    }

    //查询详情
    public AssessmentResultBO getInfo(Integer id) {
        return userDao.getInfo(id);
    }

    public void addInfo(Map<String, Object> map) {
        userDao.addInfo(map);
    }
}
