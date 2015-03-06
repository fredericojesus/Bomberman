import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public abstract class Explosion extends TimedActor
{
    public void act() 
    {
        List players = getIntersectingObjects(Player.class);
        List enemies = getIntersectingObjects(Enemie.class);
        Boss boss = (Boss) getOneIntersectingObject(Boss.class);
        
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i) != null)
            {
                Player player = (Player) players.get(i);
                if(!player.getTransparency())
                    player.death();
            }
        }
        for(int i = 0; i < enemies.size(); i++)
        {
            if(enemies.get(i) != null && enemies.get(i) != boss)
            {
                Enemie enemie = (Enemie) enemies.get(i);
                enemie.death(6); 
            }
        }
    }
}
