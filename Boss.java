import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;

public class Boss extends Enemie
{
    private Image [] healthBar;
    private boolean hB;
    private boolean wait;
    private int waitTime;
    private int timeDrop;
    private final int MAX_ENEMIES = 4;
    
    public Boss(int x, int y)
    {
        super(x, y);
        lifes = 6;
        
        createEnemieImage(imgsRight, 16, "r");
        createEnemieImage(imgsDeath, 7, "m");
        
        healthBar = new Image[10];
        
        hB = false;
        wait = false;
        waitTime = 0;
        timeDrop = 0;
    }
    
    public void act() 
    {
        if(!hB)
        {
            drawHealthBar();
            hB = true;
        }
            
        if(!die)
        {
            underBomb();
            chooseDirection();
            atMapsEdge();
            move();
            touchEnemie();
            death();
        }
        else 
            super.death(7);
            
        if(wait)
        {
            if(waitTime++ == 12)
            {
                wait = false;
                waitTime = 0;
            }   
        }
        
        dropEnemie();
    }
    
    //overriding
    private void createEnemieImage(GreenfootImage [] images, int numberOfImages, String direction)
    {
        for(int i = 0; i < numberOfImages; i++)
            images[i] = new GreenfootImage("e0" + direction + i + ".png");
    }
    
    //overriding
    public void move()
    {
        setImage();
        
        if(direction == "u")
        {    
            setLocation(getX(), getY() - speed);
            Actor bomb = getOneIntersectingObject(Bomb.class);
            if(bomb != null && !underBomb)
            {
                setLocation(getX(), getY() + speed);
                direction = "d";
            }
        }
        if(direction == "d")
        { 
            setLocation(getX(), getY() + speed); 
            Actor bomb = getOneIntersectingObject(Bomb.class);
            if(bomb != null && !underBomb)
            {
                direction = "u";
                setLocation(getX(), getY() - speed);
            }
        }
        if(direction == "l")
        {
            setLocation(getX() - speed, getY());
            Actor bomb = getOneIntersectingObject(Bomb.class);
            if(bomb != null && !underBomb)
            {
                setLocation(getX() + speed, getY()); 
                direction = "r";
            }
        }
        if(direction == "r")
        {
            setLocation(getX() + speed, getY()); 
            Actor bomb = getOneIntersectingObject(Bomb.class);
            if(bomb != null && !underBomb)
            {
                setLocation(getX() - speed, getY()); 
                direction = "l";
            }
        }
    }
    
    //overriding
    public void setImage()
    {
        if(changeImage++ == 10)
        {   
            currentImage = (currentImage+1)%NUMBER_OF_IMAGES;
            setImage(imgsRight[currentImage]);
            changeImage = 0;
        }
    }
    
    private void atMapsEdge()
    {
        int rand = 0;
        if(getX() == 48 && getY() == 80 && (direction != "r" || direction != "d"))
        {
            if(direction == "l") direction = "r";
            else //if(direction == "u")
                direction = "d";
        }
        else if(getX() == 48 && getY() == 464 && (direction != "r" || direction != "u"))
        {
            if(direction == "l") direction = "r";
            else //if(direction == "d")
                direction = "u";
        }
        else if(getX() == 432 && getY() == 80 && (direction != "l" || direction != "d"))
        {
            if(direction == "r") direction = "l";
            else //if(direction == "u")
                direction = "d";
        }
        else if(getX() == 432 && getY() == 464 && (direction != "l" || direction != "u"))
        {
            if(direction == "r") direction = "l";
            else //if(direction == "d")
                direction = "u";
        }
        else if(getX() == 48 && (direction != "d" || direction != "u" || direction != "r"))
            direction = "r";
        else if(getX() == 432 && (direction != "d" || direction != "u" || direction != "l"))
            direction = "l";
        else if(getY() == 80 && (direction != "r" || direction != "l" || direction != "d"))
            direction = "d";
        else if(getY() == 464 && (direction != "r" || direction != "l" || direction != "u"))
            direction = "u";
    }
    
    private void drawHealthBar()
    {
        GreenfootImage redRect = new GreenfootImage(8, 20);
        redRect.setColor(Color.RED);
        redRect.fill();
        
        for(int i = 0; i < lifes; i++)
            healthBar[i] = new Image(redRect);
        
        for(int i = 0, x = 350; i < lifes; i++, x+=10)
            getWorld().addObject(healthBar[i], x, 528);
    }
    
    //overriding
    public void death()
    {
        if(this != null)
        {
            Actor explosion = getOneIntersectingObject(Explosion.class);
            if(explosion != null && !wait)
            {
                Greenfoot.playSound("takedown.wav");
                lifes--;
                getWorld().removeObject(healthBar[lifes]);
                wait = true;
            }
            if(lifes == 0)
                super.death(7);
        }
    }
    
    private void dropEnemie()
    {
        if(timeDrop++ == 1000)
        {
            List objects = getObjectsInRange(20, BreakableBrick.class);
            List enemies = getWorld().getObjects(Enemie.class);
            if(objects.isEmpty() && enemies.size() < MAX_ENEMIES+1)
            { 
                int rand = Greenfoot.getRandomNumber(4);
                if(rand == 0)
                    getWorld().addObject(new Three(droppingObjectCoords(getX()), droppingObjectCoords(getY())), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
                else if(rand == 1)
                    getWorld().addObject(new Four(droppingObjectCoords(getX()), droppingObjectCoords(getY())), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
                else if(rand == 2)
                    getWorld().addObject(new Five(droppingObjectCoords(getX()), droppingObjectCoords(getY())), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
                else //if(rand == 3)
                    getWorld().addObject(new Six(droppingObjectCoords(getX()), droppingObjectCoords(getY())), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
                
                timeDrop = 0;
            }
            else
                timeDrop = 250;
        }
    }
}
