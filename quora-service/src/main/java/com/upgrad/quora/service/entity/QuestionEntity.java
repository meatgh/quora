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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question", schema = "public")
@NamedQueries(

        {
                @NamedQuery(name = "questionByUuid", query = "select q from QuestionEntity q where q.uuid = :uuid"),
                //@NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email= :email"),
                //@NamedQuery(name = "deleteById", query = "delete from UserEntity u where u.id= :id"),
                //@NamedQuery(name = "userById", query = "select u from UserEntity u where u.id= :id"),
                //@NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid")
                // @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid= :uuid")
                @NamedQuery(name="getPostedQuestion",query = "SELECT c FROM QuestionEntity c"),
                @NamedQuery(name="getQuestionByUser",query = "SELECT q FROM QuestionEntity q where q.user.uuid=:uuid"),
                @NamedQuery(name="getQuestionId",query = "SELECT qd FROM QuestionEntity qd where qd.uuid= :uuid")

        }
)
public class QuestionEntity implements Serializable {

    //id SERIAL,
    // uuid VARCHAR(200) NOT NULL,
    // content VARCHAR(500) NOT NULL,
    // date TIMESTAMP NOT NULL ,
    // user_id INTEGER NOT NULL,
    // PRIMARY KEY(id),
    // FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Size(max = 200)
    @Column(name = "UUID")
    private String uuid;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<AnswerEntity> answers = new ArrayList<>();

    @Column(name = "CONTENT")
    @NotNull
    @Size(max = 500)
    private String content;

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    @Column(name = "date")
    @NotNull
    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;



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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
