package recognizer;

/**
 * Created by yingshuo chen on 2015/3/16.
 */

import model.MyFeature;
import model.MyPoint;
import graphic.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FeatureExtractor {

    public final int order = 25;

    public List<ArrayList<MyPoint>> lines;
    public double width, height;
    public double minX, minY;
    public int noOfLines;

    public FeatureExtractor(MyRect rect) {

        lines = new ArrayList<ArrayList<MyPoint>>();
        noOfLines = 0;
        for (MyLine line : rect.lineList) {

            if (line.pointList != null) {

                noOfLines++;
                ArrayList<MyPoint> newLine = new ArrayList<MyPoint>();
                newLine.add(new MyPoint(line.pointList.get(0)));
                for (int i = 1; i < line.pointList.size(); i++) {

                    if (!line.pointList.get(i).equals(line.pointList.get(i - 1))) {
                        newLine.add(new MyPoint(line.pointList.get(i)));
                    }
                }
                lines.add(newLine);
            }
        }
        this.width = (double) rect.width;
        this.height = (double) rect.height;
        this.minX = (double) rect.lx;
        this.minY = (double) rect.ly;

    }

    public MyFeature Extract() {


        MyFeature mf = new MyFeature(noOfLines);
        Interpolate(mf);
        Reshape(mf);
        return mf;

    }

    private void Interpolate(MyFeature mf) {

        double minX = 32767, minY = 32768, maxX = 0, maxY = 0;

        for(int i = 0; i < noOfLines; i ++) {

            double total = 0;
            ArrayList<MyPoint> line = lines.get(i);
            int length = line.size();
            LinkedList<Double> distance = new LinkedList<Double>();

            for(int j = 0; j < length - 1; j++) {

                double temp = Math.sqrt(Math.pow(line.get(j).x - line.get(j + 1).x, 2) + Math.pow(line.get(j).y - line.get(j + 1).y, 2));
                distance.add(temp);
                total += temp;
            }

            double step = total / (order - 1);
            double leftDis = step;
            int k;
            MyPoint tempP = new MyPoint(line.get(0));
            mf.feature.get(i).add(new MyPoint(tempP));
            for(k = 1; !distance.isEmpty();) {

                double tempDis = distance.getFirst();

                if(tempDis >= leftDis) {
                    tempP.x += leftDis/tempDis*(line.get(k).x-tempP.x);
                    tempP.y += leftDis/tempDis*(line.get(k).y-tempP.y);
                    mf.feature.get(i).add(new MyPoint(tempP));
                    distance.set(0,tempDis - leftDis);
                    leftDis = step;

                    maxX = (tempP.x>maxX?tempP.x:maxX);
                    minX = (tempP.x<minX?tempP.x:minX);
                    maxY = (tempP.y>maxY?tempP.y:maxY);
                    minY = (tempP.y<minY?tempP.y:minY);

                }

                else {
                    distance.removeFirst();
                    leftDis -= tempDis;
                    tempP = new MyPoint(line.get(k));
                    k ++;
                }
            }
        }

        this.width = maxX - minX;
        this.height = maxY - minY;
        this.minX = minX;
        this.minY = minY;
    }

    private void Reshape(MyFeature mf) {

        double offsetX, offsetY, unit;
        if(width >= height) {
            unit = width;
            offsetX = 0;
            offsetY = (1 - height/width)/2;
        } else {
            unit = height;
            offsetX = (1 - width/height)/2;
            offsetY = 0;
        }

        for(int i = 0; i < noOfLines; i++) {

            for(MyPoint mp : mf.feature.get(i)) {

                mp.x = (mp.x - minX)/unit + offsetX;
                mp.y = (mp.y - minY)/unit + offsetY;
            }
        }
    }
}