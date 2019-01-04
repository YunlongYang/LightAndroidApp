package online.heyworld.android.light.glance.block;

import android.graphics.Point;

import online.heyworld.android.light.glance.block.bean.Block;

/**
 * Created by admin on 2019/1/3.
 */

public class BlockController {

    private Block activeBlock;
    private boolean busy;

    public void setBlock(Block activeBlock) {
        this.activeBlock = activeBlock;
        busy = false;
    }

    public void left(){
        if(!isBusy()) {
            activeBlock.attrMap.put("direct_h", "left");
            notifyChanged();
        }
    }

    public void rotate(){
        if(!isBusy()) {
            activeBlock.attrMap.put("rotate", "true");
            notifyChanged();
        }
    }

    public void right(){
        if(!isBusy()) {
            activeBlock.attrMap.put("direct_h", "right");
            notifyChanged();
        }
    }

    public void down(){
        if(!isBusy()) {
            activeBlock.attrMap.put("direct_v_quick", "true");
            busy = true;
            notifyChanged();
        }
    }

    private void notifyChanged(){
        activeBlock.attrMap.put("changed","");
    }

    private void rotateBlock(Block block){
        boolean[][] values = block.value;
        for (int i=0;i<=3;i++){
            for(int j=0;j<=i;j++){
                swap(values,i,j,j,i);
            }
        }
    }

    private boolean isBusy(){
        return busy;
    }

    private void swap(boolean[][] values,int srcX,int srcY,int destX,int destY){
        boolean src = values[srcX][srcY];
        values[srcX][srcY] = values[destX][destY];
        values[destX][destY] = src;
    }



    public void move(boolean[][] bg){
        if(activeBlock.attrMap.containsKey("direct_v_quick")){
            moveDown(bg);
            notifyChanged();
        }else{
            activeBlock.attrMap.put("direct_v","none");
            doMove(bg);
        }

    }

    public void moveDown(boolean[][] bg){
        activeBlock.attrMap.put("direct_v","down");
        doMove(bg);
    }

    private void doMove(boolean[][] bg){
        if(activeBlock.attrMap.containsKey("rotate") && Boolean.parseBoolean(activeBlock.attrMap.get("rotate"))){
            rotateBlock(activeBlock);
        }
        activeBlock.position = tryMoveImpl(activeBlock,bg,true);

        activeBlock.attrMap.put("rotate",String.valueOf(false));
        activeBlock.attrMap.put("direct_h","");
    }

    public Point tryMove(Block activeBlock, boolean[][] bg){
        return tryMoveImpl(activeBlock,bg,false);
    }

    private Point tryMoveImpl(Block activeBlock, boolean[][] bg, boolean check){
        Point point = new Point(activeBlock.position);
        tryMoveV(point,activeBlock,bg,check);
        tryMoveH(point,activeBlock,bg,check);
        return point;
    }

    private void tryMoveV(Point point, Block activeBlock, boolean[][] bg, boolean check){
        Point pointNew = new Point(point);
        if("down".equals(activeBlock.attrMap.get("direct_v"))){
            pointNew.y++;
        }else if("up".equals(activeBlock.attrMap.get("direct_v"))){
            pointNew.y--;
        }else{
            return;
        }
        try {
            if(check) {
                if (check(pointNew, activeBlock, bg)) {
                    point.y = pointNew.y;
                }
            }else{
                point.y = pointNew.y;
            }
        }catch (Exception e){

        }
    }

    private void tryMoveH(Point point, Block activeBlock, boolean[][] bg, boolean check){
        Point pointNew = new Point(point);
        if("right".equals(activeBlock.attrMap.get("direct_h"))){
            pointNew.x++;
        }else if("left".equals(activeBlock.attrMap.get("direct_h"))){
            pointNew.x--;
        }
        try {
            if(check){
                if(check(pointNew,activeBlock,bg)){
                    point.x = pointNew.x;
                }
            }else{
                point.x = pointNew.x;
            }

        }catch (Exception e){

        }
    }

    private boolean check(Point dest, Block activeBlock, boolean[][] bg)throws Exception{
        int x = dest.x;
        int y = dest.y;

        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                if(activeBlock.value[i][j] && bg[x+i][y+j]  ){
                    return false;
                }
            }
        }
        return true;
    }
}
