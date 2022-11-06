package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Platform;

public class Character extends Pane {
    Logger logger = LoggerFactory.getLogger(Character.class);
    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    int highestJump = 100;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;

    public Character(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.characterImg = new Image(getClass().getResourceAsStream("/assets/MarioSheet.png"));
        this.imageView = new AnimatedSprite(characterImg, 4, 4, 1, offsetX, offsetY, 16, 32);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }

    public Character(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey, int temp) {
        this.x = x;
        this.y = y;
        this.xMaxVelocity = 50;
        this.yMaxVelocity = 30;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.characterImg = new Image(getClass().getResourceAsStream("/assets/Megaman.png"));
        this.imageView = new AnimatedSprite(characterImg, 10, 5, 2, offsetX, offsetY, 541, 514);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }

    public void moveX() {
        setTranslateX(x);
        if (isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x - xVelocity;
        }
        if (isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x + xVelocity;
        }
    }

    public void moveY() {
        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity;

        } else if (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }
    }

    public void jump() {
        if (canJump) {
            canJump = false;
            isJumping = true;
            isFalling = false;
            yVelocity = yMaxVelocity;
        }
    }

    public void moveLeft() {
        isMoveLeft = true;
        isMoveRight = false;
    }

    public void moveRight() {
        isMoveLeft = false;
        isMoveRight = true;
    }

    public void stop() {
        xVelocity = 0;
        isMoveLeft = false;
        isMoveRight = false;
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + getWidth() >= Platform.WIDTH) {
            x = Platform.WIDTH - (int) getWidth();
        }
    }

    public void checkReachHighest() {
        if (isJumping && y <= Platform.GROUND - CHARACTER_HEIGHT - highestJump) {
            if (isJumping && yVelocity <= 0) {
                isJumping = false;
                isFalling = true;
                yVelocity = 0;
            }
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void collision(){


    }

    public void repaint() {
        setTranslateX(x);
        setTranslateY(y);
        moveX();
        moveY();
    }



    public void trace() {
        logger.info("x:{}y:{}vx:{}vy:{}", x, y, xVelocity, yVelocity);
    }

    public KeyCode getLeftKey() {
        return this.leftKey;
    }

    public KeyCode getRightKey() {
        return this.rightKey;
    }

    public KeyCode getUpKey() {
        return this.upKey;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }
}
