package tamna6.tfm2022.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/swagger-ui/*", "/api/*") // "/favicon.ico"
                .antMatchers(HttpMethod.GET, "/api/teamlist")
                .antMatchers(HttpMethod.POST, "/api/signup", "/api/logincheck", "/api/authcheck", "/api/teamdetail")
                .antMatchers(HttpMethod.PATCH, "/api/logoutcheck")
                ;
    } //정적인 웹페이지 접근 허용
}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .csrf().disable()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
//                .authorizeRequests()
//                .antMatchers("/api/authCheck").permitAll()
//                .antMatchers("/api/logincheck").permitAll()
//                .antMatchers("/api/logoutCheck").permitAll()
//                .antMatchers("/api/signup").permitAll()
//                .antMatchers("/api/teamdetail").permitAll()
//                .antMatchers("/api/teamlist").permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .formLogin().disable();
//
//        return http.build();
//    }
//}

//                .authorizeRequests() //HttpServeltRequest 사용하는 요청들에 대한 접근제한 설정하겠다는 의미
//                .antMatchers("/api/hello").permitAll() //인증없이 접근 허용
//                .anyRequest().authenticated(); //나머지 요청들은 인증되어야 함

