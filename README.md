# Assignment 2
## Algorithmic Analysis, Correctness and Performance Trade-offs

## 1. Overview

In this assignment, I implemented and analyzed three data structures in Java:

- Dynamic Array
- Linked List
- Min-Heap

The main goal of this assignment is to compare theoretical algorithm complexity with experimental performance.

The experiments were performed with four input sizes:

- 100
- 1,000
- 10,000
- 100,000

---

## 2. Implemented Data Structures

### Dynamic Array

The Dynamic Array stores elements inside an array. When the array becomes full, its capacity is increased.

Implemented operations:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

### Linked List

The Linked List consists of nodes. Each node contains a value and a reference to the next node.

Implemented operations:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

### Min-Heap

The Min-Heap is implemented using an array.

The minimum element is always located at the root.

Implemented operations:

- `insert(x)`
- `peekMin()`
- `extractMin()`

---

# 3. Complexity Analysis

## Dynamic Array

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
| --- | --- | --- | --- | --- |
| add(x) | Omega(1) | Theta(1) amortized | O(n) | O(n) during resize |
| add(index, x) | Omega(1) | Theta(n) | O(n) | O(n) during resize |
| remove(index) | Omega(1) | Theta(n) | O(n) | O(1) |
| get(index) | Omega(1) | Theta(1) | O(1) | O(1) |
| contains(x) | Omega(1) | Theta(n) | O(n) | O(1) |

`get(index)` is constant time because an array supports direct access by index.

Insertion and removal can require shifting elements. Therefore, these operations can take linear time.

`contains(x)` checks elements one by one until the value is found or the end of the array is reached.

---

## Linked List

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
| --- | --- | --- | --- | --- |
| add(x) | Omega(1) | Theta(n) | O(n) | O(1) |
| add(index, x) | Omega(1) | Theta(n) | O(n) | O(1) |
| remove(index) | Omega(1) | Theta(n) | O(n) | O(1) |
| get(index) | Omega(1) | Theta(n) | O(n) | O(1) |
| contains(x) | Omega(1) | Theta(n) | O(n) | O(1) |

A Linked List does not support direct access by index.

To access an element, the algorithm may need to move through the nodes starting from the head.

Insertion and removal at index 0 are fast because only the head reference must be changed.

---

## Min-Heap

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
| --- | --- | --- | --- | --- |
| insert(x) | Omega(1) | O(log n) | O(log n) | O(1) |
| peekMin() | Omega(1) | Theta(1) | O(1) | O(1) |
| extractMin() | Omega(1) | O(log n) | O(log n) | O(1) |

`peekMin()` is constant time because the minimum value is stored at the root.

`insert()` may move an element upward through the heap.

`extractMin()` may move an element downward to restore the heap property.

---

# 4. Algorithmic Correctness

Two operations were selected for loop invariant analysis.

## 4.1 Dynamic Array Insertion

Operation: `add(index, x)`

The algorithm shifts elements to the right before inserting the new element.

### Loop Invariant

Before every iteration of the loop, all elements that have already been processed are correctly moved one position to the right while keeping their original order.

### Initialization

Before the first iteration, no elements have been shifted.

Therefore, the invariant is true.

### Maintenance

During each iteration, one element is copied one position to the right.

The previously shifted elements remain in the correct order.

Therefore, the invariant continues to be true.

### Termination

The loop finishes after all required elements have been shifted.

The requested index becomes free, and the new value is inserted there.

Therefore, the new element is placed at the correct position and the order of the previous elements is preserved.

---

## 4.2 Min-Heap Insertion

Operation: `insert(x)`

A new element is first placed at the end of the heap.

### Loop Invariant

Before every iteration, the heap property is correct everywhere except possibly between the current element and its parent.

### Initialization

Before insertion, the heap already satisfies the Min-Heap property.

After adding a new element, only the relationship between the new element and its parent may violate the heap property.

### Maintenance

If the current element is smaller than its parent, the two elements are swapped.

After the swap, the old position satisfies the heap property.

The only possible violation is now between the new current position and its parent.

Therefore, the invariant is maintained.

### Termination

The loop stops when the element reaches the root or when its parent is smaller than or equal to it.

At this point, the Min-Heap property is satisfied everywhere.

Therefore, the insertion algorithm is correct.

---

# 5. Experimental Setup

The benchmark was implemented in Java.

### Input sizes

- n = 100
- n = 1,000
- n = 10,000
- n = 100,000

### Benchmark settings

- Each experiment was executed 5 times.
- Average execution time was calculated.
- `System.nanoTime()` was used for timing.
- `Random(42)` was used as the random seed.
- Input data was generated before timing.
- Printing was not included in the measured execution time.

For the removal experiment with `n = 100`, only 100 elements could be removed from the original structure. For larger structures, 1,000 removals were performed.

---

# 6. Experimental Results

## Workload 1 - Random Access

For this workload, 10,000 random indices were generated.

The `get(index)` operation was performed for every index.

| n | Dynamic Array Time (ns) | Linked List Time (ns) | DA Accesses | LL Accesses |
| ---: | ---: | ---: | ---: | ---: |
| 100 | 662980 | 1690080 | 10000 | 511327 |
| 1000 | 74700 | 10282320 | 10000 | 5021262 |
| 10000 | 69760 | 105146660 | 10000 | 50188951 |
| 100000 | 6320 | 752097000 | 10000 | 504940938 |

Dynamic Array uses direct indexing, so the number of accesses remained constant.

Linked List required more node accesses when the size increased.

### Random Access Execution Time

![Execution Time vs Input Size](results/plots/execution_time.png)

The graph shows that Linked List random access becomes much more expensive as the input size increases.

