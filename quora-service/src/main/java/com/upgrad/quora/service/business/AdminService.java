package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AdminDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity userDelete(String userId, String authToken){

        UserEntity userEntity = userDao.getUserByUuid(userId);
        //System.out.println(userEntity + "\n") ;
        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authToken);
        //System.out.println(userId + "  from the admin service \n") ;
        UserEntity actionEntity = userDao.getUserByUuid((userAuthTokenEntity.getUuid()));
        String role = actionEntity.getRole();

        if(role.equals("admin")){

            adminDao.deleteUser(userId);
            System.out.println(userId + " after the call to delete is made \n ") ;
            return userEntity;
        }else{

            return null;
        }

    }

}

