package windows;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	
	private JFrame window;
	private Canvas canvas;
	
	private int width, height;
	private String title;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createWindow();
	}
	
	private void createWindow() {
		window = new JFrame(title);
		window.setSize(width, height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		//make sure canvas always stay at the width and height that be given.
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		window.add(canvas);
		window.pack();//window will resize the window a little bit to let us can see canvas fully.
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getWindow() {
		return window;
	}
}
