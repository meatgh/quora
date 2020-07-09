package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "answer", schema = "public")
@NamedQueries(

        {
                @NamedQuery(name = "answerByUuid", query = "select a from AnswerEntity a where a.uuid = :uuid"),
                //@NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email= :email"),
                //@NamedQuery(name = "deleteById", query = "delete from UserEntity u where u.id= :id"),
                //@NamedQuery(name = "userById", query = "select u from UserEntity u where u.id= :id"),
                //@NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid")
                // @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid= :uuid")
               // @NamedQuery(name = "allAnswers", query = "select all from AnswerEntity all where all.question_id = :question_id")

        }
)
public class AnswerEntity implements Serializable {

    //id SERIAL,
    // uuid VARCHAR(200) NOT NULL,
    // ans VARCHAR(255) NOT NULL,
    // date TIMESTAMP NOT NULL ,
    // user_id INTEGER NOT NULL,
    // question_id INTEGER NOT NULL ,
    // PRIMARY KEY(id),
    // FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
    // FOREIGN KEY (question_id) REFERENCES QUESTION(id) ON DELETE CASCADE);

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "ANS")
    @NotNull
    @Size(max = 255)
    private String ans;

    @NotNull
    @Column(name = "DATE")
    private ZonedDateTime date;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionEntity question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

}

