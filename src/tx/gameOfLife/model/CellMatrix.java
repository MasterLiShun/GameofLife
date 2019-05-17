package tx.gameOfLife.model;

import java.util.Arrays;

/**
 * Created by MasterLi on 2019/04/26
 */
public class CellMatrix {
    /**
     * 矩阵高度
     */
    private int height;

    /**
     * 矩阵宽度
     */
    private int width;

    /**
     * 动画速度，每两个状态之间的毫秒数
     */
    private int duration;

    /**
     * 总的变化数
     */
    private int transfromNum=0;

    /**
     * 矩阵状态， 1表示活，0表示死
     */
    private int[][] matrix;

    public CellMatrix(int height, int width, int duration, int transfromNum, int[][] matrix) {
        this.height = height;
        this.width = width;
        this.duration = duration;
        this.transfromNum = transfromNum;
        this.matrix = matrix;
    }

    /**
     * 上一个状态到下一个状态的转移
     * 根据规则可以总结得出两条规则：
     * 1.对于周围活着的细胞为3的情况，下一个状态总是为活
     * 2. 对于周围活着的细胞为2的情况， 下一个状态与上一状态相同
     * 3.当周围活着的细胞小于2或者大于3的情况，下一个状态就是死
     */
    public void transform(){
        int[][] nextMatrix=new int[height][width];
        for (int y = 0; y < matrix.length; y++) {               //matrix.length指的是这个二维数组的行数
            for (int x = 0; x < matrix[0].length; x++) {        //matrix[0].length指的是matrix这个二维数组第0行的长度，说简单点，就是第0行的列数
                nextMatrix[y][x]=0;                              //这步的目的是将下一个状态先统一初始化为死，之后再根据情况修改状态
                int nearNum= findLifedNum(y,x);                  //y这里应该是这个细胞所在行数，y是所在列数
                //等于3，则下一状态总是活
                if(nearNum==3){
                    nextMatrix[y][x]=1;
                }
                //等于2，则与上一状态相同
                else if(nearNum==2){
                    nextMatrix[y][x]=matrix[y][x];
                }
            }
        }
        matrix=nextMatrix;          //最后遍历完成后，将nextMatrix赋值给matrix,完成转换.matrix是CellMatrix这个类的私有属性
    }



    /**
     * 统计每个细胞周围活着的个数
     * @param x 横坐标
     * @param y 纵坐标
     * @return
     */
    public int findLifedNum(int y, int x){                  //y是细胞所在行数,x是细胞所在列数
        int num=0;
        //一个细胞的周围在网格上有八种可能的位置，下面就将这八种可能全部考虑到
        //左边
        if(x!=0){
            num+=matrix[y][x-1];
        }
        //左上角
        if(x!=0&&y!=0){
            num+=matrix[y-1][x-1];
        }
        //上边
        if(y!=0){
            num+=matrix[y-1][x];
        }
        //右上
        if(x!=width-1&&y!=0){
            num+=matrix[y-1][x+1];
        }
        //右边
        if(x!=width-1){
            num+=matrix[y][x+1];
        }
        //右下
        if(x!=width-1&&y!=height-1){
            num+=matrix[y+1][x+1];
        }
        //下边
        if(y!=height-1){
            num+=matrix[y+1][x];
        }
        //左下
        if(x!=0&&y!=height-1){
            num+=matrix[y+1][x-1];
        }
        return num;           //最后返回的num,就是这个细胞周围存活的细胞数
    }

    @Override                             //@override的作用是告诉读代码的人这是一个复写的方法。简而言之就是重写父类中已有的方法
    public String toString() {

        StringBuilder sb = new StringBuilder();            //在 StringBuilder 上的主要操作是 append 和 insert 方法。每个方法都能有效地将给定的数据转换成字符串，然后将该字符串的字符添加或插入到字符串生成器中。
        for (int i = 0; i < matrix.length; i++) {
            sb.append(Arrays.toString(matrix[i]) + "\n");
        }
        return sb.toString();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getTransfromNum() {
        return transfromNum;
    }

    public int getDuration() {
        return duration;
    }
}
