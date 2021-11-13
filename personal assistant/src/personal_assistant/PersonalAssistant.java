/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package personal_assistant;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.util.*;
import personal_assistant.tools.MySystem;
import static personal_assistant.tools.Stages.*;

/**

 @author EMAM
 */
public class PersonalAssistant extends Application {

          private boolean isSpread = false;
          private double spreadAngle = 90;  //  <<
          private double radius = 35;  //  <<
          private double distance = 200;  //  <<
          private double speed = 500;  //  <<
          private int direction = OpeningDirection.LEFT_DOWN;
          private Color centerColor = Color.DARKCYAN;
          private Color closeColor = Color.DARKRED; 

          private Group[] circles;

          private Timeline openTimeline;
          private Timeline closeTimeline;

          private double centerX;
          private double centerY;


          @Override
          public void start(Stage s) throws Exception {
                    centerY = centerX = distance + radius;

                    circles = new Group[6];
                    circles[0] = createCenterCircle("< >" , radius , Color.BLACK , centerColor);
//                    circles[0].setFill(Color.BROWN);
                    circles[1] = createCircle("clock" , radius , Color.BLACK , Color.CHOCOLATE , (e) -> openClock());
                    circles[2] = createCircle("digital" , radius , Color.BLACK , Color.CHOCOLATE , (e) -> openDigitalClock());
                    circles[3] = createCircle("X" , radius , Color.WHITE , closeColor , (e) -> {
                              if ( ((MouseEvent) e).getClickCount() == 2 ) {
                                        closeProgram();
                              }
                    });
                    circles[4] = createCircle("sleep" , radius , Color.BLACK , Color.CHOCOLATE , (e) -> sleepOS());
                    circles[5] = createCircle("shutdown" , radius , Color.BLACK , Color.CHOCOLATE , (e) -> shutdownOS());

                    initAnimation();

                    Group root = new Group(circles);
                    Scene scene = new Scene(root , centerX * 2 , centerX * 2 , Color.TRANSPARENT);
                    mainStage = new Stage(StageStyle.TRANSPARENT);
                    mainStage.setScene(scene);
                    mainStage.setAlwaysOnTop(true);
                    mainStage.show();
          }


          /**
           @param args the command line arguments
           */
          public static void main(String[] args) {
                    launch(args);
          }


          public Group createCircle(String text , double radius , Color textColor , Color bodyColor , EventHandler e) {
                    Circle c = new Circle(radius);
//                    c.setStroke(Color.BLACK);
                    c.setFill(bodyColor);

                    Label label = new Label(text);
                    label.setTranslateX(-radius / 1.414);
                    label.setTranslateY(-radius / 1.414);
                    label.setPrefSize(radius * 1.414 , radius * 1.414);
                    label.setAlignment(Pos.CENTER);
                    label.setWrapText(true);
                    label.setTextFill(textColor);

                    Group g = new Group(c , label);
                    g.setTranslateX(centerX);
                    g.setTranslateY(centerY);
                    g.setVisible(false);
                    g.setDisable(true);
                    g.setOnMouseReleased(e);
                    g.setCursor(Cursor.HAND);

                    return g;
          }


          private void closeProgram() {
                    if ( isClockStageOpened ) {
                              clockStage.close();
                    }
                    if ( isDigitalClockStageOpened ) {
                              digitalClockStage.close();
                    }
                    mainStage.close();
          }


          private Group createCenterCircle(String text , double radius , Color textColor , Color bodyColor) {
                    Group g = createCircle(text , radius , textColor , centerColor , (e) -> {
                              if ( ((MouseEvent) e).getClickCount() == 1 ) {
                                        openCloseMenu();
                              }
                    });
                    g.setVisible(true);
                    g.setDisable(false);
                    g.setOnMousePressed((e) -> hold(e));
                    g.setOnMouseDragged((e) -> drag(e));
                    return g;
          }


          private KeyValue[] createOpeningKeyValues(int num , double[] angles) {
                    KeyValue[] keyValues = new KeyValue[num];
                    for ( int i = 0, j = 1 ; i < keyValues.length ; i += 3 , j++ ) {
                              keyValues[i] = new KeyValue(circles[j].translateXProperty() , centerX - distance * Math.cos(Math.toRadians(angles[j - 1])));
                              keyValues[i + 1] = new KeyValue(circles[j].translateYProperty() , centerY - distance * Math.sin(Math.toRadians(angles[j - 1])));
                              keyValues[i + 2] = new KeyValue(circles[j].disableProperty() , false);
                    }
                    return keyValues;
          }


          private KeyValue[] createClosingKeyValues(int num) {
                    KeyValue[] keyValues = new KeyValue[num];
                    for ( int i = 0, j = 1 ; i < keyValues.length ; i += 3 , j++ ) {
                              keyValues[i] = new KeyValue(circles[j].translateXProperty() , centerX);
                              keyValues[i + 1] = new KeyValue(circles[j].translateYProperty() , centerY);
                              keyValues[i + 2] = new KeyValue(circles[j].visibleProperty() , false);
                    }
                    return keyValues;
          }


          private void initAnimation() {
                    int circlesNum = circles.length - 1;

                    double[] angles = createAngles(circlesNum);

                    KeyValue[] keyValues = createOpeningKeyValues(circlesNum * 3 , angles);
                    openTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(distance / speed) , keyValues));

                    keyValues = createClosingKeyValues(circlesNum * 3);
                    closeTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(distance / speed) , keyValues));
          }


          private double[] createAngles(int num) {
//                  135        180      225
//                                 ^ 
//                90   < 90            >   270
//                                V 
//                  45          0         315
                    double angles[] = new double[num];
                    double directionAngle = direction * 45;
                    double startAngle = directionAngle + 0.5 * spreadAngle;
                    for ( int i = 0 ; i < angles.length ; i++ ) {
                              angles[i] = startAngle - (i) * spreadAngle / (num - 1);
                    }
                    return angles;
          }


          private void openCloseMenu() {
                    if ( isSpread ) {
                              closeMenu();
                    } else {
                              openMenu();
                    }
                    isSpread = !isSpread;
          }


          private void openMenu() {
                    closeTimeline.stop();
                    for ( int i = 1 ; i < circles.length ; i++ ) {
                              circles[i].setVisible(true);
                    }
                    openTimeline.play();
          }


          private void closeMenu() {
                    openTimeline.stop();
                    for ( int i = 1 ; i < circles.length ; i++ ) {
                              circles[i].setDisable(true);
                    }
                    closeTimeline.play();
          }


          private void shutdownOS() {
                    try {
                              MySystem.shutdown();
                    } catch ( IOException ex ) {
                              Logger.getLogger(PersonalAssistant.class.getName()).log(Level.SEVERE , null , ex);
                    }
          }


          private void sleepOS() {
                    try {
                              MySystem.sleep();
                    } catch ( IOException ex ) {
                    }
          }

}
