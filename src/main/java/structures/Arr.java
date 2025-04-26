package structures;
import structures.WPArrayList;
public class Arr {
    private static final int ELEMENTS = 1_000000; // 1 million elements

    public static void main(String[] args) {
        structures.WPArrayList<Integer> list = new structures.WPArrayList<>();

        System.out.println("Benchmarking WPArrayList with " + ELEMENTS + " elements...");

        // Benchmark add
        long start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i++) {
            list.add(i);
        }
        long end = System.nanoTime();
        System.out.println("Add: " + (end - start) / 1_000_000 + " ms");

        // Benchmark get
        start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i++) {
            list.get(i);
        }
        end = System.nanoTime();
        System.out.println("Get: " + (end - start) / 1_000_000 + " ms");

        // Benchmark set
        start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i++) {
            list.set(i, i + 1);
        }
        end = System.nanoTime();
        System.out.println("Set: " + (end - start) / 1_000_000 + " ms");

        // Benchmark contains (every 10000th number)
        start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i ++) {
            list.contains(i);
        }
        end = System.nanoTime();
        System.out.println("Contains (every 10k): " + (end - start) / 1_000_000 + " ms");

        // Benchmark indexOf (every 10000th number)
        start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i ++) {
            list.indexOf(i);
        }
        end = System.nanoTime();
        System.out.println("IndexOf (every 10k): " + (end - start) / 1_000_000 + " ms");

        // Benchmark remove (first half)
        start = System.nanoTime();
        for (int i = 0; i < ELEMENTS; i++) {
            list.remove(Integer.valueOf(i));
        }
        end = System.nanoTime();
        System.out.println("Remove (first half): " + (end - start) / 1_000_000 + " ms");
    }
}
