import java.util.regex.Pattern;

public class P2938 {
    private static final Pattern P = Pattern.compile("(0*)(1*)");

    public record P(int zeros, int ones, long moves) {}

    public long minimumSteps(String s) {
        return P.matcher(s).results()
                .map(r -> new P(r.group(1).length(), r.group(2).length(), 0))
                .reduce(new P(0, 0, 0L), (a, b) -> new P(
                        a.zeros() + b.zeros(),
                        a.ones() + b.ones(),
                        a.moves() + (long) a.ones() * b.zeros()))
                .moves();
    }
}
