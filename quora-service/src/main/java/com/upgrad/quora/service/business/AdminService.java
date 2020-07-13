package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AdminDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
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
    public UserEntity userDelete(String userId, String authorization) throws AuthorizationFailedException, UserNotFoundException {

        UserEntity userEntity = userDao.getUserByUuid(userId);
        //System.out.println(userEntity + "\n") ;
        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authorization);
//        System.out.println(userId + "  from the admin service \n") ;
        UserEntity actionEntity = userDao.getUserByUuid((userAuthTokenEntity.getUuid()));
        String role = actionEntity.getRole();

        if(userAuthTokenEntity.getAccessToken() == null){

            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");

        } else if(userAuthTokenEntity.getLogoutAt() != null){

            throw new AuthorizationFailedException("ATHR-002", "User is signed out");

        } else if(role.equals("admin") == false){

            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");

        }

        if(userEntity == null){

            throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");


        }else{

            adminDao.deleteUser(userId);
//            System.out.println(userId + " after the call to delete is made \n ") ;
            return userEntity;

        }

    }

}

