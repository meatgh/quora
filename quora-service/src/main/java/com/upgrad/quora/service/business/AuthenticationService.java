package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity signin(final String username, final String password) throws AuthenticationFailedException {

        UserEntity userEntity = userDao.getUserByUsername(username);
        if(userEntity == null){
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }

        final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());

        if(encryptedPassword.equals(userEntity.getPassword())){

            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setUuid(userEntity.getUuid());
            //userAuthToken.setLogoutAt(now);

            userDao.createAuthToken(userAuthToken);
            userDao.updateUser(userEntity);
            return userAuthToken;

        }else{
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity signout(final String userAuthToken){

        UserAuthTokenEntity signedoutAuthToken = userDao.getUserAuthToken(userAuthToken);
        signedoutAuthToken.setLogoutAt(ZonedDateTime.now());
        userDao.updateAuthToken(signedoutAuthToken);
        return signedoutAuthToken;

    }





}
