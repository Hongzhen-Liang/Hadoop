import mapper_reducer.MaxTemperatureDriver;
import mapper_reducer.MaxTemperatureMapper;
import mapper_reducer.MaxTemperatureReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MaxTemperatureMapperTest {

    @Test
    public void processesValidRecord() throws IOException,InterruptedException{
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                                  // Year ^^^^
                "99999V0203201N00261220001CN9999999N9-00111+99999999999");
                                  // Temperature ^^^^^

        new MapDriver<LongWritable,Text,Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0),value)
                .withOutput(new Text("1950"),new IntWritable(-11))
                .runTest();

    }

    @Test
    public void ignoreMissingTemperatureRecord() throws IOException{
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                // Year ^^^^
                "99999V0203201N00261220001CN9999999N9+99991+99999999999");
        // Temperature ^^^^^

        new MapDriver<LongWritable,Text,Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0),value)
                .runTest();
    }

    /**
     * Reducer的测试.
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void returnsMaximumIntegerInValues() throws IOException,InterruptedException{
        new ReduceDriver<Text,IntWritable,Text,IntWritable>()
                .withReducer(new MaxTemperatureReducer())
                .withInput(new Text("1950"), Arrays.asList(new IntWritable(10),new IntWritable(5)))
                .withOutput(new Text("1950"),new IntWritable(10))
                .runTest();
    }

    /**
     * MaxTemperatureDriver测试.
     */
    @Test
    public  void test() throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        conf.set("mapreduce.framework.name","local");
        conf.setInt("mapreduce.task.io.sort.mb",1);
        Path input=new Path("input/ncdc/micro");
        Path output = new Path("output");

        FileSystem fs = FileSystem.getLocal(conf);
        fs.delete(output,true); // delete old output

        MaxTemperatureDriver driver = new MaxTemperatureDriver();
        driver.setConf(conf);

        int exitCode = driver.run(new String[]{
                input.toString(), output.toString()
        });
        assertThat(exitCode,is(0));
    }

}
