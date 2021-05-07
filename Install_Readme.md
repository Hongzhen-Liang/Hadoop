Win10安装使用Hadoop3.0.0:https://blog.csdn.net/songhaifengshuaige/article/details/79575308
Ubuntu下Hadoop安装: https://blog.csdn.net/qq_36561697/article/details/80994405
	* https://blog.csdn.net/lglglgl/article/details/80553828
	1. 创建hadoop用户:
		```
		$ sudo useradd -m hadoop -s /bin/bash  #创建hadoop用户，并使用/bin/bash作为shell
		$ sudo passwd hadoop                   #为hadoop用户设置密码，之后需要连续输入两次密码
		$ sudo adduser hadoop sudo             #为hadoop用户增加管理员权限
		$ su - hadoop                          #切换当前用户为用户hadoop
		$ sudo apt-get update                  #更新hadoop用户的apt,方便后面的安装
		```
	2. 安装ssh，设置ssh无密码登录
		```
		$ sudo apt-get install openssh-server   #安装SSH server
		$ ssh localhost                         #登陆SSH，第一次登陆输入yes
		$ exit                                  #退出登录的ssh localhost
		$ cd ~/.ssh/                            #如果没法进入该目录，执行一次ssh localhost
		$ ssh-keygen -t rsa　					# 输入完后三次回车确认
		$ cat ./id_rsa.pub >> ./authorized_keys #加入授权
		$ ssh localhost                         #此时已不需密码即可登录localhost，并可见下图。如果失败则可以搜索SSH免密码登录来寻求答案
		```
	3. 安装JDK：参考java安装教程
	4. 安装hadoop3.2.1
		```
		$ sudo tar -zxvf  hadoop-3.2.1.tar.gz -C /usr/local    #解压到/usr/local目录下
		$ cd /usr/local
		$ sudo mv  hadoop-3.2.1    hadoop                      #重命名为hadoop
		$ sudo chown -R hadoop ./hadoop                        #修改文件权限
		$ export HADOOP_HOME=/usr/local/hadoop				   #给hadoop配置环境变量
		$ export CLASSPATH=$($HADOOP_HOME/bin/hadoop classpath):$CLASSPATH
		$ export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
		$ export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
		$ source ~/.bashrc									   #激活bash
		$ hadoop version									   #查看hadoop是否安装成功
		```
	5. hadoop设置
		1. 伪分布式设置:
			* hadoop配置文件在/usr/local/hadoop/etc/hadoop中
			1. hadoop-env.sh:
				```
				export JAVA_HOME=/opt/jdk-15.1 #设置java依赖
				```
			2. core-site.xml:
				```
				<configuration>
					<property>
						 <name>hadoop.tmp.dir</name>
						 <value>file:/usr/local/hadoop/tmp</value>
						 <description>Abase for other temporary directories.</description>
					</property>
					<property>
						 <name>fs.defaultFS</name>
						 <value>hdfs://localhost:9000</value>
					</property>
				</configuration>
				```
			3. hdfs-site.xml:
				```
				<configuration>
					<property>
						 <name>dfs.replication</name>
						 <value>1</value>
					</property>
					<property>
						 <name>dfs.namenode.name.dir</name>
						 <value>file:/usr/local/hadoop/tmp/dfs/name</value>
					</property>
					<property>
						 <name>dfs.datanode.data.dir</name>
						 <value>file:/usr/local/hadoop/tmp/dfs/data</value>
					</property>
				</configuration>
				```
			4. yarn-site.xml:
				```
				<configuration>
					<property>
						<name>yarn.nodemanager.aux-services</name>
						<value>mapreduce_shuffle</value>
						<description>YARN 集群为 MapReduce 程序提供的 shuffle 服务</description>
					</property>
				</configuration>
				```
			
			4. Hadoop 的运行方式是由配置文件决定的（运行 Hadoop 时会读取配置文件），因此如果需要从伪分布式模式切换回非分布式模式，需要删除 core-site.xml 中的配置项。此外，伪分布式虽然只需要配置 fs.defaultFS 和 dfs.replication 就可以运行（可参考官方教程），不过若没有配置 hadoop.tmp.dir 参数，则默认使用的临时目录为 /tmp/hadoo-hadoop，而这个目录在重启时有可能被系统清理掉，导致必须重新执行 format 才行。所以我们进行了设置，同时也指定 dfs.namenode.name.dir 和 dfs.datanode.data.dir，否则在接下来的步骤中可能会出错。
			5. 配置完成后，执行 NameNode 的格式化:`./bin/hdfs namenode -format`
			6. 启动namenode和datanode进程，并查看启动结果:
				* hadoop地址: /usr/local/hadoop
				* 错误解决方案:
					* ssh: connect to host localhost port 22: Connection refused: 重启ssh:sudo service ssh restart
				```
				$ ./sbin/start-dfs.sh
				$ jps
				```
			7. web界面查看NameNode 和 Datanode 信息
				1. Hadoop3:http://localhost:9870/
				2. Hadoop2:http://localhost:50070
			8. 暂停hadoop:`sbin/stop-all.sh`
			9. 重置hadoop:
				1. 需要删除`<name>dfs.datanode.data.dir</name>`下的文档
				2. 格式化:`./bin/hdfs namenode -format`
				3. 开启服务:`./sbin/start-dfs.sh`
	
1. win10 需要手动导入winutils:https://last2win.com/windows-install-hadoop/#winutils

2. Java:
	* pom:
	```
	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>Hadoop</artifactId>
    <version>1.0-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>15</source>
                    <target>15</target>
                </configuration>
            </plugin>
			<plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    <dependencies>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>3.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-hdfs</artifactId>
            <version>3.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-client</artifactId>
            <version>3.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-mapreduce-client-core</artifactId>
            <version>3.2.1</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
	</project>
	
	
	```











