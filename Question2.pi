import random
import timeit
import matplotlib.pyplot as plt

def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr)//2
        left = arr[:mid]
        right = arr[mid:]

        merge_sort(left)
        merge_sort(right)

        i = j = k = 0
        while i < len(left) and j < len(right):
            if left[i] < right[j]:
                arr[k] = left[i]
                i += 1
            else:
                arr[k] = right[j]
                j += 1
            k += 1

        while i < len(left):
            arr[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            arr[k] = right[j]
            j += 1
            k += 1

def binary_search(arr, target):
    low = 0
    high = len(arr) - 1
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] < target:
            low = mid + 1
        elif arr[mid] > target:
            high = mid - 1
        else:
            return mid
    return -1

def find_pairs(arr, k):
    pairs = []
    merge_sort(arr)
    for i in range(len(arr)):
        complement = k - arr[i]
        j = binary_search(arr, complement)
        if j != -1 and i != j:
            pairs.append((arr[i], arr[j]))
    return pairs

def test(n):
    arr = [random.randint(1, 100) for _ in range(n)]
    k = random.randint(1, 200)
    pairs = find_pairs(arr, k)
    return len(pairs)

n_values = [10, 100, 1000, 10000, 100000]
times = []

for n in n_values:
    time = timeit.timeit(lambda: test(n), number=5) / 5
    times.append(time)

plt.plot(n_values, times)
plt.xlabel('Input Size (n)')
plt.ylabel('Time (sec)')
plt.title('Find Pairs Time Complexity')
plt.show()
