package com.studymate.cache.redis;

import com.studymate.BaseIntegrationTest;
import com.studymate.DataProviderForIntegrationTests;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class RedisEducationalMaterialCacheIntegrationTest extends BaseIntegrationTest implements DataProviderForIntegrationTests {

    @Container
    private static final GenericContainer<?> REDIS;

    @SpyBean
    EducationalMaterialFacade educationalMaterialFacade;

    @Autowired
    CacheManager cacheManager;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void shouldSaveEducationalMaterialsToCacheAndThenInvalidateByTimeToLive() throws Exception {
        // Step 1: User sends a POST request to /api/users/register with registration data such as username, password, and the system registers a new user.
        // given & when
        ResultActions registerAction = mockMvc.perform(post("/api/users/register")
                .content("""
                        {
                        "username": "username",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        registerAction.andExpect(status().isCreated());

        // Step 2: User sends a POST request to /api/users/login with login credentials (i.e., username or email, password), and the system authenticates the user and returns a JWT token.
        // given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/api/users/login")
                .content("""
                        {
                        "username": "username",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult mvcResult = successLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(json, JwtResponseDto.class);
        String token = jwtResponse.token();

        // Step 3: User should save educational materials to cache
        // given & when
        educationalMaterialFacade.createEducationalMaterial(getExpectedMaterialAboutQuantumPhysics());
        educationalMaterialFacade.createEducationalMaterial(getMaterialAboutBiology());

        // Step 4: User makes a GET request to /educational-content with header "Authorization: Bearer AAAA.BBBB.CCC" and the system returns OK(200) with educational materials
        // given & when
        ResultActions performGetForTwoMaterials = mockMvc.perform(get("/api/educational-content")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult performGetForTwoMaterialsMvcResult = performGetForTwoMaterials.andExpect(status().isOk()).andReturn();
        String jsonWithTwoMaterials = performGetForTwoMaterialsMvcResult.getResponse().getContentAsString();
        List<EducationalMaterial> twoMaterials = objectMapper.readValue(jsonWithTwoMaterials, objectMapper.getTypeFactory().constructCollectionType(List.class, EducationalMaterial.class));
        assertThat(twoMaterials).hasSize(2);
        EducationalMaterial expectedFirstMaterial = getExpectedMaterialAboutQuantumPhysics();
        EducationalMaterial expectedSecondMaterial = getMaterialAboutBiology();

        assertThat(twoMaterials).containsExactlyInAnyOrder(
                new EducationalMaterial(expectedFirstMaterial.id(), expectedFirstMaterial.title(), expectedFirstMaterial.description(), expectedFirstMaterial.content(), expectedFirstMaterial.comments(), expectedFirstMaterial.status(), expectedFirstMaterial.likes(), expectedFirstMaterial.likedBy()),
                new EducationalMaterial(expectedSecondMaterial.id(), expectedSecondMaterial.title(), expectedSecondMaterial.description(), expectedSecondMaterial.content(), expectedSecondMaterial.comments(), expectedSecondMaterial.status(), expectedSecondMaterial.likes(), expectedSecondMaterial.likedBy())
        );

        // Step 5: Wait for the cache to be invalidated
        await().atMost(Duration.ofSeconds(2)).untilAsserted(() -> {
            ResultActions performGetAfterInvalidate = mockMvc.perform(get("/api/educational-content")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            );
            performGetAfterInvalidate.andExpect(status().isOk());
            verify(educationalMaterialFacade, atLeast(2)).getEducationalMaterials();
        });
    }
}