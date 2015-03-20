package parser;

import graphic.MyRect;

public class PositionDetector {


    public static boolean isLeft(MyRect p, MyRect q){
        if(p.lx < q.lx) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAbove(MyRect p, MyRect q) {
        if(p.ry <= q.ly) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBelow(MyRect p, MyRect q) {
        if(p.ly >= q.ry) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpper(MyRect p, MyRect q) {

        if(p.ry < q.ly + q.height / 4 && p.height < q.height / 2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLower(MyRect p, MyRect q) {

        if(p.ly > q.ry - q.height / 4 && p.height < q.height / 2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isContainedBy(MyRect p, MyRect q) {
        if(!Combiner.bigOperatorSet.contains(q.getValue())) {

            return false;
        } else if(!Combiner.bigOperatorSet.contains(p.getValue())) {

            if((p.rx > q.lx && p.rx < q.rx) || (p.lx > q.lx && p.lx < q.rx)) {
                return true;
            } else {
                return false;
            }
        } else {
            if(p.width < q.width) {
                if((p.rx > q.lx && p.rx < q.rx) || (p.lx > q.lx && p.lx < q.rx)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
