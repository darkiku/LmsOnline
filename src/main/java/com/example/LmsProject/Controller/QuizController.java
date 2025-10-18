package com.example.LmsProject.Controller;

import com.example.LmsProject.Dto.*;
import com.example.LmsProject.Entity.*;
import com.example.LmsProject.Repository.*;
import com.example.LmsProject.Service.CourseService;
import com.example.LmsProject.Service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private final CourseService courseService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Quiz> getQuizByCourse(@PathVariable Long courseId) {
        Quiz quiz = quizService.getQuizByCourseId(courseId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(@RequestBody QuizSubmissionDto submission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        QuizResultDto result = quizService.submitQuiz(submission, email);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check/{enrollmentId}")
    public ResponseEntity<Boolean> checkQuizPassed(@PathVariable Long enrollmentId) {
        boolean passed = quizService.hasPassedQuiz(enrollmentId);
        return ResponseEntity.ok(passed);
    }

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody CreateQuizDto dto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setPassingScore(dto.getPassingScore());

        Course course = courseService.getCourseById(dto.getCourseId());
        quiz.setCourse(course);

        Quiz saved = quizRepository.save(quiz);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/question/create")
    public ResponseEntity<Question> createQuestion(@RequestBody CreateQuestionDto dto) {
        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setPoints(dto.getPoints());

        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        question.setQuiz(quiz);

        Question saved = questionRepository.save(question);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/answer/create")
    public ResponseEntity<Answer> createAnswer(@RequestBody CreateAnswerDto dto) {
        Answer answer = new Answer();
        answer.setAnswerText(dto.getAnswerText());
        answer.setIsCorrect(dto.getIsCorrect());

        Question question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        answer.setQuestion(question);

        Answer saved = answerRepository.save(answer);
        return ResponseEntity.ok(saved);
    }
}