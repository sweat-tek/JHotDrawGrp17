//package org.jhotdraw.samples.svg.util;
//
//
//import org.jhotdraw.draw.figure.Figure;
//import org.jhotdraw.geom.GrowStroke;
//import org.jhotdraw.samples.svg.SVGAttributeKeys;
//
//import java.awt.*;
//
//import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
//import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_GRADIENT;
//
//public class SVGHitShape {
//
//    private Shape getHitShape(Shape cachedHitShape, Figure figure) {
//        if (cachedHitShape == null) {
//            if (get(FILL_COLOR) != null || get(FILL_GRADIENT) != null) {
//                cachedHitShape = new GrowStroke(
//                        (float) SVGAttributeKeys.getStrokeTotalWidth(figure, 1.0) / 2f,
//                        (float) SVGAttributeKeys.getStrokeTotalMiterLimit(figure, 1.0)).createStrokedShape(getTransformedShape());
//            } else {
//                cachedHitShape = SVGAttributeKeys.getHitStroke(figure, 1.0).createStrokedShape(getTransformedShape());
//            }
//        }
//        return cachedHitShape;
//    }
//
//}
