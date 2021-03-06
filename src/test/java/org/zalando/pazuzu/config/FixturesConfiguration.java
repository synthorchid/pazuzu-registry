package org.zalando.pazuzu.config;

import static org.mockito.Mockito.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.zauth.api.ZAuth;


@Configuration
@Profile("test")
public class FixturesConfiguration {

    @Bean
    public ZAuth zAuth() {
        ZAuth mock = mock(ZAuth.class);

        when(mock.getCurrentLogin()).thenReturn("@test_user");
        return mock;
    }
}
