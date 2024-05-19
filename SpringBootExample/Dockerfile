# 拉取 jdk 作为基础镜像
FROM openjdk:21-jdk-oracle
# 作者
MAINTAINER 渔民小镇 <262610965@qq.com>
# 添加 jar 到镜像
ADD ./spring-z-one-game/target/spring-z-one-game-1.0-SNAPSHOT.jar .
# 镜像启动后暴露的端口
EXPOSE 10100
# jar运行命令，参数使用逗号隔开
ENTRYPOINT ["java","-jar","spring-z-one-game-1.0-SNAPSHOT.jar"]