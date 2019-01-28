import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tools extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
	
	private JButton addButton = new JButton("Add New");
	private JButton deleteButton = new JButton("Delete Selected");
	private JButton saveButton = new JButton("Save Map");
	private JComboBox<MapObject> myCmb = new JComboBox<MapObject>();
	
	public MapObject selectedMapObject;
	
	public Tools(String filePath) {
		this();
		
		File file = new File(filePath); 
		
		String line; 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file)); 
			while ((line = br.readLine()) != null) {
				String tokens[] = line.split(" ");
				int triangleCtr = Integer.parseInt(tokens[0]);
				selectedMapObject = new MapObject(tokens[1]);
				mapObjects.add(selectedMapObject);
				myCmb.addItem(selectedMapObject);
				myCmb.setSelectedItem(selectedMapObject);
				for(int i = 0 ; i < triangleCtr ; i++) {
					tokens = br.readLine().split(" ");
					pushVertex(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {}
		}
	}
	
	public Tools() {
		JPanel innerPanel = new JPanel();
		
		myCmb.addActionListener(this);
		
		addButton.addActionListener(this);
		deleteButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		innerPanel.add(addButton);
		innerPanel.add(deleteButton);
		innerPanel.add(saveButton);
		innerPanel.add(myCmb);
		this.add(innerPanel);
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350 ,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addButton) {
			
			JDialog aa = new JDialog(this, true);
			JButton okButton = new JButton("OK!");
			JTextField nameField = new JTextField(11);
			aa.setTitle("Enter The Name");
			aa.getContentPane().add(new JLabel("Please enter name of the new object: "), BorderLayout.NORTH);
			aa.getContentPane().add(nameField, BorderLayout.WEST);
			aa.getContentPane().add(okButton, BorderLayout.CENTER);
			aa.pack();
			aa.setLocationRelativeTo(this);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean nameAlreadyExists = false;
					for(MapObject o : mapObjects) 
						if(o.getName().equals(nameField.getText()))
							nameAlreadyExists = true;
					
					if(nameField.getText().length() == 0) {
						JOptionPane.showMessageDialog(aa, "Name Cannot Be Null!", "Error!", JOptionPane.ERROR_MESSAGE);
					}else if(nameAlreadyExists){
						JOptionPane.showMessageDialog(aa, "The Specified Name Already Exists!", "Error!", JOptionPane.ERROR_MESSAGE);
					}else {
						selectedMapObject = new MapObject(nameField.getText());
						mapObjects.add(selectedMapObject);
						myCmb.addItem(selectedMapObject);
						myCmb.setSelectedItem(selectedMapObject);
						aa.dispose();
					}
				}
			});
			aa.pack();
			aa.setVisible(true);
			Main.myPanel.repaint();
		}else if(e.getSource() == deleteButton) {
			if(myCmb.getSelectedItem() != null) {
				mapObjects.remove(myCmb.getSelectedItem());
				myCmb.removeItem(myCmb.getSelectedItem());
				selectedMapObject = null;
				Main.myPanel.repaint();
			}
		}else if(e.getSource() == saveButton) {
			
			JDialog aa = new JDialog(this, true);
			JButton okButton = new JButton("OK!");
			JTextField nameField = new JTextField(11);
			aa.setTitle("Save Map");
			aa.getContentPane().add(new JLabel("Please enter name of the map: "), BorderLayout.NORTH);
			aa.getContentPane().add(nameField, BorderLayout.WEST);
			aa.getContentPane().add(okButton, BorderLayout.CENTER);
			aa.pack();
			aa.setLocationRelativeTo(this);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean nameAlreadyExists = false;
					boolean createIt = true;
					
					String currentDir = System.getProperty("user.dir");
					File mapsFodler = new File(currentDir + "\\Maps");
					if(!mapsFodler.exists())
						if(!mapsFodler.mkdir()) {
							System.out.println("A");
							JOptionPane.showMessageDialog(aa, "Cant create folders and files! Make sure to give rights!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
					
					File mapFolder = new File(currentDir + "\\Maps\\" + nameField.getText());
					if(!mapFolder.exists()) {
						if(!mapFolder.mkdir()) {
							System.out.println("B");
							JOptionPane.showMessageDialog(aa, "Cant create folders and files! Make sure to give rights!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}else {
						nameAlreadyExists = true;
					}
					
					if(nameField.getText().length() == 0) {
						JOptionPane.showMessageDialog(aa, "Name Cannot Be Null!", "Error!", JOptionPane.ERROR_MESSAGE);
					}else if(nameAlreadyExists){
						int status = JOptionPane.showConfirmDialog(aa, "The Specified Map Already Exists! Do you wish to override?", "Override?", JOptionPane.WARNING_MESSAGE);
						if(status == JOptionPane.OK_OPTION) {
							createIt = true;
						}else if(status == JOptionPane.CANCEL_OPTION) {
							createIt = false;
						}
					}
					
					if(createIt) {
						File outputBg = new File(System.getProperty("user.dir") + "\\Maps\\" + nameField.getText() + "\\" + nameField.getText() + ".png");
						try {
							ImageIO.write(Main.myPanel.bg, "png", outputBg);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(aa, "Cant create folders and files! Make sure to give rights!", "Error!", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
							return;
						}
						
						
						Writer writer = null;
						try {
							writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "\\Maps\\" + nameField.getText() + "\\" + nameField.getText() + ".txt")), "utf-8"));
							for(MapObject o : mapObjects) {
								ArrayList<Triangle> triangles = o.getAllTriangles();
								writer.write(triangles.size() + " " + o.getName() + "\n");
								 for(int i = 0 ; i < triangles.size() ; i++) {
								    	Triangle t = triangles.get(i);
								    	writer.write(""+ t.a.x);
								    	writer.write(" ");
								    	writer.write(""+ t.a.y);
								    	writer.write(" ");
								    	writer.write(""+ t.b.x);
								    	writer.write(" ");
								    	writer.write(""+ t.b.y);
								    	writer.write(" ");
								    	writer.write(""+ t.c.x);
								    	writer.write(" ");
								    	writer.write(""+ t.c.y);
								    	writer.write("\n");
								    }
							}
						}catch (IOException ex) {
							JOptionPane.showMessageDialog(aa, "Cant create folders and files! Make sure to give rights!", "Error!", JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
							return;
						}finally {
							try {
								writer.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						
						writer = null;
						try {
							writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "\\Maps\\" + nameField.getText() + "\\" + nameField.getText() + "V.txt")), "utf-8"));
							for(MapObject o : mapObjects) {
								ArrayList<Point> points = o.getAllVertices();
								writer.write(points.size() + " " + o.getName() + "\n");
								 for(int i = 0 ; i < points.size() ; i++) {
								    	Point t = points.get(i);
								    	writer.write(""+ t.x);
								    	writer.write(" ");
								    	writer.write(""+ t.y);
								    	writer.write("\n");
								    }
							}
						}catch (IOException ex) {
							JOptionPane.showMessageDialog(aa, "Cant create folders and files! Make sure to give rights!", "Error!", JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
							return;
						}finally {
							try {
								writer.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						aa.dispose();
					}
					
					
				}
			});
			aa.pack();
			aa.setVisible(true);
			
		}else if(e.getSource() == myCmb) {
			selectedMapObject = (MapObject)myCmb.getSelectedItem();
			if(Main.myPanel != null)
				Main.myPanel.repaint();
		}
		
	}

	public void pushVertex(int x, int y) {
		if(selectedMapObject != null)
			selectedMapObject.pushVertex(x, y);
	}

	public ArrayList<MapObject> getAllMapObjects() {
		return mapObjects;
	}

	public void removeLastVertex() {
		if(selectedMapObject != null) {
			selectedMapObject.removeLastVertex();
			Main.myPanel.repaint();
		}
	}
	
}
