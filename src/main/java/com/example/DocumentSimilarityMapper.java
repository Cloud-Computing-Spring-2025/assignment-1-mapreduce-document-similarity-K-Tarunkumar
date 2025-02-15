package com.example;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class DocumentSimilarityMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split("\t", 2);  
        
        if (parts.length < 2) return;

        String docID = parts[0];  // Document ID
        String content = parts[1].toLowerCase().replaceAll("[^a-zA-Z]", " "); 
        
        StringTokenizer tokenizer = new StringTokenizer(content);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            context.write(new Text(word), new Text(docID));
        }
    }
}
