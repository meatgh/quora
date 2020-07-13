package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class QuestionController {
    @Autowired
    private QuestionBusinessService questionBusinessService;

    @RequestMapping(method = RequestMethod.POST,path = "/question/create",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        final QuestionEntity newQuestion = questionBusinessService.newQuestion(authorization,questionRequest.getContent());
        QuestionResponse response = new QuestionResponse().id(newQuestion.getUuid()).status("QUESTION CREATED");

        return new ResponseEntity<QuestionResponse>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/question/all",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") String authorization) throws AuthorizationFailedException {
        List<QuestionEntity> questionsList = questionBusinessService.getQuestions(authorization);
        List<QuestionDetailsResponse> questionDetailsResponse = new ArrayList<>();
        for(QuestionEntity questions : questionsList){
            questionDetailsResponse.add(new QuestionDetailsResponse().content(questions.getContent()).id(questions.getUuid()));
        }
        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponse,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/question/edit/{questionId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestionContent(final QuestionEditRequest questionEditRequest,@PathVariable("questionId") final String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity question1 = questionBusinessService.getQuestionId(questionId,authorization);
        String content = question1.getContent();
        question1.setUser(question1.getUser());
        if(content == null){
            question1.setContent(question1.getContent());
        }
        else{
            question1.setContent(questionEditRequest.getContent());
        }
        question1.setDate(new Date());
        QuestionEntity questionEntity1 = questionBusinessService.editQuestion(question1);
        QuestionEditResponse question_edited = new QuestionEditResponse().id(questionEntity1.getUuid()).status("QUESTION EDITED");

        return new ResponseEntity<QuestionEditResponse>(question_edited,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE,path  ="/question/delete/{questionId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion (@PathVariable("questionId") String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity = questionBusinessService.DeleteQuestion(questionId, authorization);
        QuestionDeleteResponse questionDeleted = new QuestionDeleteResponse().id(questionEntity.getUuid()).status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleted,HttpStatus.OK);
    }

   @RequestMapping(method = RequestMethod.GET,path="question/all/{userId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestionsByUser(@PathVariable("userId") String id,@RequestHeader("authorization") String authorization) throws AuthorizationFailedException, UserNotFoundException {
       List<QuestionEntity> allQuestionsByUser = questionBusinessService.questionByUser(authorization, id);
       List<QuestionDetailsResponse>  questionListByUser = new ArrayList();
       for(QuestionEntity allQuestions : allQuestionsByUser){
           questionListByUser.add(new QuestionDetailsResponse().id(allQuestions.getUuid()).content(allQuestions.getContent()));
       }
       return new ResponseEntity<List<QuestionDetailsResponse>>(questionListByUser,HttpStatus.OK);

   }

}
