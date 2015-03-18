package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yingshuo chen on 2015/3/16.
 */
public class MyFeature {

    public List<ArrayList<MyPoint>> feature;

    public MyFeature(int lines) {

        feature = new ArrayList<ArrayList<MyPoint>>();
        for(int i = 0; i < lines; i++) {
            feature.add(new ArrayList<MyPoint>());
        }

    }


}