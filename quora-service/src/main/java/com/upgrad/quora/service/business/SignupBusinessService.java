package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) throws SignUpRestrictedException {

        //Entities creation using the username & email address given by the user at the time of the sign up - for restriction check
        UserEntity emailCheckEntity = userDao.getUserByEmail(userEntity.getEmail());
        UserEntity usernameCheckEntity = userDao.getUserByUsername(userEntity.getUserName());

        //using the created entities for a duplication check, if it is not null, it means there's already a user record with that email address
        if( emailCheckEntity != null ){ throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId"); }
        //using the created entities for a duplication check, if it is not null, it means there's already a user record with that username
        if( usernameCheckEntity != null ){  throw new SignUpRestrictedException("SGR-001","Try any other Username, this Username has already been taken"); }

        //After all the checks are done, finally, creating and signing up the user
        String[] encryptedText = cryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setPassword(encryptedText[1]);
        userEntity.setSalt(encryptedText[0]);
        return userDao.createUser(userEntity);



    }

    @Transactional
    public void updateUserDetails(UserEntity userEntity){

        userDao.updateUser(userEntity);

    }

}
