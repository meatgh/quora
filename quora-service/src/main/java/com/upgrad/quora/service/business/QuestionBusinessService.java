package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionBusinessService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity newQuestion(String authorization,String content) throws AuthorizationFailedException {
        final UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);

        if(userAuthToken==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
         if(userAuthToken.getLogoutAt()!=null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to post a question");
        }
        else {
            QuestionEntity questionEntity = new QuestionEntity();
            questionEntity.setContent(content);
            questionEntity.setDate(new Date());
            questionEntity.setUuid(UUID.randomUUID().toString());
            UserEntity userEntity = userAuthToken.getUser();
            questionEntity.setUser(userEntity);
            return questionDao.newQuestion(questionEntity);
        }
    }

    public List<QuestionEntity> getQuestions(final String authorization) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);
        if(userAuthToken==null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        else {
            if ( ZonedDateTime.now().compareTo(userAuthToken.getExpiresAt())>=0 && userAuthToken.getLogoutAt() != null) {
                throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get all questions");
            }
            List<QuestionEntity> question = questionDao.getQuestion();
            return question;
        }
    }

     public List<QuestionEntity> questionByUser(final String authorization,final String userId) throws AuthorizationFailedException, UserNotFoundException {
         UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);


         if(userAuthToken == null){
             throw new AuthorizationFailedException("ATHR-001","User has not signed in");

         }
         else if ( ZonedDateTime.now().compareTo(userAuthToken.getExpiresAt())>=0 && userAuthToken.getLogoutAt() != null) {
             throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get all questions");
         }
         else if(userDao.getUserByUuid(userId) == null){
             throw new UserNotFoundException("USR-001","User with entered uuid whose question details are to be seen does not exist");
         }
         else {
             return questionDao.questionByUser(userId);
         }

     }

    //getAllQuestionsByUser
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity getQuestionId(String questionId,String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);
        QuestionEntity questionEntity = questionDao.questionId(questionId);
        if(userAuthToken == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        else if(userAuthToken.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to edit the question");
        }
        else if (questionEntity == null){
            throw new InvalidQuestionException("QUES-001","Entered question uuid does not exist");
        }
        else if(userAuthToken.getUser().getId()!=questionEntity.getUser().getId()){
            throw new AuthorizationFailedException("ATHR-003","Only the question owner can edit the question");
        }
        else {
            return questionEntity;
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity editQuestion(QuestionEntity editedQuestion){
          questionDao.editQuestion(editedQuestion);
          return editedQuestion;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity DeleteQuestion(final String questionId, final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity = questionDao.questionId(questionId);
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);
        if(userAuthToken == null){
             throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        else if(userAuthToken.getLogoutAt()!=null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to delete a question");
        }
        else if(questionDao.questionId(questionId) == null){
            throw new InvalidQuestionException("QUES-001","Entered question uuid does not exist");
        }
        else if(userAuthToken.getUser().getRole() == "nonadmin" || userAuthToken.getUser().getId()!=questionEntity.getUser().getId()){
            throw new AuthorizationFailedException("ATHR-003","Only the question owner or admin can delete the question");
        }

        else {
            questionDao.deleteQuestion(questionId);
            return questionEntity;
        }
    }
}