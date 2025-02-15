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

# Movie Script Analysis

## Prerequisites
Ensure you have the following software installed:

- **Java 8+**
- **Apache Hadoop (v3.2.1 or later)**
- **Apache Maven** (for building the project, if needed)

## Project Structure
```
movie-script-analysis/
│-- src/main/java/com/movie/script/analysis/
│   │-- MovieScriptAnalysis.java          # Main driver class
│   │-- CharacterWordMapper.java          # Mapper for frequent words
│   │-- CharacterWordReducer.java         # Reducer for frequent words
│   │-- DialogueLengthMapper.java         # Mapper for dialogue length
│   │-- DialogueLengthReducer.java        # Reducer for dialogue length
│   │-- UniqueWordsMapper.java            # Mapper for unique words
│   │-- UniqueWordsReducer.java           # Reducer for unique words
│-- dataset/
│   ├── movie_dialogues.txt               # Sample dataset
│-- pom.xml                               # Maven build file
│-- README.md                             # Project documentation
```

## Objectives
By completing this project, you will:

- **Understand Hadoop's Architecture**: Learn how Hadoop's HDFS and MapReduce framework process large datasets.
- **Build and Deploy a MapReduce Job**: Gain experience in compiling and running a Java-based MapReduce job on a Hadoop cluster.
- **Work with Docker Containers**: Learn to use Docker for managing Hadoop components and transferring files.
- **Analyze Movie Script Data**: Extract insights from movie dialogues, such as unique words, most frequent words, and dialogue lengths.

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

### 3. Move JAR File to Shared Folder
Move the generated JAR file to a shared folder:
```bash
mv target/movie-script-analysis-1.0.jar shared-folder/input/code/
```

### 4. Copy JAR to Docker Container
Copy the JAR file to the Hadoop ResourceManager container:
```bash
docker cp shared-folder/input/code/movie-script-analysis-1.0.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. Move Dataset to Docker Container
Copy the dataset to the Hadoop ResourceManager container:
```bash
docker cp dataset/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
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
hadoop fs -put movie_dialogues.txt /input/dataset
```

### 8. Execute the MapReduce Job
Run the job with the following command:
```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/movie-script-analysis-1.0.jar com.movie.script.analysis.MovieScriptAnalysis /input/dataset /output
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
docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ shared-folder/output/
```

## Troubleshooting

- **IndexOutOfBoundsException**: Ensure that the correct input and output paths are provided.
- **Multiple SLF4J Bindings Warning**: This is a common logging issue and does not affect execution.
- **File Not Found Error**: Make sure the input file exists in HDFS before running the job.

## Contributors
- **Tarun Kumar Kanakala**


