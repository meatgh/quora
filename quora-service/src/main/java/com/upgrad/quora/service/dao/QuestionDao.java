package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {

    @PersistenceContext
    EntityManager entityManager;

    public QuestionEntity createQuestion(QuestionEntity createdQustion){

        entityManager.persist(createdQustion);
        return createdQustion;
    }

    public QuestionEntity getUserByUuid(final String Uuid) {

        try {

//            System.out.println("in the try block of user dao - getUserByUuid, before excectuion of Named query");
            return entityManager.createNamedQuery("questionByUuid", QuestionEntity.class).setParameter("uuid", Uuid).getSingleResult();

        } catch (NoResultException nre) {
            //System.out.println("in the catch block of getUserByUuid - QuestionDao");
            return null;
        }
    }

    public QuestionEntity updateQuestion(QuestionEntity question){
        return entityManager.merge(question);
    }
}
