import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Six extends Enemie
{
    private int timeTeleport;
    private int timeInvisible;
    private boolean teleport;
    
    public Six(int x, int y)
    {
        super(6, x, y, 2);    
        
        timeTeleport = 0;
        timeInvisible = 0;
        teleport = false;
    }
    
    public void act() 
    {
        teleport();
        if(!teleport)
            super.act();
    }
    
    private void teleport()
    {
        if(timeTeleport++ > 400)
        {
            teleport = true;
            getImage().setTransparency(0);
            setLocation(0, 0);
            if(timeInvisible++ == 40)
            {
                int rand = Greenfoot.getRandomNumber(3);
                switch(rand)
                {
                    case 0:
                        int rand0 = Greenfoot.getRandomNumber(((BomberWorld)getWorld()).getBricksList(1).size());
                        Coordinates aux0 = ((BomberWorld)getWorld()).getBricksList(1).get(rand0);
                        getImage().setTransparency(255);
                        setLocation(aux0.getX(), aux0.getY());
                        direction = checkStart(aux0.getX(), aux0.getY());
                        break;
                    case 1:
                        int rand1 = Greenfoot.getRandomNumber(((BomberWorld)getWorld()).getBricksList(2).size());
                        Coordinates aux1 = ((BomberWorld)getWorld()).getBricksList(2).get(rand1);
                        getImage().setTransparency(255);
                        setLocation(aux1.getX(), aux1.getY());
                        direction = checkStart(aux1.getX(), aux1.getY());
                        break;
                    case 2:
                        int rand2 = Greenfoot.getRandomNumber(((BomberWorld)getWorld()).getBricksList(3).size());
                        Coordinates aux2 = ((BomberWorld)getWorld()).getBricksList(3).get(rand2);
                        getImage().setTransparency(255);
                        setLocation(aux2.getX(), aux2.getY());
                        direction = checkStart(aux2.getX(), aux2.getY());
                        break;
                    default: break;
                }
                timeTeleport = 0;
                timeInvisible = 0;
                teleport = false;
            }
        }
    }
}
