package com.example.manager.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.security.util.JWTUtil;
import com.example.manager.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.manager.security.util.SecurityConstants;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authToken != null && !authToken.isBlank() && authToken.startsWith(SecurityConstants.BEARER_HEADER_TOKEN)){
            String jwt = authToken.substring(SecurityConstants.JWT_START_INDEX);

            if (jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid jwt token");
            } else {
                try {
                    int id = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetailsInfo userDetails = new UserDetailsInfo(userService.findUserById(id));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().print("Unauthorized");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
