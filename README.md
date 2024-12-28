# Boggle Game

A Java-based implementation of the Boggle game with a graphical interface using the ACM library.

I made this game in a highschool computer science class in 2021.

## Prerequisites

- Java Development Kit (JDK) 8.
- [ACM Library](https://jtf.acm.org/) included in the `lib` folder.

## Project Structure

- `lib/`: Contains the ACM library (`acm.jar`).

- `sounds/`: Contains sound files used in the game.
  - `bounce.wav`: Played when an already-found word is selected.
  - `sfx-cartoons10.wav`: Played when a new valid word is found.

- `src/`: Contains the source code (`Boggle.java`, `BoggleGraphics.java`, `BoggleGrid.java`).

- `bogwords.txt`: Dictionary file with valid words.


## How to Run

### Cloning Repository

1. Clone the repository:
   ```bash
   git clone https://github.com/cbolson22/BoggleGame.git
   cd BoggleGame
   ```


### VS Code Configuration

The repository includes a `.vscode/` folder with the following files:
- `launch.json`: Pre-configured debugging settings for running `BoggleGraphics`.
- `settings.json`: Specifies the path to Java 8 for the project.

### Customizing the Java Path
If your Java installation path differs from the default in `settings.json`, update it as follows:
1. Open `.vscode/settings.json`.
2. Replace `/Library/Java/JavaVirtualMachines/temurin-8.jdk/Contents/Home` with the path to your Java 8 installation.
   - On macOS, use:
     ```bash
     /usr/libexec/java_home -v 1.8
     ```
   - On Windows or Linux, locate your JDK path manually.


### Running on VS Code

1. Go to Run and Debug Tab (Shift + Command + D)
2. Run BoggleGraphics with Green Run Button in top left of VS Code