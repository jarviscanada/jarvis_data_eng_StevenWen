package ca.jrvs.apps.practice;

public class RegexExcMain {
    public static void main(String[] args) {
        RegexExc r = new RegexExcImp();

        System.out.println("JPEG tests:");
        System.out.println(r.matchJpeg("a.jpg"));     // true
        System.out.println(r.matchJpeg("a.jpeg"));    // true
        System.out.println(r.matchJpeg("a.JPG"));     // true
        System.out.println(r.matchJpeg("a.png"));     // false

        System.out.println("\nIP tests:");
        System.out.println(r.matchIp("0.0.0.0"));          // true
        System.out.println(r.matchIp("999.999.999.999"));  // true
        System.out.println(r.matchIp("1.1.1"));            // false
        System.out.println(r.matchIp("1.1.1.1.1"));        // false

        System.out.println("\nEmpty line tests:");
        System.out.println(r.isEmptyLine(""));        // true
        System.out.println(r.isEmptyLine("   "));     // true
        System.out.println(r.isEmptyLine("\t\n"));    // true
        System.out.println(r.isEmptyLine("hi"));      // false
    }
}
