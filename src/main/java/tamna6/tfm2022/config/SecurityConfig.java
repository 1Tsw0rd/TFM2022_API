package tamna6.tfm2022.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authCheck").permitAll()
                .antMatchers("/api/logincheck").permitAll()
                .antMatchers("/api/logoutCheck").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/teamdetail").permitAll()
                .antMatchers("/api/teamlist").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable();
        return http.build();
    }
}

