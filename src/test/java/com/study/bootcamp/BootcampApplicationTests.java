package com.study.bootcamp;

import com.study.bootcamp.answer.Answer;
import com.study.bootcamp.answer.AnswerRepository;
import com.study.bootcamp.question.Question;
import com.study.bootcamp.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BootcampApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("[Question] - save 메서드")
    @Test
    void testJpaQuestion() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);  // 두번째 질문 저장
    }

    @DisplayName("[Question] - findAll 메서드")
    @Test
    void testFindAllQuestion() {
        List<Question> all = questionRepository.findAll();
        assertEquals(4, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @DisplayName("[Question] - findById 메서드")
    @Test
    void testFindByIdQuestion() {
        Optional<Question> oq = questionRepository.findById(1);
        if (oq.isPresent()) {
            Question question = oq.get();
            assertEquals("sbb가 무엇인가요?", question.getSubject());
        }

    }

    @DisplayName("[Question] - findBySubject 메서드")
    @Test
    void testFindBySubjectQuestion() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(7, q.getId());
    }

    @DisplayName("[Question] - findBySubjectAndContent 메서드")
    @Test
    void testFindBySubjectAndContentQuestion() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(7, q.getId());
    }

    @DisplayName("[Question] - findBySubjectLike 메서드")
    @Test
    void testFindBySubjectLikeQuestion() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @DisplayName("[Question] - update 메서드")
    @Test
    void testUpdateQuestion() {
        Optional<Question> oq = questionRepository.findById(0);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
    }

    @DisplayName("[Question] - delete 메서드")
    @Test
    void testDeleteQuestion() {
        assertEquals(3, questionRepository.count());
        Optional<Question> oq = questionRepository.findById(9);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
        assertEquals(2, questionRepository.count());
    }

    @DisplayName("[Answer] - save 메서드")
    @Test
    void testSaveAnswer() {
        Optional<Question> oq = questionRepository.findById(7);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }

    @Transactional
    @DisplayName("[Answer, Question] - 질문에서 답변찾기")
    @Test
    void testFindAnswerFromQuestion() {
        Optional<Question> oq = questionRepository.findById(7);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
