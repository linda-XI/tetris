package tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    public int map[][];
    public final int row=20;
    public final int col=10;
    public final int square=20;

    public MapGenerator(){
        map=new int[row][col];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map[i][j]=0;
                //map[i][10]=-1;
            }
        }
    }

    public void drawMap(Graphics2D g){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]==-1){
                    g.setColor(Color.white);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==0) {
                    g.setColor(Color.black);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==1) {
                    g.setColor(Color.yellow);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==2) {
                    g.setColor(Color.red);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==3) {
                    g.setColor(Color.green);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==4) {
                    g.setColor(Color.magenta);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==5) {
                    g.setColor(Color.blue);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==6) {
                    g.setColor(Color.orange);
                    g.fillRect(j * square, i * square, square, square);
                }
                if(map[i][j]==7) {
                    g.setColor(Color.pink);
                    g.fillRect(j * square, i * square, square, square);
                }

                    //边框
                    g.setStroke(new BasicStroke(3 ));
                    g.setColor(Color.cyan);
                    g.drawRect(j*square,i*square,square,square);//绘制边框


            }
        }
    }
    public void addToMap(ArrayList<int[]> brickPosition, int state){
        for (int i = 0; i < brickPosition.size() - 1; i++) {
            int x=brickPosition.get(i)[0];
            int y=brickPosition.get(i)[1];
            map[y/square][x/square]=state;
        }
    }
}
