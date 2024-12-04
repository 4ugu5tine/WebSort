package packages.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SortService {

    public List<Integer> heapSort(List<Integer> dataset) {
        int n = dataset.size();


        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(dataset, n, i);
        }


        for (int i = n - 1; i > 0; i--) {
            Collections.swap(dataset, 0, i);
            heapify(dataset, i, 0);
        }
        return dataset;
    }


    private void heapify(List<Integer> dataset, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && dataset.get(left) > dataset.get(largest)) {
            largest = left;
        }
        if (right < n && dataset.get(right) > dataset.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(dataset, i, largest);
            heapify(dataset, n, largest);
        }
    }


    public List<Integer> quickSort(List<Integer> dataset) {
        if (dataset.size() < 2) return dataset;

        int pivot = dataset.get(dataset.size() / 2);
        List<Integer> less = dataset.stream().filter(i -> i < pivot).collect(Collectors.toList());
        List<Integer> greater = dataset.stream().filter(i -> i > pivot).collect(Collectors.toList());
        List<Integer> equals = dataset.stream().filter(i -> i == pivot).collect(Collectors.toList());

        List<Integer> sorted = new ArrayList<>();
        sorted.addAll(quickSort(less));
        sorted.addAll(equals);
        sorted.addAll(quickSort(greater));

        return sorted;
    }


    public List<Integer> mergeSort(List<Integer> dataset) {
        if (dataset.size() < 2) return dataset;

        int mid = dataset.size() / 2;
        List<Integer> left = dataset.subList(0, mid);
        List<Integer> right = dataset.subList(mid, dataset.size());

        return merge(mergeSort(left), mergeSort(right));
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) < right.get(j)) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));

        return result;
    }


    public List<Integer> radixSort(List<Integer> dataset) {

        int max = Collections.max(dataset);
        int exp = 1;
        while (max / exp > 0) {
            dataset = countingSortByDigit(dataset, exp);
            exp *= 10;
        }
        return dataset;
    }


    private List<Integer> countingSortByDigit(List<Integer> dataset, int exp) {
        int n = dataset.size();
        int[] output = new int[n];
        int[] count = new int[10];


        for (int i = 0; i < n; i++) {
            count[(dataset.get(i) / exp) % 10]++;
        }


        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }


        for (int i = n - 1; i >= 0; i--) {
            output[count[(dataset.get(i) / exp) % 10] - 1] = dataset.get(i);
            count[(dataset.get(i) / exp) % 10]--;
        }


        List<Integer> sorted = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            sorted.add(output[i]);
        }
        return sorted;
    }


    public List<Integer> bucketSort(List<Integer> dataset) {
        if (dataset.isEmpty()) return dataset;


        int max = Collections.max(dataset);
        int bucketCount = (int) Math.sqrt(dataset.size());


        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : dataset) {
            int bucketIndex = (int) ((double) num / max * (bucketCount - 1));
            buckets.get(bucketIndex).add(num);
        }


        List<Integer> sorted = new ArrayList<>();
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            sorted.addAll(bucket);
        }

        return sorted;
    }
}
