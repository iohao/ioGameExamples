package com.iohao.example.cluster.broker.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * k8s示例集群
 *
 * @author fangwei
 * @date 2022/10/31
 */
@SpringBootApplication
public class ExampleClusterBrokerNacosApplication {

    public static final String CLIENT_NAME = "gameBrokerClient";

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ExampleClusterBrokerNacosApplication.class, args);
        final GameBrokerClient client = context.getBean(CLIENT_NAME, GameBrokerClient.class);
        client.create();
    }

}
