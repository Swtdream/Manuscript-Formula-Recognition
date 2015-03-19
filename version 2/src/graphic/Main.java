package graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.MySymbol;
import utils.IOUtils;

public class Main {

	private static PaintingPanel paintPanel = null;
	private static JTextArea latexArea = null;
	private static JTextArea messageArea = null;
	private static JTextField numField = null;
	// get dataset
	private static List<MySymbol> symbols = new ArrayList<MySymbol>();
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
			JFrame myFrame = new JFrame("Recognizer");
			myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			myFrame.setLayout(null);
			myFrame.setSize(1000, 700);
			myFrame.setResizable(false);
			//Container c = myFrame.getContentPane();

			JButton clearButton = new JButton("clear");
			clearButton.setBounds(5, 5, 100, 30);
			myFrame.add(clearButton);

			clearButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					paintPanel.clear();
				}

			});

			JButton submitButton = new JButton("submit");
			submitButton.setBounds(110, 5, 100, 30);
			myFrame.add(submitButton);

			submitButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					//paintPanel.saveImage();
					// paintPanel.recognize();
				}
				
			});
			
			JButton addButton = new JButton("addData");
			addButton.setBounds(215, 5, 100, 30);
			myFrame.add(addButton);
			
			addButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					// int v = Integer.valueOf(numField.getText());
					// symbols.addAll(paintPanel.generateData(v));
				}
				
			});
			
			JButton commitButton = new JButton("commit");
			commitButton.setBounds(320, 5, 100, 30);
			myFrame.add(commitButton);
			
			commitButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					// int numStroke = symbols.get(0).getStrokes().size();
					// int v = Integer.valueOf(numField.getText());
					// IOUtils.objectToJsonFile(v, numStroke, symbols);
					// symbols.clear();
					paintPanel.recoAndParse();
				}
				
			});
			
			numField = new JTextField();
			numField.setBounds(425, 5, 200, 30);
			myFrame.add(numField);
			
			paintPanel = new PaintingPanel();
			paintPanel.setBackground(Color.WHITE);
			paintPanel.setBounds(5, 40, 985, 300);
			myFrame.add(paintPanel);

			JLabel label1 = new JLabel("Message");
			label1.setBounds(5, 350, 80, 30);
			myFrame.add(label1);
			
			JLabel label2 = new JLabel("Latex");
			label2.setBounds(510, 350, 80, 30);
			myFrame.add(label2);

			messageArea = new JTextArea();
			messageArea.setBounds(5, 390, 480, 200);
			messageArea.setEditable(false);
			messageArea.setText("TEST");
			myFrame.add(messageArea);

			latexArea = new JTextArea();
			latexArea.setBounds(510, 390, 480, 200);
			latexArea.setEditable(false);
			myFrame.add(latexArea);

			myFrame.setVisible(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
