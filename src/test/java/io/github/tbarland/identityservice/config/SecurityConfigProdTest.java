package io.github.tbarland.identityservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfigProd.class)
@ActiveProfiles("prod")
class SecurityConfigProdTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void livenessProbeIsPubliclyAccessible() throws Exception {
        mockMvc.perform(get("/actuator/health/liveness"))
                .andExpect(status().isNotFound()); // 404 = passed security, no controller
    }

    @Test
    void readinessProbeIsPubliclyAccessible() throws Exception {
        mockMvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isNotFound());
    }

    @Test
    void swaggerUiIsBlockedInProd() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void actuatorHealthIsBlockedInProd() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void openApiDocsAreBlockedInProd() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/some-protected-resource"))
                .andExpect(status().isUnauthorized());
    }
}

