package com.facebookanalizapp.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.text.Font;

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

    private Paint Fill;
    private Paint Stroke;

    private String Title = "Başlık";
    private String Info = "Bilgi";

    public BranchBehaviour OptionButtonBehaviour = null;

    private String buttonName;

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public int getX1() {
        return x1;
    }

    public BranchButton(int x, int y, int width, int height, Paint fill, Paint stroke) {
        Stroke = stroke;
        Fill = fill;
        Refresh(x, y, height, width);
        this.setVisible(false);
    }

    public BranchButton(Paint fill, Paint stroke) {
        Stroke = stroke;
        Fill = fill;
        h = w = x1 = y1 = 0;
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
                .fill(Fill)
                .stroke(Stroke)
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

        if (w > 60) {
            inUI();
        }
    }

    private void inUI() {
        Label label = new Label(Title);
        label.setFont(Font.font("Arial", 18));
        label.setTextFill(Color.web("#fff"));
        label.relocate(x1 + 70, y1 + 10);
        this.getChildren().add(label);

        Button chck = new Button("Ayarla");
        //chck.getStylesheets().add("/css/JMetroLightTheme.css");
        chck.relocate(x1 + 100, y1 + 40);
        this.getChildren().add(chck);

        Label label2 = new Label(Info);
        label2.setFont(Font.font("Arial", 15));
        label2.setTextFill(Color.web("#fff"));
        label2.relocate(x1 + 90, y1 + 70);
        this.getChildren().add(label2);

        /*Image img1 = new Image("images/ok.png");
         Image img2 = new Image("images/cancel.png");
        
         ImageView dataImg = new ImageView(img1);
         dataImg.setFitHeight(16);
         dataImg.setFitWidth(16);
         ImageView dataImg2 = new ImageView(img2);
         dataImg2.setFitHeight(16);
         dataImg2.setFitWidth(16);
        
         dataImg.relocate(x1+80, y1+90);
         dataImg2.relocate(x1+100, y1+90);
        
         this.getChildren().add(dataImg);
         this.getChildren().add(dataImg2);*/
        chck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OptionButtonBehaviour.Behaviour();
            }
        });
    }

}
