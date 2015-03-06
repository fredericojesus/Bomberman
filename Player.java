import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Player extends DynamicActor
{   
    private int numberBombs;
    private int numberBombsActive;
    private int bombLevel;
    private boolean kick;
    
    private boolean transparency;
    private int countTransp;
    private int changeTransp;
    
    private int saveSpeed;
    private boolean slow;
    private int timeSlow;
    
    private String [] moveKeys;
    private String bombKey;
    
    public Player(int player, String upKey, String downKey, String leftKey, String rightKey, String bombKey)
    {
        super("p", player, 3);
        lifes = 3;
        
        createPlayerImages(imgsDown, NUMBER_OF_IMAGES, actorNumber, "d");
        createPlayerImages(imgsUp, NUMBER_OF_IMAGES, actorNumber, "u");
        createPlayerImages(imgsLeft, NUMBER_OF_IMAGES, actorNumber, "l");
        createPlayerImages(imgsRight, NUMBER_OF_IMAGES, actorNumber, "r");
        createPlayerImages(imgsDeath, NUMBER_OF_IMAGES+1, actorNumber, "m");
        imgsDeath[4] = new GreenfootImage(actor + actorNumber + "m3.png");
        
        if(player == 2)
            setImage(imgsDown[1]);
     
        numberBombs = 1;
        numberBombsActive = 0;
        bombLevel = 1;
        kick = false;
        
        transparency = false;
        countTransp = 0;
        changeTransp = 100;
        
        saveSpeed = 1;
        slow = false;
        timeSlow = 0;
        
        moveKeys = new String[4]; //[0]->upKey, [1]->downKey, [2]->leftKey, [3]->rightKey
        moveKeys[0] = upKey;
        moveKeys[1] = downKey;
        moveKeys[2] = leftKey;
        moveKeys[3] = rightKey;
        this.bombKey = bombKey;
    }
    
    public void act() 
    {
        if(!die)
        {
            if(transparency)
                transparency();
            underBomb();
            move();
            powerTouched();
            slow();
        }
        else
            death();
            
    }    
    
    public void move()
    {
        if(timeMove++ == 1)
        {
            if(Greenfoot.isKeyDown(moveKeys[0])) //up
            {    
                setImage("u");
                setLocation(getX(), getY() - speed);
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                    setLocation(getX(), getY() + speed);
                if(bomb != null && !underBomb && kick)
                {
                    Greenfoot.playSound("Kick.wav");
                    bomb.move("u");
                }
            }
            if(Greenfoot.isKeyDown(moveKeys[1])) //down
            { 
                setImage("d");
                setLocation(getX(), getY() + speed); 
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                    setLocation(getX(), getY() - speed);
                if(bomb != null && !underBomb && kick)
                {
                    Greenfoot.playSound("Kick.wav");
                    bomb.move("d");
                }
            }
            if(Greenfoot.isKeyDown(moveKeys[2])) //left
            {
                setImage("l");
                setLocation(getX() - speed, getY());
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                    setLocation(getX() + speed, getY()); 
                if(bomb != null && !underBomb && kick)
                {
                    Greenfoot.playSound("Kick.wav");
                    bomb.move("l");
                }
            }
            if(Greenfoot.isKeyDown(moveKeys[3])) //right
            {
                setImage("r");
                setLocation(getX() + speed, getY()); 
                Actor staticActor = getOneIntersectingObject(StaticActor.class);
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                if(staticActor != null || (bomb != null && !underBomb))
                    setLocation(getX() - speed, getY());
                if(bomb != null && !underBomb && kick)
                {
                    Greenfoot.playSound("Kick.wav");
                    bomb.move("r");
                }
            }
            if(Greenfoot.isKeyDown(bombKey) && numberBombsActive < numberBombs) //bomb
            {
                Bomb bomb = (Bomb) getOneIntersectingObject(Bomb.class);
                Player player = (Player) getOneIntersectingObject(Player.class);
                Enemie enemie = (Enemie) getOneIntersectingObject(Enemie.class);
                if(bomb == null)
                {
                    getWorld().addObject(new Bomb(droppingObjectCoords(getX()), droppingObjectCoords(getY()), bombLevel, this), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
                    numberBombsActive++;
                    underBomb = true;
                    if(player != null)
                        player.setUnderBomb();
                    if(enemie != null)
                        enemie.setUnderBomb();
                }
            }
            timeMove = 1;
            if(slow) timeMove = 0; 
        }
    }
    
    private void createPlayerImages(GreenfootImage [] images, int numberOfImages, int player, String direction)
    {
        for(int i = 0; i < numberOfImages; i++)
        {
            images[i] = new GreenfootImage("p" + player + direction + i + ".png");
        }
    }
    
    //polimorfismo                          <--------------------------------------------------------------------------
    private void powerTouched()
    {
        Power power = (Power) getOneIntersectingObject(Power.class);
        if(power != null)
            power.touchPower(this);
    }

    public void decNumberBombsActive()
    {
        numberBombsActive--;
    }
    
    //função que incrementa o atributo numberBombs
    public void setNumberBombs(int n)
    {
        numberBombs = n;
    }
    
    //função que retorna o atributo numberBombs
    public int getNumberBombs()
    {
        return numberBombs;
    }
    
    //função que incrementa o atributo bombLevel
    public void setBombLevel(int n)
    {
        bombLevel = n;
    }
    
    //funcção que retorna o atributo bombLevel
    public int getBombLevel()
    {
        return bombLevel;
    }
    //função que mete a true o atributo kick
    public void setKick()
    {
        kick = true;    
    }
    
    public boolean getKick()
    {
        return kick;
    }
    
    //função que retorna o player
    public int getPlayer()
    {
        return actorNumber;
    }
    
    //função que retorna o atributo lifes
    public int getLifes()
    {
        return lifes;
    }
    
    //função que altera o atributo lifes
    public void setLifes(int n)
    {
        lifes = n;
    }
    
    public void slow()
    {
        Actor poop = getOneIntersectingObject(Poop.class);
        if(poop != null && speed != 1)
        {
            saveSpeed = speed;
            speed = 1;
            timeMove = 0;
            slow = true;
        }
        else if(poop != null && speed == 1 && !slow)
        {
            timeMove = 0;
            slow = true;
        }
        else if(slow)
        {
            if(timeSlow++ == 100)
            {
                speed = saveSpeed;
                slow = false;
                timeSlow = 0;
            }
        }
    }
    
    public boolean getSlow()
    {
        return slow;
    }
    
    public int getSaveSpeed()
    {
        return saveSpeed;
    }
    
    public void death()
    {
        die = true;
        
        if(changeImageDeath++ == 20)
        {
            if(currentImageDeath == 0) Greenfoot.playSound("Death.wav");
            setImage(imgsDeath[currentImageDeath]);
            currentImageDeath++;
            changeImageDeath = 0;
            
            if(currentImageDeath == 5)
            {
                lifes--;
                if(((BomberWorld)getWorld()).getLevel() == 10 && lifes != 0)
                    ((BomberWorld)getWorld()).restartMultiplayer();
                else
                {
                    if(lifes == 0)
                        ((BomberWorld)getWorld()).endGame(actorNumber);
                    else
                    {
                        if(actorNumber == 1)
                            setLocation(48, 80);
                        else //if(player == "2")
                            setLocation(432, 464);
                        setImage(imgsDown[1]);
                        currentImageDeath = 0;
                        ((BomberWorld)getWorld()).setMsgOnBox(actorNumber, 1, lifes); 
                        die = false;
                        transparency = true;
                        
                        speed = 1;
                        ((BomberWorld)getWorld()).setMsgOnBox(actorNumber, 3, 1);
                        numberBombs = 1;
                        ((BomberWorld)getWorld()).setMsgOnBox(actorNumber, 2, 1);
                        bombLevel = 1;
                        ((BomberWorld)getWorld()).setMsgOnBox(actorNumber, 4, 1);
                        kick = false;
                        ((BomberWorld)getWorld()).removeKickImage(actorNumber);
                    }
                }
            }
        }
    }
    
    private void transparency()
    {
        transparency = true;
        
        if(countTransp++ <= 240)
        {
            getImage().setTransparency(150);
            if(countTransp == 240)
            {
                for(int i = 0; i < 3; i++)
                {
                    imgsDown[i].setTransparency(255);
                    imgsUp[i].setTransparency(255);
                    imgsLeft[i].setTransparency(255);
                    imgsRight[i].setTransparency(255);
                }
                transparency = false;
                countTransp = 0;
            }
        }
    }
    
    public boolean getTransparency()
    {
        return transparency;
    }
}
