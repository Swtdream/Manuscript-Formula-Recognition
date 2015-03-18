package graphic;

import java.awt.BasicStroke;
import java.awt.Color;
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
import model.MyPoint;
import recognizer.FeatureExtractor;

public class PaintingPanel extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MyLine> lines;
	private List<MyRect> rectangles;

	private MyLine currentLine;

	private final static int penStrokeWidth = 2;
	private final static int rectStrokeWidth = 1;
	// private final int imageSize = 28;

	private final Stroke paintStroke = new BasicStroke(penStrokeWidth);
	private final Stroke coverStroke = new BasicStroke(rectStrokeWidth);
	private final Color color = Color.BLACK;

	private boolean isDrawing = false;

	public PaintingPanel() {

		lines = new ArrayList<MyLine>();
		rectangles = new ArrayList<MyRect>();

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(paintStroke);
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
		int button = arg0.getButton();
		if (button == MouseEvent.BUTTON1) {
			currentLine = new MyLine();
			int x = arg0.getX();
			int y = arg0.getY();
			currentLine.addPoint(x, y);
			lines.add(currentLine);
			isDrawing = true;
		} else if (button == MouseEvent.BUTTON3) {
			int size = rectangles.size();
			if (size > 0) {
				rectangles.get(size - 1).isActive = false;
				repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isDrawing) {
			isDrawing = false;
			if (currentLine.drawable()) {
				MyRect r1 = currentLine.getRectangle();
				if (rectangles.size() == 0)
					rectangles.add(r1);
				else {
					MyRect r2 = rectangles.get(rectangles.size() - 1);
					if (r2.isActive && r2.isTouched(r1)) {
						r2.join(r1);
					} else {
						r2.isActive = false;
						rectangles.add(r1);
					}
				}
			}
			lines.clear();
			repaint();
		}
	}

	// public void saveImage()
	// {
	// if(rectangles.size() == 0) return;
	// for(int i=0; i<rectangles.size(); i++)
	// {
	// Rectangle r = rectangles.get(i);
	// BufferedImage img = new BufferedImage(r.width+penStrokeWidth,
	// r.height+penStrokeWidth, BufferedImage.TYPE_INT_RGB);
	// Graphics2D g2d = img.createGraphics();
	// g2d.translate(-r.x+penStrokeWidth/2, -r.y+penStrokeWidth/2);
	// this.printAll(g2d);
	// BufferedImage img2 = new BufferedImage(imageSize, imageSize,
	// BufferedImage.TYPE_INT_RGB);
	// Graphics2D g2d2 = img2.createGraphics();
	// g2d2.drawImage(img, 0, 0, imageSize, imageSize, null);
	//
	// /*for(int i=0; i< imageSize; i++)
	// {
	// for(int j=0; j< imageSize; j++)
	// {
	// Color c = new Color(img2.getRGB(j, i));
	// if(c.getBlue()==0)
	// System.out.print(1);
	// else System.out.print(0);
	// }
	// System.out.println();
	// }*/
	//
	// try {
	// ImageIO.write(img2, "BMP", new File(i + ".bmp"));
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally
	// {
	// g2d.dispose();
	// g2d2.dispose();
	// }
	// }
	// }

	public void recognize()
	{
		System.out.println(rectangles.get(0).width);
		FeatureExtractor fe = new FeatureExtractor(rectangles.get(0));
		MyFeature myFeature = fe.Extract();
		
		// test
		MyRect mrect = new MyRect();
		for( ArrayList<MyPoint> stroke : myFeature.feature)
		{
			MyLine mline = new MyLine();
			for(MyPoint point : stroke)
			{
				mline.addPoint((int)(point.x*200)+50, (int)(point.y*200)+50);
			}
			mrect.join(mline);
		}
		repaint();
	}
	
	
	
}
