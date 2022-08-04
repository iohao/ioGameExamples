#!/bin/sh
cd /Users/join/gitme/myproject/example-iogame/example-springboot/spring-websocket-native-pb-client
protoc --java_out=src/main/java proto/common.proto
protoc --java_out=src/main/java proto/biz.proto