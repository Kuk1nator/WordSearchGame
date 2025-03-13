# WordSearchGame

A simple Java-based word search game. The program generates a grid of letters and hides a list of words within it. The user is then prompted to enter coordinates to find the hidden words.

## Features

*   **Customizable Grid Size:**  The size of the word search grid can be easily adjusted.
*   **Multiple Word Sets:** The game supports different word sets (e.g., fruits, animals, countries), allowing for varied gameplay.
*   **Random Word Placement:**  Words are placed randomly within the grid (horizontally, vertically, or diagonally).
*   **Coordinate-Based Input:** Users enter coordinates to identify the location of hidden words.
*   **Clear User Interface:**  The game provides a simple and intuitive text-based user interface.

## How to Play

1.  **Clone the Repository:**
    ```bash
    git clone [repository_url]
    cd WordSearchGame
    ```

2.  **Compile the Java Code:**
    ```bash
    javac WordSearchGame.java
    ```

3.  **Run the Game:**
    ```bash
    java WordSearchGame
    ```

4.  **Choose a Word Set:**
    The game will prompt you to select a word set from the available options (Fruits, Animals, Countries). Enter the corresponding number (1, 2, or 3).

5.  **Find the Words:**
    The game will display the word search grid.
    *   Enter coordinates in the format `row1,col1,row2,col2` to identify a word. Remember that row and column numbers start at 0.
    *   Type `quit` to exit the game.
    *   Type `new` to start a new game with the same word set.
    *   Type `show` to display the list of found and remaining words.

## Project Structure
