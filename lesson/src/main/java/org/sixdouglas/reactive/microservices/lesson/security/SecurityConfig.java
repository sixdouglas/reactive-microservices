package org.sixdouglas.reactive.microservices.lesson.security;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

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
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .pathMatchers(HttpMethod.HEAD, "/api/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/annees").hasAuthority("SCOPE_add:annee")
                        .pathMatchers(HttpMethod.DELETE, "/api/annees").hasAuthority("SCOPE_delete:annee")
                        .pathMatchers(HttpMethod.PUT, "/api/annees").hasAuthority("SCOPE_update:annee")
                        .pathMatchers(HttpMethod.GET, "/api/annees").hasAuthority("SCOPE_read:annee")

                        .pathMatchers(HttpMethod.POST, "/api/diplomes").hasAuthority("SCOPE_add:diplome")
                        .pathMatchers(HttpMethod.DELETE, "/api/diplomes").hasAuthority("SCOPE_delete:diplome")
                        .pathMatchers(HttpMethod.PUT, "/api/diplomes").hasAuthority("SCOPE_update:diplome")
                        .pathMatchers(HttpMethod.GET, "/api/diplomes").hasAuthority("SCOPE_read:diplome")

                        .pathMatchers(HttpMethod.POST, "/api/domaines").hasAuthority("SCOPE_add:domaine")
                        .pathMatchers(HttpMethod.DELETE, "/api/domaines").hasAuthority("SCOPE_delete:domaine")
                        .pathMatchers(HttpMethod.PUT, "/api/domaines").hasAuthority("SCOPE_update:domaine")
                        .pathMatchers(HttpMethod.GET, "/api/domaines").hasAuthority("SCOPE_read:domaine")

                        .pathMatchers(HttpMethod.POST, "/api/matieres").hasAuthority("SCOPE_add:matiere")
                        .pathMatchers(HttpMethod.DELETE, "/api/matieres").hasAuthority("SCOPE_delete:matiere")
                        .pathMatchers(HttpMethod.PUT, "/api/matieres").hasAuthority("SCOPE_update:matiere")
                        .pathMatchers(HttpMethod.GET, "/api/matieres").hasAuthority("SCOPE_read:matiere")

                        .pathMatchers(HttpMethod.POST, "/api/semestres").hasAuthority("SCOPE_add:semestre")
                        .pathMatchers(HttpMethod.DELETE, "/api/semestres").hasAuthority("SCOPE_delete:semestre")
                        .pathMatchers(HttpMethod.PUT, "/api/semestres").hasAuthority("SCOPE_update:semestre")
                        .pathMatchers(HttpMethod.GET, "/api/semestres").hasAuthority("SCOPE_read:semestre")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
                .build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE", "POST", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Collections.singletonList("authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
