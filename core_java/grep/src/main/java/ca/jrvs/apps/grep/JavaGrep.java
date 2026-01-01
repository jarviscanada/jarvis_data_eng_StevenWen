package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all files
     * @param rootDir input dir
     * @return files under the rootdir
     */
    List<File> listFiles(String rootDir);

    /**
     * Read a file and return all the lines
     *
     * Explain Filereader, BufferedReader , and character encoding
     * @param inputFile
     * @return lines
     * @throws IllegalArgumentException if a given input is not a file
     */
    List<String> readLines(File inputFile);

    /**
     * check if line contains the regex pattern(pass by user)
     * @param line inout string
     * @return true if there is a mathch
     */
    boolean containsPattern(String line);

    /**
     * Write lines to a file
     *
     * Explore: FileOutputStreams, OutputStreamWriter, and BufferedWriter
     *
     * @param lines matched line
     * @throws IOException if write fail
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
