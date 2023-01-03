package org.jhotdraw.samples.svg.action;

import java.awt.geom.Path2D;

public class RectRoundPathAction {

    double ACV;
    double aw;
    double ah;
    double roundRectXWidth;
    double height;
    double x;
    double y;

    public RectRoundPathAction(double ACV, double height, double aw, double ah, double roundRectX, double roundRectY, double roundRectWidth){
        this.ACV = ACV;
        this.height = height;
        this.aw = aw;
        this.ah = ah;
        this.roundRectXWidth = roundRectX + roundRectWidth;
        this.x = roundRectX;
        this.y = roundRectY;
    }
    public Path2D.Double roundedRect() {
        Path2D.Double path2D = new Path2D.Double();
        path2D.moveTo((x + aw), (float) y);
        path2D.lineTo((roundRectXWidth - aw), (float) y);
        path2D.curveTo((roundRectXWidth - aw * ACV), (float) y, (roundRectXWidth), (float) (y + ah * ACV),
                (roundRectXWidth), (y + ah));
        path2D.lineTo((roundRectXWidth), (y + height - ah));
        path2D.curveTo((roundRectXWidth), (y + height - ah * ACV), (roundRectXWidth - aw * ACV), (y + height), (roundRectXWidth - aw), (y + height));
        path2D.lineTo((x + aw), (y + height));
        path2D.curveTo((x + aw * ACV), (y + height),
                (x), (y + height - ah * ACV),
                (float) x, (y + height - ah));
        path2D.lineTo((float) x, (y + ah));
        path2D.curveTo((x), (y + ah * ACV),
                (x + aw * ACV), (float) (y),
                (float) (x + aw), (float) (y));
        path2D.closePath();
        return path2D;
    }
}
