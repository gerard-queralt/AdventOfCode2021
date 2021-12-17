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
insertCoords x1 y1 x2 y2 hydroMap = populate 0 0 hydroMap
    where
        populate curX curY (row:curMap)
            | curX == x2 && curY == y2 = (row:curMap)
            | curX >= x1 && curY >= y1 = ((incrementCount (row:curMap)):row):next
            | otherwise = (".":row):next
                where
                    next = populate newX newY $ advance curX curY (row:curMap)
                    newX = if curX == x2 then 0 else curX + 1
                    newY = if newX == 0 then curY + 1 else curY
        incrementCount (([]:row):curMap) = "1"
        incrementCount ((column:row):curMap)
            | column == "." = "1"
            | otherwise = show (1 + read(column)::Integer)
        advance curX curY (row:curMap) = if curX == x2 then curMap else if null row then ([]:curMap) else ((tail row):curMap)

main :: IO ()
main = do
    inputFile <- readFile "input.txt"
    let inputLines = lines inputFile
    let hydroMap = drawHydroMap inputLines [[".",".","."],[".",".","."],[".",".","."]]
    mapM_ printRow hydroMap

printRow :: [String] -> IO ()
printRow [] = do
    putStrLn ""
printRow (l:ls) = do
    putStr l
    printRow ls
