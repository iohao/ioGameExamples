package com.iohao.example.cluster.broker.nacos;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.iohao.game.bolt.broker.server.BrokerServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 游戏代理客户端
 *
 * @author fangwei
 * @date 2022/10/31
 */
@Slf4j
@Component
public class GameBrokerClient {

    @Resource
    GameBrokerBoot gameBrokerBoot;

    @Resource
    private NacosServiceManager nacosServiceManager;

    @Value("${spring.application.name}")
    private String app;

    /**
     * 核心就是从nacos拿到 broker的地址（pod地址是不固定的）
     */
    public void create() {
        List<String> ips = getSeedAddressFromNacos();
        final BrokerServer brokerServer = gameBrokerBoot.createBrokerServer(ips);
        brokerServer.startup();
    }

    /**
     * 从nacos得到种子地址
     *
     * @return {@link List}<{@link String}>
     */
    @SneakyThrows
    private List<String> getSeedAddressFromNacos() {
        NamingService namingService = nacosServiceManager.getNamingService();
        List<Instance> allInstances = namingService.getAllInstances(app);
        return allInstances.stream().map(Instance::getIp).collect(Collectors.toList());
    }
}
