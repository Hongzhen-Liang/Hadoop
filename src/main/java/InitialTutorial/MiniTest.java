package InitialTutorial;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


class MiniMapper extends Mapper<Text, Text,Text,Text> {
    public Text write_key = new Text();
    public Text write_value = new Text();
    @Override
    protected void map(Text key,Text value,Context context) throws IOException, InterruptedException{
        //设置map写的(key,value)
        write_key.set(key);
        write_value.set(value);
        //使用context.write方法将结果写到本地
        context.write(write_key,write_value);
    }
}

//Combine是一个局部的Reduce，实现最终Reduce的部分功能，例如本地去重，本地计数等，所以Combine实质上就是一个Reduce
class MiniCombine extends Reducer<Text,Text,Text,Text>{
    public Text write_key=new Text();
    public Text write_value = new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
        for(Text value:values){
            write_key = key;
            write_value = value;
            context.write(write_key,write_value);
        }
    }
}

class MiniReduce extends Reducer<Text,Text,Text,Text> {
    public Text write_key = new Text();
    public Text write_value = new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException,InterruptedException{
        for(Text value:values){
            write_key = key;
            write_value=value;
            context.write(write_key,write_value);
        }
    }
}


