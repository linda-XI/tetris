package tetris;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score=0;
    private Timer timer;
    private int delay = 1000;
    private BrickGenerator moveBrick;
    private MapGenerator map;
    public final int square = 20;
    public ArrayList<int[]> brickPosition;



    public GamePlay() {
        moveBrick = new BrickGenerator(BrickGenerator.BrickEnum.I);
        map = new MapGenerator();
        brickPosition=new ArrayList();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }


    public void paint(Graphics g) {
        //super.paint(g);//不加这句，就不会清除之前的图形
        map.drawMap((Graphics2D) g);
        moveBrick.draw((Graphics2D) g);

        //score
        g.setColor((Color.black));
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);


        //loose
        for(int j=0;j<map.map[0].length;j++){
            if(map.map[0][j]>0){
                play=false;
                g.setColor(Color.black);
                g.setFont(new Font("serif",Font.BOLD,30));
                g.drawString("Game over,Scores：",190,300);
                break;
            }
        }


        g.dispose();


    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(play==true){
        moveBrick.shiftDown();
        //碰到底
        if(isBottom(moveBrick.getBrickPosition())){
            map.addToMap(moveBrick.getBrickPosition(),moveBrick.getBrickState());
            moveBrick=new BrickGenerator(Enum.random(BrickGenerator.BrickEnum.class));
        }

        //碰到已存在砖块
        isTouch();

        //满行消除
        for(int i=0;i<map.map.length;i++){
            int count=0;
            for(int j=0;j<map.map[0].length;j++){
                if(map.map[i][j]>0){
                    count=count+1;
                    System.out.println(count);
                }
            }
            if(count==10){
                score=score+10;
                int m=1;
                while(i-m>=0){
                    for(int n=0;n<map.map[0].length;n++){
                        int state=map.map[i-m][n];
                        map.map[i-m+1][n]=state;
                        map.map[i-m][n]=0;
                    }
                    m=m+1;
                }
            }
        }

        repaint();


    }
    }

    private void isTouch() {
        for(int i=0;i<moveBrick.getBrickPosition().size()-1;i++){
            int brickY=moveBrick.getBrickPosition().get(i)[1];
            int brickX=moveBrick.getBrickPosition().get(i)[0];
            for(int j=0;j<map.map.length;j++){
                if(map.map[j][brickX/square]>0){
                    if(j==(brickY+square)/square){
                        map.addToMap(moveBrick.getBrickPosition(),moveBrick.getBrickState());
                        moveBrick=new BrickGenerator(Enum.random(BrickGenerator.BrickEnum.class));
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            play=true;
            moveBrick.shiftDown();
            if(isBottom(moveBrick.getBrickPosition())){
                map.addToMap(moveBrick.getBrickPosition(),moveBrick.getBrickState());
                moveBrick=new BrickGenerator(Enum.random(BrickGenerator.BrickEnum.class));
            }
            isTouch();
        }
        //右移 right border
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            play=true;
            if(! isMoveRightBorder(moveBrick.getBrickPosition())){
                if(!isRightBrick(moveBrick.getBrickPosition())) {
                    moveBrick.shiftRight();
                }
            }
        }
        //左移 left border
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            play=true;
            if(! isMoveLeftBorder(moveBrick.getBrickPosition())){
                if(!isLeftBrick(moveBrick.getBrickPosition())){
                moveBrick.shiftLeft();
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {//判断的时候不能直接用rotate，要建一个临时的，不改变brickPosition的坐标组。
            play=true;
            if(!isCeiling(moveBrick.rotatePosition())){
                if((!isRotateRightBorder(moveBrick.rotatePosition()) && (!isRotateLeftBorder(moveBrick.rotatePosition())))){
                    if((!isRightBrick(moveBrick.rotatePosition()) && (!isLeftBrick(moveBrick.rotatePosition())))){
                        moveBrick.rotate();
                    }
                }
            }

        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                moveBrick = new BrickGenerator(BrickGenerator.BrickEnum.I);
                map = new MapGenerator();
                brickPosition=new ArrayList();
                score=0;
            }
        }




        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    public boolean isBottom(ArrayList<int[]> brickPosition){
        boolean isBottom=false;
        for (int i = 0; i < brickPosition.size() ; i++) {
            if(brickPosition.get(i)[1]+square>=20*square){
                isBottom= true;
                break;
            }
        }
        return isBottom;
    }


    public boolean isMoveRightBorder(ArrayList<int[]> brickPosition) {
        boolean isBorder = false;
        for (int i = 0; i < brickPosition.size(); i++) {
            //System.out.println((brickPosition.get(i)[0]));
            if (brickPosition.get(i)[0]+square >= 10 * square) {
                isBorder = true;
                break;
            }
        }
        return isBorder;
    }
    public boolean isRotateRightBorder(ArrayList<int[]> brickPosition) {
        boolean isBorder = false;
        for (int i = 0; i < brickPosition.size(); i++) {
            //System.out.println((brickPosition.get(i)[0]));
            if (brickPosition.get(i)[0]+square > 10 * square) {
                isBorder = true;
                break;
            }
        }
        return isBorder;
    }

    public boolean isMoveLeftBorder(ArrayList<int[]> brickPosition) {
        boolean isLeftBorder = false;
        for (int i = 0; i < brickPosition.size(); i++) {
            if (brickPosition.get(i)[0] <= 0) {
                isLeftBorder = true;
                break;
            }
        }
        return isLeftBorder;
    }
    public boolean isRotateLeftBorder(ArrayList<int[]> brickPosition) {
        boolean isLeftBorder = false;
        for (int i = 0; i < brickPosition.size(); i++) {
            if (brickPosition.get(i)[0] < 0) {
                isLeftBorder = true;
                break;
            }
        }
        return isLeftBorder;
    }

    public boolean isLeftBrick(ArrayList<int[]> brickPosition){
        boolean isLeftBrick=false;
        for(int i=0;i<brickPosition.size()-1;i++){
            int brickY=brickPosition.get(i)[1];
            int brickX=brickPosition.get(i)[0];
            if((brickX-square)/square>=1){
                if(map.map[brickY/square][(brickX-square)/square]>0) {
                    isLeftBrick=true;
                    break;
                }
            }
        }
        return isLeftBrick ;
    }
    public boolean isRightBrick(ArrayList<int[]> brickPosition){
        boolean isRightBrick=false;
        for(int i=0;i<brickPosition.size()-1;i++){
            int brickY=brickPosition.get(i)[1];
            int brickX=brickPosition.get(i)[0];
            if((brickX+square)/square<10) {
                if (map.map[brickY / square][(brickX + square) / square] > 0) {
                    isRightBrick = true;
                    break;
                }
            }
        }
        return isRightBrick;
    }
    public boolean isCeiling(ArrayList<int[]> brickPosition){
        boolean isCeiling=false;
        for (int i = 0; i < brickPosition.size() ; i++) {
            if(brickPosition.get(i)[1]<0){
                isCeiling= true;
                break;
            }
        }
        return isCeiling;
    }
}