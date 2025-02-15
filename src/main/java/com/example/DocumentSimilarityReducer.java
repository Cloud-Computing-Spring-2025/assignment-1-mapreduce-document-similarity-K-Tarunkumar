package com.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class DocumentSimilarityReducer extends Reducer<Text, Text, Text, Text> {

    private final Map<String, Set<String>> documentWordsMap = new HashMap<>();

    @Override
    public void reduce(Text documentId, Iterable<Text> wordSets, Context context) throws IOException, InterruptedException {
        Set<String> wordSet = new HashSet<>();
        
        for (Text word : wordSets) {
            wordSet.addAll(Arrays.asList(word.toString().split(",")));
        }
        
        documentWordsMap.put(documentId.toString(), wordSet);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        List<String> documentIds = new ArrayList<>(documentWordsMap.keySet());

        for (int i = 0; i < documentIds.size(); i++) {
            for (int j = i + 1; j < documentIds.size(); j++) {
                String doc1 = documentIds.get(i);
                String doc2 = documentIds.get(j);

                Set<String> wordsDoc1 = documentWordsMap.get(doc1);
                Set<String> wordsDoc2 = documentWordsMap.get(doc2);

                // Compute Jaccard Similarity
                Set<String> intersection = new HashSet<>(wordsDoc1);
                intersection.retainAll(wordsDoc2);

                Set<String> union = new HashSet<>(wordsDoc1);
                union.addAll(wordsDoc2);

                if (union.isEmpty()) continue;

                double similarity = (double) intersection.size() / union.size();
                String similarityOutput = String.format("Similarity between %s and %s: %.2f", doc1, doc2, similarity);

                // Print similarity for each document pair
                System.out.println(similarityOutput);

                // Only emit pairs with similarity > 0.5
                if (similarity > 0.5) {
                    context.write(new Text(doc1 + ", " + doc2), new Text(similarityOutput));
                }
            }
        }
    }
}
