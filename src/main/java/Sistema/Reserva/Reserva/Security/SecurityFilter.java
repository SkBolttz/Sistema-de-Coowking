package Sistema.Reserva.Reserva.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Tag(name = "Configuração de segurança", description = "Configuração de segurança")
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    @Operation(summary = "Configuração de segurança")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = recuperarToken(request);

        if (token != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                DecodedJWT decodedJWT = JWT.require(algorithm)
                        .withIssuer("Controle de Reservas")
                        .build()
                        .verify(token);

                String usuario = decodedJWT.getSubject();
                String role = decodedJWT.getClaim("role").asString();

                if(usuario != null && role != null){
                    UserDetails userDetails = User.builder()
                    .username(usuario)
                    .password("")
                    .authorities("ROLE_" + role.toUpperCase())
                    .build();
                
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Erro ao validar token JWT: " + e.getMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Operation(summary = "Configuração de segurança")
    private String recuperarToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}


