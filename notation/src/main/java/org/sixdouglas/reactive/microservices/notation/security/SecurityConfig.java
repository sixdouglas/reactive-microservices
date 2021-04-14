package org.sixdouglas.reactive.microservices.notation.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()

                        .pathMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .pathMatchers(HttpMethod.HEAD, "/api/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/devoirs").hasAuthority("SCOPE_add:devoir")
                        .pathMatchers(HttpMethod.DELETE, "/api/devoirs").hasAuthority("SCOPE_delete:devoir")
                        .pathMatchers(HttpMethod.PUT, "/api/devoirs").hasAuthority("SCOPE_update:devoir")
                        .pathMatchers(HttpMethod.GET, "/api/devoirs").hasAuthority("SCOPE_read:devoir")

                        .pathMatchers(HttpMethod.POST, "/api/notes").hasAuthority("SCOPE_add:note")
                        .pathMatchers(HttpMethod.DELETE, "/api/notes").hasAuthority("SCOPE_delete:note")
                        .pathMatchers(HttpMethod.PUT, "/api/notes").hasAuthority("SCOPE_update:note")
                        .pathMatchers(HttpMethod.GET, "/api/notes").hasAuthority("SCOPE_read:note")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
                .build();
    }

    ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        jwtDecoder.setClaimSetConverter(new PermissionsSubClaimAdapter());

        return jwtDecoder;
    }
}