The measured Dynamic Array times are very small and can be affected by JVM warm-up, JIT optimization, caching and timing noise.

---

## Workload 2 - Search

For this workload, 1,000 search values were generated.

The `contains(value)` operation was performed for every value.

| n | Dynamic Array Time (ns) | Linked List Time (ns) | DA Comparisons | LL Comparisons |
| ---: | ---: | ---: | ---: | ---: |
| 100 | 520160 | 468080 | 99996 | 99905 |
| 1000 | 931000 | 1618880 | 998832 | 999170 |
| 10000 | 2614880 | 15357960 | 9948508 | 9941503 |
| 100000 | 23039800 | 138226140 | 95047696 | 94674339 |

Both structures use linear search.

The number of comparisons increased when the input size increased.

### Search Comparisons

![Operations and Comparisons vs Input Size](results/plots/operations.png)

The graph shows approximately linear growth in the number of comparisons.

Although both structures have linear search complexity, Dynamic Array was faster for large input sizes.

---

## Workload 3 - Insertion and Removal

Insertion and removal were tested at two positions:

- Beginning: index 0
- Middle: index n / 2

For `n = 100,000`, the results were:

| Operation | Dynamic Array Time (ns) | Linked List Time (ns) |
| --- | ---: | ---: |
| Beginning insertion | 8025560 | 38000 |
| Beginning removal | 5919440 | 23020 |
| Middle insertion | 3830420 | 66113560 |
| Middle removal | 4241660 | 64998580 |

Linked List was very fast for insertion and removal at the beginning.

It only needs to change references and does not need to move all elements.

Dynamic Array was slower at the beginning because many elements had to be shifted.

For middle operations, Linked List became slower because it had to traverse many nodes before reaching the required position.

The complete experimental results are stored in:

`results/tables/workload3.csv`

---

## Workload 4 - Priority Processing

For this workload, random integers were inserted into the Min-Heap.

After insertion, all minimum elements were extracted.

| n | Insert Time (ns) | Extract Time (ns) | Comparisons |
| ---: | ---: | ---: | ---: |
| 100 | 26580 | 82020 | 1035 |
| 1000 | 61840 | 138100 | 17226 |
| 10000 | 494800 | 1246800 | 239329 |
| 100000 | 1875660 | 8336700 | 3059125 |

The extracted elements were checked to verify that they were in non-decreasing order.

As the input size increased, the execution time and number of comparisons also increased.

This behavior agrees with the theoretical analysis of the Min-Heap.

---

# 7. Performance and Design Analysis

## Effect of Increasing n

Increasing the input size had different effects depending on the workload.

For Random Access, Dynamic Array remained efficient because it supports direct indexing.

Linked List became slower because more nodes had to be traversed.

For Search, both structures required more comparisons as the input size increased.

For Insertion and Removal, the position was important.

Linked List was efficient at the beginning but expensive in the middle.

For Min-Heap, execution time and comparisons increased as more elements were processed.

---

## Theory and Experimental Results

The experimental results generally agree with theoretical complexity.

Dynamic Array `get(index)` has constant complexity.

Linked List `get(index)` has linear complexity.

The `contains()` operation is linear for both structures.

Linked List insertion and removal at the beginning are constant-time operations.

Min-Heap insertion and extraction require logarithmic work per individual operation in the worst case.

---

## Same Big-O but Different Running Time

Two algorithms with the same Big-O complexity can have different real execution times.

Possible reasons include:

- constant factors
- memory organization
- cache behavior
- pointer traversal
- JVM optimization
- implementation details
- computer hardware

For example, both Dynamic Array and Linked List have linear search complexity, but Dynamic Array was faster for large inputs in this experiment.

---

# 8. Design Recommendations

## Dynamic Array

Dynamic Array is useful when:

- random access is important
- elements are frequently accessed by index
- fast iteration is needed

It is less suitable for frequent insertion and removal at the beginning.

## Linked List

Linked List can be useful when:

- insertion at the beginning is frequent
- removal at the beginning is frequent
- random access is not important

It is less suitable for frequent access to arbitrary indices.

## Min-Heap

Min-Heap is useful for priority-based processing.

It provides:

- fast access to the minimum element
- efficient insertion
- efficient minimum extraction

The correct data structure depends on the workload.

---

# 9. Testing and Validation

The implementations were tested with:

- empty structures
- one element
- multiple elements
- duplicate values
- boundary indices
- invalid indices
- large inputs

For Min-Heap, the extraction order was also verified.

Every extracted value had to be greater than or equal to the previously extracted value.

This verifies that `extractMin()` returns elements in non-decreasing order.

---

# 10. Project Structure

    DAA assignment2/
    |
    |-- src/
    |   |-- DynamicArray.java
    |   |-- LinkedList.java
    |   |-- MinHeap.java
    |   |-- Benchmark.java
    |   |-- Tests.java
    |
    |-- results/
    |   |
    |   |-- tables/
    |   |   |-- workload1.csv
    |   |   |-- workload2.csv
    |   |   |-- workload3.csv
    |   |   |-- workload4.csv
    |   |
    |   |-- plots/
    |       |-- execution_time.png
    |       |-- operations.png
    |
    |-- README.md

---

# 11. Conclusion

In this assignment, three data structures were implemented and analyzed:

- Dynamic Array
- Linked List
- Min-Heap

The experiments demonstrated that the choice of data structure has an important effect on program performance.

Dynamic Array was effective for random access.

Linked List was effective for insertion and removal at the beginning.

Min-Heap was effective for priority-based processing.

The experimental results generally agreed with the theoretical complexity analysis.

The experiments also showed that Big-O complexity is not the only factor that affects real execution time.

Memory organization, constant factors, JVM optimization and implementation details can also affect practical performance.