# 拉取jdk8作为基础镜像
FROM openjdk:17.0.2-oracle
# 作者
MAINTAINER double_joker <iogame@iohao.com>
# 添加jar到镜像并命名为 one.jar
ADD ./target/spring-z-one-game-3.0.0.io-jar-with-dependencies.jar my-one-game.jar
# 镜像启动后暴露的端口
EXPOSE 10100
# jar运行命令，参数使用逗号隔开
ENTRYPOINT ["java","-jar","my-one-game.jar"]
