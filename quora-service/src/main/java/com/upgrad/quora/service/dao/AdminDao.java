package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AdminDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    public UserEntity deleteUser(String userId){

        //UserEntity deletedEntity = userDao.getUserById(userId);
        //System.out.println(userId +"from Admin dao, first line");
        UserEntity deletedEntity = userDao.getUserByUuid(userId);
        //System.out.println(userId + " admin dao second print statement");

        try {

             entityManager.remove(deletedEntity);
             return deletedEntity;

        } catch (NoResultException nre) {
            return null;
        }

    }


}
