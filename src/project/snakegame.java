package project;
import javax.swing.*;
import java.awt.*;

public class snakegame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("SNAKEGAME");
		frame.setBounds(10, 10, 900, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		gamePanel panel = new gamePanel();
		panel.setBackground(Color.DARK_GRAY);
		frame.add(panel) ;
	}
}
