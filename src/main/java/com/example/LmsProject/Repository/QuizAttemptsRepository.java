package com.example.LmsProject.Repository;

import com.example.LmsProject.Entity.QuizAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempts, Long> {
    Optional<QuizAttempts> findByEnrollmentIdAndPassed(Long enrollmentId,boolean passed);
}
