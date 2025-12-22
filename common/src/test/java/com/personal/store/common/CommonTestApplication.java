package com.personal.store.common;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
@AutoConfigurationPackage(basePackages = "com.personal.store.common.domain")
public class CommonTestApplication {
}
