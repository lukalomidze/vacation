package pt.ribas.vacation.interceptor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.ribas.vacation.entity.Admin;
import pt.ribas.vacation.repository.AdminRepository;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String auth = Optional
            .ofNullable(request.getHeader("Authorization"))
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Include username and password in Authorization header with the format username:password"
            )
        );

        if (!auth.matches(".+:.+")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Include username and password in Authorization header with the format username:password"
            );
        }

        String username = auth.split(":")[0];
        String password = auth.split(":")[1];

        Admin admin = adminRepository
            .findByUsername(username)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Incorrect username or password"
            )
        );

        if(!encoder.matches(password, admin.getPassword())) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Incorrect username or password"
            );
        }

        return true;
    }
}
