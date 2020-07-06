package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {


    @PersistenceContext
    EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity) {

        entityManager.persist(userEntity);
        return userEntity;
    }

    public UserEntity getUserByUsername(final String username) {

        try {
            return entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("userName", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserEntity getUserByEmail(final String email) {

        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserEntity getUserById(final Integer userId) {

        try {
            return entityManager.createNamedQuery("userById", UserEntity.class).setParameter("id", userId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserEntity getUserByUuid(final String Uuid) {

        try {

            System.out.println("in the try block of user dao - getUserByUuid, before excectuion of Named query");
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", Uuid).getSingleResult();

        } catch (NoResultException nre) {
            System.out.println("in the catch block of getUserByUuid - userDao");
            return null;
        }
    }

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public UserAuthTokenEntity getUserAuthToken(final String accessToken){
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken",
                    UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }

    }

    public void updateUser(final UserEntity updatedUserEntity){ entityManager.merge(updatedUserEntity); }

    public void updateAuthToken(final UserAuthTokenEntity userAuthTokenEntity){ entityManager.merge(userAuthTokenEntity); }



}