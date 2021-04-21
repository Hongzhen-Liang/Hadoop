0. 教程
	0. Win10安装使用Hadoop3.0.0：https://blog.csdn.net/songhaifengshuaige/article/details/79575308
	1. map reduce combine简易教程: https://www.jianshu.com/p/0f83097c0c9e
		* Map：处理原始数据，为数据打标签，对数据进行分发
		* Combine：局部的Reduce，本地聚合单个Map的数据后传递给Reduce，减少网络中IO开销和Reduce的压力
		* Reduce：对Map打好标签的数据执行具体的计算

1. maven依赖
	1. Hadoop:
		```
		<dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-core</artifactId>
            <version>1.2.1</version>
        </dependency>
		```