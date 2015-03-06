import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class BombLevelUp extends Power
{
    public BombLevelUp()
    {
        super(3);   
    }
    
    public void act()
    {
        super.act();
    }
    
    public void touchPower(Player player) 
    {
        if(player.getBombLevel() < 5)
        {
            Greenfoot.playSound("PowerUp.wav");
            player.setBombLevel(player.getBombLevel()+1);
            ((BomberWorld)getWorld()).setMsgOnBox(player.getPlayer(), 4, player.getBombLevel());
        }
        getWorld().removeObject(this);
    }    
}
