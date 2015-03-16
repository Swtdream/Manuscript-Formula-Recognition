package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 陈英硕 on 2015/3/16.
 */
public class MyFeature {

    public List<ArrayList<MyPoint>> feature;

    public MyFeature(int lines) {

        feature = new ArrayList<ArrayList<MyPoint>>(lines);

    }


}