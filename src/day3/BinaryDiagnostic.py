import os
from functools import reduce


def count_zeros_and_ones(list_of_binary_numbers):
    count_zeros_in_fun = []
    count_ones_in_fun = []
    for line in list_of_binary_numbers:
        for index, c in enumerate(line):
            if index == len(count_zeros_in_fun):  # len(count_zeros_in_fun) == len(count_ones_in_fun)
                count_zeros_in_fun.append(0)
                count_ones_in_fun.append(0)
            if c == '0':
                count_zeros_in_fun[index] += 1
            else:
                count_ones_in_fun[index] += 1
    return count_zeros_in_fun, count_ones_in_fun


if __name__ == "__main__":
    root = os.path.dirname(__file__)
    path = os.path.join(root, 'input.txt')
    with open(path, 'r') as input_file:
        input_list = input_file.readlines()
    input_list = list(map(lambda l: l.strip('\n'), input_list))

    count_zeros, count_ones = count_zeros_and_ones(input_list)

    gamma = 0
    epsilon = 0
    for i in range(0, len(count_zeros)):  # len(count_zeros) == len(count_ones)
        # since the lists are inverted we "reverse" the index
        gamma += (0 if count_zeros[i] > count_ones[i] else 1) * (2 ** (len(count_zeros) - 1 - i))
        epsilon += (1 if count_zeros[i] > count_ones[i] else 0) * (2 ** (len(count_zeros) - 1 - i))

    print("Gamma: " + str(gamma))
    print("Epsilon: " + str(epsilon))
    print("Part 1: " + str(gamma * epsilon))

    filtered_oxygen = input_list.copy()
    current_digit = 0
    while len(filtered_oxygen) != 1 and current_digit < len(count_zeros):  # not specified, but just to be safe
        filter_digit = 0 if count_zeros[current_digit] > count_ones[current_digit] else 1
        filtered_oxygen = list(filter(lambda n: n[current_digit] == str(filter_digit), filtered_oxygen))
        current_digit += 1
        count_zeros, count_ones = count_zeros_and_ones(filtered_oxygen)

    filtered_CO2 = input_list.copy()
    current_digit = 0
    while len(filtered_CO2) != 1 and current_digit < len(count_zeros):  # not specified, but just to be safe
        filter_digit = 1 if count_zeros[current_digit] > count_ones[current_digit] else 0
        filtered_CO2 = list(filter(lambda n: n[current_digit] == str(filter_digit), filtered_CO2))
        current_digit += 1
        count_zeros, count_ones = count_zeros_and_ones(filtered_CO2)

    oxygen = 0
    CO2 = 0
    for i in range(0, len(filtered_oxygen[0])):  # len(filtered_oxygen[0]) == len(filtered_CO2[0])
        oxygen += int(filtered_oxygen[0][i]) * (2 ** (len(filtered_oxygen[0]) - 1 - i))
        CO2 += int(filtered_CO2[0][i]) * (2 ** (len(filtered_CO2[0]) - 1 - i))

    print("Oxygen: " + str(oxygen))
    print("CO2: " + str(CO2))
    print("Part 2: " + str(oxygen * CO2))
