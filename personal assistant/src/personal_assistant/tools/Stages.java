/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package personal_assistant.tools;

import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**

 @author EMAM
 */
public class Stages {

          private static double clockRadius = 200;  //  <<
          private static boolean hasSecondHand = true;  //  <<
          private static Color bodyColor = Color.BLACK;  //  <<
          private static Color hourHandColor = Color.BLACK;  //  <<
          private static Color minuteHandColor = Color.CADETBLUE;  //  <<
          private static Color secondHandColor = Color.YELLOWGREEN;  //  <<

          public static Stage clockStage;
          public static boolean isClockStageOpened;

          public static Stage digitalClockStage;
          public static boolean isDigitalClockStageOpened;

          public static Stage mainStage;


          public static void openClock() {
                    if ( isClockStageOpened ) {
                              clockStage.close();
                    } else {
                              Group root = new Clock(clockRadius , hasSecondHand , true , bodyColor , hourHandColor , minuteHandColor , secondHandColor);
                              root.setOnMousePressed((e) -> hold(e));
                              root.setOnMouseDragged((e) -> drag(e));
                              root.setTranslateX(200);
                              root.setTranslateY(200);
                              Scene scene = new Scene(root , Color.TRANSPARENT);
                              clockStage = new Stage(StageStyle.TRANSPARENT);
                              clockStage.setScene(scene);
                              clockStage.show();
                    }
                    isClockStageOpened = !isClockStageOpened;
          }


          public static void openDigitalClock() {
                    if ( isDigitalClockStageOpened ) {
                              digitalClockStage.close();
                    } else {
                              StackPane root = new StackPane(new DigitalClock(500));
//                              root.setTranslateX(250);
//                              root.setTranslateY(250);
                              Scene scene = new Scene(root , Color.TRANSPARENT);
                              digitalClockStage = new Stage(StageStyle.TRANSPARENT);
                              digitalClockStage.setScene(scene);
                              digitalClockStage.show();
                    }
                    isDigitalClockStageOpened = !isDigitalClockStageOpened;
          }

          private static double mouseX;
          private static double mouseY;


          public static void hold(MouseEvent e) {
                    new Stages().holdStage(e);
          }


          public static void drag(MouseEvent e) {
                    new Stages().dragStage(e);
          }


          private void holdStage(MouseEvent e) {
                    mouseX = e.getSceneX();
                    mouseY = e.getSceneY();
          }


          public void dragStage(MouseEvent e) {
                    double difX = e.getScreenX() - mouseX;
                    double difY = e.getScreenY() - mouseY;
                    ((Node) e.getSource()).getScene().getWindow().setX(difX);
                    ((Node) e.getSource()).getScene().getWindow().setY(difY);
          }

}
