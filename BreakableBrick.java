import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class BreakableBrick extends StaticActor
{
    public void act() 
    {
        List explosion = getObjectsInRange(20, Explosion.class);
        if(!explosion.isEmpty())
            getWorld().removeObject(this);
    }    
}
