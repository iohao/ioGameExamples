#!/bin/sh
cd /Users/join/gitme/myproject/ioGame-example-springboot/spring-client-native-pb
protoc --java_out=src/main/java proto/common.proto
protoc --java_out=src/main/java proto/biz.proto