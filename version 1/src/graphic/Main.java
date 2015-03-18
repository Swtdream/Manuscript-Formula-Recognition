package graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	private static PaintingPanel paintPanel = null;
	private static JTextArea latexArea = null;
	private static JTextArea messageArea = null;

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
					paintPanel.recognize();
				}
				
			});
			
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
			
//			JButton andButton = new JButton("∧");
//			andButton.setBounds(5, 350, 50, 30);
//			myFrame.add(andButton);
//
//			JButton orButton = new JButton("∨");
//			orButton.setBounds(60, 350, 50, 30);
//			myFrame.add(orButton);
//
//			JButton notButton = new JButton("¬");
//			notButton.setBounds(115, 350, 50, 30);
//			myFrame.add(notButton);
//
//			JButton lpButton = new JButton("(");
//			lpButton.setBounds(170, 350, 50, 30);
//			myFrame.add(lpButton);
//
//			JButton rpButton = new JButton(")");
//			rpButton.setBounds(225, 350, 50, 30);
//			myFrame.add(rpButton);
//
//			JButton implyButton = new JButton("→");
//			implyButton.setBounds(280, 350, 50, 30);
//			myFrame.add(implyButton);

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
