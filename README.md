# Boggle Game

A Java-based implementation of the Boggle game with a graphical interface using the ACM library.

I made this game in a highschool computer science class in 2021.

---

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java Development Kit (JDK) 8**
- **ACM Library** (already included in the `lib/` folder)

---

## Project Structure

The repository is organized as follows:

- **`lib/`**: Contains the ACM library required for the game.
  - `acm.jar`: The core ACM library file.
  
- **`sounds/`**: Contains sound files used in the game.
  - `bounce.wav`: Played when selecting an already-found word.
  - `sfx-cartoons10.wav`: Played when a new valid word is found.

- **`src/`**: Contains the source code for the project:
  - `Boggle.java`: Core game logic.
  - `BoggleGraphics.java`: Handles the graphical interface.
  - `BoggleGrid.java`: Manages the game board.

- **`bogwords.txt`**: Dictionary file containing valid words.

---

## How to Run

### Cloning the Repository

1. Clone the repository using the following commands:
   ```bash
   git clone https://github.com/cbolson22/BoggleGame.git
   cd BoggleGame
   ```

---

### Running the Game in VS Code

The repository includes a .vscode/ folder to simplify running and debugging the game in **Visual Studio Code.**
**Steps:**

1. Open the project in **Visual Studio Code.**
2. Navigate to the **Run and Debug Tab** (Shortcut: **`Shift + Command + D`**).
3. Select **`Run BoggleGraphics`** from the available configurations.
4. Click the green **Run** button in the top left corner to start the game.

---

### Configuring Java Path (Optional)

If your Java 8 installation path differs from the default in **`settings.json`**, update the file as follows:

1. Open **`.vscode/settings.json`**.
2. Replace the existing path with your Java 8 installation path:
  - **For macOS:**
  ```bash
  /usr/libexec/java_home -v 1.8
  ```
  - **For Windows or Linux:** Locate your JDK 8 installation manually and update the path.

---

### Notes
- Ensure you have Java 8 installed and properly set up before running the game.
- For questions or issues, feel free to contact me or submit an issue on the GitHub repository.
