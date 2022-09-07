#!/bin/sh
cd /Users/join/gitme/myproject/iogame-example/example-springboot/spring-websocket-native-pb-client
protoc --java_out=src/main/java proto/common.proto
protoc --java_out=src/main/java proto/biz.proto