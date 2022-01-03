#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

pair<int, int> createPair(string s){
    string first;
    int i;
    for(i = 0; i < s.size() && s[i] != ','; ++i){
        first.push_back(s[i]);
    }
    ++i; //skip the comma
    string second;
    for(i; i < s.size(); ++i){
        second.push_back(s[i]);
    }
    return make_pair(stoi(first), stoi(second));
}

pair<int, int> maxXmaxY(vector<pair<int, int>> coords){
    int maxX = 0;
    int maxY = 0;
    for(int i = 0; i < coords.size(); ++i){
        pair<int, int> coord = coords[i];
        if(coord.first > maxX)
            maxX = coord.first;
        if(coord.second > maxY)
            maxY = coord.second;
    }
    return make_pair(maxX, maxY);
}

void emptyPaper(vector<vector<char>>& paper, int maxX, int maxY){
    for(int i = 0; i <= maxY; ++i){
        vector<char> line;
        for(int j = 0; j <= maxX; ++j){
            line.push_back('.');
        }
        paper.push_back(line);
    }
}

void drawPoints(vector<vector<char>>& paper, const vector<pair<int, int>>& pointCoords){
    for(int i = 0; i < pointCoords.size(); ++i){
        paper[pointCoords[i].second][pointCoords[i].first] = '#';
    }
}

void foldOnce(vector<vector<char>>& paper, string instruction){
    bool foldHor = instruction[0] == 'x';
    string lineString;
    for(int i = 2; i < instruction.size(); ++i){
        lineString.push_back(instruction[i]);
    }
    int line = stoi(lineString);
    vector<vector<char>> newPaper;
    if(foldHor){
        for(int i = 0; i < paper.size(); ++i){
            vector<char> newLine;
            int opposite;
            for(int j = line + 1; j < paper[i].size(); ++j){
                opposite = j - 2 * (j - line);
                if(paper[i][j] == '#' || paper[i][opposite] == '#')
                    newLine.push_back('#');
                else
                    newLine.push_back('.');
            }
            for(int j = opposite - 1; 0 <= j; ++j){
                newLine.push_back(paper[i][j]);
            }
            reverse(newLine.begin(), newLine.end());
            newPaper.push_back(newLine);
        }
    } else {
        int opposite;
        for(int i = line + 1; i < paper.size(); ++i){
            vector<char> newLine;
            opposite = i - 2 * (i - line);
            for(int j = 0; j < paper[0].size(); ++j){
                if(paper[i][j] == '#' || paper[opposite][j] == '#')
                    newLine.push_back('#');
                else
                    newLine.push_back('.');
            }
            newPaper.push_back(newLine);
        }
        for(int i = opposite - 1; 0 <= i; --i){
            newPaper.push_back(paper[i]);
        }
        reverse(newPaper.begin(), newPaper.end());
    }
    paper = newPaper;
}

int countPoints(const vector<vector<char>>& paper){
    int count = 0;
    for(int i = 0; i < paper.size(); ++i){
        for(int j = 0; j < paper[i].size(); ++j){
            if(paper[i][j] == '#')
                ++count;
        }
    }
    return count;
}

void showPaper(const vector<vector<char>>& paper){
    for(int i = 0; i < paper.size(); ++i){
        for(int j = 0; j < paper[i].size(); ++j){
            cout << paper[i][j];
        }
        cout << endl;
    }
}

int main(){
    vector<vector<char>> paper;
    vector<pair<int, int>> pointCoords;
    vector<string> fold;
    
    fstream inputFile;
    inputFile.open("input.txt",ios::in);
    if (inputFile.is_open()){
        string line;
        while(getline(inputFile, line) && line != ""){
            pointCoords.push_back(createPair(line));
        }
        while(getline(inputFile, line)){
            int i;
            for(i = 0; i < line.size() && line[i] != 'x' && line[i] != 'y'; ++i); //"fold along" contains neither 'x' nor 'y'
            if(i < line.size()){
                string instruction;
                for(i; i < line.size(); ++i){
                    instruction.push_back(line[i]);
                }
                fold.push_back(instruction);
            }
        }
        inputFile.close();
    }
    
    pair<int, int> max = maxXmaxY(pointCoords);
    emptyPaper(paper, max.first, max.second);
    drawPoints(paper, pointCoords);
    foldOnce(paper, fold[0]);
    int pointsAfterFirst = countPoints(paper);
    cout << "Part 1: " << pointsAfterFirst << endl;
    for(int i = 1; i < fold.size(); ++i){
        foldOnce(paper, fold[i]);
    }
    showPaper(paper);
}
