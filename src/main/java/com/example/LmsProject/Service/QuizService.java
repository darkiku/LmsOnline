package com.example.LmsProject.Service;

import com.example.LmsProject.Dto.QuizResultDto;
import com.example.LmsProject.Dto.QuizSubmissionDto;
import com.example.LmsProject.Entity.*;
import com.example.LmsProject.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizAttemptsRepository quizAttemptsRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;

    public Quiz getQuizByCourseId(Long courseId) {
        System.out.println("Looking for quiz with courseId: " + courseId);

        Quiz quiz = quizRepository.findByCourseId(courseId)
                .orElseThrow(() -> {
                    System.err.println("Quiz not found for course: " + courseId);
                    return new RuntimeException("Quiz not found for course: " + courseId);
                });

        System.out.println("Quiz found: " + quiz.getTitle());
        System.out.println("Questions: " + (quiz.getQuestions() != null ? quiz.getQuestions().size() : 0));

        return quiz;
    }

    public QuizResultDto submitQuiz(QuizSubmissionDto submission, String studentEmail) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Enrollment enrollment = enrollmentRepository.findById(submission.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Quiz quiz = quizRepository.findById(submission.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        int score = 0;
        int totalPoints = 0;

        for (Question question : quiz.getQuestions()) {
            totalPoints += question.getPoints();
            Long selectedAnswerId = submission.getAnswers().get(question.getId());

            if (selectedAnswerId != null) {
                Answer answer = answerRepository.findById(selectedAnswerId).orElse(null);
                if (answer != null && answer.getIsCorrect()) {
                    score += question.getPoints();
                }
            }
        }

        int percentage = (score * 100) / totalPoints;
        boolean passed = percentage >= quiz.getPassingScore();

        QuizAttempts attempt = new QuizAttempts();
        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setEnrollment(enrollment);
        attempt.setScore(score);
        attempt.setPassed(passed);
        attempt.setAttemptDate(LocalDateTime.now());
        quizAttemptsRepository.save(attempt);

        if (passed) {
            enrollment.setCompleted(true);
            enrollment.setCompletedAt(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        String message = passed
                ? "🎉 Congratulations! You passed the quiz with " + percentage + "% and completed the course!"
                : "😔 You didn't pass. Required: " + quiz.getPassingScore() + "%, You got: " + percentage + "%. Try again!";

        return new QuizResultDto(score, totalPoints, passed, message);
    }

    public boolean hasPassedQuiz(Long enrollmentId) {
        return quizAttemptsRepository.findByEnrollmentIdAndPassed(enrollmentId, true).isPresent();
    }
}