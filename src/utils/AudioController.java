package utils;

import javafx.animation.*;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;

public class AudioController {
    private static final int nChannels = 5;
    private static final double[] volumes = new double[nChannels];
    private static final Map<String, Pair<MediaPlayer,Integer>> audios = new HashMap<>();
    private static final ArrayList<MediaPlayer>[] channels = new ArrayList[nChannels];

    static{
        Arrays.fill(volumes, 1);
        Arrays.fill(channels,new ArrayList<>());
    }

    public static double getVolume(int channel){
        return volumes[channel];
    }
    public static void setVolume(int channel, double volume){
        if(volume<0) volume = 0;
        else if(volume>1) volume = 1;
        volumes[channel] = volume;
        for (MediaPlayer media : channels[channel]) {
            media.setVolume(volume);
        }
    }

    public static void insert(String name,String path,int channel){
        MediaPlayer media = new MediaPlayer(new Media(ClassLoader.getSystemResource(path).toString()));
        audios.put(name,new Pair<>(media,channel));
        channels[channel].add(media);
        media.setVolume(0);
        media.play();
        media.stop();
    }
    public static void play(String name,int times,double fadeMs){
        Pair<MediaPlayer,Integer> audio = audios.get(name);
        MediaPlayer media = audio.getKey();
        int channel = audio.getValue();

        media.setCycleCount(times);
        if(fadeMs>0){
            final Animation anim = new Transition() {
                {
                    setCycleDuration(Duration.millis(fadeMs));
                    setInterpolator(Interpolator.LINEAR);
                }
                @Override
                protected void interpolate(double frac) {
                    media.setVolume(frac*getVolume(channel));
                }
            };
            anim.setCycleCount(1);
            anim.setAutoReverse(false);
            media.setVolume(0);
            media.play();
            anim.play();
        }
        else{
            media.setVolume(getVolume(channel));
            media.play();
        }
    }
    public static void stop(String name,double fadeMs){
        Pair<MediaPlayer,Integer> audio = audios.get(name);
        MediaPlayer media = audio.getKey();
        int channel = audio.getValue();

        if(fadeMs>0){
            final Animation anim = new Transition() {
                {
                    setCycleDuration(Duration.millis(fadeMs));
                    setInterpolator(Interpolator.LINEAR);
                }
                @Override
                protected void interpolate(double frac) {
                    media.setVolume((1-frac)*getVolume(channel));
                }
            };
            anim.setCycleCount(1);
            anim.setAutoReverse(false);
            anim.play();
            anim.setOnFinished(e->media.stop());
        }
        else media.stop();
    }
    public static void stopAll(){
        for(Pair<MediaPlayer,Integer> audio:audios.values()){
            audio.getKey().stop();
        }
    }

    public static Slider createSlider(int channel){
        Slider slider = new Slider(0,100,getVolume(channel)*100);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.valueProperty().addListener((e,prev,cur)->setVolume(channel,slider.getValue()/100));
        return slider;
    }

    public static Pane createPane(){
        VBox pane = new VBox(20);


        HBox channel0 = new HBox();

        Text text0 = new Text("BGM:");
        text0.setFont(Fonts.getConsolas(2, FontWeight.NORMAL));
        text0.setFill(Color.rgb(0,0,0));

        Slider slider0 = createSlider(0);


        HBox channel1 = new HBox();

        Text text1 = new Text("SFX:");
        text1.setFont(Fonts.getConsolas(2, FontWeight.NORMAL));
        text1.setFill(Color.rgb(0,0,0));

        Slider slider1 = createSlider(1);


        channel0.getChildren().addAll(text0,slider0);
        channel1.getChildren().addAll(text1,slider1);
        pane.getChildren().addAll(channel0,channel1);
        return pane;
    }
}
