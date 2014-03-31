package com.facebookanalizapp.ui;

import com.facebookanalizapp.process.FXMLTool;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private int countBranch = 0;

    public void setNodePosition(int posX, int posY) {
        this.nodePositionX = posX + RADIUS_END ;//Başlangıç noktasını sıfırlıyor
        this.nodePositionY = posY + RADIUS_END ;
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
        branchButtonIndex = new ArrayList<BranchButton>();
    }
    private BranchButton branch1;
    private BranchButton branch2;
    private BranchButton branch3;

    private Path Button1;
    private Path Button2;
    private Path Button3;
    private Circle circle;

    private List<BranchButton> branchButtonIndex;

    private void drawNode() {

        Button1 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                0, // Start angle
                120, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.rgb(78, 78, 78),// Fill
                Color.rgb(78, 78, 78) //Stroke
        );

        Button2 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                120, // Start angle
                240, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.rgb(78, 78, 78),// Fill
                Color.rgb(78, 78, 78) //Stroke
        );

        Button3 = createDartboardField(
                this.positionX,// Center X
                this.positionY,// Center Y
                240, // Start angle
                360, // End angle
                RADIUS_START, // Radius start
                RADIUS_END, // Radius end
                Color.rgb(78, 78, 78),// Fill
                Color.rgb(78, 78, 78) //Stroke
        );

        buttonColorEvents(Button1, Color.rgb(78, 78, 78), Color.rgb(95, 95, 95), Color.rgb(60, 60, 60));
        buttonColorEvents(Button2, Color.rgb(78, 78, 78), Color.rgb(95, 95, 95), Color.rgb(60, 60, 60));
        buttonColorEvents(Button3, Color.rgb(78, 78, 78), Color.rgb(95, 95, 95), Color.rgb(60, 60, 60));

        circle = CircleBuilder.create()
                //.stroke(Color.rgb(66,66,81))
                .radius(CIRCLE_SIZE)
                .centerX(this.positionX)
                .centerY(this.positionX)
                .fill(Color.rgb(231, 231, 233))
                .build();

        buttonColorEvents(circle, Color.rgb(231, 231, 233), Color.rgb(220, 220, 222), Color.rgb(200, 200, 202));

        branch1 = new BranchButton(Color.rgb(255, 64, 0), Color.rgb(255, 64, 0));
        branch2 = new BranchButton(Color.rgb(109, 217, 0), Color.rgb(109, 217, 0));
        branch3 = new BranchButton(Color.rgb(0, 178, 178), Color.rgb(0, 178, 178));
        
        branch1.setTitle("Veri");
        branch2.setTitle("Veri Madenciliği");
        branch3.setTitle("Sunum");
        
        branch1.OptionButtonBehaviour= new BranchBehaviour() {

            @Override
            public void Behaviour() {
                 FXMLTool.instance().openFXML("Veri Seçiniz", "DataFXML.fxml", true);
            }
        };

        Button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Veri
            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(branch1);
            }

        });

        Button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Verimadenciliği
            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(branch2);
            }

        });

        Button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Sunum
            @Override
            public void handle(MouseEvent t) {
                controlBranchButtonVisible(branch3);
            }

        });

        groupAdd();
    }

    private void buttonColorEvents(final javafx.scene.shape.Shape btn, final Paint color, final Paint entered, final Paint pressed) {
        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                btn.setFill(entered);
            }
        });
        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                btn.setFill(color);
            }
        });
        btn.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                btn.setFill(pressed);
            }
        });

        btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                btn.setFill(entered);
            }
        });
    }

    private void groupAdd() {
        this.getChildren().add(branch1);
        this.getChildren().add(branch2);
        this.getChildren().add(branch3);

        this.getChildren().add(Button1);
        this.getChildren().add(Button2);
        this.getChildren().add(Button3);
        this.getChildren().add(circle);

        Image img1 = new Image("images/data.png");
        Image img2 = new Image("images/mining.png");
        Image img3 = new Image("images/press.png");

        ImageView dataImg = new ImageView(img1);
        ImageView miningImg = new ImageView(img2);
        ImageView pressImg = new ImageView(img3);

        this.getChildren().add(dataImg);
        this.getChildren().add(miningImg);
        this.getChildren().add(pressImg);

        dataImg.relocate(40, -40);
        miningImg.relocate(-18, 49);
        pressImg.relocate(-68, -32);

        Tooltip tp1 = new Tooltip();
        tp1.setText("Veri Bloğu");

        Tooltip tp2 = new Tooltip();
        tp2.setText("Veri Madenciliği Bloğu");

        Tooltip tp3 = new Tooltip();
        tp3.setText("Sunum Bloğu");

        Tooltip.install(Button1, tp1);
        Tooltip.install(Button2, tp2);
        Tooltip.install(Button3, tp3);

    }

    private void controlBranchButtonVisible(BranchButton currentBranchButton) {
        if (!currentBranchButton.isVisible()) {
            if (countBranch == 0 && currentBranchButton.getButtonName() == null) {
                new openBranchAnimation(currentBranchButton, positionX, positionY);
                currentBranchButton.setButtonName("A");
                branchButtonIndex.add(currentBranchButton);
                System.out.println("A");
                countBranch += 1;
            } else if (countBranch == 1 && currentBranchButton.getButtonName() == null) {
                new openBranchAnimation(currentBranchButton, positionX + branchButtonWidth, positionY);
                currentBranchButton.setButtonName("B");
                branchButtonIndex.add(currentBranchButton);
                System.out.println("B");
                countBranch += 1;
            } else if (countBranch == 2 && currentBranchButton.getButtonName() == null) {
                new openBranchAnimation(currentBranchButton, positionX + branchButtonWidth * 2, positionY);
                currentBranchButton.setButtonName("C");
                branchButtonIndex.add(currentBranchButton);
                System.out.println("C");
                countBranch += 1;
            }
        } else {
            if (currentBranchButton.getButtonName().contains(branchButtonIndex.get(0).getButtonName())) {
                new closeBranchAnimation(currentBranchButton, currentBranchButton.getX1(), positionY);
                //System.out.println(branchButtonIndex.size());
                if (branchButtonIndex.size() == 3) {// A + 2 Buton Listede ise ; A kapanma işlemi başlamıştır
                    //Diğer 2 buton taşınır
                    new moveXBranchAnimation(branchButtonIndex.get(2), branchButtonIndex.get(2).getX1(), positionY);
                    new moveXBranchAnimation(branchButtonIndex.get(1), branchButtonIndex.get(1).getX1(), positionY);
                } else if (branchButtonIndex.size() == 2) {// A + 1 Buton Listede ise ; A kapanma işlemi başlamıştır
                    //Diğer 1 Buton Taşınır
                    new moveXBranchAnimation(branchButtonIndex.get(1), branchButtonIndex.get(1).getX1(), positionY);
                }
                branchButtonIndex.remove(currentBranchButton);
                System.out.println("-A");
                countBranch -= 1;
            } else if (currentBranchButton.getButtonName().contains(branchButtonIndex.get(1).getButtonName())) {
                new closeBranchAnimation(currentBranchButton, currentBranchButton.getX1(), positionY);
                //System.out.println(branchButtonIndex.size());
                if (branchButtonIndex.size() == 3) {//B + 2 Buton Listede ise ; B kapanma işlemi Başlamıştır
                    new moveXBranchAnimation(branchButtonIndex.get(2), branchButtonIndex.get(2).getX1(), positionY);
                } else if (branchButtonIndex.size() == 2) {
                    new moveXBranchAnimation(branchButtonIndex.get(1), branchButtonIndex.get(1).getX1(), positionY);
                }
                branchButtonIndex.remove(currentBranchButton);
                System.out.println("-B");
                countBranch -= 1;
            } else if (currentBranchButton.getButtonName().contains(branchButtonIndex.get(2).getButtonName())) {
                new closeBranchAnimation(currentBranchButton, currentBranchButton.getX1(), positionY);
                System.out.println("-C");
                branchButtonIndex.remove(currentBranchButton);
                countBranch -= 1;
            }
        }
    }

    private double tempX;
    private double tempY;

    private void dragAndDrop(final Group obj) {
        obj.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                tempX = t.getX()+153;
                tempY = t.getY()+56;
            }
        });

        obj.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.isControlDown()) {//Ctrl kontrol
                    if (obj.isPressed()) {
                        obj.relocate(t.getSceneX()-tempX, t.getSceneY()-tempY);
                    }    
                    obj.getChildren().clear();
                    groupAdd();
                }
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

    /*
     Branch Button Animation Classes
     */
    class openBranchAnimation {

        private int count = 1;

        public openBranchAnimation(final BranchButton branch, final int posX, final int posY) {
            branch.setVisible(true);
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    branch.Refresh(posX, posY - RADIUS_END, count, RADIUS_END * 2);
                    count += 1;
                }
            }));
            fiveSecondsWonder.setCycleCount(branchButtonWidth);
            fiveSecondsWonder.play();
            // button.setDisable(true);
            count = 1;
        }
    }

    class closeBranchAnimation {

        private int count;

        public closeBranchAnimation(final BranchButton branch, final int posX, final int posY) {
            count = branchButtonWidth;

            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    branch.Refresh(posX, posY - RADIUS_END, count, RADIUS_END * 2);
                    count -= 1;
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
                    branch.setButtonName(null);
                }
            });
        }
    }

    class moveXBranchAnimation {

        private int count = 1;

        public moveXBranchAnimation(final BranchButton branch, final int posX, final int posY) {

            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    branch.Refresh(posX - count, posY - RADIUS_END, branchButtonWidth, RADIUS_END * 2);
                    count += 1;
                }
            }));

            fiveSecondsWonder.setCycleCount(branchButtonWidth);
            fiveSecondsWonder.play();
            //BranchButton Animasyonu bittiğinde button açılacak
           /* fiveSecondsWonder.setOnFinished(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent t) {

             }
             });*/
        }
    }
}
