import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Bomb extends TimedActor
{
    private GreenfootImage [] images;
    private int changeImage;
    private int currentImage;
   
    private int level;
    private Player player;
    private boolean kick;
    private String direction;
    
    private int x;
    private int y;
    
    private GreenfootSound boom;
    
    public Bomb(int a, int b, int l, Player p)
    {
        time = 0;
        images = new GreenfootImage[3];
        
        for(int i = 0; i < 3; i++)
            images[i] = new GreenfootImage("bomb" + i + ".png");
        
        level = l;
        player = p;
        kick = false;
        x = a;
        y = b;
        
        boom = new GreenfootSound("explosion.wav");
        boom.setVolume(90);
    }
    
    public void act() 
    {
        if(changeImage++ == 10)
        {
            currentImage = (currentImage+1)%images.length;
            setImage(images[currentImage]);
            changeImage = 0;
        }
        if(kick)
            move(direction);
        
        Actor explosion = getOneIntersectingObject(Explosion.class);
        if(time++ == 120 || explosion != null)
        {
            boom.play();
            explosion();
            player.decNumberBombsActive();
            getWorld().removeObject(this);
        }
    }    
    
    private void explosion()
    {  
        getWorld().addObject(new ExplosionCenter(), x, y);
        
        int b5 = 0, b6 = 0, b7 = 0, b8 = 0;
        
        if(level > 1)
        {
            for(int i = 1; i < level; i++)
            {
                List brick15 = getWorld().getObjectsAt(x-32*i, y, BreakableBrick.class);
                List brick5 = getWorld().getObjectsAt(x-32*i, y, Brick.class);
                if(!brick15.isEmpty() && b5 == 0)
                {
                    getWorld().addObject(new ExplosionFinish(3), x-32*i, y);
                    b5 = 1;
                }
                else if(brick5.isEmpty() && b5 == 0)
                    getWorld().addObject(new ExplosionMiddle(0), x-32*i, y);
                else
                    b5 = 1;
            
                List brick16 = getWorld().getObjectsAt(x+32*i, y, BreakableBrick.class);
                List brick6 = getWorld().getObjectsAt(x+32*i, y, Brick.class);
                if(!brick16.isEmpty() && b6 == 0)
                {
                    getWorld().addObject(new ExplosionFinish(1), x+32*i, y);
                    b6 = 1;
                }
                else if(brick6.isEmpty() && b6 == 0)
                    getWorld().addObject(new ExplosionMiddle(2), x+32*i, y);
                else
                    b6 = 1;
                
                List brick17 = getWorld().getObjectsAt(x, y-32*i, BreakableBrick.class);
                List brick7 = getWorld().getObjectsAt(x, y-32*i, Brick.class);
                if(!brick17.isEmpty() && b7 == 0)
                {
                    getWorld().addObject(new ExplosionFinish(0), x, y-32*i);
                    b7 = 1;
                }
                else if(brick7.isEmpty() && b7 == 0)
                    getWorld().addObject(new ExplosionMiddle(1), x, y-32*i);
                else
                    b7 = 1;
        
                List brick18 = getWorld().getObjectsAt(x, y+32*i, BreakableBrick.class);
                List brick8 = getWorld().getObjectsAt(x, y+32*i, Brick.class);
                if(!brick18.isEmpty() && b8 == 0)
                {
                    getWorld().addObject(new ExplosionFinish(2), x, y+32*i);
                    b8 = 1;
                }
                if(brick8.isEmpty() && b8 == 0)
                    getWorld().addObject(new ExplosionMiddle(3), x, y+32*i);
                else
                    b8 = 1;
                    
                brick5.clear(); brick6.clear(); brick7.clear(); brick8.clear();
            }
        }
        
        List brick1 = getWorld().getObjectsAt(x-32*level, y, Brick.class);
        if(brick1.isEmpty() && b5 == 0)
            getWorld().addObject(new ExplosionFinish(3), x-32*level, y);
            
        List brick2 = getWorld().getObjectsAt(x+32*level, y, Brick.class);
        if(brick2.isEmpty() && b6 == 0)
            getWorld().addObject(new ExplosionFinish(1), x+32*level, y);
          
        List brick3 = getWorld().getObjectsAt(x, y-32*level, Brick.class);
        if(brick3.isEmpty() && b7 == 0)
            getWorld().addObject(new ExplosionFinish(0), x, y-32*level);
        
        List brick4 = getWorld().getObjectsAt(x, y+32*level, Brick.class);
        if(brick4.isEmpty() && b8 == 0)
            getWorld().addObject(new ExplosionFinish(2), x, y+32*level);
    }
    
    public void setLevel(int n)
    {
        level = n;
    }
    
    public void move(String d)
    {
        direction = d;
        kick = true;
        
        Actor staticActor = getOneIntersectingObject(StaticActor.class);
        Actor bomb = getOneIntersectingObject(Bomb.class);
        Actor player = getOneIntersectingObject(Player.class);
        Actor enemie = getOneIntersectingObject(Enemie.class);
        if(staticActor != null || bomb != null || player != null || enemie != null)
        {
            kick = false;
            if(direction == "u")
            {
                setLocation(getX(), getY() + 13);
                y += 13;
            }
            else if(direction == "d")
            {
                setLocation(getX(), getY() - 13);
                y -= 13;
            }
            else if(direction == "l")
            {
                setLocation(getX() + 13, getY());
                x += 13;
            }
            else //if(direction == "r")
            {
                setLocation(getX() - 13, getY());
                x -= 13;
            }
        }
        else
        {
            if(direction == "u")
            {
                setLocation(getX(), getY() - 3);
                y -= 3;
            }
            else if(direction == "d")
            {
                setLocation(getX(), getY() + 3);
                y += 3;
            }
            else if(direction == "l")
            {
                setLocation(getX() - 3, getY());
                x -= 3;
            }
            else //if(direction == "r")
            {
                setLocation(getX() + 3, getY());
                x += 3;
            }
        }
    }
}
