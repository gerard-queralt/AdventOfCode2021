import os
import copy


def find_paths(from_cave, visited_small=None):
    if visited_small is None:
        visited_small = []
    if from_cave == 'end':
        return 1
    count_paths = 0
    if from_cave.islower():  # start will also count as small, so we won't visit it twice
        visited_small.append(from_cave)
    for cave in paths[from_cave]:
        if cave not in visited_small:
            count_paths += find_paths(cave, copy.copy(visited_small))
    return count_paths


def only_one_repeated(visited_small):
    one_repeated = False
    already_checked = []
    for cave in visited_small:
        if cave not in already_checked:
            if visited_small.count(cave) == 2:
                if one_repeated:
                    return False
                else:
                    one_repeated = True
                already_checked.append(cave)
            elif visited_small.count(cave) > 2:
                return False
    return True


def find_paths_one_repeat(from_cave, visited_small=None):
    if visited_small is None:
        visited_small = []
    if from_cave == 'end':
        return 1
    count_paths = 0
    if from_cave.islower():
        visited_small.append(from_cave)
    for cave in paths[from_cave]:
        if cave != 'start' and only_one_repeated(visited_small):
            count_paths += find_paths_one_repeat(cave, copy.copy(visited_small))
    return count_paths


if __name__ == "__main__":
    root = os.path.dirname(__file__)
    path = os.path.join(root, 'input.txt')
    with open(path, 'r') as input_file:
        input_list = input_file.readlines()
    input_list = list(map(lambda l: l.strip('\n'), input_list))

    paths = {}
    for path in input_list:
        pathSplit = path.split('-')
        if paths.get(pathSplit[0]) is None:
            paths[pathSplit[0]] = []
        paths[pathSplit[0]].append(pathSplit[1])
        if paths.get(pathSplit[1]) is None:
            paths[pathSplit[1]] = []
        paths[pathSplit[1]].append(pathSplit[0])
    print("Part 1:", find_paths('start'))
    print("Part 2:", find_paths_one_repeat('start'))
