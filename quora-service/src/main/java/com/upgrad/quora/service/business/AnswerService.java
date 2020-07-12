package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity createAnswer(AnswerEntity answerEntity){

        return answerDao.createAnswer(answerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAnswer(AnswerEntity answerEntity){

         answerDao.updateAnswer(answerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity deleteAnswer(String answerId, String authToken){

        AnswerEntity answerToDelete = answerDao.getAnswerByUuid(answerId);
        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authToken);
//        System.out.println(answerId + "  from the answer service \n");
        UserEntity actioningUser = userDao.getUserByUuid((userAuthTokenEntity.getUuid()));
        String role = actioningUser.getRole();
        if(role.equals("admin") || answerToDelete.getUser().equals(actioningUser)){

            return answerDao.deleteAnswer(answerId);
        }else{
            return null;
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AnswerEntity> getAllAnswersToQuestion(String questionId, String authorization) throws AuthorizationFailedException, InvalidQuestionException {


        QuestionEntity question = questionDao.getUserByUuid(questionId);
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);

        if(userAuthToken == null){

            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");

        }

        if(userAuthToken.getLogoutAt() != null){

            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get the answers");
        }

        if(question == null){

            throw new InvalidQuestionException("QUES-001", "The question with entered uuid whose details are to be seen does not exist");
        }

        List<AnswerEntity> allAnswers = question.getAnswers();
        return allAnswers;


    }
}
