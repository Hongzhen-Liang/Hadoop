# Hadoop教程
0. 运行教程:
    1. 地址：/usr/local/hadoop
    2. 运行: ./sbin/start-dfs.sh
    3. 测试: jps
    4. 暂停: sbin/stop-all.sh
    5. 重置hadoop:
        0. 重启ssh:`sudo service ssh restart`
        1. 需要删除`<name>dfs.datanode.data.dir</name>`下的文档
        2. 格式化:`./bin/hdfs namenode -format`
        3. 开启服务:`./sbin/start-dfs.sh`

1. 常见指令
    1. 创建文件: `hadoop fs -mkdir -p /usr/sinscry/books/`  (-p创建未存在的文件夹) 
    2. 查看结构: 
        1. hadoop: `hadoop fs -ls /`
        2. 本地系统: `hadoop fs -ls file:///`
    3. 复制文件: 
    		* 本地到hadoop: `hadoop fs -copyFromLocal 生成数量.py  hdfs://localhost/usr/sinscry/生成数量.py`
    		* hadoop到本地: `hadoop fs -copyToLocal /usr/sinscry/生成数量.py ./`
2. 类
    1. URL: URL读取数据
        1. URLCat: java.net.URL对象打开数据流，从中读取数据
        2. FileSystemCat: 通用文件api实现显示hadoop文件数据
        3. FileSystemDoubleCat: 展示seek()移动绝对位置的能力，让文件输出两次
        4. FileCopyWithProgress: 本地文件复制到Hadoop 文件系统
    2. mapper_reducer: 查找最低气温
        * resource/ncdc/micro:部分气温数据
        1. MaxTemperatureMapper: mapper
        2. MaxTemperatureReducer:reducer
        3. MaxTemperatureDriver: 运行程序
        