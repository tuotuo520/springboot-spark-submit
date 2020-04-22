package com.taihe.springbootsparksubmit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author SUNHUI
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement(proxyTargetClass=true)
public class SpringbootSparkSubmitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSparkSubmitApplication.class, args);
    }

}
