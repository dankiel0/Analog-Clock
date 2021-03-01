package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnalogClock {
	public static void main(String[] args) {
		// the window
		JFrame window = new JFrame();
		window.getContentPane().add(new PaintSurface());
		
		// defaults
		window.setAlwaysOnTop(true);
		window.pack();
		window.setTitle("Clock");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
		// keeps the program running and repaints the paint surface once per second
		while(true) {
			window.getContentPane().repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}
}

// this is what the clock is drawn on
class PaintSurface extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// size of window is 240, 240
	public PaintSurface() {
		super.setPreferredSize(new Dimension(240, 240));
	}
	
	// "graphics.drawString()" doesn't center the text, so this method is necessary
	private void drawCenteredString(Graphics graphics, String text, Rectangle rectangle) {
	    FontMetrics metrics = graphics.getFontMetrics();
	    int x = rectangle.x + (rectangle.width - metrics.stringWidth(text)) / 2;
	    int y = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    graphics.drawString(text, x, y);
	}
	
	private void drawClock(Graphics graphics) {
		// draws numbers
		for(int i = 1; i < 13; i++) {
			int x = (int) (Math.cos((i - 3) * 0.52359877559) * 195);
			int y = (int) (Math.sin((i - 3) * 0.52359877559) * 195);
			
			drawCenteredString(graphics, Integer.toString(i), new Rectangle(x, y));
		}
		
		// draws clock hands
		Calendar calendar = Calendar.getInstance();
		
		int hourHandX2 = (int) (Math.cos((calendar.get(Calendar.HOUR) - 3) * 0.523598776) * 60);
		int hourHandY2 = (int) (Math.sin((calendar.get(Calendar.HOUR) - 3) * 0.523598776) * 60);
		
		int minuteHandX2 = (int) (Math.cos((calendar.get(Calendar.MINUTE) - 15) * 0.10471975512) * 105);
		int minuteHandY2 = (int) (Math.sin((calendar.get(Calendar.MINUTE) - 15) * 0.10471975512) * 105);
		
		int secondHandX2 = (int) (Math.cos((calendar.get(Calendar.SECOND) - 15) * 0.10471975512) * 95);
		int secondHandY2 = (int) (Math.sin((calendar.get(Calendar.SECOND) - 15) * 0.10471975512) * 95);
		
		graphics.drawLine(0, 0, hourHandX2, hourHandY2);
		graphics.drawLine(0, 0, minuteHandX2, minuteHandY2);
		graphics.setColor(Color.RED);
		graphics.drawLine(0, 0, secondHandX2, secondHandY2);
	}
	
	// this method is called on "repaint()"
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Font font = new Font("Arial", Font.PLAIN, 16);
		graphics.setFont(font);
		graphics.translate(120, 120);
		
		drawClock(graphics);
	}
}
