

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

	// 变量即对象引用声明
	private JFrame myFrame; // 声明JFrame引用，以作后用
	private int panelWidth = 1600; // 声明窗口的宽度
	private int panelHeight = 1000; // 声明窗口的高度
	private int increment = 0; // 每次刷新一帧之后，一列代码向下偏移的行数，后面用作不断的自增1
	private int singleCodeHeight = 20; // 单个字符的高度
	private int singleCodeWidth = 28; // 单个字符的宽度
	private int codeMatrixColumn = 56; // 屏幕上每一行能显示的字符数量（如果全都显示）
	private int codeMatrixRow = 46; // 屏幕上每一列能显示的字符数量（如果全都显示）
	private char[][] codeMatrix; // 用来存储生成的随机字符二维矩阵
	private int codeDisplayedEachColumn = 25; // 每一列显示在屏幕上可以看见的字符数量，即雨滴的长度
	private int[] startPositonEachColumn; // 每一列代码雨第一个字符的起始位置
	private Random rand; // 声明一个随机数类对象引用，后面用来产生随机的字符以及每一列起始的位置

	// 构造方法，生成随机字符集，生成随机的列起始位置，初始化JFrame
	public CodeRainPanel() {
		rand = new Random();
		// 产生随机字符集,  在输出矩阵显示的时候是按行显示的，所以在初始化矩阵时要列变行，行变列
		codeMatrix = new char[codeMatrixColumn][codeMatrixRow];
		for (int i = 0; i < codeMatrixColumn; ++i) {
			for (int j = 0; j < codeMatrixRow; ++j) {
				codeMatrix[i][j] = (char) (rand.nextInt(89) + 33);
			}
		}

		// 产生随机的列起始位置
		startPositonEachColumn = new int[codeMatrixColumn];
		for (int i = 0; i < codeMatrixColumn; ++i) {
			startPositonEachColumn[i] = rand.nextInt(codeMatrixColumn);
		}

		// 初始化父类JFrame
		myFrame = new JFrame();
		myFrame.setSize(panelWidth, panelHeight);
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		myFrame.add(this);
	}

	// 重写父类paint()方法
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("Arial", Font.PLAIN, 20));// 设置显示在窗口中的字符的字体和大小
		for (int i = 0; i < codeMatrixColumn; ++i) {
			// 按行输出字符矩阵，就变成了屏幕上一列一列的字符雨，在按行输出时，每一列是随机选择字符雨第一个字符的起始位置（startPositonEachColumn[i]）
			// 按照设定，从起始位置开始，输出 codeDisplayedEachColumn 个字符
			for (int j = startPositonEachColumn[i] + increment; j < startPositonEachColumn[i] + codeDisplayedEachColumn + increment; ++j) {
				
				if(j - startPositonEachColumn[i] - increment==codeDisplayedEachColumn-1) {
					g.setColor(Color.WHITE); //如果显示的是最后一个字符，将其设置为白色
				}else {
					//其余的按照第一个到倒数第二个依次从黑色到绿色
					g.setColor(new Color(0, (int) Math.floor(255 / codeDisplayedEachColumn) * (j - startPositonEachColumn[i] - increment), 0));
				}
				//输出时保持X轴不变，Y轴逐渐增加，并且通过取余数来保证数组不会越界，实现循环输出
				g.drawString( String.valueOf(codeMatrix[i][j % codeMatrixRow]),   i * singleCodeWidth+10,   (j % codeMatrixRow) * singleCodeHeight+30);
			}
		}
		++increment; // 显示完一帧之后自增一，使下一帧的每一列都下移一个单位
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);  //调节代码雨的下落速度
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
