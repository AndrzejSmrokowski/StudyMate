package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.CommentData;

public class CommentMapper {
    public static Comment mapToComment(CommentData data) {
        return Comment.builder()
                .text(data.text())
                .author(data.author())
                .build();
    }
}
