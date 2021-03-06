package com.facebookanalizapp.ui;

import com.facebookanalizapp.controller.DataFXMLController;
import com.facebookanalizapp.controller.MainFXMLController;
import com.facebookanalizapp.controller.MiningFXMLController;
import com.facebookanalizapp.controller.PresentationFXMLController;
import com.facebookanalizapp.db.DatabaseManager;
import com.facebookanalizapp.entity.ClusteringEntity;
import com.facebookanalizapp.entity.CosineEntity;
import com.facebookanalizapp.entity.DataEntity;
import com.facebookanalizapp.entity.ExecutedRulesEntity;
import com.facebookanalizapp.entity.MiningEntity;
import com.facebookanalizapp.entity.PresentationEntity;
import com.facebookanalizapp.mining.KMeans;
import com.facebookanalizapp.process.FXMLTool;
import com.facebookanalizapp.process.Node;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ufuk halis
 */
public class NodeUI extends Group {

    public Node parent;

    public Long nodeId = 0l;

    private final int RADIUS_START = 40;
    private final int RADIUS_END = 80;

    private final float CIRCLE_SIZE = 38f;

    private final int positionX = 0;
    private final int positionY = 0;

    private final int branchButtonWidth = 160;

    private int nodePositionX;
    private int nodePositionY;

    private int countBranch = 0;

    private NodeUI content;

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

    public NodeUI(Node _parent, int posX, int posY) {
        this.parent = _parent;
        setNodePosition(posX, posY);
        drawNode();
        dragAndDrop(this);
        branchButtonIndex = new ArrayList<BranchButton>();
        content = this;

    }
    public BranchButton branch1;
    public BranchButton branch2;
    public BranchButton branch3;

    public BranchButton getBranch1() {
        return branch1;
    }

    public BranchButton getBranch2() {
        return branch2;
    }

    public BranchButton getBranch3() {
        return branch3;
    }

    private Path Button1;
    private Path Button2;
    private Path Button3;
    private Circle circle;
    private StackPane NodeLabel;

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

        branch1.setTitle("Data");
        branch2.setTitle("Data Mining");
        branch3.setTitle("Presentation");

        branch1.OptionButtonBehaviour = new BranchBehaviour() {

            @Override
            public void Behaviour() {
                FXMLTool.instance().openFXML("Selecting Data Layer", "DataFXML.fxml", false);
                DataFXMLController.instance().parentNode = parent;
                DataFXMLController.instance().refreshTable();
            }
        };

        branch2.OptionButtonBehaviour = new BranchBehaviour() {

            @Override
            public void Behaviour() {
                FXMLTool.instance().openFXML("Data Mining Layer", "MiningFXML.fxml", false, 708, 522);
                MiningFXMLController.instance().parentNode = parent;
                MiningFXMLController.instance().fillAttributeList();
                MiningFXMLController.instance().fillKmeansControls();
            }
        };

        branch3.OptionButtonBehaviour = new BranchBehaviour() {

            @Override
            public void Behaviour() {
                FXMLTool.instance().openFXML("Presentation Layer", "PresentationFXML.fxml", false);
                PresentationFXMLController.instance().parentNode = parent;
                PresentationFXMLController.instance().setSelectedChartType();

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

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {

                if (t.getButton() == MouseButton.SECONDARY) {
                    String[] buttonOptions = new String[]{"Save", "Remove", "Cancel"};

                    int result = JOptionPane.showOptionDialog(null, "Select the node chosen for the process.", "Select Operations", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttonOptions, buttonOptions[0]);

                    switch (result) {
                        case 0://Kaydetme ve güncelleme
                            if (nodeId == 0) {
                                saveNode();
                            } else {
                                ExecutedRulesEntity execNode = DatabaseManager.instance().find(ExecutedRulesEntity.class, nodeId);
                                MiningEntity mEntity = DatabaseManager.instance().find(MiningEntity.class, execNode.getMiningID());
                                CosineEntity cosEntity = DatabaseManager.instance().find(CosineEntity.class, mEntity.getCosineID());
                                ClusteringEntity cEntity = DatabaseManager.instance().find(ClusteringEntity.class, mEntity.getCosineID());

                                DatabaseManager.instance().removeEntity(DatabaseManager.instance().find(DataEntity.class, execNode.getDataID()));
                                DatabaseManager.instance().removeEntity(cosEntity);
                                DatabaseManager.instance().removeEntity(cEntity);
                                DatabaseManager.instance().removeEntity(mEntity);
                                DatabaseManager.instance().removeEntity(DatabaseManager.instance().find(PresentationEntity.class, execNode.getPresentationID()));
                                DatabaseManager.instance().removeEntity(execNode);
                                saveNode();
                            }

                            MainFXMLController.instance().getNodesFromDB();

                            break;
                        case 1://Sil
                            MainFXMLController.instance().removeNodeFromPane(content);
                            break;
                        case 2://İptal - Boş
                            break;
                        default:
                    }
                } else {
                    if (parent.getData() != null && parent.getMining() != null && parent.getPresentation() != null) {
                        parent.execute(parent);
                    } else {
                        Dialogs.create().
                                title("Module settings missing!").message("Please check the details of the modules. Module settings are not set!").showInformation();
                    }
                }
            }
        });

