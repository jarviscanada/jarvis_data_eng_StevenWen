package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "Usage: JavaGrepMain regex rootPath outFile"
            );
        }

        // create JavaGrepLambdaImp instead of JavaGrepImp
        JavaGrepLambdaImp javaGrep = new JavaGrepLambdaImp();
        javaGrep.setRegex(args[0]);
        javaGrep.setRootPath(args[1]);
        javaGrep.setOutFile(args[2]);

        try {
            // calls process() from parent,
            // but overridden methods in this class will be used
            javaGrep.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Implement using lambda and stream APIs
     */
    @Override
    public List<String> readLines(File inputFile) {
        try (Stream<String> lines = Files.lines(inputFile.toPath(), StandardCharsets.UTF_8)) {
            return lines.collect(Collectors.toList());
        } catch (Exception e) {
            return java.util.Collections.emptyList(); // skip bad file
        }
    }

    /**
     * Implement using lambda and stream APIs
     */
    @Override
    public List<File> listFiles(String rootDir) {
        try (Stream<java.nio.file.Path> paths = Files.walk(java.nio.file.Paths.get(rootDir))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(java.nio.file.Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to traverse directory: " + rootDir, e);
        }
    }
}
