package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import com.group10.contestPlatform.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// using for quiz_question table
@Getter @Setter
public class QuizQuestionQuery {
    private Long id;
    private String imgurl;
    private String content;
    private Float score;

    public QuizQuestionQuery(Long id, String imgurl, String content, Float score) {
        this.id = id;
        this.imgurl = imgurl;
        this.content = content;
        this.score = score;
    }

        public QuizQuestionQuery(Question question) {
        this.id = question.getId();
        this.imgurl = question.getImgURI();
        this.content = question.getContent();
        this.score = question.getScore();
    }

    @Override
    public String toString() {
        return "QuizQuestionQuery{" +
                "id=" + id +
                ", imgurl='" + imgurl + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                '}';
    }
}
