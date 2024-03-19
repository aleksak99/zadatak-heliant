package rs.heliant.zadatak.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.heliant.zadatak.configuration.CustomUserDetailsService;
import rs.heliant.zadatak.service.JwtService;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.isBlank(bearerToken)) {
            log.debug("Token ne postoji.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        if (!bearerToken.startsWith("Bearer ")) {
            log.debug("Format tokena nije validan. Token: {}", bearerToken);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        String jwt = bearerToken.substring(7);
        String korisnickoIme;
        try {
            korisnickoIme = jwtService.getUsername(jwt);
        } catch (ExpiredJwtException e) {
            log.debug("Token je istekao. Token: {}", jwt);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if (korisnickoIme != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(korisnickoIme);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/v1/login");
    }
}
