public class Tests {

    public static void main(String[] args) {

        testDynamicArray();
        testLinkedList();
        testMinHeap();

        System.out.println("\nAll tests passed successfully!");
    }

    private static void testDynamicArray() {
        System.out.println("Testing DynamicArray...");

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assert array.size() == 3;
        assert array.get(0) == 10;
        assert array.get(1) == 20;
        assert array.get(2) == 30;

        array.add(1, 15);

        assert array.get(0) == 10;
        assert array.get(1) == 15;
        assert array.get(2) == 20;

        assert array.contains(20);
        assert !array.contains(100);

        array.add(20);
        assert array.contains(20);

        int removed = array.remove(1);

        assert removed == 15;
        assert array.get(1) == 20;

        DynamicArray largeArray = new DynamicArray();

        for (int i = 0; i < 10000; i++) {
            largeArray.add(i);
        }

        assert largeArray.size() == 10000;
        assert largeArray.get(9999) == 9999;

        boolean exceptionThrown = false;

        try {
            array.get(100);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assert exceptionThrown;

        System.out.println("DynamicArray tests passed.");
    }

    private static void testLinkedList() {
        System.out.println("Testing LinkedList...");

        LinkedList list = new LinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assert list.size() == 3;
        assert list.get(0) == 10;
        assert list.get(1) == 20;
        assert list.get(2) == 30;

        list.add(0, 5);

        assert list.get(0) == 5;
        assert list.get(1) == 10;

        list.add(2, 15);

        assert list.get(2) == 15;

        assert list.contains(30);
        assert !list.contains(100);

        list.add(20);
        assert list.contains(20);

        int removed = list.remove(0);

        assert removed == 5;
        assert list.get(0) == 10;

        LinkedList largeList = new LinkedList();

        for (int i = 0; i < 10000; i++) {
            largeList.add(0, i);
        }

        assert largeList.size() == 10000;

        boolean exceptionThrown = false;

        try {
            list.get(50000);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assert exceptionThrown;

        System.out.println("LinkedList tests passed.");
    }

    private static void testMinHeap() {
        System.out.println("Testing MinHeap...");

        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(40);
        heap.insert(5);

        assert heap.peekMin() == 5;

        int previous = Integer.MIN_VALUE;

        while (!heap.isEmpty()) {
            int current = heap.extractMin();

            assert current >= previous;

            previous = current;
        }

        boolean exceptionThrown = false;

        try {
            heap.extractMin();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }

        assert exceptionThrown;

        MinHeap largeHeap = new MinHeap();

        for (int i = 10000; i >= 1; i--) {
            largeHeap.insert(i);
        }

        assert largeHeap.peekMin() == 1;

        System.out.println("MinHeap tests passed.");
    }
}