package hello.core; // basePackages default

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages = "hello.core", // 탐색할 패키지 시작위치 지정
        // basePackageClasses = AutoAppConfig.class,
        // 패키지 위치를 특정하지 않고 설정 정보 클래스의 위치를 프로젝트 최상단에 위치하는 것을 권장
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
