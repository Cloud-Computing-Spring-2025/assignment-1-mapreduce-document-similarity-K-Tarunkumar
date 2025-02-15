[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18028214&assignment_repo_type=AssignmentRepo)
### **📌 Document Similarity Using Hadoop MapReduce**  

#### **Objective**  
The goal of this assignment is to compute the **Jaccard Similarity** between pairs of documents using **MapReduce in Hadoop**. You will implement a MapReduce job that:  
1. Extracts words from multiple text documents.  
2. Identifies which words appear in multiple documents.  
3. Computes the **Jaccard Similarity** between document pairs.  
4. Outputs document pairs with similarity **above 50%**.  

---

### **📥 Example Input**  

You will be given multiple text documents. Each document will contain several words. Your task is to compute the **Jaccard Similarity** between all pairs of documents based on the set of words they contain.  

#### **Example Documents**  

##### **doc1.txt**  
```
hadoop is a distributed system
```

##### **doc2.txt**  
```
hadoop is used for big data processing
```

##### **doc3.txt**  
```
big data is important for analysis
```

---

# 📏 Jaccard Similarity Calculator

## Overview

The Jaccard Similarity is a statistic used to gauge the similarity and diversity of sample sets. It is defined as the size of the intersection divided by the size of the union of two sets.

## Formula

The Jaccard Similarity between two sets A and B is calculated as:

```
Jaccard Similarity = |A ∩ B| / |A ∪ B|
```

Where:
- `|A ∩ B|` is the number of words common to both documents
- `|A ∪ B|` is the total number of unique words in both documents

## Example Calculation

Consider two documents:
 
**doc1.txt words**: `{hadoop, is, a, distributed, system}`
**doc2.txt words**: `{hadoop, is, used, for, big, data, processing}`

- Common words: `{hadoop, is}`
- Total unique words: `{hadoop, is, a, distributed, system, used, for, big, data, processing}`

Jaccard Similarity calculation:
```
|A ∩ B| = 2 (common words)
|A ∪ B| = 10 (total unique words)

Jaccard Similarity = 2/10 = 0.2 or 20%
```

## Use Cases

Jaccard Similarity is commonly used in:
- Document similarity detection
- Plagiarism checking
- Recommendation systems
- Clustering algorithms

## Implementation Notes

When computing similarity for multiple documents:
- Compare each document pair
- Output pairs with similarity > 50%

### **📤 Expected Output**  

The output should show the Jaccard Similarity between document pairs in the following format:  
```
(doc1, doc2) -> 60%  
(doc2, doc3) -> 50%  
```

# document-similarity

## Prerequisites
Ensure you have the following software installed:

- **Java 8+**
- **Apache Hadoop (v3.2.1 or later)**
- **Apache Maven** (for building the project, if needed)

## Project Structure
```
document-similarity/
│-- src/main/java/com/example/
│   │-- DocumentSimilarityDriver.java       # Main driver class
│   │-- DocumentSimilarityCombiner.java    # Combiner for document similarity
│   │-- DocumentSimilarityMapper.java      # Mapper for document similarity
│   │-- DocumentSimilarityReducer.java     # Reducer for document similarity
│   │-- JaccardSimilarityReducer.java      # Reducer for Jaccard Similarity
│-- dataset/
│   ├── document_data.txt                  # Sample dataset
│-- pom.xml                                # Maven build file
│-- README.md                              # Project documentation
```
### 1. **DocumentSimilarityDriver.java**
The **DocumentSimilarityDriver** sets up and runs the Hadoop MapReduce job. It configures the input/output paths, job name, and key-value classes. The driver defines the mapper, combiner, and reducer classes to be used. Once configured, it executes the job and checks for completion.

**Key Logic:**
- Initializes Hadoop job configuration.
- Defines Mapper, Reducer, and Combiner.
- Starts the job and waits for completion.

