package tx.gameOfLife.model;

import java.util.Arrays;

/**
 * Created by MasterLi on 2019/04/26
 */
public class CellMatrix {
    /**
     * ����߶�
     */
    private int height;

    /**
     * ������
     */
    private int width;

    /**
     * �����ٶȣ�ÿ����״̬֮��ĺ�����
     */
    private int duration;

    /**
     * �ܵı仯��
     */
    private int transfromNum=0;

    /**
     * ����״̬�� 1��ʾ�0��ʾ��
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
     * ��һ��״̬����һ��״̬��ת��
     * ���ݹ�������ܽ�ó���������
     * 1.������Χ���ŵ�ϸ��Ϊ3���������һ��״̬����Ϊ��
     * 2. ������Χ���ŵ�ϸ��Ϊ2������� ��һ��״̬����һ״̬��ͬ
     * 3.����Χ���ŵ�ϸ��С��2���ߴ���3���������һ��״̬������
     */
    public void transform(){
        int[][] nextMatrix=new int[height][width];
        for (int y = 0; y < matrix.length; y++) {               //matrix.lengthָ���������ά���������
            for (int x = 0; x < matrix[0].length; x++) {        //matrix[0].lengthָ����matrix�����ά�����0�еĳ��ȣ�˵�򵥵㣬���ǵ�0�е�����
                nextMatrix[y][x]=0;                              //�ⲽ��Ŀ���ǽ���һ��״̬��ͳһ��ʼ��Ϊ����֮���ٸ�������޸�״̬
                int nearNum= findLifedNum(y,x);                  //y����Ӧ�������ϸ������������y����������
                //����3������һ״̬���ǻ�
                if(nearNum==3){
                    nextMatrix[y][x]=1;
                }
                //����2��������һ״̬��ͬ
                else if(nearNum==2){
                    nextMatrix[y][x]=matrix[y][x];
                }
            }
        }
        matrix=nextMatrix;          //��������ɺ󣬽�nextMatrix��ֵ��matrix,���ת��.matrix��CellMatrix������˽������
    }



    /**
     * ͳ��ÿ��ϸ����Χ���ŵĸ���
     * @param x ������
     * @param y ������
     * @return
     */
    public int findLifedNum(int y, int x){                  //y��ϸ����������,x��ϸ����������
        int num=0;
        //һ��ϸ������Χ���������а��ֿ��ܵ�λ�ã�����ͽ�����ֿ���ȫ�����ǵ�
        //���
        if(x!=0){
            num+=matrix[y][x-1];
        }
        //���Ͻ�
        if(x!=0&&y!=0){
            num+=matrix[y-1][x-1];
        }
        //�ϱ�
        if(y!=0){
            num+=matrix[y-1][x];
        }
        //����
        if(x!=width-1&&y!=0){
            num+=matrix[y-1][x+1];
        }
        //�ұ�
        if(x!=width-1){
            num+=matrix[y][x+1];
        }
        //����
        if(x!=width-1&&y!=height-1){
            num+=matrix[y+1][x+1];
        }
        //�±�
        if(y!=height-1){
            num+=matrix[y+1][x];
        }
        //����
        if(x!=0&&y!=height-1){
            num+=matrix[y+1][x-1];
        }
        return num;           //��󷵻ص�num,�������ϸ����Χ����ϸ����
    }

    @Override                             //@override�������Ǹ��߶������������һ����д�ķ����������֮������д���������еķ���
    public String toString() {

        StringBuilder sb = new StringBuilder();            //�� StringBuilder �ϵ���Ҫ������ append �� insert ������ÿ������������Ч�ؽ�����������ת�����ַ�����Ȼ�󽫸��ַ������ַ���ӻ���뵽�ַ����������С�
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
