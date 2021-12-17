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
insertCoords x1 y1 x2 y2 hydroMap = if x1 == x2 || y1 == y2 then populateHorVer else populateDia
    where
        populateHorVer = populateMapHorVer 0 0 hydroMap
            where
                ((minX, minY),(maxX, maxY)) = ((min x1 x2, min y1 y2),(max x1 x2, max y1 y2))
                populateMapHorVer _ _ [] = []
                populateMapHorVer curX curY (row:curMap)
                    | curY > maxY = (row:curMap)
                    | otherwise = (populateRow curX curY row):(populateMapHorVer curX (curY + 1) curMap)
                populateRow _ _ [] = []
                populateRow curX curY (column:row)
                    | curX > maxX = (column:row)
                    | otherwise = (populateColumn curX curY column):(populateRow (curX + 1) curY row)
                populateColumn _ _ [] = []
                populateColumn curX curY column = if minX <= curX && curX <= maxX && 
                                                    minY <= curY && curY <= maxY
                                                    then increment column
                                                    else column
        
        populateDia = populateMapDia 0 0 (minX, minY) hydroMap
            where
                ((minX, minY),(maxX, maxY)) = if y1 < y2 then ((x1, y1),(x2, y2)) else ((x2, y2),(x1, y1))
                incOrDecX = if minX < maxX then (\x -> x + 1) else (\x -> x - 1)
                populateMapDia _ _ _ [] = []
                populateMapDia curX curY (targetX, targetY) (row:curMap)
                    | curY > maxY = (row:curMap)
                    | otherwise = if newRow == row
                                     then row:(populateMapDia curX (curY + 1) (targetX, targetY) curMap)
                                     else newRow:(populateMapDia curX (curY + 1) (incOrDecX targetX, targetY + 1) curMap)
                                     where newRow = (populateRow curX curY (targetX, targetY) row)
                populateRow _ _ _ [] = []
                populateRow curX curY (targetX, targetY) (column:row)
                    | curX > targetX = (column:row)
                    | otherwise = (populateColumn curX curY (targetX, targetY) column):(populateRow (curX + 1) curY (targetX, targetY) row)
                populateColumn _ _ _ [] = []
                populateColumn curX curY (targetX, targetY) column = if curX == targetX &&
                                                                        curY == targetY
                                                                        then increment column
                                                                        else column
        
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
