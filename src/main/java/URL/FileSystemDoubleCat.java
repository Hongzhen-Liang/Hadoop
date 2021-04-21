package URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

/**
 * 展示seek()移动绝对位置的能力，让文件输出两次.
 */
public class FileSystemDoubleCat {
    public static void main(String[] args) throws Exception{
        String uri = "hdfs://localhost:9000/usr/sinscry/test.txt";
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem"); //配置访问hdfs
        FileSystem fs = FileSystem.get(URI.create(uri),conf);
        FSDataInputStream in = fs.open(new Path(uri));
        IOUtils.copyBytes(in,System.out,4096,false);
        in.seek(0);//go back to start.
        IOUtils.copyBytes(in,System.out,4096,false);
        IOUtils.closeStream(in);
    }
}
