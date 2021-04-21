package URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

public class FileCopyWithProgress {
    public static void main(String[] args) throws Exception {
//        String localSrc=args[0];
//        String dst = args[1];
        String localSrc = "./quangle.txt";
        String dst = "hdfs://localhost:9000/usr/sinscry/tt.txt";
        InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
        FileSystem fs= FileSystem.get(URI.create(dst),new Configuration());
        OutputStream out = fs.create(new Path(dst), new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in,out,4096,true);
    }
}
