import Data.List.Split --sudo apt-get install libghc-split-dev

initializeMap :: [String] -> [[String]]
initializeMap cl = initializedMap
    where
        coords = concatMap extractCoords cl
        xcoords = map (\(x, _) -> x) coords
        ycoords = map (\(_, y) -> y) coords
        maxX = maximum xcoords
        maxY = maximum ycoords
        row = initializeRow maxX
        initializedMap = addRows maxY
        
        initializeRow x
            | x < 0 = []
            | otherwise = ".":(initializeRow (x-1))
        addRows y
            | y < 0 = []
            | otherwise = row:(addRows (y-1))

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
        populateColumn curX curY column
            | x1 == x2 || y1 == y2 = if (min x1 x2) <= curX && curX <= (max x1 x2) && 
                                        (min y1 y2) <= curY && curY <= (max y1 y2)
                                        then increment column
                                        else column
            | otherwise = column -- tmp, for part 1
        increment column
            | column == "." = "1"
            | otherwise = show((read column :: Integer) + 1)
            
countOverlap :: [[String]] -> Integer
countOverlap hydroMap = countOverlapRec hydroMap 0
    where
        countOverlapRec [] n = n
        countOverlapRec (row:hydroMap) n = countOverlapRec hydroMap (countOverlapRow row n)
        countOverlapRow [] n = n
        countOverlapRow (column:row) n
            | column == "." || column == "1" = countOverlapRow row n
            | otherwise = countOverlapRow row (n + 1)

main :: IO ()
main = do
    inputFile <- readFile "input.txt"
    let inputLines = lines inputFile
    let initializedMap = initializeMap inputLines
    let hydroMap = drawHydroMap inputLines initializedMap
    --mapM_ printRow hydroMap
    print $ countOverlap hydroMap

printRow :: [String] -> IO ()
printRow [] = do
    putStrLn ""
printRow (l:ls) = do
    putStr l
    printRow ls
