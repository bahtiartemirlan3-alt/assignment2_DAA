import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final int RANDOM_SEED = 42;

    public static void main(String[] args) {

        System.out.println("===== DAA ASSIGNMENT 2 BENCHMARK =====");

        workload1RandomAccess();
        workload2Search();
        workload3InsertionRemoval();
        workload4MinHeap();

        System.out.println("\n===== BENCHMARK FINISHED =====");
    }

    private static void workload1RandomAccess() {

        System.out.println("\n===== WORKLOAD 1: RANDOM ACCESS =====");
        System.out.println("n | DynamicArray(ns) | LinkedList(ns) | DA accesses | LL accesses");

        for (int n : SIZES) {

            Random random = new Random(RANDOM_SEED);

            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt(1_000_000);
            }

            int[] indices = new int[10000];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = random.nextInt(n);
            }

            long arrayTimeTotal = 0;
            long listTimeTotal = 0;

            long arrayAccessTotal = 0;
            long listAccessTotal = 0;

            for (int run = 0; run < RUNS; run++) {

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int value : values) {
                    array.add(value);
                    list.add(0, value);
                }

                array.resetMetrics();
                list.resetMetrics();

                long start = System.nanoTime();

                for (int index : indices) {
                    array.get(index);
                }

                long end = System.nanoTime();
                arrayTimeTotal += end - start;
                arrayAccessTotal += array.getAccesses();

                start = System.nanoTime();

                for (int index : indices) {
                    list.get(index);
                }

                end = System.nanoTime();
                listTimeTotal += end - start;
                listAccessTotal += list.getAccesses();
            }

            System.out.println(
                    n + " | "
                            + arrayTimeTotal / RUNS + " | "
                            + listTimeTotal / RUNS + " | "
                            + arrayAccessTotal / RUNS + " | "
                            + listAccessTotal / RUNS
            );
        }
    }
    private static void workload2Search() {

        System.out.println("\n===== WORKLOAD 2: SEARCH =====");
        System.out.println("n | DynamicArray(ns) | LinkedList(ns) | DA comparisons | LL comparisons");

        for (int n : SIZES) {

            Random random = new Random(RANDOM_SEED);

            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt(1_000_000);
            }

            int[] searchValues = new int[1000];

            for (int i = 0; i < searchValues.length; i++) {
                searchValues[i] = random.nextInt(1_000_000);
            }

            long arrayTimeTotal = 0;
            long listTimeTotal = 0;

            long arrayComparisonTotal = 0;
            long listComparisonTotal = 0;

            for (int run = 0; run < RUNS; run++) {

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int value : values) {
                    array.add(value);
                    list.add(0, value);
                }

                array.resetMetrics();
                list.resetMetrics();

                long start = System.nanoTime();

                for (int value : searchValues) {
                    array.contains(value);
                }

                long end = System.nanoTime();

                arrayTimeTotal += end - start;
                arrayComparisonTotal += array.getComparisons();

                start = System.nanoTime();

                for (int value : searchValues) {
                    list.contains(value);
                }

                end = System.nanoTime();

                listTimeTotal += end - start;
                listComparisonTotal += list.getComparisons();
            }

            System.out.println(
                    n + " | "
                            + arrayTimeTotal / RUNS + " | "
                            + listTimeTotal / RUNS + " | "
                            + arrayComparisonTotal / RUNS + " | "
                            + listComparisonTotal / RUNS
            );
        }
    }
    private static void workload3InsertionRemoval() {

        System.out.println("\n===== WORKLOAD 3: INSERTION / REMOVAL =====");

        for (int n : SIZES) {

            System.out.println("\n--- n = " + n + " ---");

            benchmarkBeginning(n);
            benchmarkMiddle(n);
        }
    }

    private static void benchmarkBeginning(int n) {

        long daInsertTime = 0;
        long llInsertTime = 0;

        long daRemoveTime = 0;
        long llRemoveTime = 0;

        long daInsertMovements = 0;
        long llInsertMovements = 0;

        long daRemoveMovements = 0;
        long llRemoveMovements = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = createArray(n);
            LinkedList list = createList(n);

            array.resetMetrics();
            list.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.add(0, i);
            }

            long end = System.nanoTime();

            daInsertTime += end - start;
            daInsertMovements += array.getMovements();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.add(0, i);
            }

            end = System.nanoTime();

            llInsertTime += end - start;
            llInsertMovements += list.getMovements();

            array = createArray(n);
            list = createList(n);

            array.resetMetrics();
            list.resetMetrics();
            int operations = Math.min(1000, n);

            start = System.nanoTime();

            for (int i = 0; i < operations; i++) {
                array.remove(0);
            }

            end = System.nanoTime();

            daRemoveTime += end - start;
            daRemoveMovements += array.getMovements();

            start = System.nanoTime();

            for (int i = 0; i < operations; i++) {
                list.remove(0);
            }

            end = System.nanoTime();

            llRemoveTime += end - start;
            llRemoveMovements += list.getMovements();
        }

        System.out.println("Beginning (index 0)");

        System.out.println(
                "DynamicArray insertion: time = "
                        + daInsertTime / RUNS
                        + " ns, movements = "
                        + daInsertMovements / RUNS
        );

        System.out.println(
                "LinkedList insertion: time = "
                        + llInsertTime / RUNS
                        + " ns, movements = "
                        + llInsertMovements / RUNS
        );

        System.out.println(
                "DynamicArray removal: time = "
                        + daRemoveTime / RUNS
                        + " ns, movements = "
                        + daRemoveMovements / RUNS
        );

        System.out.println(
                "LinkedList removal: time = "
                        + llRemoveTime / RUNS
                        + " ns, movements = "
                        + llRemoveMovements / RUNS
        );
    }

    private static void benchmarkMiddle(int n) {

        long daInsertTime = 0;
        long llInsertTime = 0;

        long daRemoveTime = 0;
        long llRemoveTime = 0;

        long daInsertMovements = 0;
        long llInsertAccesses = 0;

        long daRemoveMovements = 0;
        long llRemoveAccesses = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = createArray(n);
            LinkedList list = createList(n);

            array.resetMetrics();
            list.resetMetrics();

            int middle = n / 2;

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.add(middle, i);
            }

            long end = System.nanoTime();

            daInsertTime += end - start;
            daInsertMovements += array.getMovements();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.add(middle, i);
            }

            end = System.nanoTime();

            llInsertTime += end - start;
            llInsertAccesses += list.getAccesses();

            array = createArray(n);
            list = createList(n);

            array.resetMetrics();
            list.resetMetrics();

            int operations = Math.min(1000, n);
            start = System.nanoTime();

            for (int i = 0; i < operations; i++) {
                array.remove(array.size() / 2);
            }

            end = System.nanoTime();

            daRemoveTime += end - start;
            daRemoveMovements += array.getMovements();

            start = System.nanoTime();

            for (int i = 0; i < operations; i++) {
                list.remove(list.size() / 2);
            }

            end = System.nanoTime();

            llRemoveTime += end - start;
            llRemoveAccesses += list.getAccesses();
        }

        System.out.println("Middle (index n/2)");

        System.out.println(
                "DynamicArray insertion: time = "
                        + daInsertTime / RUNS
                        + " ns, movements = "
                        + daInsertMovements / RUNS
        );

        System.out.println(
                "LinkedList insertion: time = "
                        + llInsertTime / RUNS
                        + " ns, accesses = "
                        + llInsertAccesses / RUNS
        );

        System.out.println(
                "DynamicArray removal: time = "
                        + daRemoveTime / RUNS
                        + " ns, movements = "
                        + daRemoveMovements / RUNS
        );

        System.out.println(
                "LinkedList removal: time = "
                        + llRemoveTime / RUNS
                        + " ns, accesses = "
                        + llRemoveAccesses / RUNS
        );
    }

    private static void workload4MinHeap() {

        System.out.println("\n===== WORKLOAD 4: PRIORITY PROCESSING =====");
        System.out.println("n | Insert(ns) | Extract(ns) | Comparisons");

        for (int n : SIZES) {

            Random random = new Random(RANDOM_SEED);

            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt(1_000_000);
            }

            long insertTimeTotal = 0;
            long extractTimeTotal = 0;
            long comparisonTotal = 0;

            for (int run = 0; run < RUNS; run++) {

                MinHeap heap = new MinHeap();

                heap.resetComparisons();

                long start = System.nanoTime();

                for (int value : values) {
                    heap.insert(value);
                }

                long end = System.nanoTime();

                insertTimeTotal += end - start;

                long insertComparisons = heap.getComparisons();

                heap.resetComparisons();

                int previous = Integer.MIN_VALUE;

                start = System.nanoTime();

                while (!heap.isEmpty()) {

                    int current = heap.extractMin();
                    if (current < previous) {
                        throw new RuntimeException(
                                "ERROR: Heap output is not sorted!"
                        );
                    }

                    previous = current;
                }

                end = System.nanoTime();

                extractTimeTotal += end - start;

                long extractComparisons = heap.getComparisons();

                comparisonTotal +=
                        insertComparisons + extractComparisons;
            }

            System.out.println(
                    n + " | "
                            + insertTimeTotal / RUNS + " | "
                            + extractTimeTotal / RUNS + " | "
                            + comparisonTotal / RUNS
            );
        }
    }

    private static DynamicArray createArray(int n) {

        DynamicArray array = new DynamicArray();

        for (int i = 0; i < n; i++) {
            array.add(i);
        }

        return array;
    }

    private static LinkedList createList(int n) {

        LinkedList list = new LinkedList();
        for (int i = 0; i < n; i++) {
            list.add(0, i);
        }

        return list;
    }
}