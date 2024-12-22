

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.io.IODialog;
import acm.program.GraphicsProgram;
import acm.graphics.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BoggleGraphics extends GraphicsProgram{
	
	private static final int SPACE_BETWEEN_SQUARES = 10;
	
	private Boggle boggle;
	
	private IODialog dialog = getDialog();
	
	private GRect lastRect;
	
	private String word = "";
	private ArrayList<String> words;
	
	private int score;
	private int totalScore;
	private boolean timeLeft;

	private GRect scoreRect;
	private GRect progressBar;
	
	
	public void run() {
		addMouseListeners();
		
		runGame();
		pause(2000);
		String userInput = dialog.readLine("Do you want to play again (press \"y\" for yes)?").toLowerCase();
		while(userInput.equals("y")) {
			runGame();
			pause(2000);
			userInput = dialog.readLine("Do you want to play again (press \"y\" for yes)?").toLowerCase();
		}
		System.exit(0);
	}
	
	private void runGame() {
		boggle = new Boggle();
		words = boggle.wordsToArrayList();
		setUpGraphics();
		timer(getTime());
		unfillAll();
		addAllWordLabels();
	}
	
	private void setUpGraphics() {
		GRect windowBackround = new GRect(0,0,800,600);
		windowBackround.setFillColor(Color.LIGHT_GRAY);
		windowBackround.setFilled(true);
		add(windowBackround);
		GLabel welcome = new GLabel("BOGGLE",  230, 30);
		welcome.setFont("Garamond-25");
		add(welcome);
		GRect backround = new GRect(60,35,425,380);
		backround.setFillColor(Color.CYAN);
		backround.setFilled(true);
		add(backround);
		int dimension = 80;
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				GRect rect = new GRect(i*dimension + SPACE_BETWEEN_SQUARES*i + 100,k*dimension + SPACE_BETWEEN_SQUARES*k + 50, dimension, dimension);
				rect.setFillColor(Color.white);
				rect.setFilled(true);
				add(rect);
				String letter = "" + boggle.getGrid().getSquare(i, k);
				if(letter.equals("Q")) {
					letter += "u";
				}
				GLabel label = new GLabel("" + letter, i*dimension + SPACE_BETWEEN_SQUARES*i + dimension * .4 + 102,k*dimension + SPACE_BETWEEN_SQUARES*k + dimension * .6 + 50);
				label.setFont("Garamond-20");
				add(label);
				GOval circ = new GOval(i*dimension + SPACE_BETWEEN_SQUARES*i + 101,k*dimension + SPACE_BETWEEN_SQUARES*k + 51, dimension - 2, dimension - 2);
				add(circ);
			}
		}
		
		startScoreLabel();
		GLabel wordLabel = new GLabel(" ", 260, 450);
		wordLabel.setFont("Garamond-20");
		add(wordLabel); //the label that refreshes as user drags
	}
	
	private int getTime() {
		String time;
		time = dialog.readLine("How many minutes would you like (1,2 or 3)?");
		while(!time.equals("1") && !time.equals("2") && !time.equals("3")) {
			time = dialog.readLine("How many minutes would you like (you must input 1,2 or 3)?");
		}
		return Integer.parseInt(time) * 60;
	}
	
	private void startScoreLabel() {
		score = 0;
		totalScore = boggle.totalScore();
		
		GLabel scoreLabel = new GLabel("Score: " + score + " out of total " + totalScore + " points.", 500, 75);
    	add(scoreLabel);
		
		scoreRect = new GRect(500, 85, 200, 10);
    	add(scoreRect);

		progressBar = new GRect(500, 85, 0, 10);
		progressBar.setFilled(true);
		add(progressBar);
	}
	
	private void timer(int time) {
		long initTime = System.currentTimeMillis();
		
		GLabel timer = new GLabel("timer: " + (time - ((System.currentTimeMillis() - initTime)/1000)), 500, 50);
		add(timer);
		timeLeft = true;
		for(int i = 0; i < time; i++) {
			pause(1000);
			timer.setLabel("timer: " + (time - ((System.currentTimeMillis() - initTime)/1000)));
		}
		timeLeft = false;
	}
	
	//sets all square tiles back to white ("unfilled") so it looks like they have all been unselected
	private void unfillAll() {
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				((GRect)getElementAt(i*(80+SPACE_BETWEEN_SQUARES) + 100,k*(80 + SPACE_BETWEEN_SQUARES) + 50)).setFillColor(Color.white);
			}
		}
	}
	
	//after the time is up, adds all remaining words the user didn't get to the screen in red
	private void addAllWordLabels() {
		int labX = 500;
		int labY = 130;
		for(String str: words) {
			//places the label if not already one there (meaning the user hasn't found it)
			if(!(getElementAt(labX,labY) instanceof GLabel)) {
				GLabel label = new GLabel(str, labX, labY);
				label.setColor(Color.RED);
				add(label);
			}
			if(labY > 430) {
				labX += 50;
				labY = 130;
			} else {
				labY += 20;
			}
		}
	}
	
	
	private void refreshScore() {
		GLabel scoreLab = (GLabel)getElementAt(500,75);
		scoreLab.setLabel("Score: " + score + " out of total " + totalScore);

		double progressWidth = (score / (double) totalScore) * 200;
    	progressBar.setSize(progressWidth, 10);
		
		if (score > 0 && progressBar.getColor() != Color.GREEN) {
			progressBar.setColor(Color.GREEN);
		}
		
	}
	
	private void refreshWordLabel() {
		GLabel l = (GLabel)(getElementAt(260,450));
		l.setLabel("" + word.substring(0,1).toUpperCase() + word.substring(1));
		l.setLocation(260 - (word.length()/2)*10, 450);
	}
	

	//to make sounds work with .wav files now
	private void playSound(String filePath) {
		try {
			File audioFile = new File("sounds/" + filePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	//adds word as label to the screen if not already label where it should be (it hasn't been found yet)
	private void addWord(String word) {
		int index = words.indexOf(word);
		int x = 500;
		int y = 130;
		if(index != 0) {
			x += (index / 17)*50;	//finding coordinates for where label should be (17 per col)
			y += (index % 17)*20;
		}
		if(!(getElementAt(x,y) instanceof GLabel)){
			add(new GLabel(word, x,y));
			score += boggle.getWordsInBoard().getVal(word);
			refreshScore();
			this.word = word + " is a word!";
			refreshWordLabel();
			playSound("sfx-cartoons10.wav");
			// MediaTools.loadAudioClip("sfx-cartoons10.au").play();
		} else {
			this.word = "You've already found " + word.substring(0,1).toUpperCase() + word.substring(1);
			refreshWordLabel();
			playSound("bounce.wav");
			// MediaTools.loadAudioClip("bounce.au").play();
		}
	}

	
	public void mouseReleased(MouseEvent e) {
		if(timeLeft && !word.equals("")) {
			if(boggle.getWordsInBoard().getVal(word) != -1) {
				addWord(word);
			} else {
				this.word = word + " is not a word on the board.";
				refreshWordLabel();
			}
			word = "";
			unfillAll();
			lastRect = null;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(timeLeft) {
			int x = e.getX();
			int y = e.getY();
			GObject object = getElementAt(x,y);
			//only the circles within the board can be dragged on to select squares
			if((object instanceof GOval) && x > 60 && x < 480 && y > 35 && y < 410) {
				object = getElementAt(object.getX() - 1, object.getY() - 1); //gets square
				if(((GRect)object).getFillColor().equals(Color.white) && !object.equals(lastRect)) { //so doesn't continue to fill square as move around within a circle
					if(lastRect == null) {
						lastRect = (GRect)object;
					}
					if(Math.abs(object.getX() - lastRect.getX()) < 100 && Math.abs(object.getY() - lastRect.getY()) < 100) {	//if neighbor
						((GRect)object).setFillColor(Color.PINK);
						int squareX = (x - 100)/(80+SPACE_BETWEEN_SQUARES);
						int squareY = (y - 50)/(80+SPACE_BETWEEN_SQUARES);
						word += java.lang.Character.toLowerCase(boggle.getGrid().getSquare(squareX, squareY));
						if((""+boggle.getGrid().getSquare(squareX, squareY)).equals("Q")) {
								word += 'u';
						}
						refreshWordLabel();
						lastRect = (GRect)object;
					 }
				}
			}
		}
	}
	
	
}