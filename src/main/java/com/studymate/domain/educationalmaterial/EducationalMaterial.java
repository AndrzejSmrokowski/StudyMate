package com.studymate.domain.educationalmaterial;

import lombok.Builder;

import java.util.List;

@Builder
record EducationalMaterial(
        String id,
        String title,
        String description,
        String content,
        List<Comment> comments,
        MaterialStatus status,
        int likes
) {
    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
