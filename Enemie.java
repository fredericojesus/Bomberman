import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Enemie extends DynamicActor
{     
    protected String direction;
    
    protected int timeDie;
    
    public Enemie(int enemie, int x, int y, int s)
    {   
        super("e", enemie, 4);
        speed = s;
        lifes = 1;
        
        createEnemieImages(imgsLeft, NUMBER_OF_IMAGES, actorNumber, "l");
        createEnemieImages(imgsRight, NUMBER_OF_IMAGES, actorNumber, "r");
        createEnemieImages(imgsDown, NUMBER_OF_IMAGES, actorNumber, "d");
        createEnemieImages(imgsUp, NUMBER_OF_IMAGES, actorNumber, "u");
        createEnemieImages(imgsDeath, NUMBER_OF_IMAGES+1, actorNumber, "m");
        imgsDeath[5] = new GreenfootImage(actor + actorNumber + "m4.png");
     
        setImage(imgsLeft[0]);
      
        direction = checkStart(x, y);
        timeDie = 0;
    }
    
    //overloading
    public Enemie(int x, int y)
    {
        super("e", 0);
        
        direction = checkStart(x, y);
        timeDie = 0;
    }
    
    public void act() 
    {
        if(!die)
        {
            underBomb();
            move();
            chooseDirection();
            touchEnemie();
        }
        else 
            death(6);
    }       
    
    public void move()
    {
        if(timeMove++ == 1)
        {   
            if(direction == "u")
            {    
                setImage(direction);
                setLocation(getX(), getY() - speed);
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                {
                    setLocation(getX(), getY() + speed);
                    direction = "d";
                }
            }
            if(direction == "d")
            { 
                setImage(direction);
                setLocation(getX(), getY() + speed); 
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                {
                    direction = "u";
                    setLocation(getX(), getY() - speed);
                }
            }
            if(direction == "l")
            {
                setImage(direction);
                setLocation(getX() - speed, getY());
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                {
                    setLocation(getX() + speed, getY()); 
                    direction = "r";
                }
            }
            if(direction == "r")
            {
                setImage(direction);
                setLocation(getX() + speed, getY()); 
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                {
                    setLocation(getX() - speed, getY()); 
                    direction = "l";
                }
            }
            timeMove = 0;
        }
    }
    
    protected void touchEnemie()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player != null && timeDie++ == 3)
        {
            if(!player.getTransparency())
                player.death();
            timeDie = 0;
        }
    }
    
    private void createEnemieImages(GreenfootImage [] images, int numberOfImages, int enemie, String direction)
    {
        if(direction == "l" || direction == "r" || direction == "m")
        {
            for(int i = 0; i < numberOfImages; i++)
                images[i] = new GreenfootImage("e" + enemie + direction + i + ".png");
        }
        else if(direction == "d" || direction == "u")
        {  
            for(int i = 0, j = 0; i < numberOfImages; i+=2, j+=2)
            {
                images[i] = new GreenfootImage("e" + enemie + "l" + j + ".png");
                images[i+1] = new GreenfootImage("e" + enemie + "r" + (j+1) + ".png");
            }
        }
    }
    
    protected void chooseDirection()
    {
        for(int i = 0; i < 49; i++)
        {
            if(getX() == ((BomberWorld)getWorld()).getEnemiesTurningPositions().get(i).getX() 
                && getY() == ((BomberWorld)getWorld()).getEnemiesTurningPositions().get(i).getY())
            {
                int rand = Greenfoot.getRandomNumber(4);
                switch(rand)
                {
                    case 0: direction = "l"; break;
                    case 1: direction = "r"; break;
                    case 2: direction = "d"; break;
                    case 3: direction = "u"; break;
                    default: break;
                }
            }
        }
    }
    
    public void death(int n)
    {
        die = true;
        
        if(changeImageDeath++ == 20)
        {
            if(currentImageDeath == 0) Greenfoot.playSound("corpse.wav");
            setImage(imgsDeath[currentImageDeath]);
            currentImageDeath++;
            changeImageDeath = 0;
            
            if(currentImageDeath == n)
               getWorld().removeObject(this);
        }
        
    }
    
    protected String checkStart(int rx, int ry)
    {
        int rand;
        for(int cy = 80; cy <= 464; cy+=64)
        {
            for(int cx = 80; cx <= 400; cx+=64)
            {
                if(rx == cx && ry == cy)
                {
                    rand = Greenfoot.getRandomNumber(3);
                    if(rand == 1)
                        return "l";
                    else return "r";
                }
            }
        }
        for(int cx = 48; cx <= 432; cx+=64)
        {
            for(int cy = 112; cy <= 464; cy+=64)
            {
                if(rx == cx && ry == cy)
                {
                    rand = Greenfoot.getRandomNumber(3);
                    if(rand == 1)
                        return "u";
                    else return "d";
                }
            }
        }
        rand = Greenfoot.getRandomNumber(4);
        if(rand == 0) return "d";
        else if(rand == 1) return "l";
        else if(rand == 2) return "u";
        else return "r";
    }
}