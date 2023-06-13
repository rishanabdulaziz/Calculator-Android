
package Minesweeper;

import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scores extends JFrame{

    //Create container
    private Container pane;

    //Create the fixed number of records
    private final int RECORD_NUM = 5;

    //Create temporary storage area for all player names and duration played according to three difficulty levels
    private String easyGamePlayer[] = new String[RECORD_NUM];
    private long easyGameDuration[] = new long[RECORD_NUM];
    private String intermediateGamePlayer[] = new String[RECORD_NUM];
    private long intermediateGameDuration[] = new long[RECORD_NUM];
    private String difficultGamePlayer[] = new String[RECORD_NUM];
    private long difficultGameDuration[] = new long[RECORD_NUM];

    //Use different panels for the three difficulty levels
    private JPanel difficultyPanel, easyPanel, intermediatePanel, difficultPanel, scorePanel;

    private String[] difficultyStr = {"Easy", "Intermediate", "Difficult"};
    private JComboBox difficultyCB;

    //Create labels for beginner panel
    private JLabel difficultyLabel, easyBlankLabel, easyPlayerTitleLabel, easyDurationTitleLabel;
    private JLabel[] easyNumLabels = new JLabel[RECORD_NUM];
    private JLabel[] easyPlayerLabels = new JLabel[RECORD_NUM];
    private JLabel[] easyDurationLabels = new JLabel[RECORD_NUM];

    //Create labels for intermediate panel
    private JLabel intermediateBlankLabel, intermediatePlayerTitleLabel, intermediateDurationTitleLabel;
    private JLabel[] intermediateNumLabels = new JLabel[RECORD_NUM];
    private JLabel[] intermediatePlayerLabels = new JLabel[RECORD_NUM];
    private JLabel[] intermediateDurationLabels = new JLabel[RECORD_NUM];

    //Create labels for difficult panel
    private JLabel difficultBlankLabel, difficultPlayerTitleLabel, difficultDurationTitleLabel;
    private JLabel[] difficultNumLabels = new JLabel[RECORD_NUM];
    private JLabel[] difficultPlayerLabels = new JLabel[RECORD_NUM];
    private JLabel[] difficultDurationLabels = new JLabel[RECORD_NUM];

    //Create listener object
    private actionHandler aH = new actionHandler();

    //Create layout object
    GridLayout gLayout = new GridLayout(6,3);
    CardLayout cLayout = new CardLayout(5,5);

    //Constructor
    public Scores() throws FileNotFoundException{

        pane = getContentPane();

        //Create JComboBox object
        difficultyCB = new JComboBox(difficultyStr);
        difficultyCB.setSelectedIndex(0);
        difficultyCB.addActionListener(aH);

        //Add combo box to panel
        difficultyPanel = new JPanel();
        difficultyLabel = new JLabel("Difficulty Level : ");
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyCB);

        //Create panel object (default display is easy)
        easyPanel = new JPanel();
        easyPanel.setLayout(gLayout);
        intermediatePanel = new JPanel();
        intermediatePanel.setLayout(gLayout);
        difficultPanel = new JPanel();
        difficultPanel.setLayout(gLayout);

        //Create score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(cLayout);

        //Add three panels to score panel
        scorePanel.add("Easy", easyPanel);
        scorePanel.add("Intermediate", intermediatePanel);
        scorePanel.add("Difficult", difficultPanel);

        //Create label objects for easy panel
        easyBlankLabel = new JLabel();
        easyPlayerTitleLabel = new JLabel("Players' Name");
        easyDurationTitleLabel = new JLabel("Best Times");

        for (int i = 0; i < RECORD_NUM ; i++){
            easyNumLabels[i] = new JLabel("          " + (i+1) + ".");
            easyPlayerLabels[i] = new JLabel("Name" + (i+1));
            easyDurationLabels[i] = new JLabel("Time" + (i+1));
        }//End for

        //Add objects to beginner panel
        easyPanel.add(easyBlankLabel);
        easyPanel.add(easyPlayerTitleLabel);
        easyPanel.add(easyDurationTitleLabel);

        for (int i = 0; i < RECORD_NUM; i++){
        easyPanel.add(easyNumLabels[i]);
        easyPanel.add(easyPlayerLabels[i]);
        easyPanel.add(easyDurationLabels[i]);
        }//End for

        //Update beginner panel value
        updateEasyPanel();

        //Create label objects for intermediate panel
        intermediateBlankLabel = new JLabel();
        intermediatePlayerTitleLabel = new JLabel("Players' Name");
        intermediateDurationTitleLabel = new JLabel("Best Times");

        for (int i = 0;i < RECORD_NUM; i++){
            intermediateNumLabels[i] = new JLabel("          " + (i+1) + ".");
            intermediatePlayerLabels[i] = new JLabel("Name" + (i+1));
            intermediateDurationLabels[i] = new JLabel("Time" + (i+1));
        }//End for

        //Add objects to intermediate panel
        intermediatePanel.add(intermediateBlankLabel);
        intermediatePanel.add(intermediatePlayerTitleLabel);
        intermediatePanel.add(intermediateDurationTitleLabel);

        for (int i = 0;i < RECORD_NUM; i++){
        intermediatePanel.add(intermediateNumLabels[i]);
        intermediatePanel.add(intermediatePlayerLabels[i]);
        intermediatePanel.add(intermediateDurationLabels[i]);
        }//End for

        //Update intermediate panel value
        updateIntermediatePanel();

        //Create label objects for difficult panel
        difficultBlankLabel = new JLabel();
        difficultPlayerTitleLabel = new JLabel("Players' Name");
        difficultDurationTitleLabel = new JLabel("Best Times");

        for (int i = 0; i < RECORD_NUM; i++){
            difficultNumLabels[i] = new JLabel("          " + (i+1) + ".");
            difficultPlayerLabels[i] = new JLabel("Name" + (i+1));
            difficultDurationLabels[i] = new JLabel("Time" + (i+1));
        }//End for

        //Add objects to difficult panel
        difficultPanel.add(difficultBlankLabel);
        difficultPanel.add(difficultPlayerTitleLabel);
        difficultPanel.add(difficultDurationTitleLabel);

        for (int i = 0; i < RECORD_NUM; i++){
        difficultPanel.add(difficultNumLabels[i]);
        difficultPanel.add(difficultPlayerLabels[i]);
        difficultPanel.add(difficultDurationLabels[i]);
        }//End for

        //Update difficult panel value
        updateDifficultPanel();

        //Add objects to pane
        pane.add(difficultyPanel, BorderLayout.NORTH);
        pane.add(scorePanel, BorderLayout.CENTER);

        setIconImage(Toolkit.getDefaultToolkit().getImage("Minesweeper Icon.png"));
        setSize(400,400);
        setResizable(false);
        setVisible(false);
        setTitle("High Scores : Easy Level");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//End Constructor

    class actionHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (difficultyCB.getSelectedIndex() == 0){
                cLayout.show(scorePanel, "Easy");
                setTitle("High Scores : Easy Level");
                System.out.println("Easy");
            }//End Easy
            if (difficultyCB.getSelectedIndex() == 1){
                cLayout.show(scorePanel, "Intermediate");
                setTitle("High Scores : Intermediate Level");
                System.out.println("Intermediate");
            }//End Intermediate
            if (difficultyCB.getSelectedIndex() == 2){
                cLayout.show(scorePanel, "Difficult");
                setTitle("High Scores : Difficult Level");
                System.out.println("Difficult");
            }//End Difficult
        }//End actionPerformed
    }//End ActionHandler

    public void updateEasyPanel() throws FileNotFoundException{
        Scanner inFile = new Scanner(new FileReader("Easy.txt"));

        for (int i = 0; i < RECORD_NUM; i++){
            easyPlayerLabels[i].setText(inFile.next());
            easyDurationLabels[i].setText(Long.toString(inFile.nextLong()));
        }//End for

        inFile.close();
    }//End updateEasyPanel

     public void updateIntermediatePanel() throws FileNotFoundException{
        Scanner inFile = new Scanner(new FileReader("Intermediate.txt"));

        for (int i=0;i<RECORD_NUM;i++){
            intermediatePlayerLabels[i].setText(inFile.next());
            intermediateDurationLabels[i].setText(Long.toString(inFile.nextLong()));
        }//End for

        inFile.close();
    }//End updateIntermediatePanel

     public void updateDifficultPanel() throws FileNotFoundException{
        Scanner inFile = new Scanner(new FileReader("Difficult.txt"));

        for (int i = 0; i < RECORD_NUM; i++){
            difficultPlayerLabels[i].setText(inFile.next());
            difficultDurationLabels[i].setText(Long.toString(inFile.nextLong()));
        }//End for

        inFile.close();
    }//End updateDiffucultPanel

     public void checkEasy(long newDuration) throws FileNotFoundException{
         //Determine whether need to update score
         boolean flag = false;
         //Position is used to determine which user need to be replaced
         int position = 4;
         Scanner inFile = new Scanner(new FileReader("Easy.txt"));

         //Copy values into temporary storage
         for (int i = 0; i < RECORD_NUM; i++){
             easyGamePlayer[i] = inFile.next();
             easyGameDuration[i] = inFile.nextLong();
         }//End for

         //Check whether shorter time than 5 scores
         for (int i = 4; i >= 0; i--){
             if (newDuration <= easyGameDuration[i]){
                 position = i;
                 flag = true;
             }//End if
             else{
                 break; //Quit the loop
             }//End else
        }//End for

         //Determine whether need to change text file based on comparison flag
         if (flag == true){
             String newPlayer = JOptionPane.showInputDialog(null, "Please Enter Your Nick Name", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
             changeEasyTxt(newDuration, newPlayer, position);
         }//End if

         inFile.close();
     }//End checkEasy

     public void checkIntermediate (long newDuration) throws FileNotFoundException{
         //Determine whether need to update score
         boolean flag = false;
         //Position is used to determine which user need to be replaced
         int position = 4;
         Scanner inFile = new Scanner(new FileReader("Intermediate.txt"));

         //Copy values into temporary storage
         for (int i = 0; i < RECORD_NUM; i++){
             intermediateGamePlayer[i] = inFile.next();
             intermediateGameDuration[i] = inFile.nextLong();
         }//End for

         //Check whether shorter time than 5 scores
         for (int i = 4; i >= 0; i--){
             if (newDuration <= intermediateGameDuration[i]){
                 position = i;
                 flag = true;
             }//End if
             else{
                 break; //Quit the loop
             }//End else
        }//End for

         //Determine whether need to change text file based on comparison flag
         if (flag == true){
             String newPlayer = JOptionPane.showInputDialog(null, "Please Enter Your Nick Name", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
             changeIntermediateTxt(newDuration, newPlayer, position);
         }//End if

         inFile.close();
     }//End checkIntermediate

      public void checkDifficult (long newDuration) throws FileNotFoundException{
         //Determine whether need to update score
         boolean flag = false;
         //Position is used to determine which user need to be replaced
         int position = 4;
         Scanner inFile = new Scanner(new FileReader("Difficult.txt"));

         //Copy values into temporary storage
         for (int i = 0; i < RECORD_NUM; i++){
             difficultGamePlayer[i] = inFile.next();
             difficultGameDuration[i] = inFile.nextLong();
         }//End for

         //Check whether shorter time than 5 scores
         for (int i=4;i>=0;i--){
             if (newDuration <= difficultGameDuration[i]){
                 position = i;
                 flag = true;
             }//End if
             else{
                 break; //Quit the loop
             }//End else
        }//End for

         //Determine whether need to change text file based on comparison flag
         if (flag == true){
             String newPlayer = JOptionPane.showInputDialog(null, "Please Enter Your Nick Name", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
             changeDifficultTxt(newDuration, newPlayer, position);
         }//End if

         inFile.close();
     }//End checkDifficult

     public void changeEasyTxt(long newDuration, String newPlayer, int position) throws FileNotFoundException{
         PrintWriter outFile = new PrintWriter ("Easy.txt");

         //Change temporary storage according to position given
         switch(position){
             case (0):{
                 easyGamePlayer[0] = newPlayer;
                 easyGameDuration[0] = newDuration;
             }//End case 0
             break;

             case (1):{
                 easyGamePlayer[1] = newPlayer;
                 easyGameDuration[1] = newDuration;
             }//End case 1
             break;

             case (2):{
                 easyGamePlayer[2] = newPlayer;
                 easyGameDuration[2] = newDuration;
             }//End case 2
             break;

             case (3):{
                 easyGamePlayer[3] = newPlayer;
                 easyGameDuration[3] = newDuration;
             }//End case 3
             break;

             case (4):{
                 easyGamePlayer[4] = newPlayer;
                 easyGameDuration[4] = newDuration;
             }//End case 4
             break;
         }//End switch

         //Copy temporary value to txt file
         for (int i = 0; i < RECORD_NUM; i++){
             outFile.println(easyGamePlayer[i] + " " + Long.toString(easyGameDuration[i]));
         }//end for

         outFile.close();
         updateEasyPanel();
     }//End changeEasyTxt

     public void changeIntermediateTxt(long newDuration, String newPlayer, int position) throws FileNotFoundException{
         PrintWriter outFile = new PrintWriter ("Intermediate.txt");

         //Change temporary storage according to position given
         switch(position){
             case (0):{
                 intermediateGamePlayer[0] = newPlayer;
                 intermediateGameDuration[0] = newDuration;
             }//End case 0
             break;

             case (1):{
                 intermediateGamePlayer[1] = newPlayer;
                 intermediateGameDuration[1] = newDuration;
             }//End case 1
             break;

             case (2):{
                 intermediateGamePlayer[2] = newPlayer;
                 intermediateGameDuration[2] = newDuration;
             }//End case 2
             break;

             case (3):{
                 intermediateGamePlayer[3] = newPlayer;
                 intermediateGameDuration[3] = newDuration;
             }//End case 3
             break;

             case (4):{
                 intermediateGamePlayer[4] = newPlayer;
                 intermediateGameDuration[4] = newDuration;
             }//end case 4
             break;
         }//End switch

         //Copy temporary value to txt file
         for (int i = 0; i < RECORD_NUM; i++){
             outFile.println(intermediateGamePlayer[i] + " " + Long.toString(intermediateGameDuration[i]));
         }//End for

         outFile.close();
         updateIntermediatePanel();
     }//End changeIntermediateTxt

     public void changeDifficultTxt(long newDuration, String newPlayer, int position) throws FileNotFoundException{
         PrintWriter outFile = new PrintWriter ("Difficult.txt");

         //Change temporary storage according to position given
         switch(position){
             case (0):{
                 difficultGamePlayer[0] = newPlayer;
                 difficultGameDuration[0] = newDuration;
             }//End case 0
             break;

             case (1):{
                 difficultGamePlayer[1] = newPlayer;
                 difficultGameDuration[1] = newDuration;
             }//End case 1
             break;

             case (2):{
                 difficultGamePlayer[2] = newPlayer;
                 difficultGameDuration[2] = newDuration;
             }//End case 2
             break;

             case (3):{
                 difficultGamePlayer[3] = newPlayer;
                 difficultGameDuration[3] = newDuration;
             }//End case 3
             break;

             case (4):{
                 difficultGamePlayer[4] = newPlayer;
                 difficultGameDuration[4] = newDuration;
             }//End case 4
             break;
         }//End switch

         //Copy temporary value to txt file
         for (int i = 0 ;i < RECORD_NUM; i++){
             outFile.println(difficultGamePlayer[i] + " " + Long.toString(difficultGameDuration[i]));
         }//End for

         outFile.close();
         updateDifficultPanel();
     }//End changeDifficultTxt

}
