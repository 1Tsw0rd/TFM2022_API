package tamna6.tfm2022.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry reg){
        reg.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PATCH");  //접근 허용할 주소
    }

}

/*
CORS 설정
@CrossOrigin 사용

addMapping : CORS 적용할 URL 패턴 정의, /**은 API서버의 모든 API 접근 허용
allowedOrigins : 접근 허용할 주소
allowedHeaders : 허용할 Method 설정 ex)allowedMethods("GET", "POST");
allowedMethods
* */
