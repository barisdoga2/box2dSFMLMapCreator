import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MyFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public MyFrame(String filePath) {
				
		Main.myPanel = new MyPanel(filePath);
        Main.myPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        Main.myPanel.setPreferredSize(new Dimension((int)Main.myPanel.bg.getWidth(), (int)Main.myPanel.bg.getHeight()));

        JScrollPane scroll = new JScrollPane(Main.myPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
		addKeyListener(Main.myPanel);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setVisible(true);
	}

}
