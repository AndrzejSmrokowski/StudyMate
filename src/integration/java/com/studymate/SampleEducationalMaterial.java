package com.studymate;

import com.studymate.domain.educationalmaterial.Comment;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.MaterialStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface SampleEducationalMaterial {
    default EducationalMaterial getMaterialAboutBiology() {
        return EducationalMaterial.builder()
                .id("2")
                .title("Podstawy biologii")
                .description("Podstawowe informacje na temat biologii")
                .content("Tresc materialu edukacyjnego o biologii")
                .comments(new ArrayList<>())
                .status(MaterialStatus.APPROVED)
                .likes(15)
                .likedBy(new HashSet<>())
                .build();

    }

    default EducationalMaterial getExpectedMaterialAboutQuantumPhysics() {
        Comment comment = Comment.builder()
                .text("Bardzo ciekawy material!")
                .author("User1")
                .build();

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        return EducationalMaterial.builder()
                .id("1")
                .title("Wprowadzenie do fizyki kwantowej")
                .description("Krotkie wprowadzenie do podstaw fizyki kwantowej")
                .content("Tresc materialu edukacyjnego o fizyce kwantowej")
                .comments(comments)
                .status(MaterialStatus.APPROVED)
                .likes(10)
                .likedBy(new HashSet<>())
                .build();
    }
}
