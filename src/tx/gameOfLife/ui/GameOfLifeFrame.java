package tx.gameOfLife.ui;

import tx.gameOfLife.model.CellMatrix;
import tx.gameOfLife.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by MasterLI on 2019/04/26.
 */
public class GameOfLifeFrame extends JFrame {

    private JButton openFileBtn = new JButton("ѡ���ļ�");
    private JButton startGameBtn = new JButton("��ʼ��Ϸ");
    private JLabel durationPromtLabel = new JLabel("����������ã�msΪ��λ��");
    private JTextField durationTextField = new JTextField();
    /**
     * ��Ϸ�Ƿ�ʼ�ı�־
     */
    private boolean isStart = false;

    /**
     * ��Ϸ�����ı�־
     */
    private boolean stop = false;

    private CellMatrix cellMatrix;
    private JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
    private JPanel gridPanel = new JPanel();

    private JTextField[][] textMatrix;


    /**
     * ����Ĭ�ϼ��200ms
     */
    private static final int DEFAULT_DURATION = 200;

    //�������
    private int duration = DEFAULT_DURATION;

    public GameOfLifeFrame() {
        setTitle("������Ϸ");
        openFileBtn.addActionListener(new OpenFileActioner());
        startGameBtn.addActionListener(new StartGameActioner());

        buttonPanel.add(openFileBtn);
        buttonPanel.add(startGameBtn);
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);

        this.setSize(1000, 1200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private class OpenFileActioner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fcDlg = new JFileChooser(".");          //����Ĭ����ʾΪ��ǰ�ļ���
            fcDlg.setDialogTitle("��ѡ���ʼ�����ļ�");
            int returnVal = fcDlg.showOpenDialog(null);        //ʹ��showOpenDialog()��������ʾ����ѡ���ļ��Ĵ��ڣ���ѡ����ĳ���ļ��󣬻��߹رմ˴�����ô���᷵��һ��������ֵ��������ص���0�� �����Ѿ�ѡ����ĳ���ļ����������1����ѡ����ȡ����ť����ֱ�ӹر��˴���
            if (returnVal == JFileChooser.APPROVE_OPTION) {     //JFileChooser.APPROVE_OPTION�Ǹ����ͳ���������0

                isStart = false;
                stop = true;
                startGameBtn.setText("��ʼ��Ϸ");             //�⼸�����������޸�״̬��û��̫���ʵ������

                String filepath = fcDlg.getSelectedFile().getPath();  //���ѡ���ļ���·����getSelectedFile().getPath()Ӧ����JFileChooser������е����÷���
                cellMatrix = Utils.initMatrixFromFile(filepath);
                initGridLayout();
                showMatrix();
                gridPanel.updateUI();
            }
        }


    }

    private void showMatrix() {

        int[][] matrix = cellMatrix.getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * ������ʾ��gridlayout�Ĳ���
     */
    private void initGridLayout() {
        int rows = cellMatrix.getHeight();
        int cols = cellMatrix.getWidth();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add("Center", gridPanel);
    }


    private class StartGameActioner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isStart) {

                //��ȡʱ��
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    duration = DEFAULT_DURATION;
                }

                new Thread(new GameControlTask()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("��ͣ��Ϸ");
            } else {
                stop = true;
                isStart = false;
                startGameBtn.setText("��ʼ��Ϸ");
            }
        }
    }

    private class GameControlTask implements Runnable {

        @Override
        public void run() {

            while (!stop) {
                cellMatrix.transform();
                showMatrix();

                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

}
