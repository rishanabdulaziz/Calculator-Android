
package Minesweeper;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JFrame{

    //Set constants for number of rows and columns
    private final int ROW = 6;
    private final int COL = 5;

    //To store mine position
    private int[][] minePosition = new int[10][2];

    //To store buttons information (mines/number of mines surrouded)
    private String[][] mineValue = new String[ROW][COL];

    //To store whether buttons have been clicked
    private boolean[][] mineClicked = new boolean[ROW][COL];

    //Default value of mine (difficulty level - easy)
    private int maxMine = 4;

    private int difficultyLevel = 4;

    private long startingTime, endingTime, milliSecDiff, secDiff;

    //Create container
    private Container pane;

    private JPanel minePanel, msgPanel, durationPanel;

    private JLabel msgLabel, iconLabel, durationLabel;

    private Icon minesweeperIcon, newGameIcon, gameIcon, winIcon, loseIcon;

    private JButton[][] mineField = new JButton[ROW][COL];

    private JMenuBar menuBar;

    private JMenu gameMenu, difficultyMenu, helpMenu;

    private JMenuItem newGameMenuItem, scoresMenuItem, exitMenuItem, helpMenuItem;

    private JRadioButtonMenuItem easyMenuItem, intermediateMenuItem, difficultMenuItem;

    private ButtonGroup difficultyGroup;

    private String helpStr = "HOW TO PLAY\n1. Uncover a mine, and the game ends.\n2. Uncover a number, "
            + "and it tells you how many mines lay\n    hidden in the eight surrounding squares - information "
            + "you\n    use to deduce which nearby squares are safe to click.\n\nHINTS & TIPS\n1. Study the "
            + "patterns. If three squares in a row display 2-3-2,\n    then you know three mines are probably lined "
            + "up beside\n    that row. If a square says 8, every surrounding square is\n    mined.\n2. Explore the unexplored."
            + " Not sure where to click next?\n    Try clearing some unexplored territory. You're better\n    off clicking in "
            + "the middle of unmarked squares than in an\n    area you suspect is mined.\n\nHOW TO SCORE\n1. There are three "
            + "difficulty levels, with the number of mines \n    increasing with difficulty level.\n2. You are scored on how "
            + "fast you can clear the board.\n3. 5 fastest time for each level is stored. ";

    //Create keystroke objects
    KeyStroke newGameKey = KeyStroke.getKeyStroke("F2");
    KeyStroke scoresKey = KeyStroke.getKeyStroke("F4");
    KeyStroke helpKey = KeyStroke.getKeyStroke("F1");

    //Create scores form object
    private Scores scoresForm;

    //Create listener object
    private actionHandler aH = new actionHandler();

    //Constructor
    public Minesweeper() throws FileNotFoundException{

        pane = getContentPane();

        //Create scores object
        scoresForm = new Scores();

        //Create icon object
        minesweeperIcon = new ImageIcon("Minesweeper Icon.png");
        newGameIcon = new ImageIcon("New Game Icon.png");
        gameIcon = new ImageIcon("Game Icon.png");
        winIcon = new ImageIcon("Win Icon.png");
        loseIcon = new ImageIcon("Lose Icon.png");

        //Create JMenuBar object and add it to the JFrame
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //Create JMenu object and add it to the JMenuBar
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        difficultyMenu = new JMenu("Difficulty");
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        //Create JMenuItem objects and add to the JMenu
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(newGameKey);
        newGameMenuItem.setMnemonic('N');
        newGameMenuItem.addActionListener(aH);
        scoresMenuItem = new JMenuItem("High Scores");
        scoresMenuItem.setAccelerator(scoresKey);
        scoresMenuItem.setMnemonic('S');
        scoresMenuItem.addActionListener(aH);
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic('x');
        exitMenuItem.addActionListener(aH);
        easyMenuItem = new JRadioButtonMenuItem("Easy");
        easyMenuItem.setMnemonic('E');
        easyMenuItem.addActionListener(aH);
        intermediateMenuItem = new JRadioButtonMenuItem("Intermediate");
        intermediateMenuItem.setMnemonic('m');
        intermediateMenuItem.addActionListener(aH);
        difficultMenuItem = new JRadioButtonMenuItem("Difficult");
        difficultMenuItem.setMnemonic('t');
        difficultMenuItem.addActionListener(aH);
        helpMenuItem = new JMenuItem("View Help");
        helpMenuItem.setAccelerator(helpKey);
        helpMenuItem.setMnemonic('V');
        helpMenuItem.addActionListener(aH);

        //Add JMenuItem objects to difficultyMenu
        difficultyMenu.add(easyMenuItem);
        difficultyMenu.add(intermediateMenuItem);
        difficultyMenu.add(difficultMenuItem);

        //Add JMenuItem objects and difficultyMenu to gameMenu
        gameMenu.add(newGameMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(difficultyMenu);
        gameMenu.addSeparator();
        gameMenu.add(scoresMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(exitMenuItem);

        //Add JMenuItem objects to helpMenu
        helpMenu.add(helpMenuItem);

        //Group radio buttons to difficultyMenu and check easy menu item by default
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyMenuItem);
        difficultyGroup.add(intermediateMenuItem);
        difficultyGroup.add(difficultMenuItem);
        easyMenuItem.setSelected(true);

        //Create JLabel object
        msgLabel = new JLabel("Difficulty Level : Easy");
        iconLabel = new JLabel(gameIcon);

        //Create JPanel object and add JLabel to the JPanel
        msgPanel = new JPanel();
        msgPanel.add(iconLabel);
        msgPanel.add(msgLabel);

        //Create JPanel object and set layout for JPanel object
        minePanel = new JPanel();
        minePanel.setLayout(new GridLayout(6,5,1,0));

        //Create minefield
        for (int row = 0; row < mineField.length; row++){
            for (int col = 0; col < mineField[row].length; col++){
                mineField[row][col] = new JButton("");
                mineField[row][col].addActionListener(aH);
                minePanel.add(mineField[row][col]);
            }
        }

        //Create durationPanel object and add durationLabel to it
        durationPanel = new JPanel();
        durationLabel = new JLabel();
        durationPanel.add(durationLabel);

        //Add objects to the JFrame
        pane.add(msgPanel, BorderLayout.NORTH);
        pane.add(minePanel, BorderLayout.CENTER);
        pane.add(durationPanel, BorderLayout.SOUTH);

        setIconImage(Toolkit.getDefaultToolkit().getImage("Minesweeper Icon.png"));
        setSize(300,425);
        setResizable(false);
        setVisible(true);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Start the game for first time
        startGame();

    }//End constructor

    class actionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e){

            if (e.getSource() == exitMenuItem){
                System.exit(0);
            }

            if (e.getSource() == easyMenuItem){
                difficultyLevel = 1;
                startGame(difficultyLevel);
            }

            if (e.getSource() == intermediateMenuItem){
                difficultyLevel = 2;
                startGame(difficultyLevel);
            }

            if (e.getSource() == difficultMenuItem){
                difficultyLevel = 3;
                startGame(difficultyLevel);
            }

            if (e.getSource() == newGameMenuItem){
                startGame();
            }

            if (e.getSource() == scoresMenuItem){
                scoresForm.setVisible(true);
            }

            if(e.getSource() == helpMenuItem){
                JOptionPane.showMessageDialog(null, helpStr, "Minesweeper Help", JOptionPane.INFORMATION_MESSAGE, minesweeperIcon);
            }//End helpMenuItem

            if (e.getSource() instanceof JButton){
                //Check to see which button has been click then set text accordingly
                for (int row = 0; row < mineField.length; row++){
                    for (int col = 0; col < mineField[row].length; col++){
                        //Reveal everything if lose
                        if (e.getSource() == mineField[row][col]){
                            if (mineValue[row][col].equals("BOOM")){
                                mineField[row][col].setIcon(minesweeperIcon);
                                mineField[row][col].setEnabled(false);
                                mineClicked[row][col] = true;
                            }//End if
                            else{
                                //Reveal everything if win
                                mineField[row][col].setText(mineValue[row][col]);
                                mineField[row][col].setEnabled(false);
                                mineClicked[row][col] = true;
                            }//End else
                            try {
                                checkWin(maxMine);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
                            }//End catch
                        }//End if
                    }//End col
                }//End row
            }//End JButton
        }//End method
    }//End actionHandler

    //Check whether win or lose
    public void checkWin(int maxMine) throws FileNotFoundException{
        int clickCount = 0;

        //Check win condition
        for (int row = 0; row < mineClicked.length; row++){
            for (int col = 0; col < mineClicked[row].length; col++){
                if (mineClicked[row][col] == true && (!("BOOM".equals(mineValue[row][col]))) ){
                    clickCount++;
                }//End if
            }//End for col
        }//End for row

        switch (maxMine){
            case 4:{
                if (clickCount == 26)
                    win(maxMine);
            }//End case 4
            break;

            case 6:{
                if (clickCount == 24)
                    win(maxMine);
            }//End case 6
            break;

            case 10:{
                if (clickCount == 20)
                    win(maxMine);
            }//End case 10
            break;
        }//End switch

        //Check lose condition
        for (int row = 0; row < maxMine; row++){ //Check until maximum mine for each difficulty level
                if (mineClicked[minePosition[row][0]][minePosition[row][1]] == true){
                    lose();
                }//End if
        }//End for row
    }//End checkWin

    //Perform win action
    public void win(int maxMine) throws FileNotFoundException{
        msgLabel.setText("Congratulations ! You Win !!!");
        iconLabel.setIcon(winIcon);
        endTime();

        //Loop to reveal all button values
        for (int row = 0; row < mineField.length; row++){
            for (int col = 0; col < mineField[row].length; col++){
                if (mineValue[row][col].equals("BOOM")){
                    mineField[row][col].setIcon(minesweeperIcon);
                    mineField[row][col].setEnabled(false);
                }//End if
                else{
                    mineField[row][col].setText(mineValue[row][col]);
                    mineField[row][col].setEnabled(false);
                }//End else
            }//End for col
        }//End for row

        //Check high score
        switch (maxMine){
            case 4:
                scoresForm.checkEasy(secDiff);break;
            case 6:
                scoresForm.checkIntermediate(secDiff);break;
            case 10:
                scoresForm.checkDifficult(secDiff);break;
        }//End switch

        //Request to start new game
        startGame();
    }//End win

    //Perform lose action
    public void lose(){
        msgLabel.setText("Sorry ! You Lose !!!");
        iconLabel.setIcon(loseIcon);
        endTime();

        //Loop to reveal all button values
        for (int row = 0; row < mineField.length;row++){
            for (int col = 0; col < mineField[row].length;col++){
                if (mineValue[row][col].equals("BOOM")){
                    mineField[row][col].setIcon(minesweeperIcon);
                    mineField[row][col].setEnabled(false);
                }//End if
                else{
                    mineField[row][col].setText(mineValue[row][col]);
                    mineField[row][col].setEnabled(false);
                }//End else
            }//End for col
        }//End for row

        //Request to start new game
        startGame();
    }//End lose

    //Start game for the first time and when newGameMenuItem is clicked
    public void startGame(){
        //Prompt to ask user whether start a new game
        int result = JOptionPane.showConfirmDialog(null, "Start New Game?", "New Game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, newGameIcon);

        //Perform action according to user's choice
        if (result == JOptionPane.YES_OPTION){
            initialise();
            generateMine();
            determineMineValue(maxMine);
            startTime();
        }//End if
        else{
            //Reset all buttons
            for (int row = 0;row < mineField.length; row++){
                for (int col = 0; col < mineField[row].length; col++){
                    mineField[row][col].setEnabled(false);
                    mineField[row][col].setText("");
                    mineField[row][col].setIcon(null);
                }//End for col
            }//End for row
        }//End else
    }//End startGame

    //Start game for radio buttons
    public void startGame(int difficultyLevel){
        //Prompt to ask user whether start a new game
        int result = JOptionPane.showConfirmDialog(null, "Start New Game?", "New Game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, newGameIcon);

        //Perform action according to user's choice
        if (result == JOptionPane.YES_OPTION){
            if (difficultyLevel == 1)
                maxMine = 4;
            if (difficultyLevel == 2)
                maxMine = 6;
            if (difficultyLevel == 3)
                maxMine = 10;

            initialise();
            generateMine();
            determineMineValue(maxMine);
            startTime();
        }//End if
    }//End startGame

    public void initialise(){

        //Set all buttons to default state
        for (int row = 0; row < mineField.length; row++){
            for (int col = 0; col < mineField[row].length; col++){
                mineField[row][col].setEnabled(true);
                mineField[row][col].setText("");
                mineField[row][col].setIcon(null);

                mineClicked[row][col] = false; //Set all mineClicked to false
            }//End for col
        }//end for row

        //Reset all mineValue
        for (int row = 0; row < mineValue.length; row++){
          for (int col = 0; col < mineValue[row].length; col++){
              mineValue[row][col] = "";
          }//End for col
        }//End for row

        //Reset msgLabel
        switch(maxMine){
            case (4):{
                msgLabel.setText("Difficulty Level : Easy");
            }//End case 4
            break;

            case (6):{
                msgLabel.setText("Difficulty Level : Intermediate");
            }//End case 6
            break;

            case (10):{
                msgLabel.setText("Difficulty Level : Difficult");
            }//End case 10
            break;
        }//End switch

        //Reset iconLabel
        iconLabel.setIcon(gameIcon);
    }//End initialise

    public void startTime(){
        //Reset duration and durationLabel before start time
        startingTime = 0;
        endingTime = 0;
        durationLabel.setText("");

        //Get start time
        startingTime = new Date().getTime();
    }//End startTime

    public void endTime(){
        //Get end time
        endingTime = new Date().getTime();

        //Get time difference in milliseconds
        milliSecDiff = endingTime - startingTime;

        //Convert to seconds
        secDiff = milliSecDiff/1000;

        //Display secDiff in durationLabel
        durationLabel.setText("Duration : " + Long.toString(secDiff) + " second(s)");
    }//End endTime

    public void generateMine(){
        //Initialise the value in minePosition (to prevent being initialise with 0)
         for (int row = 0; row < minePosition.length; row++){
            for (int col = 0; col < minePosition[row].length; col++){
                minePosition[row][col] = 9;
                System.out.println(minePosition[row][col]);
            }//End for col
        }//End for row

        //Create random generator
        Random r = new Random();

        //Create temporary space for mine generated
        int colMine, rowMine;

        for (int i = 0; i < minePosition.length; i++){

            do{
                colMine = r.nextInt(5); //Generate random number for column
                rowMine = r.nextInt(6); //Generate random number for row
            }while(checkRepetition(rowMine, colMine));

            minePosition[i][0] = rowMine;
            minePosition[i][1] = colMine;

            System.out.println("col" + i+  ":"+ minePosition[i][1]);
            System.out.println("Row"+ i + ":"+ minePosition[i][0]+"\n");
        }//End for
    }//End generateMine

    public boolean checkRepetition(int rowMine, int colMine){
       boolean flag = false;

       //Loop to check repetition (to avoid the mine to be stored at the same position)
        for (int i = 0; i < minePosition.length; i++){
            if ((rowMine == minePosition[i][0]) && (colMine == minePosition[i][1])){
                flag = true;
                break;
            }//End if
        }//End for
        return flag;
    }//End checkRepetition

    public void determineMineValue(int maxMine){
        //Implement mine according to minePosition
        switch (maxMine){
            case 4:{
                for (int x = 0; x < 4; x++){
                mineValue[minePosition[x][0]][minePosition[x][1]] = "BOOM";
                }//End for 4
            }//End case 4
            break;

            case 6:{
                for (int x = 0; x < 6; x++){
                mineValue[minePosition[x][0]][minePosition[x][1]] = "BOOM";
                }//End for 6
            }//End case 6
            break;

            case 10:{
                for (int x = 0; x < 10; x++){
                mineValue[minePosition[x][0]][minePosition[x][1]] = "BOOM";
                }//End for 10
            }//End case 10
            break;

            default:{
                for (int x = 0; x < 4; x++){
                mineValue[minePosition[x][0]][minePosition[x][1]] = "BOOM";
                }//End for default
            }//End default
        }//End switch

      //Determine the value for each mine
      for (int row = 0; row < mineValue.length; row++){
          for (int col = 0; col < mineValue[row].length; col++){
              if (!("BOOM".equals(mineValue[row][col]))){

                  //Check each position for number of mines
                  if (row == 0 && col == 0){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col+1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (0,0) TOP LEFT

                  if (row == 0 && col == 4){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (0,4) TOP RIGHT

                  if (row == 5 && col == 0){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (5,0) BOTTOM LEFT

                   if (row == 5 && col == 4){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (5,4) BOTTOM RIGHT

                  if ((row == 0 && col == 1) || (row == 0 && col==2) || (row == 0 && col == 3) ){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col+1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (0,1) (0,2) (0,3) TOP

                  if ((row == 1 && col == 0) || (row == 2 && col == 0) || (row == 3 && col == 0) || (row == 4 && col == 0)){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col+1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (1,0) (2,0) (3,0) (4,0) LEFT

                  if ((row == 1 && col == 4) || (row == 2 && col == 4) || (row == 3 && col == 4) || (row == 4 && col == 4)){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col-1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (1,4) (2,4) (3,4) (4,4) RIGHT

                  if ((row == 5 && col == 1) || (row == 5 && col == 2) || (row == 5 && col == 3) ){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      mineValue[row][col] = Integer.toString(mineCount);
                  }//End if (5,1) (5,2) (5,3) BOTTOM

                  if ((row == 1 && col == 1) || (row == 1 && col == 2) || (row == 1 && col == 3) ||
                      (row == 2 && col == 1) || (row == 2 && col == 2) || (row == 2 && col == 3) ||
                      (row == 3 && col == 1) || (row == 3 && col == 2) || (row == 3 && col == 3) ||
                      (row == 4 && col == 1) || (row == 4 && col == 2) || (row == 4 && col == 3) ){
                      int mineCount = 0;
                      if ("BOOM".equals(mineValue[row-1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row-1][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row][col+1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col-1]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col]))
                          mineCount++;
                      if ("BOOM".equals(mineValue[row+1][col+1]))
                          mineCount++;
                     mineValue[row][col] = Integer.toString(mineCount);
                  }//End if CENTER
              }//End if
          }//End for col
      }//End for row
    }//End determineMineValue

    public static void main(String[] args) throws FileNotFoundException{
        
        Minesweeper mSP = new Minesweeper();

    }

}
