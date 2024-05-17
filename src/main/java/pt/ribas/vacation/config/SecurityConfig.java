package pt.ribas.vacation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.server.ResponseStatusException;

import pt.ribas.vacation.entity.Employee;
import pt.ribas.vacation.repository.EmployeeRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Value("${auth.admin.username}")
            private String adminUsername;

            @Value("${auth.admin.password}")
            private String adminPassword;

            @Autowired
            private EmployeeRepository employeeRepository;

            @Override
            public UserDetails loadUserByUsername(
                String username
            ) throws UsernameNotFoundException {
                if (username.equals(adminUsername)) {
                    return User.builder()
                        .username(adminUsername)
                        .password(adminPassword)
                        .roles("ADMIN")
                    .build();
                }

                Employee employee = employeeRepository
                    .findByEmail(username)
                .orElseThrow(
                    () -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Incorrect username or password"
                    )
                );

                return User.builder()
                    .username(employee.getEmail())
                    .password(employee.getPassword())
                    .roles(
                        switch (employee.getSupervisor()) {
                            case null -> "SUPERVISOR";
                            default -> "EMPLOYEE";
                        }
                    )
                .build();
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(
                requestRegistry -> requestRegistry
                    .requestMatchers("/register-employee").hasAnyRole("ADMIN")
                    .requestMatchers("/employee/all").hasAnyRole("ADMIN")
                    .requestMatchers("/get-supervisor-employees").hasAnyRole("SUPERVISOR")
                    .requestMatchers("/alter-vacation-request").hasAnyRole("ADMIN", "SUPERVISOR")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(
                sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
        .build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
