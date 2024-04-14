package ru.fp.participantservice.service.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static ru.fp.participantservice.service.auth.JWTService.USER_ROLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private static final String BEARER = "Bearer ";
    private static final String ROLE_PREFIX = "ROLE_";
    private final JWTService jwtService;

    public Optional<Authentication> authorize(HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<Authentication> verify(String token) {
        try {
            DecodedJWT jwt = jwtService.getJwtVerifier().verify(token);
            String issuer = jwt.getSubject();
            Claim role = jwt.getClaim("roles");
            Authentication authentication = createAuthentication(issuer, role.asString());

            return Optional.of(authentication);

        } catch (JWTDecodeException e) {
            return Optional.empty();

        } catch (Exception e) {
            log.warn("Unknown error while trying to verify JWT token", e);
            return Optional.empty();
        }
    }

    private static Authentication createAuthentication(String user, @NonNull String... roles) {
        List<GrantedAuthority> authorities = Stream.of(roles)
                .distinct()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(nonNull(user) ? user : "N/A", "N/A", authorities);
    }

    private static Optional<String> extractBearerTokenHeader(@NonNull HttpServletRequest request) {

        try {

            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorization != null) {

                if (authorization.startsWith(BEARER)) {
                    String token = authorization.substring(BEARER.length()).trim();

                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }

                }

            }
            return Optional.empty();

        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract bearer token", e);
            return Optional.empty();
        }
    }

}
