package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void join(UserVo vo) {
	userDao.insert( vo );
    }

    @Transactional
    public UserVo getUser(UserVo vo) {
	return userDao.get( vo );
    }
    
}
