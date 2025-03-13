import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordSearchGame {

    // --- Grid Class ---
    static class Grid {
        private int size;
        private char[][] grid;
        private Random random = new Random();

        public Grid(int size) {
            this.size = size;
            this.grid = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    grid[i][j] = ' ';  // Initialize with empty spaces
                }
            }
        }

        public void fillEmptySpaces() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (grid[row][col] == ' ') {
                        grid[row][col] = (char) (random.nextInt(26) + 'A'); // Fill with random capital letters
                    }
                }
            }
        }

        public void displayGrid() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    System.out.print(grid[row][col] + " ");
                }
                System.out.println();
            }
        }

        public void placeWord(String word, int row, int col, String direction) {
            word = word.toUpperCase();

            if (direction.equals("horizontal")) {
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col + i] = word.charAt(i);
                }
            } else if (direction.equals("vertical")) {
                for (int i = 0; i < word.length(); i++) {
                    grid[row + i][col] = word.charAt(i);
                }
            } else if (direction.equals("diagonal")) {
                for (int i = 0; i < word.length(); i++) {
                    grid[row + i][col + i] = word.charAt(i);
                }
            }
        }

        public boolean isValidPlacement(String word, int row, int col, String direction) {
            word = word.toUpperCase();

            if (direction.equals("horizontal")) {
                if (col + word.length() > size) {
                    return false;
                }
                for (int i = 0; i < word.length(); i++) {
                    if (grid[row][col + i] != ' ' && grid[row][col + i] != word.charAt(i)) {
                        return false;
                    }
                }
            } else if (direction.equals("vertical")) {
                if (row + word.length() > size) {
                    return false;
                }
                for (int i = 0; i < word.length(); i++) {
                    if (grid[row + i][col] != ' ' && grid[row + i][col] != word.charAt(i)) {
                        return false;
                    }
                }
            } else if (direction.equals("diagonal")) {
                if (row + word.length() > size || col + word.length() > size) {
                    return false;
                }
                for (int i = 0; i < word.length(); i++) {
                    if (grid[row + i][col + i] != ' ' && grid[row + i][col + i] != word.charAt(i)) {
                        return false;
                    }
                }
            }
            return true;
        }

        public int getSize() {
            return size;
        }

        public char[][] getGrid() {
            return grid;
        }
    }

    // --- WordManager Class ---
    static class WordManager {
        private String wordFile;
        private List<String> words;
        private Set<String> foundWords = new HashSet<>();
        private Random random = new Random();

        public WordManager(String wordFile) {
            this.wordFile = wordFile;
            this.words = loadWords();
        }

        private List<String> loadWords() {
            List<String> wordList = new ArrayList<>();
            try {
                File file = new File(wordFile);
                System.out.println("Current working directory: " + System.getProperty("user.dir"));
                System.out.println("Listing files in data directory:");
                //**Explicit path to the data directory**
                File dataDir = new File("C:\\Users\\Admin\\Documents\\WordSearchGame\\data");

                if (dataDir.exists() && dataDir.isDirectory()) {
                    for (String fileName : dataDir.list()) {
                        System.out.println(fileName);
                    }
                } else {
                    System.out.println("Data directory not found.");
                }
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim().toUpperCase();
                    wordList.add(word);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Word file '" + wordFile + "' not found.");
                System.exit(1); // Terminate the game if the file is not found
            }
            return wordList;
        }

        public void hideWords(Grid grid) {
            String[] directions = {"horizontal", "vertical", "diagonal"};
            List<String> placedWords = new ArrayList<>();

            // Collections.sort(words, (a, b) -> b.length() - a.length()); // Sort descending by length

            for (String word : words) {
                boolean placed = false;
                for (int i = 0; i < 500; i++) { // Try more times
                    String direction = directions[random.nextInt(directions.length)];
                    int row = random.nextInt(grid.getSize());
                    int col = random.nextInt(grid.getSize());

                    if (grid.isValidPlacement(word, row, col, direction)) {
                        grid.placeWord(word, row, col, direction);
                        placedWords.add(word);
                        placed = true;
                        break;
                    }
                }
                if (!placed) {
                    System.out.println("Warning: Could not place word '" + word + "' in the grid.");
                }
            }
            this.words = placedWords;
        }

        public boolean checkWord(Grid grid, int startRow, int startCol, int endRow, int endCol) {
            String word = extractWordFromGrid(grid, startRow, startCol, endRow, endCol);
            if (words.contains(word) && !foundWords.contains(word)) {
                foundWords.add(word);
                return true;
            }
            return false;
        }

        private String extractWordFromGrid(Grid grid, int startRow, int startCol, int endRow, int endCol) {
            StringBuilder word = new StringBuilder();
            char[][] gameGrid = grid.getGrid();

            if (startRow == endRow) { // Horizontal
                if (startCol <= endCol) {
                    for (int col = startCol; col <= endCol; col++) {
                        word.append(gameGrid[startRow][col]);
                    }
                } else {
                    for (int col = startCol; col >= endCol; col--) {
                        word.append(gameGrid[startRow][col]);
                    }
                }
            } else if (startCol == endCol) { // Vertical
                if (startRow <= endRow) {
                    for (int row = startRow; row <= endRow; row++) {
                        word.append(gameGrid[row][startCol]);
                    }
                } else {
                    for (int row = startRow; row >= endRow; row--) {
                        word.append(gameGrid[row][startCol]);
                    }
                }
            } else { // Diagonal
                if (startRow <= endRow && startCol <= endCol) {
                    int row = startRow;
                    int col = startCol;
                    while (row <= endRow && col <= endCol) {
                        word.append(gameGrid[row][col]);
                        row++;
                        col++;
                    }
                } else if (startRow >= endRow && startCol >= endCol) {
                    int row = startRow;
                    int col = startCol;
                    while (row >= endRow && col >= endCol) {
                        word.append(gameGrid[row][col]);
                        row--;
                        col--;
                    }
                } else if (startRow <= endRow && startCol >= endCol) {
                    int row = startRow;
                    int col = startCol;
                    while (row <= endRow && col >= endCol) {
                        word.append(gameGrid[row][col]);
                        row++;
                        col--;
                    }
                } else if (startRow >= endRow && startCol <= endCol) {
                    int row = startRow;
                    int col = startCol;
                    while (row >= endRow && col <= endCol) {
                        word.append(gameGrid[row][col]);
                        row--;
                        col++;
                    }
                }
            }

            return word.toString();
        }

        public List<String> getRemainingWords() {
            List<String> remaining = new ArrayList<>(words);
            remaining.removeAll(foundWords);
            return remaining;
        }

        public List<String> getWords() {
            return words;
        }

        public Set<String> getFoundWords() {
            return foundWords;
        }
    }

    // --- GameLogic Class ---
    static class GameLogic {
        private int gridSize;
        private String wordFile;
        private Grid grid;
        private WordManager wordManager;
        private boolean gameOver = false;
        private Scanner scanner = new Scanner(System.in);

        public GameLogic(int gridSize, String wordFile) {
            this.gridSize = gridSize;
            this.wordFile = wordFile;
            this.grid = new Grid(gridSize);
            this.wordManager = new WordManager(wordFile);
            this.wordManager.hideWords(this.grid);
            this.grid.fillEmptySpaces();
        }

        public void playTurn() {
            grid.displayGrid();
            System.out.println("Words left: " + wordManager.getRemainingWords().size());
            System.out.println("Enter coordinates (row1, col1, row2, col2) or 'quit', 'new', 'show':");
            String userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.equals("QUIT")) {
                gameOver = true;
                System.out.println("Quitting the game.");
                return;
            } else if (userInput.equals("NEW")) {
                startNewGame();
                System.out.println("Starting a new game.");
                return;
            } else if (userInput.equals("SHOW")) {
                System.out.println("Words found: " + wordManager.getFoundWords());
                System.out.println("Words remaining: " + wordManager.getRemainingWords());
                return;
            }

            try {
                String[] coords = userInput.split(",");
                if (coords.length != 4) {
                    throw new IllegalArgumentException("Invalid number of coordinates.");
                }

                int startRow = Integer.parseInt(coords[0].trim());
                int startCol = Integer.parseInt(coords[1].trim());
                int endRow = Integer.parseInt(coords[2].trim());
                int endCol = Integer.parseInt(coords[3].trim());

                if (!Utils.validateCoordinates(gridSize, startRow, startCol, endRow, endCol)) {
                    throw new IllegalArgumentException("Coordinates are out of bounds.");
                }

                if (wordManager.checkWord(grid, startRow, startCol, endRow, endCol)) {
                    System.out.println("Correct! Word found.");
                    if (wordManager.getRemainingWords().isEmpty()) {
                        gameOver = true;
                        System.out.println("Congratulations! You found all the words!");
                    }
                } else {
                    System.out.println("Incorrect. Try again.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }

        public void startGame() {
            while (!gameOver) {
                playTurn();
            }
        }

        // Placeholder for the startNewGame method
        public void startNewGame() {
            // Implement the logic to start a new game
        }
    }

    // --- Utils Class ---
    static class Utils {
        public static boolean validateCoordinates(int gridSize, int row1, int col1, int row2, int col2) {
            return (row1 >= 0 && row1 < gridSize &&
                    col1 >= 0 && col1 < gridSize &&
                    row2 >= 0 && row2 < gridSize &&
                    col2 >= 0 && col2 < gridSize);
        }
    }

    // --- Main Method ---
    public static void main(String[] args) {
        int gridSize = 15; // Increase grid size
        String wordFile = "data/words1.txt"; // Make sure the word list exists in this relative path
        GameLogic game = new GameLogic(gridSize, wordFile);
        game.startGame();
    }
}