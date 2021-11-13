/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package personal_assistant.tools;

import java.time.LocalTime;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.*;
import segment.Segment;
import segment.Segments;

/**

 @author EMAM
 */
public class DigitalClock extends Group {

          private boolean is12;
          private final double width;
          private final double height;
          private boolean hasSeconds;
          private Color onColor;
          private Color offColor;

          private Segment hourDigit1;
          private Segment hourDigit2;
          private Segment minuteDigit1;
          private Segment minuteDigit2;
          private Segment secondDigit1;
          private Segment secondDigit2;
          private Timeline t;


          public DigitalClock(boolean is12 , double width , double height , boolean hasSeconds , Color onColor , Color offColor) {
                    this.is12 = is12;
                    this.width = width;
                    this.height = height;
                    this.hasSeconds = hasSeconds;
                    this.onColor = onColor;
                    this.offColor = offColor;
                    draw();
                    initAnimation();
                    play();
          }


          public DigitalClock(double width) {
                    this(width , width / 3);
          }


          public DigitalClock(double width , double height) {
                    this(true , width , height);
          }


          public DigitalClock(boolean is12 , double width , double height) {
                    this(is12 , width , height , true , Color.ORANGERED , Color.rgb(0 , 0 , 0 , 0.2));
          }


          public DigitalClock() {
                    this(200 , 100);
          }


          public Segment getHourDigit1() {
                    return hourDigit1;
          }


          public void setHourDigit1(Segment hourDigit1) {
                    this.hourDigit1 = hourDigit1;
          }


          public Segment getHourDigit2() {
                    return hourDigit2;
          }


          public void setHourDigit2(Segment hourDigit2) {
                    this.hourDigit2 = hourDigit2;
          }


          public Segment getMinuteDigit1() {
                    return minuteDigit1;
          }


          public void setMinuteDigit1(Segment minuteDigit1) {
                    this.minuteDigit1 = minuteDigit1;
          }


          public Segment getMinuteDigit2() {
                    return minuteDigit2;
          }


          public void setMinuteDigit2(Segment minuteDigit2) {
                    this.minuteDigit2 = minuteDigit2;
          }


          public Color getOffColor() {
                    return offColor;
          }


          public void setOffColor(Color offColor) {
                    this.offColor = offColor;
          }


          public Color getOnColor() {
                    return onColor;
          }


          public void setOnColor(Color onColor) {
                    this.onColor = onColor;
          }


          public Segment getSecondDigit1() {
                    return secondDigit1;
          }


          public void setSecondDigit1(Segment secondDigit1) {
                    this.secondDigit1 = secondDigit1;
          }


          public Segment getSecondDigit2() {
                    return secondDigit2;
          }


          public void setSecondDigit2(Segment secondDigit2) {
                    this.secondDigit2 = secondDigit2;
          }


          public boolean isHasSeconds() {
                    return hasSeconds;
          }


          public void setHasSeconds(boolean hasSeconds) {
                    this.hasSeconds = hasSeconds;
          }


          public boolean isIs12() {
                    return is12;
          }


          public void setIs12(boolean is12) {
                    this.is12 = is12;
          }


          private void draw() {
                    Segment firstLetterInAMOrPM = Segments.Create(Segments.SegmentType.FOURTEEN_SEGMENT_1 , width / 20 , height * 3 / 8 , onColor , offColor,false);
                    Segment secondLetterInAMOrPM = Segments.Create(Segments.SegmentType.FOURTEEN_SEGMENT_1 , width / 20 , height * 3 / 8 , onColor , offColor,false);

                    this.getChildren().addAll(
                            drawHour() , drawDots() , drawMinute() , drawSecond() ,
                            drawDay() , drawAMPM()
                    );
          }


          public Group drawDots() {
                    Circle d1 = new Circle(50);
                    Circle d2 = new Circle(50);
                    Group g = new Group(d1 , d2);
                    return g;
          }


          public Group drawHour() {
                    hourDigit1 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_2 , width / 10 , height * 3 / 4 , onColor , offColor,false);
                    Group h1 = hourDigit1.getDrawn();

                    hourDigit2 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_1 , width / 10 , height * 3 / 4 , onColor , offColor,false);
                    Group h2 = hourDigit2.getDrawn();

                    Group g = new Group(h1 , h2);
                    g.setTranslateX(-width * 11 / 50);
                    g.setTranslateY(-width * 11 / 50);
                    return g;
          }


          public Group drawMinute() {
                    minuteDigit1 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_1 , width / 10 , height * 3 / 4 , onColor , offColor,false);
                    Group m1 = minuteDigit1.getDrawn();
                    m1.setTranslateX(width * 11 / 50);

                    minuteDigit2 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_1 , width / 10 , height * 3 / 4 , onColor , offColor,false);
                    Group m2 = minuteDigit2.getDrawn();
                    m2.setTranslateX(width * 5 / 50);

                    Group g = new Group(m1 , m2);
                    g.setTranslateX(-width * 11 / 50);
                    g.setTranslateY(-width * 11 / 50);
                    return g;
          }


          public Group drawSecond() {
                    secondDigit1 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_1 , width / 20 , height * 3 / 8 , onColor , offColor,false);
                    Group s1 = secondDigit1.getDrawn();
                    s1.setTranslateX(width / 3);

                    secondDigit2 = Segments.Create(Segments.SegmentType.SEVEN_SEGMENT_1 , width / 20 , height * 3 / 8 , onColor , offColor,false);
                    Group s2 = secondDigit2.getDrawn();
                    s2.setTranslateX(width / 2 - width / 20);

                    Group g = new Group(s1 , s2);
                    g.setTranslateX(-width * 11 / 50);
                    g.setTranslateY(-width * 11 / 50);
                    return g;
          }


          public Group drawDay() {

                    Group g = new Group();
                    g.setTranslateX(-width * 11 / 50);
                    g.setTranslateY(-width * 11 / 50);
                    return g;
          }


          public Group drawAMPM() {
                    Group g = new Group();
                    g.setTranslateX(-width * 11 / 50);
                    g.setTranslateY(-width * 11 / 50);
                    return g;
          }


          private void initAnimation() {
                    t = new Timeline(
                            new KeyFrame(Duration.seconds(1) ,
                                    e -> refresh()
                            ));
                    t.setCycleCount(Timeline.INDEFINITE);
          }


          private void play() {
                    t.play();
          }


          private void refresh() {
                    LocalTime time = LocalTime.now();
                    int h = time.getHour();
                    int m = time.getMinute();
                    int s = time.getSecond();

                    hourDigit1.write((h % 10 + "").charAt(0));
                    hourDigit2.write((h / 10 + "").charAt(0));
                    minuteDigit1.write((m % 10 + "").charAt(0));
                    minuteDigit2.write((m / 10 + "").charAt(0));
                    secondDigit1.write((s % 10 + "").charAt(0));
                    secondDigit2.write((s / 10 + "").charAt(0));
          }

}
