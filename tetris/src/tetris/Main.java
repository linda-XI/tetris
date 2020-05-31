package tetris;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame obj=new JFrame();
        GamePlay gamePlay=new GamePlay();
        obj.setBounds(0,0,1000,1000);
        obj.setTitle("Tetris");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }

}
