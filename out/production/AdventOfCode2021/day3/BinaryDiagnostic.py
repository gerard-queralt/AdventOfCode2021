import os

if __name__ == "__main__":
    root = os.path.dirname(__file__)
    path = os.path.join(root, 'input.txt')
    with open(path, 'r') as inputFile:
        inputList = inputFile.readlines()
    countZeros = []
    countOnes = []
    for line in inputList:
        line = line.strip('\n')
        for index, c in enumerate(line):
            if index == len(countZeros):  # len(countZeros) == len(countOnes)
                countZeros.append(0)
                countOnes.append(0)
            if c == '0':
                countZeros[index] += 1
            else:
                countOnes[index] += 1

    gamma = 0
    epsilon = 0
    for i in range(0, len(countZeros)):  # len(countZeros) == len(countOnes)
        #  since the lists are inverted we "reverse" the index
        gamma += (0 if countZeros[i] > countOnes[i] else 1) * (2 ** (len(countZeros) - 1 - i))
        epsilon += (1 if countZeros[i] > countOnes[i] else 0) * (2 ** (len(countZeros) - 1 - i))

    print("Gamma: " + str(gamma))
    print("Epsilon: " + str(epsilon))
    print("Part 1: " + str(gamma * epsilon))
