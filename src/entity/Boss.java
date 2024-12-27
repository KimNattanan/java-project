package entity;

import javafx.scene.canvas.Canvas;
import utils.AnimLoader;
import utils.GamePanel;

import static utils.Tools.mathRnd;

public class Boss extends Entity{
    private final AnimLoader idleAnim = new AnimLoader("boss/idle",2),
                             walkLeftAnim = new AnimLoader("boss/walkLeft",1.2),
                             walkRightAnim = new AnimLoader("boss/walkRight",1.2);
    private final double leftmost,rightmost;
    private int direction;
    private double speed;
    private final double minSpeed = 200, // px/s
                         maxSpeed = 1500; // px/s
    private long cooldown;
    private final long minCooldown = (long)(1e9), // nanosec
                       maxCooldown = (long)(8e9); // nanosec
    private final double stopChance = 0.5/1e2;
    private final long minStopTime = (long)(1e9),
                       maxStopTime = (long)(4e9);

    public Boss(Canvas canvas){
        super(192*1.5,593*1.5);
        leftmost = -(getW()/2);
        rightmost = canvas.getWidth() - leftmost;

        translate(leftmost,500);
        setAction("walkRight",walkRightAnim);
        direction = 1;
        speed = mathRnd(minSpeed,maxSpeed);
        cooldown = (long)mathRnd(minCooldown,maxCooldown);
    }

    public void upd(long dt){
        if(GamePanel.getIsRewardable()) {
            if (!getAction().equals("idle")) setAction("idle", idleAnim);
        }else{
            if (dt < cooldown) {
                if (!getAction().equals("idle")) setAction("idle", idleAnim);
                cooldown -= dt;
            } else {
                if (direction < 0 && !getAction().equals("walkLeft")) setAction("walkLeft", walkLeftAnim);
                else if (direction > 0 && !getAction().equals("walkRight")) setAction("walkRight", walkRightAnim);
                translate(speed / 1e9 * (dt - cooldown) * direction, 0);
                cooldown = 0;
                if (getX() <= leftmost || getX() >= rightmost) {
                    setX(getX() <= leftmost ? leftmost : rightmost);
                    speed = mathRnd(minSpeed, maxSpeed);
                    cooldown = (long) mathRnd(minCooldown, maxCooldown);
                    direction = -direction;
                } else if (mathRnd(0, 1) < stopChance) {
                    speed = mathRnd(minSpeed, maxSpeed);
                    cooldown = (long) mathRnd(minStopTime, maxStopTime);
                    if (mathRnd(0, 1) < 0.5) direction = -direction;
                }
            }
        }
        super.upd(dt);
    }
}
