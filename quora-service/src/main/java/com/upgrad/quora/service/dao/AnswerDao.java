package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    public AnswerEntity createAnswer(AnswerEntity answer){

        entityManager.persist(answer);
        return answer;
    }

    public AnswerEntity getAnswerByUuid(String uuid){

        try {

            return entityManager.createNamedQuery("answerByUuid", AnswerEntity.class).setParameter("uuid", uuid).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }

    public void updateAnswer(final AnswerEntity updatedAnswer){ entityManager.merge(updatedAnswer); }

    public AnswerEntity deleteAnswer(String answerId){

        try {
            AnswerEntity deletedAnswer = getAnswerByUuid(answerId);
            entityManager.remove(deletedAnswer);
            return deletedAnswer;
        } catch (NoResultException nre ) {
            return null;
        }

    }
}
