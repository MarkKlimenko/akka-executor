package systems.vostok.executor.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

@SpringBootApplication
class Application extends SpringBootServletInitializer {
    @Override
    SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(Application.class)
    }

    static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args)
    }
}
