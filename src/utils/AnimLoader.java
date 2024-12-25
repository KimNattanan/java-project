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
    private List<Image> imgs = new ArrayList<>();
    private int ptr;
    private long frameTime, deltaTime;

    public AnimLoader(String directoryPath, double sec){
        loadPngImages(directoryPath);
        setDuration(sec);
    }

    private void loadPngImages(String directoryPath) {
        try {
            Path folderPath = Paths.get(
                Objects.requireNonNull(
                    ClassLoader.getSystemResource(directoryPath)
                ).toURI()
            );
            try (Stream<Path> files = Files.list(folderPath)) {
                files.forEach(file ->
                    imgs.add(new Image(String.valueOf(ClassLoader.getSystemResource(directoryPath+"/"+file.getFileName()))))
                );
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setDuration(double sec){
        if(imgs.isEmpty()) return;
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
