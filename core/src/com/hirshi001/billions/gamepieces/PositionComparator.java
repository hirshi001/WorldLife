package com.hirshi001.billions.gamepieces;

import java.util.Comparator;

public class PositionComparator implements Comparator<Positionable> {
    @Override
    public int compare(Positionable o1, Positionable o2) {
        if(o1.getLayerPosition().y == o2.getLayerPosition().y) return 0;
        return o1.getLayerPosition().y-o2.getLayerPosition().y>0?-1:1;
    }
}
