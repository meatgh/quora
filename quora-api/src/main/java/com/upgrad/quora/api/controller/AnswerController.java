package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AnswerController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerDao answerDao;

    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@PathVariable("questionId") final String questionId, AnswerRequest answerRequest, @RequestHeader("authorization")final String authorization){

        AnswerEntity answerEntity = new AnswerEntity();
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);
        QuestionEntity question = questionDao.getUserByUuid(questionId);
        UserEntity user = userDao.getUserByUuid(userAuthToken.getUuid());

        answerEntity.setAns(answerRequest.getAnswer());
        answerEntity.setDate(ZonedDateTime.now());
        answerEntity.setQuestion(question);
        answerEntity.setUser(user);
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerService.createAnswer(answerEntity);

        AnswerResponse answerResponse = new AnswerResponse().id(answerEntity.getUuid()).status("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answerId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswerContent(@PathVariable("answerId") final String answerId, AnswerEditRequest answerEditRequest, @RequestHeader("authorization")final String authorization){

        AnswerEntity answerToEdit = answerDao.getAnswerByUuid(answerId);
        UserAuthTokenEntity userAuthToken = userDao.getUserAuthToken(authorization);
//        UserEntity user = userDao.getUserByUuid(userAuthToken.getUuid());
//        QuestionEntity question = answerToEdit.getQuestion();

        answerToEdit.setAns(answerEditRequest.getContent());
        //answerToEdit.setDate(ZonedDateTime.now());
        //answerToEdit.setQuestion(question);
        //answerToEdit.setUser(user);
        //answerToEdit.setUuid(answerId);
        answerService.updateAnswer(answerToEdit);

        AnswerEditResponse answerResponse = new AnswerEditResponse().id(answerToEdit.getUuid()).status("ANSWER EDITED");
        return new ResponseEntity<AnswerEditResponse>(answerResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@PathVariable("answerId")final String answerId, @RequestHeader("authorization")final String authorisation){

        AnswerEntity deletedAnswer = answerService.deleteAnswer(answerId, authorisation);
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse().id(deletedAnswer.getUuid()).status("ANSWER DELETED");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);

    }




}
