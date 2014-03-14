package com.facebookanalizapp.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;

public class BranchButton extends Group {

    private int degrees = 1; // ilginç bişey :)

    private int h;
    private int w;

    private int x1;
    private int y1;
    
    private int x2;
    private int y2;

    private int x3;
    private int y3;

    private int x4;
    private int y4;

    public BranchButton(int x, int y, int width, int height) {
        Refresh(x, y, height, width);
        this.setVisible(false);
    }
    
    public BranchButton() {
      h=w=x1=y1=0;
      this.setVisible(false);
    }

    public void Refresh(int x, int y, int width, int height) {
        x1 = x;
        y1 = y;
        w = width;
        h = height;
        
        x2 = x1 + w;
        y2 = y1;

        x3 = x2;
        y3 = y2 + h;

        x4 = x3 - w;
        y4 = y3;
        
        this.draw();
    }

    private void draw() {
        this.getChildren().clear();//Grup içerisindeki herşeyi siler.

        final Path path = PathBuilder.create()
                .fill(Color.LIGHTGREEN)
                .stroke(Color.DARKGREEN)
                .elements(
                        new MoveTo(x1, y1), // Move to Point 1
                        new LineTo(x2, y2), // Draw a Line to Point 2
                        ArcToBuilder.create() // Draw an Arc to Point 3
                        .radiusX(degrees)
                        .radiusY(degrees)
                        .x(x3)
                        .y(y3)
                        .sweepFlag(true)
                        .build(),
                        new LineTo(x4, y4), // Draw a Line to Point 4
                        ArcToBuilder.create() // Draw an Arc back to Point 1
                        .radiusX(degrees)
                        .radiusY(degrees)
                        .x(x1)
                        .y(y1)
                        .sweepFlag(false)
                        .build())
                .build();
        this.getChildren().add(path);
    }

}
