package com.example.javaapplicationtest.mockito;

import com.example.javaapplicationtest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
