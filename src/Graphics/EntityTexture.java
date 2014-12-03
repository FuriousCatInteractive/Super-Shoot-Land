package Graphics;

import Entities.MovingEntity;
import Entities.Player;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by coco on 14-11-20.
 */
public class EntityTexture {



    public static final int SPRITESHEET_H = 5;
    public static final int SPRITESHEET_W = 4;


    public static final int IDLE[]   = {00};
    public static final int WALK[]   = {10, 11, 12, 13};
    public static final int JUMP[]   = {20, 21, 22};
    public static final int SHOOT[]  = {30, 31, 32, 33};
    public static final int HURT[]   = {40};

    public static final boolean LEFT  = false;
    public static final boolean RIGHT = true;

    public static int  compteur=0;
    public static int  dureeeAnimation = 20;


    public static void updateTexture(Player sprite){

        int SPRITE_H  = (int)sprite.getImage_h()/SPRITESHEET_H;
        int SPRITE_W  = (int)sprite.getImage_w()/SPRITESHEET_W;
        int y = decideState(sprite)/10;
        int x = decideState(sprite)%10;
        //System.out.println(decideState(sprite));
        sprite.setTextureRect(new IntRect(x*SPRITE_W, y*SPRITE_H, SPRITE_W, SPRITE_H));
        if(sprite.state!=JUMP) {
            if (sprite.getVitesseX() < 0 && sprite.getScale().x > 0) {//flip selon x
                sprite.state = WALK;
                sprite.setScale(-1.0f * sprite.getScale().x, sprite.getScale().y);
                sprite.move(sprite.getGlobalBounds().width, 0);
            }
            else if (sprite.getVitesseX() > 0 && sprite.getScale().x < 0) {//flip selon x
                sprite.state = WALK;
                sprite.setScale(-1.0f * sprite.getScale().x, sprite.getScale().y);
                sprite.move(-1.0f * sprite.getGlobalBounds().width, 0);
            }
            else if (sprite.getVitesseX() == 0 && sprite.state!=SHOOT)
                sprite.state = IDLE;
            else if(decideState(sprite) == SHOOT[SHOOT.length-1] && sprite.state==SHOOT){
                sprite.state = IDLE;
                //System.out.println("arret shoot");
                compteur=0;
            }
        }

    }

    public static int decideState(Player p){
        if(p.state == WALK || p.state==SHOOT) {
            int compteurMax = dureeeAnimation * p.state.length;
            compteur = ((compteur >= compteurMax - 1) ? 0 : (compteur + 1));
            return p.state[compteur / dureeeAnimation];
        }
        else if(p.state == JUMP){
            if(p.getVitesseY()<2)
                return p.state[0];
            if(p.getVitesseY()>2)
                return p.state[1];
            else
                return  p.state[p.state.length-1];
        }
        else
            return IDLE[0];
    }

    public static Texture loadTexture(String imagePath) {
        Texture textureSprite = new Texture();
        try {
            textureSprite.loadFromFile(Paths.get(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textureSprite;
    }
   /* public void mergeTextureSprite(List<GameBaseEntity> entityList) {
        for(GameBaseEntity it: entityList) {
            if(it instanceof Mob) {
                it.setTexture(loadTexture("rsc/img/poule.png"));
                it.setScale(3f,3f);
            }
            else if(it instanceof Player) {
                it.setTexture(loadTexture("rsc/img/zombie.png"));
                it.setScale(3f,3f);
            }
            else if(it instanceof Bullet) {
                it.setTexture(loadTexture("rsc/img/poule.png"));
                it.setScale(1f, 1f);
            }
            updateTexture(it,it.getId(),1);
        }
    }
    public static void mergeTextureSprite(GameBaseEntity entityList) {
        if(entityList instanceof Mob) {
            entityList.setTexture(loadTexture("rsc/img/poule.png"));
            entityList.setScale(3f,3f);
        }
        else if(entityList instanceof Player) {
            entityList.setTexture(loadTexture("rsc/img/zombie.png"));
            entityList.setScale(3f,3f);
        }
        else if(entityList instanceof Bullet) {
            entityList.setTexture(loadTexture("rsc/img/rock.png"));
            entityList.setScale(1f, 1f);
        }
        updateTexture(entityList,entityList.getId(),1);
    }
    public static void loadImageOnSprite(GameBaseEntity entity, String path, float scaleX, float scaleY) {
        entity.setTexture(loadTexture(path));
        entity.setScale(scaleX, scaleY);
    }
    public void updateList(List<GameBaseEntity> entityList){
        for(GameBaseEntity it: entityList){
            updateTexture(it, it.getId(),it.getDirection());
        }
    }*/


}
