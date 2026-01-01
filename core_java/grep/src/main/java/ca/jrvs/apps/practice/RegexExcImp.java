package ca.jrvs.apps.practice;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc{

    private static final Pattern JPEG_PATTERN =
            Pattern.compile("^.+\\.(jpe?g)$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean matchJpeg(String filename) {
        if (filename == null) return false;
        return JPEG_PATTERN.matcher(filename).matches();
    }

    private static final Pattern IP_PATTERN =
            Pattern.compile("^\\d{1,3}(\\.\\d{1,3}){3}$");

    @Override
    public boolean matchIp(String ip) {
        if (ip == null) return false;
        return IP_PATTERN.matcher(ip).matches();
    }

    private static final Pattern EMPTY_LINE_PATTERN =
            Pattern.compile("^\\s*$");

    @Override
    public boolean isEmptyLine(String line) {
        if (line == null) return false;
        return EMPTY_LINE_PATTERN.matcher(line).matches();
    }
}
