## 网关在k8s上使用nacos获取种子列表 启动集群网关

在个例子使用的是springcloud的nacos版本，因为项目里有中台需要访问逻辑服。如果不想用springcloud 可以选择springboot版本的nacos  代码api大同小异


该服务打包成镜像后启动两个以上副本 你会发现他们互相发现了自己


![复本](./fb.png)
![控制台](./jt.png)