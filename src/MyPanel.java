import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class MyPanel extends JPanel implements MouseListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	public BufferedImage bg;
	
	public MyPanel(String filePath) {
		
		 setBorder(BorderFactory.createLineBorder(Color.red));
	     setPreferredSize(new Dimension(800, 600));
	     
	     addMouseListener(this);
	     
	     try {
			bg = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D) g0;

		g.drawImage(bg, null, 0, 0);
		
		ArrayList<MapObject> allObjects = Main.tools.getAllMapObjects();
		for(MapObject o : allObjects) {
			Color color = Color.black;
			if(o.equals(Main.tools.selectedMapObject))
				color = Color.red;
			g.setColor(color);
			for (int i = 0; i < o.getAllVertices().size(); ++i) {
				Point p0 = o.getAllVertices().get(i);
				Point p1 = o.getAllVertices().get((i + 1) % o.getAllVertices().size());
				g.drawLine(p0.x, p0.y, p1.x, p1.y);
			}
			
			for (int i = 0; i < o.getAllVertices().size(); ++i) {
				Point p = o.getAllVertices().get(i);
				
				g.fillOval(p.x - 3, p.y - 3, 7, 7);
				
				g.drawString(""+ i, p.x - 3, p.y - 3);	
			}
		}
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			Main.tools.pushVertex(e.getX(), e.getY());
			repaint();
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			Main.tools.removeLastVertex();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
}
