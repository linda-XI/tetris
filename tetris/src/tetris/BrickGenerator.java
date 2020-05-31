package tetris;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BrickGenerator {
    public boolean floating = false;
    public final int square=20;
    public final int halfSquare=10;
    public int brickX;
    public int brickY;
    public ArrayList<int[]> brickPosition;
    public int position1[];
    public int position2[];
    public int position3[];
    public int position4[];
    public int center[];
    public BrickEnum brick;
    public List list;



    public enum BrickEnum {
        O(1,Color.yellow), I(2,Color.red), Z(3,Color.green),
        TZ(4,Color.magenta), L(5,Color.blue),TL(6,Color.orange),
        T(7,Color.pink);

        private final int state;
        private final Color color;

        BrickEnum(int state, Color color) {
            this.state=state;
            this.color=color;
        }
        private int getState(){
            return state;
        }
        private Color getColor(){
            return color;
        }
    }

    public BrickGenerator(BrickEnum b) {
        brickX=100;
        brickY=0;
        floating=true;
        brickPosition=new ArrayList();
        if(b.equals(BrickEnum.O)){
            brick=BrickEnum.O;
            position1= new int[]{brickX, brickY};
            position2= new int[]{brickX+square, brickY};
            position3= new int[]{brickX+square, brickY+square};
            position4= new int[]{brickX, brickY+square};
            center= new int[]{brickX+halfSquare, brickY+halfSquare};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.I)){
            brick=BrickEnum.I;
            position1= new int[]{brickX, brickY};
            position2= new int[]{brickX+square, brickY};
            position3= new int[]{brickX+2*square, brickY};
            position4= new int[]{brickX+3*square, brickY};
            center= new int[]{brickX+square, brickY};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.L)){
            brick=BrickEnum.L;
            position1= new int[]{brickX, brickY};
            position2= new int[]{brickX, brickY+square};
            position3= new int[]{brickX+square, brickY+square};
            position4= new int[]{brickX+2*square, brickY+square};
            center= new int[]{brickX+square, brickY+square};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.TL)){
            brick=BrickEnum.TL;
            position1= new int[]{brickX-2*square, brickY+square};
            position2= new int[]{brickX-square, brickY+square};
            position3= new int[]{brickX, brickY+square};
            position4= new int[]{brickX, brickY};
            center= new int[]{brickX-square, brickY+square};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.Z)){
            brick=BrickEnum.Z;
            position1= new int[]{brickX, brickY};
            position2= new int[]{brickX+square, brickY};
            position3= new int[]{brickX+square, brickY+square};
            position4= new int[]{brickX+2*square, brickY+square};
            center= new int[]{brickX+square, brickY+square};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.TZ)){
            brick=BrickEnum.TZ;
            position1= new int[]{brickX-square, brickY+square};
            position2= new int[]{brickX, brickY+square};
            position3= new int[]{brickX, brickY};
            position4= new int[]{brickX+square, brickY};
            center= new int[]{brickX, brickY+square};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
        if(b.equals(BrickEnum.T)){
            brick=BrickEnum.T;
            position1= new int[]{brickX-square, brickY+square};
            position2= new int[]{brickX, brickY};
            position3= new int[]{brickX, brickY+square};
            position4= new int[]{brickX+square, brickY+square};
            center= new int[]{brickX, brickY+square};
            list= Arrays.asList(position1,position2,position3,position4,center);
            brickPosition.addAll(list);
        }
    }
    public ArrayList<int[]> getBrickPosition(){
        return brickPosition;
    }
    public int getBrickState(){
        return brick.getState();
    }

    public void draw(Graphics2D g) {
        if (floating) {
            for (int i = 0; i < brickPosition.size() - 1; i++) {
                int x = brickPosition.get(i)[0];
                int y = brickPosition.get(i)[1];
                g.setColor(brick.getColor());
                g.fillRect(x,y,square,square);

                g.setStroke(new BasicStroke(3));
                g.setColor((Color.cyan ));
                g.drawRect(x,y,square,square);
            }
        }
    }
    public void shiftDown(){
        for (int i = 0; i < brickPosition.size(); i++) {
            brickPosition.get(i)[1]=brickPosition.get(i)[1]+square;
        }
    }

    public void shiftLeft() {
        for (int i = 0; i < brickPosition.size(); i++) {
            brickPosition.get(i)[0] = brickPosition.get(i)[0] - square;
        }
    }

    public void shiftRight() {
        for (int i = 0; i < brickPosition.size(); i++) {
            brickPosition.get(i)[0] = brickPosition.get(i)[0] + square;
        }
    }

    public void rotate(){
        int centerY=brickPosition.get(4)[1];
        int centerX=brickPosition.get(4)[0];
        for (int i = 0; i < brickPosition.size() ; i++) {
            int x=brickPosition.get(i)[0];
            int y=brickPosition.get(i)[1];
            brickPosition.get(i)[0]=centerX-y+centerY;
            brickPosition.get(i)[1]=centerY+x-centerX;
            //System.out.println(brickPosition.get(i)[0]+","+brickPosition.get(i)[1]);
        }
    }
    public ArrayList<int[]> rotatePosition(){
        ArrayList<int[]> rotatePosition= new ArrayList<>();//不能直接=BRICKPOSITION.因为会指向一个内存地址
        int centerY=brickPosition.get(4)[1];
        int centerX=brickPosition.get(4)[0];
        for (int i = 0; i < brickPosition.size() ; i++) {
            int x=brickPosition.get(i)[0];
            int y=brickPosition.get(i)[1];
            int[] position=new int[2];//要在for里面初始化，不然新的值会覆盖旧的
            position[0]=centerX-y+centerY;
            position[1]=centerY+x-centerX;
            rotatePosition.add(position);
        }
        for (int i = 0; i < brickPosition.size() ; i++) {
            System.out.print(rotatePosition.get(i)[0]+",");
        }
        return rotatePosition;
    }








    //public void setMapValue(int value,int row,int col){}


    public BrickEnum initial(){
        return Enum.random(BrickEnum.class);
    }


}
