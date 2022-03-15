/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mingqi;

import com.mingqi.annotation.rest.AnonymousGetMapping;
import com.mingqi.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppRun {
    private static Logger logger = LoggerFactory.getLogger(AppRun.class);
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        //SpringApplication.run(AppRun.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppRun.class, args);
        printSystemInfo(applicationContext);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }
    private static void printSystemInfo(ApplicationContext applicationContext) {
        AppRun.applicationContext = applicationContext;
        Environment environment = applicationContext.getEnvironment();
        String port = environment.getProperty("server.port");
        String context = environment.getProperty("server.servlet.context-path");
        String ip = "localhost";
        String systemIndexUrl = "http://" + ip + ":" + port + context;
////        System.out.printf("\n>>>>>>>>>>>>>>>>>>系统首页：%s >>>>>>>>\n", systemIndexUrl);
////        String swaggerApiUrl = systemIndexUrl + "swagger-ui.html";
        String swaggerBootStrapApiUrl = systemIndexUrl + "doc.html";
////        System.out.printf(">>>>>>>>>>>>>>>>>>>>接口文档地址（原生swaggerUI）：%s >>>>>>>>>\n", swaggerApiUrl);
////        System.out.printf(">>>>>>>>>>>>>>>>>>>>接口文档地址2(bootstrap美化过，推荐使用)：%s >>>>>>>>>\n", swaggerBootStrapApiUrl);
        String druidAccessUrl = systemIndexUrl + "druid";
////        System.out.printf(">>>>>>>>>>>>>>>>>>>>druid数据监控中心访问url：%s >>>>>>>>>\n", druidAccessUrl);
        logger.info(">>>>>>>>>>>>>>>>>>>>接口文档地址2(bootstrap美化过，推荐使用)：%s >>>>>>>>>\n");
        logger.info(swaggerBootStrapApiUrl);
        logger.info(">>>>>>>>>>>>>>>>>>>>druid数据监控中心访问url：%s >>>>>>>>>\n");
        logger.info(druidAccessUrl);
    }
    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
