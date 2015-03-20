package parser;

import graphic.MyRect;
import javafx.collections.ModifiableObservableListBase;

import java.util.*;

public class Combiner {

    public final static Integer big[] = new Integer[] {0,1,2,3};

    public final static HashSet<Integer> bigSet = new HashSet<Integer>(Arrays.asList(big));

    private List<MyRect> myRects;



    public Combiner(List<MyRect> mr) {


        this.myRects = new LinkedList<MyRect>(mr);

    }

    public MyRect combine() {

        List<MyRect> rects = new LinkedList<MyRect>(myRects);
        MyRect leftMost = findLeftMost(myRects);
        rects.remove(leftMost);
        execute(leftMost,rects);
    }

    private MyRect merge(MyRect p, MyRect q) {

    }

    private MyRect execute(MyRect r, List<MyRect> mr) {

        List<MyRect> rects = new LinkedList<MyRect>(mr);


    }

    private MyRect findLeftMost(List<MyRect> mr) {

        double minX = 32767, minBigX = 32767;
        MyRect leftMostRect = null;
        MyRect leftMostBig = null;

        for(MyRect rect : mr) {
            if(rect.lx <= minX) {
                minX = rect.lx;
                leftMostRect = rect;
            } else if(bigSet.contains(rect.getValue()) && rect.lx <= minBigX) {
                minBigX = rect.lx;
                leftMostBig = rect;
            }
        }

        if(left(leftMostRect, leftMostBig)) {
            if(above(leftMostRect,leftMostBig) || below(leftMostRect,leftMostBig)) {
                return leftMostBig;
            } else {
                return leftMostRect;
            }
        } else {
            return leftMostBig;
        }

    }

    private boolean left(MyRect p, MyRect q){
        if(p.lx < q.lx) {
            return true;
        } else {
            return false;
        }
    }

    private boolean above(MyRect p, MyRect q) {
        if(p.ly > q.ry) {
            return true;
        } else {
            return false;
        }
    }

    private boolean below(MyRect p, MyRect q) {
        if(p.ry <= q.ly) {
            return true;
        } else {
            return false;
        }
    }
}
