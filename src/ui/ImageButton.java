package ui;

import javafx.scene.control.Button;

public class ImageButton extends Button {
    private final String defaultImg,hoverImg,activeImg;
    private boolean myHover,myActive;
    public ImageButton(String defaultImg,String hoverImg,String activeImg){
        this.defaultImg = defaultImg;
        this.hoverImg = hoverImg;
        this.activeImg = activeImg;

        this.setOnMouseEntered(e->setMyHover(true));
        this.setOnMouseDragEntered(e->setMyHover(true));
        this.setOnMouseExited(e->setMyHover(false));
        this.setOnMouseDragExited(e->setMyHover(false));

        this.setOnMousePressed(e->setMyActive(true));
        this.setOnMouseReleased(e->setMyActive(false));

        upd();
    }

    private void setImg(String img){
        this.setStyle("-fx-background-color: transparent;" +
                "-fx-background-image: url("+img+");" +
                "-fx-background-size: 100% 100%;");
    }

    private void upd(){
        if(getMyHover()&&getMyActive()){
            setImg(activeImg);
        }else if(getMyHover()){
            setImg(hoverImg);
        }else if(getMyActive()){
            setImg(activeImg);
        }else{
            setImg(defaultImg);
        }
    }

    private boolean getMyHover(){ return myHover; }
    private void setMyHover(boolean bool){
        myHover=bool;
        upd();
    }

    private boolean getMyActive(){ return myActive; }
    private void setMyActive(boolean bool){
        myActive=bool;
        upd();
    }
}
