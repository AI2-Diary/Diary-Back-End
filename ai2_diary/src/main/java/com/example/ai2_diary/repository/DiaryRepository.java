package com.example.ai2_diary.repository;

import com.example.ai2_diary.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
