import Data.List

isClosing :: Char -> Bool
isClosing c = c == ')' || c == ']' || c == '}' || c == '>'

closing :: Char -> Char
closing '(' = ')'
closing '[' = ']'
closing '{' = '}'
closing '<' = '>'

scorePart1 :: Char -> Int
scorePart1 ')' = 3
scorePart1 ']' = 57
scorePart1 '}' = 1197
scorePart1 '>' = 25137

scorePart2 :: Char -> Int
scorePart2 ')' = 1
scorePart2 ']' = 2
scorePart2 '}' = 3
scorePart2 '>' = 4

scoreCorrupted :: String -> [Char] -> Int
scoreCorrupted [] _ = 0
scoreCorrupted (c:chunk) expected
    | not $ isClosing c = scoreCorrupted chunk (closing c : expected)
    | otherwise = if c /= head expected then scorePart1 c else scoreCorrupted chunk (tail expected)
    
scoreIncomplete :: String -> [Char] -> Int
scoreIncomplete [] expected = foldl (\acc c -> 5 * acc + scorePart2 c) 0 expected
scoreIncomplete (c:chunk) expected
    | not $ isClosing c = scoreIncomplete chunk (closing c : expected)
    | otherwise = scoreIncomplete chunk (tail expected)
    
main :: IO ()
main = do
    inputFile <- readFile "input.txt"
    let inputLines = lines inputFile
    
    let part1Scores = map (\l -> scoreCorrupted l []) inputLines
    putStr "Part 1: "
    print $ sum part1Scores
    
    let incompleteLines = zipWith (\scoreC l -> if scoreC == 0 then l else "") part1Scores inputLines -- ignore the corrupted lines
    let part2Scores =  dropWhile (==0) $ sort $ map (\l -> scoreIncomplete l []) incompleteLines
    putStr "Part 2: "
    print $ head $ drop (length part2Scores `div` 2) part2Scores
