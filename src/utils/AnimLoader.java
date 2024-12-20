package utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.scene.image.Image;

public class AnimLoader {
    private List<Image> imgs;
    private int ptr, jump;
    private long frameTime, deltaTime;

    public AnimLoader(){
        setDuration(1);
    }

    private static void loadPngImages(List<Image> imgs, String directoryPath) {
        try {
            // Access folder1 inside resources
            Path folderPath = Paths.get(
                Objects.requireNonNull(
                    ClassLoader.getSystemResource(directoryPath)
                ).toURI()
            );

            // List files in folder1
            try (Stream<Path> files = Files.list(folderPath)) {
                files.forEach(file ->
                    imgs.add(new Image(String.valueOf(ClassLoader.getSystemResource(directoryPath+"/"+file.getFileName()))))
                );
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromDir(String directoryPath){
        imgs = new ArrayList<>();
        loadPngImages(imgs, directoryPath);
        ptr=0;
    }
    public void setDuration(double sec){
        if(imgs == null || imgs.isEmpty()) return;
        frameTime = (long)(1e9*sec/imgs.size());
    }
    public void upd(long dt){
        if(imgs==null || imgs.isEmpty()) return;
        if((deltaTime += dt) < frameTime) return;
        int jump = (int)((deltaTime/frameTime)%imgs.size());
        ptr = (ptr+jump)%imgs.size();
        deltaTime %= frameTime;
    }
    public Image getFrame(){ return imgs.get(ptr); }
}
