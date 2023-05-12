package com.technomarket.technomarket.repository;

import com.technomarket.technomarket.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
