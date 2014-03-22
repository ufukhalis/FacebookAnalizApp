/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.ui;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author hp1
 */
public class MainFrameUI extends ScrollPane {

    private static final double MAX_SCALE = 5.5d; //Max zoom boyutu
    private static final double MIN_SCALE = .5d; //Min zoom boyutu

    Pane root;
    ScrollPane Content;
    NodeUI nodeUi ;
    MenuBar menuBar;
    ToolBar toolBar;
    BorderPane borderPane;
    
    public MainFrameUI() {
        borderPane = new BorderPane();
        nodeUi = new NodeUI(195, 195);
        toolBar = new ToolBar();
        toolBar.setPrefWidth(800);
        toolBar.getItems().add(new Button("Tool Opt2"));
        
        //borderPane.setTop(toolBar);
        Pane pane = new Pane();
        pane.getChildren().add(nodeUi);
        
        borderPane.setCenter(pane);
        root = new Pane();
        root.getChildren().add(toolBar);
        root.getChildren().add(borderPane);

        this.prefHeight(600);
        this.prefWidth(800);
        Content = this;
        
        this.setOnScroll(new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent t) { 
                final double scale = calculateScale(t, root);
                root.setScaleX(scale);
                root.setScaleY(scale);              
            }
        });
          
        this.setContent(root);
    }

    private double calculateScale(ScrollEvent scrollEvent, Pane root) {
        double scale = root.getScaleX() + scrollEvent.getDeltaY() / 100;

        if (scale <= MIN_SCALE) {
            scale = MIN_SCALE;
        } else if (scale >= MAX_SCALE) {
            scale = MAX_SCALE;
        }
        return scale;
    }

}
