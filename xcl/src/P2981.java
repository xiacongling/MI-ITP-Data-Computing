import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class P2981 {
    public int maximumLength(String s) {
        Map<Integer, Map<Integer, Integer>> repeats = new HashMap<>();

        var pre = new Object() {
            int c = s.charAt(0);
            int repeat = 1;
        };
        IntStream.concat(s.chars(), IntStream.of(0))
                .skip(1)
                .forEach(c -> {
                    if (pre.c != c) {
                        repeats.compute(pre.c, (_, counter) -> {
                            if (counter == null) {
                                counter = new TreeMap<>(Comparator.reverseOrder());
                            }
                            counter.compute(pre.repeat, (_, count) -> count == null ? 1 : (count + 1));
                            return counter;
                        });
                        pre.c = c;
                        pre.repeat = 0;
                    }
                    pre.repeat += 1;
                });
        return repeats.values().stream()
                .mapToInt(counter -> {
                    for (Map.Entry<Integer, Integer> e : counter.entrySet()) {
                        int length = e.getKey();
                        int count = e.getValue();
                        if (count >= 3) {
                            return length;
                        }
                        if ((count == 2 && length > 1) || counter.getOrDefault(length - 1, 0) >= 1) {
                            return length - 1;
                        }
                        if (length > 2) {
                            return length - 2;
                        }
                    }
                    return -1;
                })
                .max()
                .orElse(-1);
    }

    public static void main(String[] args) {
        P2981 p = new P2981();
        System.out.println(p.maximumLength("aca"));
        System.out.println(p.maximumLength("eccdnmcnkl"));
        System.out.println(p.maximumLength("acc"));
        System.out.println(p.maximumLength("aa"));
    }
}
