package runJava;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URL;

public class URLCat {
    public static void main(String[] args) throws Exception{
        URL.setURLStreamHandlerFactory( new  FsUrlStreamHandlerFactory());
        String uri = "hdfs://localhost:9000/usr/sinscry/test.txt";
        InputStream in=null;
        try{
//            in = new URL(args[0]).openStream();
            in = new URL(uri).openStream();
            IOUtils.copyBytes(in,System.out,4096,false);
        }finally {
            IOUtils.closeStream(in);
        }
    }
}
