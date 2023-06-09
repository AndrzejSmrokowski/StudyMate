package com.studymate.domain.admin;

import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import com.studymate.domain.user.SimpleGrantedAuthority;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.UserRepository;
import com.studymate.domain.user.dto.RegisterUserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@AllArgsConstructor
@Component
public class AdministrationFacade {
    private final UserRepository userRepository;
    private final EducationalMaterialFacade educationalMaterialFacade;
    private final UserManagementFacade userManagementFacade;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public void approveEducationalMaterial(String materialId) {
        educationalMaterialFacade.approveMaterial(materialId);
    }

    public void rejectEducationalMaterial(String materialId) {
        educationalMaterialFacade.rejectMaterial(materialId);
    }

    @PostConstruct
    public void createAdmin() {
        if (!userManagementFacade.existsByUsername("admin")) {
            RegisterUserDto adminUserDto = new RegisterUserDto("admin", "password");
            userManagementFacade.registerUser(adminUserDto);
            List<SimpleGrantedAuthority> adminAuthorities = List.of(new SimpleGrantedAuthority("ADMIN"));
            User user = userManagementFacade.findUserByUsername("admin");
            User admin = User.builder()
                    .username(user.username())
                    .password(user.password())
                    .authorities(adminAuthorities)
                    .userId(user.userId())
                    .build();
            userManagementFacade.updateUserData(admin);
        }
    }
}
