package edu.mfvp.encryptpassword.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        var attribute = request.getHeader(HEADER_ATTRIBUTE);
        if (attribute == null || !attribute.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        var token = attribute.replace(TOKEN_PREFIX, "");

        var authenticationToken = getAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token)
            throws UnsupportedEncodingException {
        var user = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD))
                .build()
                .verify(token).getSubject();
        return new UsernamePasswordAuthenticationToken(user, null, List.of());
    }
}
