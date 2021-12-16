#include <iostream>
#include <vector>
#include <array>
#include <utility>

using namespace std;

typedef pair<int, bool> BingoNumber;
typedef array<BingoNumber, 5> Row;
typedef array<Row, 5> Board;

vector<int> splitAtComma(const string& numbersString){ //not the cleaniest way probably
    vector<string> numbersSplit;
    vector<int> numbers;
    int i = 0;
    for(int j = 0; j < numbersString.size(); ++j){
        char current = numbersString[j];
        if(current != ','){
            if(i == numbersSplit.size())
                numbersSplit.push_back("");
            numbersSplit[i].push_back(current);
        }
        else{
            numbers.push_back( stoi(numbersSplit[i]) );
            ++i;
        }
    }
    numbers.push_back( stoi(numbersSplit[i]) ); //last digit
    return numbers;    
}

void prettyPrintVecOfBoards(const vector<Board*>& boards){
    for(int b = 0; b < boards.size(); ++b){
        Board cur = *(boards[b]);
        for(int r = 0; r < cur.size(); ++r){
            for(int c = 0; c < cur[r].size(); ++c){
                cout << cur[r][c].first << " ";
            }
            cout << endl;
        }
        cout << endl;
    }
}

vector<Board*> copy(const vector<Board*>& from){
    vector<Board*> to;
    for(Board* b : from){
        to.push_back(new Board(*b));
    }
    return to;
}

bool checkBingo(const Board& board){
    for(int r = 0; r < board.size(); ++r){
        for(int c = 0; c < board[r].size(); ++c){
            if(not board[r][c].second){
                break;
            }
            if(c == board[r].size() - 1){
                return true;
            }
        }
    }
    for(int c = 0; c < board[0].size(); ++c){
        for(int r = 0; r < board.size(); ++r){
            if(not board[r][c].second){
                break;
            }
            if(r == board.size() - 1){
                return true;
            }
        }
    }
}

int result(int n, const Board& board){
    int sumUnmarked = 0;
    for(int r = 0; r < board.size(); ++r){
        for(int c = 0; c < board[r].size(); ++c){
            if(not board[r][c].second)
                sumUnmarked += board[r][c].first;
        }
    }
    return n * sumUnmarked;
}

void firstToWin(const vector<int>& numbers, vector<Board*> boards){
    for(int n = 0; n < numbers.size(); ++n){
        for(Board* cur : boards){
            for(int r = 0; r < cur->size(); ++r){
                for(int c = 0; c < (*cur)[r].size(); ++c){
                    if(not (*cur)[r][c].second /*small optimization*/ and (*cur)[r][c].first == numbers[n]){
                        (*cur)[r][c].second = true;
                        if(checkBingo(*cur)){
                            cout << "Part 1: " << result(numbers[n], *cur) << endl;
                            return;
                        }
                    }
                }
            }
        }
    }
}

void lastToWin(const vector<int>& numbers, vector<Board*> boards){
    for(int n = 0; n < numbers.size(); ++n){
        for(auto it = boards.begin(); it != boards.end();){
            Board* cur = *it;
            bool breakInner = false;
            for(int r = 0; r < cur->size() and not breakInner; ++r){
                for(int c = 0; c < (*cur)[r].size() and not breakInner; ++c){
                    if(not (*cur)[r][c].second /*small optimization*/ and (*cur)[r][c].first == numbers[n]){
                        (*cur)[r][c].second = true;
                        if(checkBingo(*cur)){
                            if(boards.size() == 1){
                                cout << "Part 2: " << result(numbers[n], *cur) << endl;
                                return;
                            }
                            else{
                                breakInner = true;
                                it = boards.erase(it);
                            }
                        }
                    }
                }
            }
            if(not breakInner)
                ++it;
        }
    }
}

int main()
{
    string numbersString;
    cin >> numbersString;
    vector<int> numbers = splitAtComma(numbersString);
    
    vector<Board*> boards;
    int b = 0; //current board
    int r = 0; //current row
    int c = 0; //current column
    
    string numString;
    while(cin >> numString){
        if(c == 0 and r == 0) //new board
            boards.push_back(new Board);
        
        int num = stoi(numString);
        (*boards[b])[r][c] = make_pair(num, false);
        ++c;
        if(c == 5){
            ++r;
            c = 0;
        }
        if(r == 5){
            ++b;
            r = 0;
        }
    }
    firstToWin(numbers, copy(boards));
    lastToWin(numbers, copy(boards));
}
 
