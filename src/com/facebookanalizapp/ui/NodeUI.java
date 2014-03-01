

package com.facebookanalizapp.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;

/**
 *
 * @author ufuk halis
 */
public class NodeUI extends Group{
    private final int RADIUS_START = 70;
    private final int RADIUS_END = 140;
    
    private final float CIRCLE_SIZE = 50f;    
    
    private int positionX;
    private int positionY;

    /**
     * 
     * @param positionX set Node position X
     * @param positionY set Node position Y
     */
    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    
    public NodeUI(int X, int Y) {
        setPosition(X, Y);
        drawNode();
    }
    
    private void drawNode(){
        Path Button1 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                0, // Start angle
                120,  // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
                );
        
        Path Button2 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                120, // Start angle
                240,  // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
                );
  
        Path Button3 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                240, // Start angle
                360,  // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
                );

        Circle circle = CircleBuilder.create()
                .stroke(Color.DARKGREEN)
                .radius(CIRCLE_SIZE)
                .centerX(this.positionX)
                .centerY(this.positionX)
                .fill(Color.LIGHTGREEN)
                .build();
        
        this.getChildren().add(Button1);
        this.getChildren().add(Button2);
        this.getChildren().add(Button3);
        this.getChildren().add(circle);
    }
    
    private Path createDartboardField(double centerX, double centerY,
            double degreeStart, double degreeEnd, double innerRadius,
            double outerRadius,  Color bgColor, Color strkColor) {
        
        double angleAlpha = degreeStart * (Math.PI / 180);
        double angleAlphaNext = degreeEnd * (Math.PI / 180);

        //Point 1
        double pointX1 = centerX + innerRadius * Math.sin(angleAlpha);
        double pointY1 = centerY - innerRadius * Math.cos(angleAlpha);

        //Point 2
        double pointX2 = centerX + outerRadius * Math.sin(angleAlpha);
        double pointY2 = centerY - outerRadius * Math.cos(angleAlpha);

        //Point 3
        double pointX3 = centerX + outerRadius * Math.sin(angleAlphaNext);
        double pointY3 = centerY - outerRadius * Math.cos(angleAlphaNext);

        //Point 4
        double pointX4 = centerX + innerRadius * Math.sin(angleAlphaNext);
        double pointY4 = centerY - innerRadius * Math.cos(angleAlphaNext);

        Path path = PathBuilder.create()
                .fill(bgColor)
                .stroke(strkColor)
                .elements(
                        new MoveTo(pointX1, pointY1), // Move to Point 1
                        new LineTo(pointX2, pointY2), // Draw a Line to Point 2
                        ArcToBuilder.create() // Draw an Arc to Point 3
                        .radiusX(outerRadius)
                        .radiusY(outerRadius)
                        .x(pointX3)
                        .y(pointY3)
                        .sweepFlag(true)
                        .build(),
                        new LineTo(pointX4, pointY4), // Draw a Line to Point 4
                        ArcToBuilder.create() // Draw an Arc back to Point 1
                        .radiusX(innerRadius)
                        .radiusY(innerRadius)
                        .x(pointX1)
                        .y(pointY1)
                        .sweepFlag(false)
                        .build())
                .build();
        return path;
    }
}
