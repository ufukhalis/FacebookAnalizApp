/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.ui;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author hp1
 */
public class MainFrameUI extends ScrollPane {

    private static final double MAX_SCALE = 5.5d; //Max zoom boyutu
    private static final double MIN_SCALE = .5d; //Min zoom boyutu
    
    Pane root;

    public MainFrameUI() {
        NodeUI nodeUi = new NodeUI(195, 195);
        root = new Pane();
        root.getChildren().add(nodeUi);

        
        this.setOnScroll(new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent t) {
                final double scale = calculateScale(t,root);
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