        groupAdd();
    }

    private void saveNode() {
        DataEntity dataEntity = new DataEntity();
        dataEntity.setName(parent.getData().getName());
        String raw = "";
        for (int i = 0; i < parent.getData().getJsonDataList().size(); i++) {
            raw += parent.getData().getJsonDataList().get(i) + "#";
        }
        dataEntity.setRawData(raw);
        dataEntity = (DataEntity) DatabaseManager.instance().saveEntity(dataEntity);

        ClusteringEntity clustEntity = new ClusteringEntity();
        clustEntity.setName(parent.getMining().getName());
        String rawAttribute = "";
        for (int i = 0; i < parent.getMining().getClusteringSelectedRulesList().size(); i++) {
            rawAttribute += parent.getMining().getClusteringSelectedRulesList().get(i) + ",";
        }
        clustEntity.setAttributeList(rawAttribute);

        MiningEntity miningEntity = new MiningEntity();
        if (parent.getMining().getMininType() == 1) {//clustering
            clustEntity = (ClusteringEntity) DatabaseManager.instance().saveEntity(clustEntity);
            miningEntity.setName(parent.getMining().getName());
            miningEntity.setCosineID(0l);//Şimdilik cosinearray boşsa sıfır doluysa cosineentity eklenir biz şimdilik 1 verdik
            miningEntity.setClusteringID(clustEntity.getId());
            miningEntity.setK(0);
            miningEntity.setLoop(0);
        } else if (parent.getMining().getMininType() == 2) {//kmeans
            String cosineArray = "";
            for (KMeans kmeans : parent.getMining().getKmeansPresentationData()) {
                cosineArray += kmeans.getPersonName() + "," + kmeans.getKmeansName() + "#";
            }

            CosineEntity cosineEntity = new CosineEntity();
            cosineEntity.setMiningType(2);
            cosineEntity.setValues(cosineArray);

            cosineEntity = (CosineEntity) DatabaseManager.instance().saveEntity(cosineEntity);

            miningEntity.setName(parent.getMining().getName());
            miningEntity.setCosineID(cosineEntity.getId());//Şimdilik cosinearray boşsa sıfır doluysa cosineentity eklenir biz şimdilik 1 verdik
            miningEntity.setClusteringID(0l);
            miningEntity.setK(parent.getMining().getK());
            miningEntity.setLoop(parent.getMining().getLoop());
        }
        miningEntity = DatabaseManager.instance().saveEntity(miningEntity);

        PresentationEntity presentEntity = new PresentationEntity();
        presentEntity.setChartType(parent.getPresentation().getChartType());
        presentEntity.setPresentationName(parent.getPresentation().getName());
        presentEntity = (PresentationEntity) DatabaseManager.instance().saveEntity(presentEntity);

        ExecutedRulesEntity ruleEntity = new ExecutedRulesEntity();
        ruleEntity.setDataID(dataEntity.getId());
        ruleEntity.setMiningID(miningEntity.getId());
        ruleEntity.setPresentationID(presentEntity.getId());
        ruleEntity.setName(parent.getName());
        ruleEntity = (ExecutedRulesEntity) DatabaseManager.instance().saveEntity(ruleEntity);
        nodeId = ruleEntity.getId();
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

    private void showNodeLabel() {
        NodeLabel = new StackPane();
        NodeLabel.setVisible(false);
        Text helpText = new Text(parent.getName());
        helpText.setFont(Font.font("Tohama", FontWeight.NORMAL, 11));
        helpText.setFill(Color.WHITE);
        helpText.setStroke(Color.web("#fff"));

        Rectangle helpIcon = new Rectangle(helpText.getBoundsInLocal().getWidth() + 5, 16);
        helpIcon.setFill(Color.web("#333"));
        helpIcon.setStroke(Color.web("#444"));
        helpIcon.setArcHeight(8.5);
        helpIcon.setArcWidth(5.5);

        NodeLabel.getChildren().addAll(helpIcon, helpText);
        NodeLabel.setAlignment(Pos.CENTER);
        NodeLabel.relocate(-helpIcon.getBoundsInLocal().getWidth() / 2, 82);

        this.getChildren().add(NodeLabel);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                NodeLabel.setVisible(true);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                NodeLabel.setVisible(false);
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

        showNodeLabel();//Node'un ismini gösterir.

        Image img1 = new Image("images/data.png");
        Image img2 = new Image("images/mining.png");
        Image img3 = new Image("images/press.png");
        Image img4 = new Image("images/play.png");

        ImageView dataImg = new ImageView(img1);
        ImageView miningImg = new ImageView(img2);
        ImageView pressImg = new ImageView(img3);
        ImageView playImg = new ImageView(img4);

        //Resimer ile buttoların mouse eventleri aynı olayı işlesin
        playImg.setOnMouseClicked(circle.getOnMouseClicked());
        playImg.setOnMouseEntered(circle.getOnMouseEntered());
        playImg.setOnMouseExited(circle.getOnMouseExited());
        playImg.setOnMousePressed(circle.getOnMousePressed());
        playImg.setOnMouseReleased(circle.getOnMouseReleased());

        dataImg.setOnMouseClicked(Button1.getOnMouseClicked());
        dataImg.setOnMouseEntered(Button1.getOnMouseEntered());
        dataImg.setOnMouseExited(Button1.getOnMouseExited());
        dataImg.setOnMousePressed(Button1.getOnMousePressed());
        dataImg.setOnMouseReleased(Button1.getOnMouseReleased());

        miningImg.setOnMouseClicked(Button2.getOnMouseClicked());
        miningImg.setOnMouseEntered(Button2.getOnMouseEntered());
        miningImg.setOnMouseExited(Button2.getOnMouseExited());
        miningImg.setOnMousePressed(Button2.getOnMousePressed());
        miningImg.setOnMouseReleased(Button2.getOnMouseReleased());

        pressImg.setOnMouseClicked(Button3.getOnMouseClicked());
        pressImg.setOnMouseEntered(Button3.getOnMouseEntered());
        pressImg.setOnMouseExited(Button3.getOnMouseExited());
        pressImg.setOnMousePressed(Button3.getOnMousePressed());
        pressImg.setOnMouseReleased(Button3.getOnMouseReleased());

        this.getChildren().add(dataImg);
        this.getChildren().add(miningImg);
        this.getChildren().add(pressImg);
        this.getChildren().add(playImg);

        dataImg.relocate(40, -40);
        miningImg.relocate(-18, 49);
        pressImg.relocate(-68, -32);
        playImg.relocate(-13, -16);

        Tooltip tp1 = new Tooltip();
        tp1.setText("Data Block");

        Tooltip tp2 = new Tooltip();
        tp2.setText("Data Mining Block");

        Tooltip tp3 = new Tooltip();
        tp3.setText("Presentation Block");

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
                //System.out.println("A");
                countBranch += 1;
            } else if (countBranch == 1 && currentBranchButton.getButtonName() == null) {
                new openBranchAnimation(currentBranchButton, positionX + branchButtonWidth, positionY);
                currentBranchButton.setButtonName("B");
                branchButtonIndex.add(currentBranchButton);
                //System.out.println("B");
                countBranch += 1;
            } else if (countBranch == 2 && currentBranchButton.getButtonName() == null) {
                new openBranchAnimation(currentBranchButton, positionX + branchButtonWidth * 2, positionY);
                currentBranchButton.setButtonName("C");
                branchButtonIndex.add(currentBranchButton);
                //System.out.println("C");
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
                //System.out.println("-A");
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
                //System.out.println("-B");
                countBranch -= 1;
            } else if (currentBranchButton.getButtonName().contains(branchButtonIndex.get(2).getButtonName())) {
                new closeBranchAnimation(currentBranchButton, currentBranchButton.getX1(), positionY);
                //System.out.println("-C");
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
                tempX = t.getX() + 153;
                tempY = t.getY() + 56;
            }
        });

        obj.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.isControlDown()) {//Ctrl kontrol
                    if (obj.isPressed()) {
                        obj.relocate(t.getSceneX() - tempX, t.getSceneY() - tempY);
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
        }
    }
}
