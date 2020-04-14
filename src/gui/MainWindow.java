package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame{
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 1024;
	
	private MainPanel panel = new MainPanel();
	
	private JButton generate = new JButton("Generate");
	
	public MainWindow(){
		add(panel);
		//add(generate);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}
//	
//	class ButtonListener implements ActionListener {
//	    private int clickCount = 0;
//
//	    public void actionPerformed(ActionEvent e) {
//	    	
//	      
//	      if (e.getSource() == generate) {
//	    	  System.out.println("hello");
//	       JFrame terrain = new JFrame();
//	       terrain.setSize(MainWindow.WIDTH, MainWindow.HEIGHT);
//	       terrain.add(panel);
//	      }
//	    }
//	  }
}

