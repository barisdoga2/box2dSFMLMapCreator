import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	
	public static Tools tools;
	public static MyFrame myFrame;
	public static MyPanel myPanel;
	
	
	public static void main(String[] args) {
				
		JFrame startup = new JFrame();
		JButton createNew = new JButton("Create a New Map(Select .PNG File)");
		JButton loadExisting = new JButton("Load Existing Map(Select Map Folder)");
		
		createNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "png");
				fc.removeChoosableFileFilter(fc.getFileFilter());
				fc.addChoosableFileFilter(imageFilter);
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						tools = new Tools();
						myFrame = new MyFrame(fc.getSelectedFile().getAbsolutePath());
						startup.dispose();
					}
				});
				fc.showOpenDialog(startup);
			}
		});
		loadExisting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String dir = fc.getSelectedFile().getAbsolutePath();
						boolean isTxtFile = new File(dir + "\\" + fc.getSelectedFile().getName() + ".txt").exists();
						boolean isTxtFile2 = new File(dir + "\\" + fc.getSelectedFile().getName() + "V.txt").exists();
						boolean isPngFile = new File(dir + "\\" + fc.getSelectedFile().getName()  + ".png").exists();
						if(isPngFile && isTxtFile && isTxtFile2) {
							tools = new Tools(dir + "\\" + fc.getSelectedFile().getName() + "V.txt");
							myFrame = new MyFrame(dir + "\\" + fc.getSelectedFile().getName()  + ".png");
							startup.dispose();
						}else {
							JOptionPane.showMessageDialog(startup, "This is not a map folder!", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
				fc.showOpenDialog(startup);
			}
		});
		
		
		startup.add(createNew, BorderLayout.EAST);
		startup.add(loadExisting, BorderLayout.WEST);
		startup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startup.pack();
		startup.setLocationRelativeTo(null);
		startup.setVisible(true);
	}
	
}
