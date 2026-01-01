package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();

        for (File file : listFiles(rootPath)){
            for (String line : readLines(file)){
                if (containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);

    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<>();
        File root = new File(rootDir);

        traverse(root, files);

        return files;
    }

    private void traverse(File file, List<File> files){
        // if it is file add to list
        if (file.isFile()){
            files.add(file);
            return;
        }

        // if it is dir
        File[] child = file.listFiles();
        // null case
        if (child == null) return;
        // loop though the child dir we call this function again
        for (File c : child){
            traverse(c, files);
        }

    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();

        // reading line by line
        try (BufferedReader br =
                     new BufferedReader(
                             // getting the charactor we could use charactor encoding with this method than FileReader
                             new InputStreamReader(
                                     Files.newInputStream(inputFile.toPath()),
                                     // encoding charactor
                                     StandardCharsets.UTF_8))) {

            String line;
            // until the end of the file
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.error("Failed to read file {}", inputFile.getAbsolutePath(), e);
        }

        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        // prevents NullPointerException
        if (line == null) return false;
        // search line for the pattern
        return Pattern.compile(regex).matcher(line).find();
    }

    @Override

    public void writeToFile(List<String> lines) throws IOException {
        File out = new File(outFile);
        File parent = out.getParentFile();
        // create parent directories if they don't exist
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // create stream to write to file
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(out),
                        StandardCharsets.UTF_8))) {

            // write to file
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }


    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: JavaGrepMain regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

}
