package parser;

import graphic.MyRect;

import java.util.*;

public class Combiner {

    public final static Integer bigOperator[] = new Integer[] {0,1,2,3};

    public final static Integer Operator[] = new Integer[] {0,1,2,3,24,25,26,27};

    public final static HashSet<Integer> bigOperatorSet = new HashSet<Integer>(Arrays.asList(bigOperator));

    public final static HashSet<Integer> OperatorSet = new HashSet<Integer>(Arrays.asList(Operator));

    private List<MyRect> myRects;



    public Combiner(List<MyRect> mr) {


        initialize(mr);
        Collections.sort(mr);
        this.myRects = new LinkedList<MyRect>(mr);


    }

    public void initialize(List<MyRect> mr) {

        for(MyRect rect : mr) {
            if(rect.getValue() == 3) {
                boolean i = false, j = false;
                for(MyRect r : mr) {
                    if(PositionDetector.isContainedBy(r, rect)) {
                        if(PositionDetector.isAbove(r, rect)) {
                            i = true;
                        } else if(PositionDetector.isBelow(r, rect)) {
                            j = true;
                        }
                    }
                }
                if(!(i && j)) {
                    rect.setValue(25);
                    rect.setLatex("-");
                }
            }
        }
    }

    private MyRect merge(MyRect o, MyRect p, MyRect q, MyRect r) {

        if(o.getValue() == 3) {
            if(p != null) {
                o.setLatex(o.getLatex() + "{" + p.getLatex() + "}");
            }
            if(q != null) {
                o.setLatex(o.getLatex() + "{" + q.getLatex() + "}");
            }
            if(r != null) {
                o.setLatex(o.getLatex() + "{" + r.getLatex() + "}");
            }
        } else {
            if(q != null) {
                o.setLatex(o.getLatex() +  "_{" + q.getLatex() + "}");
            }
            if(p != null) {
                o.setLatex(o.getLatex() + "^{" + p.getLatex() + "}");
            }
            if(r != null) {
                o.setLatex(o.getLatex() + r.getLatex());
            }
        }

        return o;
    }

    public MyRect combine() {

        return combine(myRects);

    }

    private MyRect combine(List<MyRect> mr) {

        if(mr.size() == 0) {
            return  null;
        }

        List<MyRect> rightSet = new LinkedList<MyRect>(mr);
        MyRect r = rightSet.remove(0);
        int length = rightSet.size();

        if(bigOperatorSet.contains(r.getValue())) {

            List<MyRect> aboveSet = new LinkedList<MyRect>();
            List<MyRect> belowSet = new LinkedList<MyRect>();

            for(int i = 0; i < length; i++) {

                MyRect rect = rightSet.get(0);
                if(PositionDetector.isAbove(rect, r)) {

                    aboveSet.add(rect);
                } else if(PositionDetector.isBelow(rect, r)) {

                    belowSet.add(rect);
                } else {

                   break;
                }
                rightSet.remove(0);
            }


            return merge(r, combine(aboveSet), combine(belowSet), combine(rightSet));
        } else if(OperatorSet.contains(r.getValue())) {
            return merge(r, null, null, combine(rightSet));
        } else {

            List<MyRect> upperSet = new LinkedList<MyRect>();
            List<MyRect> lowerSet = new LinkedList<MyRect>();

            for(int i = 0; i < length; i++) {

                MyRect rect = rightSet.get(0);
                if(PositionDetector.isUpper(rect, r)) {

                    upperSet.add(rect);
                } else if(PositionDetector.isLower(rect, r)) {

                    lowerSet.add(rect);
                } else {

                    break;
                }
                rightSet.remove(0);
            }

            return merge(r, combine(upperSet), combine(lowerSet), combine(rightSet));
        }

    }

//    private MyRect findLeftMost(List<MyRect> mr) {
//
//        double minX = 32767, minBigX = 32767;
//        MyRect leftMostRect = null;
//        MyRect leftMostBig = null;
//
//        for(MyRect rect : mr) {
//            if(rect.lx <= minX) {
//                minX = rect.lx;
//                leftMostRect = rect;
//            } else if(bigOperatorSet.contains(rect.getValue()) && rect.lx <= minBigX) {
//                minBigX = rect.lx;
//            }
//                leftMostBig = rect;
//        }
//
//        if(PositionDetector.isLeft(leftMostRect, leftMostBig)) {
//            if(PositionDetector.isAbove(leftMostRect, leftMostBig) || PositionDetector.isBelow(leftMostRect, leftMostBig)) {
//                return leftMostBig;
//            } else {
//                return leftMostRect;
//            }
//        } else {
//            return leftMostBig;
//        }
//
//    }


}
