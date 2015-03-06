import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Five extends Enemie
{
    private int sendHurricane;
    
    public Five(int x, int y)
    {
        super(5, x, y, 2);
        
        sendHurricane = 0;
    }
    
    public void act() 
    {
        super.act();
        if(this != null)
        {
            sendHurricane();
        }
    }
    
    private void sendHurricane()
    {
        if(sendHurricane++ == 400)
        {
            List<Player> listP = getWorld().getObjects(Player.class);
            Player player = listP.get(0);
            getWorld().addObject(new Hurricane(player.getX(), player.getY()), getX(), getY());
            sendHurricane = 0;
        }
    }
}
