

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyCodeRain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CodeRainPanel myCodeRainPanel = new CodeRainPanel();
		Thread t = new Thread(myCodeRainPanel);
		t.setName("my code rain");
		t.start();
	}
}

class CodeRainPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ������������������
	private JFrame myFrame; // ����JFrame���ã���������
	private int panelWidth = 1600; // �������ڵĿ��
	private int panelHeight = 1000; // �������ڵĸ߶�
	private int increment = 0; // ÿ��ˢ��һ֮֡��һ�д�������ƫ�Ƶ������������������ϵ�����1
	private int singleCodeHeight = 20; // �����ַ��ĸ߶�
	private int singleCodeWidth = 28; // �����ַ��Ŀ��
	private int codeMatrixColumn = 56; // ��Ļ��ÿһ������ʾ���ַ����������ȫ����ʾ��
	private int codeMatrixRow = 46; // ��Ļ��ÿһ������ʾ���ַ����������ȫ����ʾ��
	private char[][] codeMatrix; // �����洢���ɵ�����ַ���ά����
	private int codeDisplayedEachColumn = 25; // ÿһ����ʾ����Ļ�Ͽ��Կ������ַ�����������εĳ���
	private int[] startPositonEachColumn; // ÿһ�д������һ���ַ�����ʼλ��
	private Random rand; // ����һ���������������ã�������������������ַ��Լ�ÿһ����ʼ��λ��

	// ���췽������������ַ������������������ʼλ�ã���ʼ��JFrame
	public CodeRainPanel() {
		rand = new Random();
		// ��������ַ���,  �����������ʾ��ʱ���ǰ�����ʾ�ģ������ڳ�ʼ������ʱҪ�б��У��б���
		codeMatrix = new char[codeMatrixColumn][codeMatrixRow];
		for (int i = 0; i < codeMatrixColumn; ++i) {
			for (int j = 0; j < codeMatrixRow; ++j) {
				codeMatrix[i][j] = (char) (rand.nextInt(89) + 33);
			}
		}

		// �������������ʼλ��
		startPositonEachColumn = new int[codeMatrixColumn];
		for (int i = 0; i < codeMatrixColumn; ++i) {
			startPositonEachColumn[i] = rand.nextInt(codeMatrixColumn);
		}

		// ��ʼ������JFrame
		myFrame = new JFrame();
		myFrame.setSize(panelWidth, panelHeight);
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		myFrame.add(this);
	}

	// ��д����paint()����
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("Arial", Font.PLAIN, 20));// ������ʾ�ڴ����е��ַ�������ʹ�С
		for (int i = 0; i < codeMatrixColumn; ++i) {
			// ��������ַ����󣬾ͱ������Ļ��һ��һ�е��ַ��꣬�ڰ������ʱ��ÿһ�������ѡ���ַ����һ���ַ�����ʼλ�ã�startPositonEachColumn[i]��
			// �����趨������ʼλ�ÿ�ʼ����� codeDisplayedEachColumn ���ַ�
			for (int j = startPositonEachColumn[i] + increment; j < startPositonEachColumn[i] + codeDisplayedEachColumn + increment; ++j) {
				
				if(j - startPositonEachColumn[i] - increment==codeDisplayedEachColumn-1) {
					g.setColor(Color.WHITE); //�����ʾ�������һ���ַ�����������Ϊ��ɫ
				}else {
					//����İ��յ�һ���������ڶ������δӺ�ɫ����ɫ
					g.setColor(new Color(0, (int) Math.floor(255 / codeDisplayedEachColumn) * (j - startPositonEachColumn[i] - increment), 0));
				}
				//���ʱ����X�᲻�䣬Y�������ӣ�����ͨ��ȡ��������֤���鲻��Խ�磬ʵ��ѭ�����
				g.drawString( String.valueOf(codeMatrix[i][j % codeMatrixRow]),   i * singleCodeWidth+10,   (j % codeMatrixRow) * singleCodeHeight+30);
			}
		}
		++increment; // ��ʾ��һ֮֡������һ��ʹ��һ֡��ÿһ�ж�����һ����λ
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);  //���ڴ�����������ٶ�
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
