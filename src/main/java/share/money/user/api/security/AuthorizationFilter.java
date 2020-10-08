package share.money.user.api.security;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import share.money.user.api.repository.UserRepository;
import share.money.user.api.repository.entity.UserEntity;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, Environment environment) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(environment.getProperty("authorization.token.header.name"));
        if (header == null) return null;

        String token = header.replace(environment.getProperty("authorization.token.header.prefix"), "");

        String userId = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (userId != null) {

            UserEntity userEntity = userRepository.findByUserId(userId);
            UserPrincipal userPrincipal = new UserPrincipal(userEntity);
            return new UsernamePasswordAuthenticationToken(userId, null, userPrincipal.getAuthorities());
        }
        return null;
    }
}