### 2. **DocumentSimilarityCombiner.java**
The **DocumentSimilarityCombiner** performs local aggregation before data is sent to the reducer. It aggregates the word counts for each document, which reduces the amount of data transferred between the map and reduce phases.

**Key Logic:**
- Aggregates word counts locally before sending them to the reducer.
- Reduces data transfer by summing word occurrences.

### 3. **DocumentSimilarityMapper.java**
The **DocumentSimilarityMapper** processes each document, tokenizes the content, and emits key-value pairs. The key is a word, and the value is the document ID. This enables the reducer to know which documents share common words.

**Key Logic:**
- Reads document and tokenizes content.
- Emits word-document pairs to identify shared words.

### 4. **DocumentSimilarityReducer.java**
The **DocumentSimilarityReducer** groups document IDs by shared words and generates document pairs. It computes the **Jaccard Similarity** score, which measures the overlap of words between document pairs. Document pairs below a certain threshold are filtered out.

**Key Logic:**
- Groups document IDs by shared words.
- Generates document pairs and calculates Jaccard Similarity.
- Filters pairs with low similarity.

### 5. **JaccardSimilarityReducer.java**
The **JaccardSimilarityReducer** calculates the final similarity score between document pairs by applying the **Jaccard Similarity** formula. It computes the intersection and union of words between two documents and filters out pairs with a similarity score below the threshold.

**Key Logic:**
- Calculates intersection and union of words for document pairs.
- Computes Jaccard Similarity and filters based on a threshold.
## Setup and Execution

### 1. Start the Hadoop Cluster
Run the following command to start the Hadoop cluster:
```bash
docker compose up -d
```

### 2. Build the Code
Build the code using Maven:
```bash
mvn clean package
```


```

### 4. Copy JAR to Docker Container
Copy the JAR file to the Hadoop ResourceManager container:
```bash
docker cp /workspaces/assignment-1-mapreduce-document-similarity-K-Tarunkumar/target/DocumentSimilarity-0.0.1-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. Move Dataset to Docker Container
Copy the dataset to the Hadoop ResourceManager container:
```bash
docker cp /workspaces/assignment-1-mapreduce-document-similarity-K-Tarunkumar/input/Doc1.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
docker cp /workspaces/assignment-1-mapreduce-document-similarity-K-Tarunkumar/input/Doc2.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
docker cp /workspaces/assignment-1-mapreduce-document-similarity-K-Tarunkumar/input/Doc3.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. Connect to Docker Container
Access the Hadoop ResourceManager container:
```bash
docker exec -it resourcemanager /bin/bash
```
Navigate to the Hadoop directory:
```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. Set Up HDFS
Create a folder in HDFS for the input dataset:
```bash
hadoop fs -mkdir -p /input/dataset
```
Copy the input dataset to HDFS:
```bash
hadoop fs -put /opt/hadoop-3.2.1/share/hadoop/mapreduce/Doc1.txt /input/dataset/
```

### 8. Execute the MapReduce Job
Run the job with the following command:
```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/DocumentSimilarity-0.0.1-SNAPSHOT.jar com.example.controller.DocumentSimilarityDriver /input/dataset/* /output
```

### 9. View the Output
To check the output directory on HDFS:
```bash
hadoop fs -ls /output
```
To view the results:
```bash
hadoop fs -cat /output/part-*
```

### 10. Copy Output from HDFS to Local OS
Copy the output from HDFS to your local machine:
```bash
hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```
Then, use Docker to copy from the container to your local machine:
```bash
exit
docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/   /workspaces/assignment-1-mapreduce-document-similarity-K-Tarunkumar/output
```

## Troubleshooting

- **IndexOutOfBoundsException**: Ensure that the correct input and output paths are provided.
- **Multiple SLF4J Bindings Warning**: This is a common logging issue and does not affect execution.
- **File Not Found Error**: Make sure the input file exists in HDFS before running the job.

## Contributors
- **Tarun Kumar Kanakala**


