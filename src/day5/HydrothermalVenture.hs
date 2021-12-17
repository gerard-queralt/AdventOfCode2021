import Data.List.Split --sudo apt-get install libghc-split-dev

drawHydroMap :: [String] -> [[String]] -> [[String]]
drawHydroMap [] hydroMap = hydroMap
drawHydroMap (coord:cl) hydroMap = drawHydroMap cl updatedMap
    where
        updatedMap = insertCoords x1 y1 x2 y2 hydroMap
        [(x1,y1),(x2,y2)] = extractCoords coord
        
extractCoords :: String -> [(Integer, Integer)]
extractCoords coord = [(x1, y1), (x2, y2)]
    where
        [coord1, coord2] = splitOn " -> " coord
        [x1, y1] = map (\s -> read s :: Integer) $ splitOn "," coord1
        [x2, y2] = map (\s -> read s :: Integer) $ splitOn "," coord2
        
insertCoords :: Integer -> Integer -> Integer -> Integer -> [[String]] -> [[String]]
insertCoords x1 y1 x2 y2 hydroMap = populateMap 0 0 hydroMap
    where
        populateMap _ _ [] = []
        populateMap curX curY (row:curMap) = (populateRow curX curY row):(populateMap curX (curY + 1) curMap)
        populateRow _ _ [] = []
        populateRow curX curY (column:row) = (populateColumn curX curY column):(populateRow (curX + 1) curY row)
        populateColumn _ _ [] = []
        populateColumn curX curY column = if x1 <= curX && curX <= x2 && 
                                             y1 <= curY && curY <= y2
                                             then "1"
                                             else column

main :: IO ()
main = do
    inputFile <- readFile "input.txt"
    let inputLines = lines inputFile
    let initializedMap = [[".","."],[".","."]]
    let hydroMap = drawHydroMap inputLines initializedMap
    mapM_ printRow hydroMap

printRow :: [String] -> IO ()
printRow [] = do
    putStrLn ""
printRow (l:ls) = do
    putStr l
    printRow ls
