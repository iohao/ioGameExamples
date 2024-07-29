## 游戏网关（Broker）

### 打 jar 包

#### 1 在 broker pom 中添加下述配置
> 进入 SpringBootExample/spring-game-broker 目录，在 pom.xml 中添加下述配置

```xml
    <build>
        <plugins>
            <!--
            broker 打 jar 包
            -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.iohao.game.spring.broker.GameBrokerApplication</mainClass>
                    <layout>JAR</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

#### 2 打 jar 包
> 使用终端进入 SpringBootExample/spring-game-broker 目录，输入下述命令。
```shell
mvn package
```

命令执行后，会生成 target/spring-game-broker-1.0-SNAPSHOT.jar

#### 3 注意事项
不使用时，将步骤1 中的 pom 注释，否则在打单体包时会有冲突。

以上是单独打游戏网关（Broker）的 jar 包步骤