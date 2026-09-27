public class MinHeap {

    private int[] heap;
    private int size;
    private long comparisons;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        comparisons = 0;
    }

    public void insert(int x) {
        if (size == heap.length) {
            resize();
        }

        heap[size] = x;
        int current = size;
        size++;

        while (current > 0) {
            int parent = (current - 1) / 2;

            comparisons++;

            if (heap[current] >= heap[parent]) {
                break;
            }

            swap(current, parent);
            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return heap[0];
    }
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];

        heap[0] = heap[size - 1];
        size--;

        int current = 0;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;

            if (left < size) {
                comparisons++;

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                comparisons++;

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == current) {
                break;
            }

            swap(current, smallest);
            current = smallest;
        }

        return min;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetComparisons() {
        comparisons = 0;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
}