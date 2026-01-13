package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LambdaStreamMain {
    public static void main(String[] args) {
        LambdaStreamExc lse = new LambdaStreamImp();

        // 1) toUpperCase + toList
        System.out.println(lse.toList(lse.toUpperCase("a", "b", "c")));

        // 2) filter out strings containing "a"
        Stream<String> s = lse.createStrStream("apple", "bob", "cat");
        System.out.println(lse.toList(lse.filter(s, "a")));

        // 3) printOdd
        lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));

        // 4) flatNestedInt
        Stream<List<Integer>> nested = Stream.of(Arrays.asList(1, 2), Arrays.asList(3), Arrays.asList(4, 5));
        System.out.println(lse.toList(lse.flatNestedInt(nested)));
    }
}
