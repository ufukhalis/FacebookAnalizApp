package com.facebookanalizapp.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.util.Duration;

/**
 *
 * @author ufuk halis
 */
public class NodeUI extends Group {

    private final int RADIUS_START = 40;
    private final int RADIUS_END = 80;

    private final float CIRCLE_SIZE = 38f;

    private final int positionX = 0;
    private final int positionY = 0;

    private final int branchButtonWidth = 160;

    private int nodePositionX;
    private int nodePositionY;

    private int countBranch = 1;

    public void setNodePosition(int posX, int posY) {
        this.nodePositionX = posX + RADIUS_END;//Başlangıç noktasını sıfırlıyor
        this.nodePositionY = posY + RADIUS_END;
        this.relocate(nodePositionX, nodePositionY);
    }

    public int getNodePositionX() {
        return nodePositionX;
    }

    public int getNodePositionY() {
        return nodePositionY;
    }

    public NodeUI(int posX, int posY) {
        setNodePosition(posX, posY);
        drawNode();
        dragAndDrop(this);
    }
    private BranchButton branch1;
    private BranchButton branch2;
    private BranchButton branch3;
    private int animationCount = 1;
    private Path Button1;
    private Path Button2;
    private Path Button3;
    private Circle circle;

    private void drawNode() {

        Button1 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                0, // Start angle
                120, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
        );

        Button2 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                120, // Start angle
                240, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
        );

        Button3 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                240, // Start angle
                360, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.LIGHTGREEN,// Fill
                Color.DARKGREEN //Stroke
        );

        circle = CircleBuilder.create()
                .stroke(Color.DARKGREEN)
                .radius(CIRCLE_SIZE)
                .centerX(this.positionX)
                .centerY(this.positionX)
                .fill(Color.LIGHTGREEN)
                .build();

        branch1 = new BranchButton();
        branch2 = new BranchButton();
        branch3 = new BranchButton();

        branch1.setButtonName("A");
        branch2.setButtonName("B");
        branch3.setButtonName("C");

        Button1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(Button1, branch1);
            }

        });

        Button2.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(Button2, branch2);
            }

        });

        Button3.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(Button3, branch3);
            }

        });

        groupAdd();
    }

    private void groupAdd() {
        this.getChildren().add(branch1);
        this.getChildren().add(branch2);
        this.getChildren().add(branch3);

        this.getChildren().add(Button1);
        this.getChildren().add(Button2);
        this.getChildren().add(Button3);
        this.getChildren().add(circle);
    }
    List<BranchButton> listBranchButton = new ArrayList<>();

    private void controlBranchButtonVisible(Path currentButton, BranchButton currentBranchButton) {
        if (!currentBranchButton.isVisible()) {
            if (countBranch == 1) {
                openBranchAnimation(currentButton, currentBranchButton, positionX, positionY);
            } else if (countBranch == 2) {
                openBranchAnimation(currentButton, currentBranchButton, positionX + branchButtonWidth, positionY);
            } else if (countBranch == 3) {
                openBranchAnimation(currentButton, currentBranchButton, positionX + branchButtonWidth * 2, positionY);
            }
            countBranch++;
        } else {
            countBranch--;
            closeBranchAnimation(currentBranchButton, positionX, positionY);
            
        }
    }

   

    private void openBranchAnimation(Path button, final BranchButton branch, final int posX, final int posY) {
        branch.setVisible(true);
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                branch.Refresh(posX, posY - RADIUS_END, animationCount, RADIUS_END * 2);
                animationCount += 1;
            }
        }));
        fiveSecondsWonder.setCycleCount(branchButtonWidth);
        fiveSecondsWonder.play();
        // button.setDisable(true);
        animationCount = 1;
        listBranchButton.add(branch);
        for (BranchButton branchButton : listBranchButton) {
            System.out.println("value : " + branchButton.getButtonName());
        }
    }
    int index = 0;
    private void closeBranchAnimation(final BranchButton branch, final int posX, final int posY) {
        animationCount = branchButtonWidth;
        
        for (int i = 0; i < listBranchButton.size(); i++) {
            if (listBranchButton.get(i).getButtonName().equalsIgnoreCase(branch.getButtonName())) {
                index = i;
            }
        }
        
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                        /*switch (index) {
                            case 0:
                                branch.Refresh(posX, posY - RADIUS_END, animationCount, RADIUS_END * 2);
                                System.out.println("0");
                                listBranchButton.get(1).Refresh(listBranchButton.get(1).getX1() - animationCount, posY - RADIUS_END, branchButtonWidth, RADIUS_END * 2);
                                listBranchButton.get(2).Refresh(listBranchButton.get(2).getX1() - animationCount, posY - RADIUS_END, branchButtonWidth, RADIUS_END * 2);
                                break;
                            case 1:
                                System.out.println("1");
                                branch.Refresh(posX, posY - RADIUS_END, animationCount, RADIUS_END * 2);
                                listBranchButton.get(2).Refresh(listBranchButton.get(2).getX1() - animationCount, posY - RADIUS_END, branchButtonWidth, RADIUS_END * 2);
                                break;                                
                            case 2:
                                System.out.println("2");
                                branch.Refresh(posX, posY - RADIUS_END, animationCount, RADIUS_END * 2);
                                break;
                        }
                    
                

                animationCount -= 1;*/
            }
        }));
        fiveSecondsWonder.setCycleCount(branchButtonWidth);
        fiveSecondsWonder.play();
        //BranchButton Animasyonu bittiğinde button açılacak
        fiveSecondsWonder.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //   button.setDisable(false);
                branch.setVisible(false);
            }
        });
        listBranchButton.remove(branch);
    }

    private double tempX;
    private double tempY;

    private void dragAndDrop(final Group obj) {
        obj.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                tempX = t.getX() + RADIUS_END;
                tempY = t.getY() + RADIUS_END;
            }
        });
        obj.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (obj.isPressed()) {
                    obj.relocate(t.getSceneX() - tempX, t.getSceneY() - tempY);
                }
                obj.getChildren().clear();
                groupAdd();
            }
        });
    }

    private Path createDartboardField(double centerX, double centerY,
            double degreeStart, double degreeEnd, double innerRadius,
            double outerRadius, Color bgColor, Color strkColor) {

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
