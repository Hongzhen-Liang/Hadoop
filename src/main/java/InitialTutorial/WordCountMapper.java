package InitialTutorial;



import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;


import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    public Text write_key = new Text();
    public IntWritable write_value = new IntWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        String[] words = StringUtils.split(value.toString(), ' ');
        for(int i =0;i<words.length;i++){
            String word=words[i];
            write_key.set(word);
            write_value.set(1);
            context.write(write_key,write_value);
        }
    }
}
