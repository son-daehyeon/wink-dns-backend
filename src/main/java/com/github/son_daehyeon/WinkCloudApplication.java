package com.github.son_daehyeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.son_daehyeon.common.property.JwtProperty;
import com.github.son_daehyeon.common.property.RedisProperty;
import com.github.son_daehyeon.common.property.WinkProperty;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperty.class, RedisProperty.class, WinkProperty.class})
public class WinkCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinkCloudApplication.class, args);
    }
}
