package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

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

