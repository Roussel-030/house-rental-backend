package io.github.roussel030.module.auth.service.jwtService;

import io.github.roussel030.module.user.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    @ConfigProperty(name = "jwt.expire-in")
    int expire;

    @ConfigProperty(name = "jwt.verify.issuer")
    String issuer;

    @Override
    public String generateToken(User user) {
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole().name());

        return Jwt.issuer(issuer)
                .upn(user.getEmail())
                .groups(roles)
                .claim("userId", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .expiresIn(expire)
                .sign();
    }
}
