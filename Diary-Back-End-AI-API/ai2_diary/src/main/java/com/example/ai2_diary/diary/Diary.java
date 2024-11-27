package com.example.ai2_diary.diary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "preview_image", columnDefinition = "TEXT")
    private String imgUrl;

    @Builder
    public Diary(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
