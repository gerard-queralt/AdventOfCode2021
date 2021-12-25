import os
from functools import reduce


def compute_risk_level():
    risk_level = 0
    for i in range(0, len(input_list)):
        for j in range(0, len(input_list[i])):
            current = input_list[i][j]
            if 0 < i and input_list[i - 1][j] <= current:
                continue
            if i < len(input_list) - 1 and input_list[i + 1][j] <= current:
                continue
            if 0 < j and input_list[i][j - 1] <= current:
                continue
            if j < len(input_list[i]) - 1 and input_list[i][j + 1] <= current:
                continue
            risk_level += 1 + current
    return risk_level


def compute_largest_basins():
    largest_basins = [0, 0, 0]
    for i in range(0, len(input_list)):
        for j in range(0, len(input_list[i])):
            current = input_list[i][j]
            if 0 < i and input_list[i - 1][j] <= current:
                continue
            if i < len(input_list) - 1 and input_list[i + 1][j] <= current:
                continue
            if 0 < j and input_list[i][j - 1] <= current:
                continue
            if j < len(input_list[i]) - 1 and input_list[i][j + 1] <= current:
                continue
            size = size_of_basin(i, j)
            if size > largest_basins[0]:
                largest_basins[0] = size
                largest_basins.sort()
    return reduce(lambda acc, s: acc * s, largest_basins)


def size_of_basin(i, j, visited=None):
    if visited is None:
        visited = []

    if (i, j) in visited or \
            i not in range(0, len(input_list)) or \
            j not in range(0, len(input_list[i])) or \
            input_list[i][j] == 9:
        return 0
    visited.append((i, j))
    size = 1 + size_of_basin(i - 1, j, visited) + size_of_basin(i + 1, j, visited) + \
        size_of_basin(i, j - 1, visited) + size_of_basin(i, j + 1, visited)
    return size


if __name__ == "__main__":
    root = os.path.dirname(__file__)
    path = os.path.join(root, 'input.txt')
    with open(path, 'r') as input_file:
        # for each line we remove the line break and cast each number to int
        input_list = list(map(lambda l: list(map(lambda c: int(c), l.strip('\n'))), input_file.readlines()))

    print("Part 1: " + str(compute_risk_level()))
    print("Part 2: " + str(compute_largest_basins()))
