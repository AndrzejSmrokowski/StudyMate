package com.studymate.domain.educationalmaterial;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public
record EducationalMaterial(
        String id,
        String title,
        String description,
        String content,
        List<Comment> comments,
        MaterialStatus status,
        int likes,
        Set<String> likedBy
) {
    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
