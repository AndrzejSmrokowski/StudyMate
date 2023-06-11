package com.studymate.domain.user.dto;

import com.studymate.domain.progresstracking.Progress;
import com.studymate.domain.user.SimpleGrantedAuthority;
import lombok.Builder;

import java.util.Collection;

@Builder
public record UserData(
        String username,
        Progress progress,
        Collection<SimpleGrantedAuthority> authorities,
        String userEmail
) {

}
