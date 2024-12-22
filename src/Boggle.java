import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class Boggle {
    private BoggleGrid grid;
    private ArrayList<String> possibleWords;
    private ArrayList<String> wordsInBoard;

    public Boggle() {
        grid = new BoggleGrid();
        wordsInBoard = new ArrayList<>();
        possibleWords = new ArrayList<>();
        loadWordsFromFile("bogwords.txt"); // Load valid words from the file
    }

    public BoggleGrid getGrid() {
        return grid;
    }

    public ArrayList<String> wordsToArrayList() {
        HashSet<String> uniqueWords = new HashSet<>(); // To Store unique words in hash set
        HashSet<String> prefixes = generatePrefixes(); // Generate prefixes
        int rows = 4, cols = 4;

        // DFS for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] visited = new boolean[rows][cols];
                dfs(i, j, new StringBuilder(), visited, uniqueWords, prefixes);
            }
        }

        wordsInBoard = new ArrayList<>(uniqueWords); // Convert HashSet to ArrayList
        wordsInBoard.sort(String::compareTo);
        
        return wordsInBoard;
    }

    private void dfs(int row, int col, StringBuilder currentWord, boolean[][] visited, HashSet<String> uniqueWords, HashSet<String> prefixes) {
        if (row < 0 || col < 0 || row >= 4 || col >= 4 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        currentWord.append(Character.toLowerCase(grid.getSquare(row, col)));    // Lowercase because bogwords.txt is lowercase

        // Stop (return) if no longer possible to make word
        if (!prefixes.contains(currentWord.toString())) {
            visited[row][col] = false;
            currentWord.deleteCharAt(currentWord.length() - 1);
            return;
        }

        if (possibleWords.contains(currentWord.toString())) {
            uniqueWords.add(currentWord.toString());
        }

        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Try dfs on all possible directions
        for (int k = 0; k < 8; k++) {
            dfs(row + rowOffsets[k], col + colOffsets[k], currentWord, visited, uniqueWords, prefixes);
        }

        visited[row][col] = false;  // Backtrack so can visit this square again for other paths
        currentWord.deleteCharAt(currentWord.length() - 1);

    }

    private HashSet<String> generatePrefixes() {
        HashSet<String> prefixes = new HashSet<>();
        for (String word : possibleWords) {
            for (int i = 1; i <= word.length(); i++) {
                prefixes.add(word.substring(0, i));
            }
        }
        return prefixes;
    }

    public int totalScore() {
        // Calculate the total possible score for the board
        int total_score = 0;

        for(String word : wordsInBoard) {
            total_score += word.length();
        }

        return total_score;
    }

    public WordDictionary getWordsInBoard() {
        return new WordDictionary(wordsInBoard);
    }

    private void loadWordsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                possibleWords.add(line.trim()); // Add each word to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading the word file: " + fileName);
            e.printStackTrace();
        }
    }
}

class WordDictionary {
    private ArrayList<String> words;

    public WordDictionary(ArrayList<String> words) {
        this.words = words;
    }

    public int getVal(String word) {
        // Return the value for the word if it exists, -1 otherwise
        return words.contains(word) ? word.length() : -1;
    }
}
