package InitialTutorial;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class WordCountCombine extends Reducer<Text,IntWritable,Text,IntWritable> {
    public Text write_key = new Text();
    public IntWritable write_value = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException{
        int sum=0;
        for (IntWritable value:values){
            sum+=value.get();
        }
        context.write(key,new IntWritable(sum));
    }
}
