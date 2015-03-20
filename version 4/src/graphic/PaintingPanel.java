package graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.MyFeature;
import model.MySymbol;
import parser.PositionDetector;
import recognizer.FeatureExtractor;
import recognizer.SymbolClassifier;

public class PaintingPanel extends JPanel implements MouseListener,
		MouseMotionListener {

	private static final long serialVersionUID = 1L;

	private static long rectID = 0;

	private List<MyLine> lines;
	private List<MyRect> rectangles;

	private MyLine currentLine;

	private final static int penStrokeWidth = 2;
	private final static int rectStrokeWidth = 1;

	private final Stroke paintStroke = new BasicStroke(penStrokeWidth);
	private final Stroke coverStroke = new BasicStroke(rectStrokeWidth);
	private final Color color = Color.BLACK;
	private final Font font = new Font("TimesRoman", Font.PLAIN, 15);

	private SymbolClassifier myClassifier = null;
	private PositionDetector myDetector = null;
	private OnParsedListener myParserListener = null;

	private boolean isDrawing = false;
	private boolean isParsing = false;

	public PaintingPanel() {

		lines = new ArrayList<MyLine>();
		rectangles = new ArrayList<MyRect>();

		myClassifier = new SymbolClassifier();
		myDetector = new PositionDetector();

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void addOnParsedListener(OnParsedListener listener) {
		this.myParserListener = listener;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(paintStroke);
		g2d.setFont(font);
		g2d.setColor(color);

		for (MyLine line : lines) {
			line.draw(g2d);
		}
		for (MyRect rect : rectangles) {
			rect.drawLines(g2d);
		}

		g2d.setStroke(coverStroke);

		for (MyRect rect : rectangles) {
			rect.drawRect(g2d);
		}

	}

	public void clear() {
		rectangles.clear();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isDrawing) {
			int x = arg0.getX();
			int y = arg0.getY();
			currentLine.addPoint(x, y);
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (!isParsing) {
			int button = arg0.getButton();
			if (button == MouseEvent.BUTTON1) {
				currentLine = new MyLine();
				int x = arg0.getX();
				int y = arg0.getY();
				currentLine.addPoint(x, y);
				lines.add(currentLine);
				isDrawing = true;
			} else if (button == MouseEvent.BUTTON3) {
				int size = rectangles.size() - 1;
				if (size >= 0) {
					MyRect gRect = rectangles.get(size);
					if (gRect.isActive) {
						gRect.isActive = false;
						recognize(gRect.getID(), size);
						repaint();
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isDrawing) {
			isDrawing = false;
			if (currentLine.drawable()) {
				MyRect r1 = currentLine.getRectangle(rectID++);
				if (rectangles.size() == 0)
					rectangles.add(r1);
				else {
					int l = rectangles.size() - 1;
					MyRect r2 = rectangles.get(l);
					if (r2.isActive && r2.isTouching(r1)) {
						r2.join(r1);
					} else {
						r2.isActive = false;
						recognize(r2.getID(), l);
						rectangles.add(r1);
					}
				}
			}
			lines.clear();
			repaint();
		}
	}

	public void recognize(long id, int index) {
		Thread thread = new Thread(new RecognizeRunnable(id, index));
		thread.start();
	}

	public void recoAndParse() {
		// Pause the drawing function.
		isParsing = true;
		for (int i = 0; i < rectangles.size(); i++) {
			MyRect rect = rectangles.get(i);
			if (rect.isActive) {
				recognize(rect.getID(), i);
			}
		}
		Thread thread = new Thread(new ParserRunnable());
		thread.start();
	}

	private class ParserRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!isAllRecognized()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			myDetector.setBlocks(rectangles);
			String res = myDetector.getResult() + "\n\n";
			if (myParserListener != null) {
				myParserListener.onParsed(res);
			}
			PaintingPanel.this.isParsing = false;
		}

		private boolean isAllRecognized() {
			for (MyRect rect : rectangles) {
				if (rect.getValue() == -1)
					return false;
			}
			return true;
		}

	}

	private class RecognizeRunnable implements Runnable {
		private long goalID;
		private int goalIndex;

		public RecognizeRunnable(long id, int index) {
			goalID = id;
			goalIndex = index;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			MyRect rect = rectangles.get(goalIndex);
			FeatureExtractor fe = new FeatureExtractor(rect);
			MyFeature myFeature = fe.Extract();
			MySymbol mySymbol = fe.PointToUnit(myFeature);
			int value = myClassifier.classify(mySymbol.getStrokes());
			if (rectangles.size() > goalIndex) {
				MyRect gRect = rectangles.get(goalIndex);
				if (gRect.isMe(goalID)) {
					gRect.setValue(value);
					gRect.setLatex(SymbolClassifier.valueToLatex[value]);
					gRect.isActive = false;
					repaint();
				}
			}
		}

	}

	/*
	 * public List<MySymbol> generateData(int v) { List<MySymbol> symbols = new
	 * ArrayList<MySymbol>(); for(MyRect rect : rectangles) { FeatureExtractor
	 * fe = new FeatureExtractor(rect); MyFeature mf = fe.Extract(); MySymbol
	 * myS = fe.PointToUnit(mf); myS.setValue(v); symbols.add(myS); } return
	 * symbols; }
	 */

}
