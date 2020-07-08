package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

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
}
