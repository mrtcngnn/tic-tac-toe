import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.GridLayout;
import java.awt.FlowLayout;

public class XOX extends JFrame implements MouseInputListener, Action{
    int round = 1; //If it is odd Player 1's turn, if it is even Player 2's turn.
    
    static String[][] boardStatus = new String[3][3];   //3x3 game board.
    
    String symbol = "";

    static int x = 0;  //tiklanan yerler
    static int y = 0;
    static int locX = 0; //arraydeki yerleri
    static int locY = 0;
    boolean checkEmpty = true;
    boolean checkX = true;
    boolean checkY = true;
    boolean full = true;

    JFrame board;

    public XOX(){
        setTitle("X-O-X");
        setSize(800,800);
        board = new JFrame();
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                boardStatus[i][j] = "";
            }
        }
        addMouseListener(this);
	    addMouseMotionListener(this);
        setVisible(true);
    }

    public void paint (Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.white);
        g.drawLine(100, 100, 100, 700); //sol ust -> sol alt
        g.drawLine(700, 100, 700, 700); //sag ust -> sag alt
        g.drawLine(100, 100, 700, 100); //sol ust -> sag ust
        g.drawLine(100, 700, 700, 700); //sol alt -> sag alt
        g.drawLine(300, 100, 300, 700); 
        g.drawLine(500, 100, 500, 700); 
        g.drawLine(100, 300, 700, 300);
        g.drawLine(100, 500, 700, 500);
        if (checkX && checkY){
            g.setColor(Color.white);
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (boardStatus[i][j].equals("X")){
                        switch (i){
                            case 0:
                                y = 150;
                                break;
                            case 1:
                                y = 350;
                                break;
                            case 2:
                                y = 550;
                                break;
                        }
                        switch (j){
                            case 0:
                                x = 150;
                                break;
                            case 1:
                                x = 350;
                                break;
                            case 2:
                                x = 550;
                                break;
                        }
                        g.drawLine(x, y, x+100, y+100);
                        g.drawLine(x+100, y, x, y+100);
                    }else if (boardStatus[i][j].equals("O")){
                        switch (i){
                            case 0:
                                y = 150;
                                break;
                            case 1:
                                y = 350;
                                break;
                            case 2:
                                y = 550;
                                break;
                        }
                        switch (j){
                            case 0:
                                x = 150;
                                break;
                            case 1:
                                x = 350;
                                break;
                            case 2:
                                x = 550;
                                break;
                        }
                        g.fillOval(x, y, 100, 100);
                    }
                }    
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        repaint();
        x = e.getX();
        y = e.getY();
        if (x >= 100 && x <= 700 && y >= 100 && y <= 700){
            checkX = true;
            checkY = true;
            if (x >= 100 && x <= 300){
                locY = 0;
            }else if (x >= 300 && x <= 500){
                locY = 1;
            }else if (x >= 500 && x <= 700){
                locY = 2;
            }
            if (y >= 100 && y <= 300){
                locX = 0;
            }else if (y >= 300 && y <= 500){
                locX = 1;
            }else if (y >= 500 && y <= 700){
                locX = 2;
            }
        }
        if (boardStatus[locX][locY].equals("")){
            if (round % 2 == 1){
                symbol = "X";
            }else {
                symbol = "O";
            }
            boardStatus[locX][locY] = symbol;
            String result = checkWinner(locX, locY, symbol);
            if (result.equals("X")){
                this.dispose();
                new WinnerWindow("X");
            }else if (result.equals("O")){
                this.dispose();
                new WinnerWindow("O");
            }
        round++;
        if (round == 10 && !(result.equals("X") || result.equals("O"))){
            this.dispose();
            new WinnerWindow("");
        }
        }else{
            this.dispose();
            new AlertWindow();
        }
    }

    public String checkWinner(int x, int y, String sym){
        int locX = x;
        int locY = y;
        String s = sym;
        if (locX==0 && locY==0){ //sol ust 
            if (boardStatus[locX+1][locY].equals(s) && boardStatus[locX+2][locY].equals(s)){ //en soldaki column
                return s;
            }
            if (boardStatus[locX][locY+1].equals(s) && boardStatus[locX][locY+2].equals(s)){ //en ustteki row
                return s;
            }
            if (boardStatus[locX+1][locY+1].equals(s) && boardStatus[locX+2][locY+2].equals(s)){ //kosegen
                return s;
            }
        }
        if (locX==1 && locY==0){ //sol orta 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX+1][locY].equals(s)){ //en soldaki column
                return s;
            }
            if (boardStatus[locX][locY+1].equals(s) && boardStatus[locX][locY+2].equals(s)){ //ortadaki row
                return s;
            }
        }
        if (locX==2 && locY==0){ //sol alt 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX-2][locY].equals(s)){ //en soldaki column
                return s;
            }
            if (boardStatus[locX][locY+1].equals(s) && boardStatus[locX][locY+2].equals(s)){ //en alttaki row
                return s;
            }
            if (boardStatus[locX-1][locY+1].equals(s) && boardStatus[locX-2][locY+2].equals(s)){ //kosegen
                return s;
            }
        }
        if (locX==0 && locY==1){ //orta ust 
            if (boardStatus[locX+1][locY].equals(s) && boardStatus[locX+2][locY].equals(s)){ //en ortadaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY+1].equals(s)){ //en ustteki row
                return s;
            }
        }
        if (locX==1 && locY==1){ //orta 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX+1][locY].equals(s)){ //ortadaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY+1].equals(s)){ //ortadaki row
                return s;
            }
            if (boardStatus[locX-1][locY-1].equals(s) && boardStatus[locX+1][locY+1].equals(s)){ //kosegen
                return s;
            }
        }
        if (locX==2 && locY==1){ //orta alt 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX-2][locY].equals(s)){ //ortadaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY+1].equals(s)){ //en alttaki row
                return s;
            }
        }
        if (locX==0 && locY==2){ //sag ust 
            if (boardStatus[locX+1][locY].equals(s) && boardStatus[locX+2][locY].equals(s)){ //en sagdaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY-2].equals(s)){ //en ustteki row
                return s;
            }
            if (boardStatus[locX+1][locY-1].equals(s) && boardStatus[locX+2][locY-2].equals(s)){ //kosegen
                return s;
            }
        }
        if (locX==1 && locY==2){ //sag orta 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX+1][locY].equals(s)){ //en sagdaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY-2].equals(s)){ //ortadaki row
                return s;
            }
        }
        if (locX==2 && locY==2){ //sag alt 
            if (boardStatus[locX-1][locY].equals(s) && boardStatus[locX-2][locY].equals(s)){ //en sagdaki column
                return s;
            }
            if (boardStatus[locX][locY-1].equals(s) && boardStatus[locX][locY-2].equals(s)){ //en alttaki row
                return s;
            }
            if (boardStatus[locX-1][locY-1].equals(s) && boardStatus[locX-2][locY-2].equals(s)){ //kosegen
                return s;
            }
        }
        return "";
    }

    public class AlertWindow extends JFrame implements Action{

        public AlertWindow(){
            setTitle("Alert!!!");
            setSize(400, 200);
            setLayout(new GridLayout(2,1));
            JLabel message = new JLabel("This place is not empty. Please choose another place.");
            JButton exit = new JButton("Continue");
            JPanel text = new JPanel(new FlowLayout()); 
            JPanel tus = new JPanel(new FlowLayout());
            text.add(message);
            add(text);
            tus.add(exit);
            add(tus);
            exit.addActionListener(this);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
           String command = e.getActionCommand();
			if (command.equals("Continue")) {
				dispose();
			}
        }
        public Object getValue(String key) {
            // TODO Auto-generated method stub
            return null;
        }
        public void putValue(String key, Object value) {
            // TODO Auto-generated method stub
            
        }
    }

    public class WinnerWindow extends JFrame implements Action{

        
        public WinnerWindow(String winner){
            setTitle("Result!");
            setSize(400, 200);
            setLayout(new GridLayout(2,1));
            JLabel message;
            if (winner.equals("")){
                message = new JLabel("The game ended in a draw.");
            }else{
                message = new JLabel("Player "+winner+" won the game. Congratulations :)");
            }
            JButton newGame = new JButton("New Game");
            newGame.setBackground(Color.green);
            JButton exit = new JButton("Exit");
            exit.setBackground(Color.red); 
            JPanel text = new JPanel(new FlowLayout()); 
            JPanel tus = new JPanel(new FlowLayout());
            text.add(message);
            add(text);
            tus.add(newGame);
            tus.add(exit);
            add(tus);
            newGame.addActionListener(this);
            exit.addActionListener(this);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
           String command = e.getActionCommand();
           System.out.println(command);
			if (command.equals("New Game")) { //hatali
                this.dispose();
                new XOX();
			}else if (command.contentEquals("Exit")){
                System.exit(0);
            }
        }

        public Object getValue(String key) {
            // TODO Auto-generated method stub
            return null;
        }
        public void putValue(String key, Object value) {
            // TODO Auto-generated method stub
            
        }

    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    public void putValue(String key, Object value) {
        // TODO Auto-generated method stub
        
    }
    public static void main(String[] args) {
        XOX game = new XOX();
    }

}