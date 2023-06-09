## indexVerse
Simple multithreaded inverted index generator


### Benchmarking results with mutable HashMap and mutable Set

| [info] Benchmark                   | numThreads | Mode | Cnt | Score /  Error | Units |
|------------------------------------|------------|------|-----|----------------|-------|
| [info] BenchmarkIndex.testIndexing | 1          | avgt | 6   | 4.300 ± 0.760  | s/op  |
| [info] BenchmarkIndex.testIndexing | 2          | avgt | 6   | 2.177 ± 0.176  | s/op  |
| [info] BenchmarkIndex.testIndexing | 4          | avgt | 6   | 1.249 ± 0.058  | s/op  |
| [info] BenchmarkIndex.testIndexing | 8          | avgt | 6   | 0.693 ± 0.019  | s/op  |


### Benchmarking with immutable data structures

| [info] Benchmark                    | numThreads | Mode | Cnt | Score /  Error | Units |
|-------------------------------------|------------|------|-----|----------------|-------|
| [info] BenchmarkIndex.testIndexing  | 1          | avgt | 6   | 3.487 ± 0.181  | s/op  |
| [info] BenchmarkIndex.testIndexing  | 2          | avgt | 6   | 1.859 ± 0.010  | s/op  |
| [info] BenchmarkIndex.testIndexing  | 4          | avgt | 6   | 0.996 ± 0.020  | s/op  |
| [info] BenchmarkIndex.testIndexing  | 8          | avgt | 6   | 0.560 ± 0.037  | s/op  |


