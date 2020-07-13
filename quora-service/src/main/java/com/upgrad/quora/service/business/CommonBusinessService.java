package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.CommonDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonBusinessService {

    @Autowired
    private CommonDao commonDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity getUserDetails(final String uuid, final String authorization) throws UserNotFoundException, AuthorizationFailedException {
        final UserAuthTokenEntity userAuthToken = commonDao.getUserAuthToken(authorization);
        final UserEntity userEntity = commonDao.getUserDetails(uuid);
        if(userAuthToken == null){
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else if(userAuthToken.getLogoutAt()!=null){
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
        }
        else if (userEntity == null) {
                throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
            }
        else {
            return userEntity;
        }
    }
}


