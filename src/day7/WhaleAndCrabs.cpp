#include <iostream>
#include <vector>

using namespace std; 

int maxOfVec(const vector<int>& numbers){
    int maxN = numbers[0];
    for(int n = 1; n < numbers.size(); ++n){
        int curN = numbers[n];
        if(maxN < curN){
            maxN = curN;
        }
    }
    return maxN;
}

int minOfVec(const vector<int>& numbers){
    int minN = numbers[0];
    for(int n = 1; n < numbers.size(); ++n){
        int curN = numbers[n];
        if(curN < minN){
            minN = curN;
        }
    }
    return minN;
}

long calculateFuelLinear(int pos, const vector<int>& numbers){
    long fuel = 0;
    for(int n = 0; n < numbers.size(); ++n){
        fuel += abs(numbers[n] - pos);
    }
    return fuel;
}

long calculateFuelExponential(int pos, const vector<int>& numbers){
    long fuel = 0;
    for(int n = 0; n < numbers.size(); ++n){
        long fuelOfStep = 0;
        int minN = min(numbers[n], pos);
        int maxN = max(numbers[n], pos);
        for(int s = minN + 1; s <= maxN; ++s){
            fuelOfStep += (s - minN);
        }
        fuel += fuelOfStep;
    }
    return fuel;
}

long align(const vector<int>& numbers, bool linear){
    int minN = minOfVec(numbers);
    int maxN = maxOfVec(numbers);
    long minFuel = 0;
    if(linear)
        minFuel = calculateFuelLinear(minN, numbers);
    else
        minFuel = calculateFuelExponential(minN, numbers);
    for(int n = minN + 1; n < maxN; ++n){
        long curFuel = 0;
        if(linear)
            curFuel = calculateFuelLinear(n, numbers);
        else
            curFuel = calculateFuelExponential(n, numbers);
        if(curFuel < minFuel)
            minFuel = curFuel;
    }
    return minFuel;
}

int main()
{
    string input;
    cin >> input;
    
    vector<string> numbersString;
    numbersString.push_back("");
    
    int n = 0;
    for(int s = 0; s < input.size(); ++s){
        if(input[s] != ','){
            numbersString[n] += input[s];
        }
        else{
            numbersString.push_back("");
            ++n;
        }
    }
    
    vector<int> numbers;
    for(int i = 0; i < numbersString.size(); ++i){
        numbers.push_back( stoi(numbersString[i]) );
    }
    cout << "Part 1: " << align(numbers, true) << endl;
    cout << "Part 2: " << align(numbers, false) << endl;
}
