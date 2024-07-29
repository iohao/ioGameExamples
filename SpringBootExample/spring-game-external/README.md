## 游戏对外服

### 打 jar 包

#### 1 在 external pom 中添加下述配置
> 进入 SpringBootExample/spring-game-external 目录，在 pom.xml 中添加下述配置

```xml
    <build>
        <plugins>
            <!--
            external 打 jar 包
            -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.iohao.game.spring.external.GameExternalApplication</mainClass>
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
> 使用终端进入 SpringBootExample/spring-game-external 目录，输入下述命令。
```shell
mvn package
```

命令执行后，会生成 target/spring-game-external-1.0-SNAPSHOT.jar

#### 3 注意事项
不使用时，将步骤1 中的 pom 注释，否则在打单体包时会有冲突。

以上是单独打游戏对外服的 jar 包步骤