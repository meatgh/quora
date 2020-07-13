package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public QuestionEntity newQuestion(final QuestionEntity questionEntity){
        entityManager.persist(questionEntity);
        return questionEntity;

    }

    public List<QuestionEntity> getQuestion(){
        try {
            return entityManager.createNamedQuery("getPostedQuestion", QuestionEntity.class)
                    .getResultList();
        }
        catch (Exception nre) {
            return null;
        }
    }

    public List<QuestionEntity> questionByUser(String id){
        try {
            return entityManager.createNamedQuery("getQuestionByUser", QuestionEntity.class).setParameter("uuid", id).getResultList();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public QuestionEntity questionId(String uuid){
        try {
            return entityManager.createNamedQuery("getQuestionId", QuestionEntity.class).setParameter("uuid", uuid)
                    .getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public void editQuestion(QuestionEntity updatedQuestion){
        entityManager.merge(updatedQuestion);
    }

    public QuestionEntity deleteQuestion(String deleteQuestionId){
        QuestionEntity questionEntity = questionId(deleteQuestionId);
        try {
            entityManager.remove(questionEntity);
            return questionEntity;
        }
        catch (NoResultException nre){
            return null;
        }
    }


}
