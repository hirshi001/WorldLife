package com.hirshi001.billions.util.math;

import com.badlogic.gdx.math.Vector2;

public class Line {

    static Vector2 lineLineIntersection(Vector2 A, Vector2 B, Vector2 C, Vector2 D)
    {
        // Line AB represented as a1x + b1y = c1
        double a1 = B.y - A.y;
        double b1 = A.x - B.x;
        double c1 = a1*(A.x) + b1*(A.y);

        // Line CD represented as a2x + b2y = c2
        double a2 = D.y - C.y;
        double b2 = C.x - D.x;
        double c2 = a2*(C.x)+ b2*(C.y);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return null;
        }
        else
        {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;
            return new Vector2((float)x, (float)y);
        }
    }

    public static Vector2 segmentIntersectionPoint(Vector2 A, Vector2 B, Vector2 C, Vector2 D){
        Vector2 intPoint = lineLineIntersection(A, B, C, D);
        if(intPoint==null) return null;
        if(inBoundingBox(intPoint, A, B) && inBoundingBox(intPoint, C, D)) return intPoint;
        return null;
    }

    public static boolean inBoundingBox(Vector2 point, Vector2 A, Vector2 B){
        return (point.x>A.x && point.x<B.x && point.y>A.y && point.y<B.y) || (point.x<A.x && point.x>B.x && point.y<A.y && point.y>B.y);
    }

}
