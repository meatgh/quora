package com.upgrad.quora.service.entity;

public class AnswerEntity {

    //id SERIAL,uuid VARCHAR(200) NOT NULL, ans VARCHAR(255) NOT NULL,date TIMESTAMP NOT NULL , user_id INTEGER NOT NULL, question_id INTEGER NOT NULL , PRIMARY KEY(id), FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE, FOREIGN KEY (question_id) REFERENCES QUESTION(id) ON DELETE CASCADE);
}
