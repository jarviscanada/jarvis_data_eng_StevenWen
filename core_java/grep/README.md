# Introduction
This project contains a Java implementation of a grep application. The app recursively searches files under 
a given root directory, finds lines that match a user-provided regex pattern, and writes all matched lines to an output 
file. The project is built with Java (I/O, collections, regex), uses Maven for dependency management and packaging, 
and uses SLF4J + Log4j for logging. Development was done in IntelliJ IDEA. A Docker image is provided for portable 
execution without installing Java locally.

# Quick Start - Local (Java)
1. Build the project (creates a jar under target/):
```bash
cd core_java/grep
mvn clean package
```
2. Run Grep - Example:
```bash 
java -cp target/grep-1.0-SNAPSHOT.jar \
ca.jrvs.apps.grep.JavaGrepImp \
'.*Romeo.*Juliet.*' ./data ./out/grep.txt
```
3. Check output:
```bash
cat ./out/grep.txt | head
```

# Quick Start - Docker
1. Pull the Docker image
```bash
docker pull stevenw233/grep
```
2. Verify the Docker
```bash
docker image ls | grep grep
```
3. Run the container
```bash
docker run --rm \
-v $(pwd)/data:/data \
-v $(pwd)/log:/log \
stevenw233/grep \
'.*Romeo.*Juliet.*' /data /log/grep.out
```

# Implemenation1
## Pseudocode
`process` method pseudocode.
```pseudo
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
The original implementation may load many lines into memory (e.g., returning List<String> from readLines and 
collecting all matches before writing). For very large inputs (e.g., 50GB), this can cause OutOfMemoryError.
To fix it, stream the file line-by-line and write matches immediately to the output (or return a Stream<String> 
from readLines and consume it with a small buffer), so memory usage stays low regardless of file size.

# Test
- Prepared sample input files under ./data 
- Ran the app with multiple regex patterns:
  - pattern that matches many lines (sanity check output is non-empty)
  - pattern that matches nothing (output file should be empty)
  - edge cases (special characters, empty/invalid args)
- Verified results by comparing:
  - output file contents vs expected lines using cat, head, grep, and visual inspection.
- Confirmed output file path creation.

# Deployment
- Package the application into a runnable jar using Maven (mvn clean package).
- Create a Dockerfile that uses a Java 8 runtime base image and copies the jar into the container.
- Set ENTRYPOINT to java -jar ... so the container behaves like an executable.
- Run the container by mounting host input/output directories with Docker volumes, then pass regex rootPath outFile as arguments.

# Improvement
Add automated tests (JUnit) for regex matching, directory traversal, and file I/O edge cases.
Improve CLI usability: better argument validation, help message, and optional flags (file extension filters, case-insensitive mode, recursive toggle). 