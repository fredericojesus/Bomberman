import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class BomberWorld extends World
{
    private int level;
    private int timeLevel;
    private boolean start;
    private int round;
    
    private Player player1;
    private Player player2;
    private int playerLifes1;
    private int playerLifes2;
    private int playerSpeed;
    private int playerNumberBombs;
    private int playerBombLevel;
    private boolean playerKick;
    
    private TextBox lifesPlayer1;
    private TextBox lifesPlayer2;
    private TextBox numberBombsP1;
    private TextBox numberBombsP2;
    private TextBox speedP1;
    private TextBox speedP2;
    private TextBox explosionLevelP1;
    private TextBox explosionLevelP2;
    
    private Image p1kick = new Image("b4");
    private Image p2kick = new Image("b4");
    
    //all the positions that the enemy will possibly turn
    private List<Coordinates> enemiesTurningPositions;
    //we divided the map in three parts
    private List<Coordinates> bricksPositions1; //possible positions to have a breakable brick in part1
    private List<Coordinates> bricksPositions2; //possible positions to have a breakable brick in part2
    private List<Coordinates> bricksPositions3; //possible positions to have a breakable brick in part3
    
    private GreenfootSound startSound;
    private GreenfootSound bossLevel;
  
    /** constructor of BomberWorld */
    public BomberWorld()
    {    
        // Create a new world with 480x544 cells with a cell size of 1x1 pixels.
        super(480 , 544, 1);
        setBackground("bombermanStart.png");
        
        level = 100; //u can try the level you want(max = 5) by putting start = true in the next code line.
        //start = true;
        timeLevel = 0;
        round = 1;
        
        playerLifes1 = 3;
        playerLifes2 = 3;
        playerSpeed = 1;
        playerNumberBombs = 1;
        playerBombLevel = 1;
        playerKick = false;
        
        addButton();
                
        enemiesTurningPositions = new ArrayList<Coordinates>();
        for(int i = 0, cx = 48; cx <= 432; cx+=64)
            for(int cy = 80; cy <= 464; cy+=64, i++)
                enemiesTurningPositions.add(new Coordinates(cx, cy));
                
        startSound = new GreenfootSound("startSound.wav");
        bossLevel = new GreenfootSound("BossLevel.mp3");
        bossLevel.setVolume(50);
    }
    
    public void act()
    {   
        setPaintOrder();
        
        if(level > 0 && level < 6 && start == false)
            checkLevel();
        
        if(start)
            createLevel();
            
        if(level == 100) startSound.play();
        
    }
    
    /** function that creates the level */
    private void createLevel()
    {
        switch(level)
        {
            case 0:
                startSound.play();
                removeObjects(getObjects(null));
                setBackground("bombermanStart.png");        
                addButton();
                start = false;
                break;
            case 20:
                removeObjects(getObjects(null));
                setBackground("help.png");
                addObject(new Button(9, 6, 0), 100, 500);
                start = false;
                break;
            case 10:
                startSound.stop();
                if(timeLevel == 20) Greenfoot.playSound("Round" + round + ".mp3");
                setBackground("round" + round + ".png");
                removeObjects(getObjects(null));
                if(timeLevel++ == 150)
                {
                    setBackground(new GreenfootImage(1, 1));
                    createScenario();
                    player1.setLifes(playerLifes1);
                    player2.setLifes(playerLifes2);
                    start = false;
                    timeLevel = 0;
                }
                break;
            case 1:
                startSound.stop();
                if(timeLevel == 0) Greenfoot.playSound("Ready.wav");
                setBackground("Level1.png");
                removeObjects(getObjects(null));
                if(timeLevel++ == 150)
                {
                    Greenfoot.playSound("Go.wav");
                    setBackground(new GreenfootImage(1, 1));
                    createScenario();
                    start = false;
                    timeLevel = 0;
                }
                break;
            default:
                if(timeLevel == 0) Greenfoot.playSound("Ready.wav");
                setBackground("Level" + level + ".png");
                removeObjects(getObjects(null));
                if(timeLevel++ == 150)
                {
                    Greenfoot.playSound("Go.wav");
                    setBackground(new GreenfootImage(1, 1));
                    createScenario();
                    player1.setLifes(playerLifes1);
                    player1.setSpeed(playerSpeed);
                    player1.setNumberBombs(playerNumberBombs);
                    player1.setBombLevel(playerBombLevel);
                    if(playerKick)
                    {
                        player1.setKick();
                        setKickImage(1);
                    }
                    start = false;
                    timeLevel = 0;
                }
        }
    }
    
    public void changeLevel(int n)
    {
        level = n;
        start = true;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    /** function that checks if exists enemies on the map, if not changes the level
     *  or the game ends if we are on last level.
     */
    private void checkLevel()
    {
        List enemies = getObjects(Enemie.class);
        if(enemies.isEmpty())
        {
            if(level == 5)
                endGame(1);
            else
            {
                playerLifes1 = player1.getLifes();
                if(player1.getSlow()) playerSpeed = player1.getSaveSpeed();
                else playerSpeed = player1.getSpeed();
                playerNumberBombs = player1.getNumberBombs();
                playerBombLevel = player1.getBombLevel();
                playerKick = player1.getKick();
                changeLevel(++level);
            }
        }
    }
    
    private void addButton()
    {
        addObject(new Button(1, 0, 1), 350, 300);
        addObject(new Button(2, 1, 10), 350, 350);
        addObject(new Button(3, 2, 20), 350, 400);
        addObject(new Button(4, 3, 0), 350, 450);
    }
    
    /** function that changes the message on a certain player box */
    public void setMsgOnBox(int player, int box, int number)
    {
        String msg = Integer.toString(number);
        
        if(player == 1)
        {
            switch(box)
            {
                case 1: if(player1.getLifes() == 1) lifesPlayer1.setColor(Color.RED);
                        else lifesPlayer1.setColor(Color.BLACK);
                        lifesPlayer1.setText(msg); 
                        break;
                case 2: numberBombsP1.setText(msg); break;
                case 3: speedP1.setText(msg); break;
                case 4: explosionLevelP1.setText(msg); break;
                default: break;
            }
        }
        else //if(player == 2)
        {
            switch(box)
            {
                case 1: lifesPlayer2.setText(msg); break;
                case 2: numberBombsP2.setText(msg); break;
                case 3: speedP2.setText(msg); break;
                case 4: explosionLevelP2.setText(msg); break;
                default: break;
            }
        }
    }
  
    public void setKickImage(int player)
    {
        if(player == 1)
            addObject(p1kick, 400, 16);
        else //if(player == "2")
            addObject(p2kick, 80, 528);    
    }
    
    public void removeKickImage(int player)
    {
        if(player == 1)
            removeObject(p1kick);
        else //if(player == "2")
            removeObject(p2kick);
    }
    
    /**Function that creates the playing scenario*/
    public void createScenario()
    {   
        //creates all the grass
        for(int i = 16; i < 480; i+=32)
            for(int j = 48; j < 512; j+= 32)
                addObject(new Image(new GreenfootImage("grass.png")), i, j);
        
        //up border
        for(int i = 16; i < 480; i+= 32)
            addObject(new Brick(), i, 48);
            
        //down border
        for(int i = 16; i < 480; i+= 32)
            addObject(new Brick(), i, 496);
            
        //left border
        for(int i = 48; i < 512; i+= 32)
            addObject(new Brick(), 16, i);  
            
        //right border
        for(int i = 48; i < 512; i+= 32)
            addObject(new Brick(), 464, i);
        
        //unbreakable bricks
        for(int i = 80; i < 480; i+=64)
            for(int j = 112; j < 512; j+=64)
                addObject(new Brick(), i, j); 
                
        fixedBricks();
        
        //Breakable bricks and bottom text
        if(level == 10)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(18);
        }
        else if(level == 1)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(15);
            addObject(new Image("dLevel1"), 240, 528);
        }
        else if(level == 2)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(12);
            addObject(new Image("dLevel2"), 240, 528);
        }
        else if(level == 3)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(9);
            addObject(new Image("dLevel3"), 240, 528);
        }
        else if(level == 4)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(9);
            addObject(new Image("dLevel4"), 240, 528);
        }
        else if(level == 5)
        {
            bricksPositions1();
            bricksPositions2();
            bricksPositions3();
            createRandomBricks(6);
            bossLevel.play();
            addObject(new Image("dFinalLevel"), 240, 528);
        }
        
        //player1
        player1 = new Player(1, "w", "s", "a", "d", "space");
        addObject(player1, 48, 80);
        addObject(new Image("p1"), 16, 16);
        lifesPlayer1 = new TextBox(Integer.toString(playerLifes1));
        if(playerLifes1 == 1) 
        {
            lifesPlayer1.setColor(Color.RED);
            lifesPlayer1.setText(Integer.toString(playerLifes1));
        }
        addObject(lifesPlayer1, 45, 23);
        addObject(new Image("b1"), 112, 16);
        numberBombsP1 = new TextBox(Integer.toString(playerNumberBombs));
        addObject(numberBombsP1, 139, 23);
        addObject(new Image("b2"), 208, 16); 
        speedP1 = new TextBox(Integer.toString(playerSpeed));
        addObject(speedP1, 236, 23);
        addObject(new Image("b3"), 304, 16);
        explosionLevelP1 = new TextBox(Integer.toString(playerBombLevel));
        addObject(explosionLevelP1, 333, 23);
        
        //player2
        if(level == 10)
        {
            player2 = new Player(2, "up", "down", "left", "right", "enter");
            addObject(player2, 432, 464);
            addObject(new Image("p2"), 464, 528);
            lifesPlayer2 = new TextBox(Integer.toString(playerLifes2));
            addObject(lifesPlayer2, 430, 534);
            if(playerLifes2 == 1)
            {
                lifesPlayer2.setColor(Color.RED);
                lifesPlayer2.setText(Integer.toString(playerLifes2));
            }
            addObject(new Image("b1"), 368, 528);
            numberBombsP2 = new TextBox("1");
            addObject(numberBombsP2, 333, 534);
            addObject(new Image("b2"), 272, 528);
            speedP2 = new TextBox("1");
            addObject(speedP2, 236, 534);             
            addObject(new Image("b3"), 176, 528); 
            explosionLevelP2 = new TextBox("1");
            addObject(explosionLevelP2, 139, 534);                     
        }
        
        //enemies
        if(level == 1)
        {
            insertEnemie(bricksPositions1, 1, 1);
            insertEnemie(bricksPositions2, 1, 1);
            insertEnemie(bricksPositions2, 2, 2);
            insertEnemie(bricksPositions3, 3, 1);
        }
        if(level == 2)
        {
            insertEnemie(bricksPositions1, 1, 1);
            insertEnemie(bricksPositions1, 2, 2);
            insertEnemie(bricksPositions2, 3, 1);
            insertEnemie(bricksPositions2, 3, 1);
            insertEnemie(bricksPositions3, 4, 2);
        }
        if(level == 3)
        {
            insertEnemie(bricksPositions1, 2, 2);
            insertEnemie(bricksPositions1, 3, 1);
            insertEnemie(bricksPositions2, 4, 2);
            insertEnemie(bricksPositions2, 4, 2);
            insertEnemie(bricksPositions3, 5, 2);
            insertHeart(bricksPositions3);
            
        }
        if(level == 4)
        {
            insertEnemie(bricksPositions1, 2, 2);
            insertEnemie(bricksPositions1, 3, 1);
            insertEnemie(bricksPositions2, 3, 1);
            insertEnemie(bricksPositions2, 5, 2);
            insertEnemie(bricksPositions3, 4, 2);
            insertEnemie(bricksPositions3, 6, 2);
            insertHeart(bricksPositions3);
        }
        if(level == 5)
        {
            insertEnemie(enemiesTurningPositions, 0, 2);
            insertHeart(bricksPositions3);
        }
    }
    
    /** function that puts an enemy on a random position of the map */
    private void insertEnemie(List<Coordinates> list, int enemy, int speed)
    {
        int rand = Greenfoot.getRandomNumber(list.size());
        Coordinates aux = list.get(rand);
        if(enemy == 3)
            addObject(new Three(aux.getX(), aux.getY()), aux.getX(), aux.getY());
        else if(enemy == 4)
            addObject(new Four(aux.getX(), aux.getY()), aux.getX(), aux.getY());
        else if(enemy == 5)
            addObject(new Five(aux.getX(), aux.getY()), aux.getX(), aux.getY());
        else if(enemy == 6)
            addObject(new Six(aux.getX(), aux.getY()), aux.getX(), aux.getY());
        else if(enemy == 0)
        {
            while(true)
            {
                List breakableBrick = getObjectsAt(aux.getX(), aux.getY(), BreakableBrick.class);
                if(breakableBrick.isEmpty())
                    break;
                rand = Greenfoot.getRandomNumber(list.size());
                aux = list.get(rand);
            }
            addObject(new Boss(aux.getX(), aux.getY()), aux.getX(), aux.getY());
            return;
        }
        else
            addObject(new Enemie(enemy, aux.getX(), aux.getY(), speed), aux.getX(), aux.getY());
            
        list.remove(rand);    
    }

    /** function that puts a LifeUp on a random position of the map */
    private void insertHeart(List<Coordinates> list)
    {
        int rand = Greenfoot.getRandomNumber(list.size());
        Coordinates aux = list.get(rand);
        addObject(new LifeUp(), aux.getX(), aux.getY());
    }
    
    /**function that creates the breakable bricks that will always exist around the player */
    private void fixedBricks()
    {   
        createPowers(48, 176);
        addObject(new BreakableBrick(), 48, 176);
        createPowers(48, 208);
        addObject(new BreakableBrick(), 48, 208);
        createPowers(80, 144);
        addObject(new BreakableBrick(), 80, 144);
        createPowers(112, 112);
        addObject(new BreakableBrick(), 112, 112);
        createPowers(112, 144);
        addObject(new BreakableBrick(), 112, 144);
        createPowers(144, 80);
        addObject(new BreakableBrick(), 144, 80);
        createPowers(176, 80);
        addObject(new BreakableBrick(), 176, 80);
        
        if(level == 10)
        {
            createPowers(304, 464);
            addObject(new BreakableBrick(), 304, 464);
            createPowers(336, 464);
            addObject(new BreakableBrick(), 336, 464);
            createPowers(368, 432);
            addObject(new BreakableBrick(), 368, 432);
            createPowers(368, 400);
            addObject(new BreakableBrick(), 368, 400);
            createPowers(400, 400);
            addObject(new BreakableBrick(), 400, 400);
            createPowers(432, 368);
            addObject(new BreakableBrick(), 432, 368);
            createPowers(432, 336);
            addObject(new BreakableBrick(), 432, 336);
        }
    }
    
    public List<Coordinates> getEnemiesTurningPositions()
    {
        return enemiesTurningPositions;    
    }
    
    /**function that initializes the attribute bricksPositions1*/
    private void bricksPositions1()
    {
        bricksPositions1 = new ArrayList<Coordinates>();
        
        int cy = 240;
        for(int k = 0, cx = 48; cx <= 144; cx+=32)
        {
            if(cx == 80) cy = 208;
            else if(cx == 112) cy = 176;
            else if(cx == 144) cy = 144;
            while(cy <= 464)
            {
                bricksPositions1.add(new Coordinates(cx, cy));
                if(k == 0)
                    cy+=32;
                else
                    cy+=64;
            }
            if(k == 0)
                k = 1;
            else 
                k = 0;
        }
    }
    
    /**function that initializes the attribute bricksPositions2*/
    private void bricksPositions2()
    {
        bricksPositions2 = new ArrayList<Coordinates>();
        
        int cy = 112;
        for(int k = 0, cx = 176; cx <= 304; cx+=32)
        {
            if(cx > 176)
                cy = 80;
            while(cy <= 464)
            {
                bricksPositions2.add(new Coordinates(cx, cy));
                if(k == 0)
                    cy+=32;
                else
                    cy+=64;
                if(cx == 304 && cy == 464 && level == 10)
                    break;
            }
            if(k == 0)
                k = 1;
            else 
                k = 0;
        }
    }
    
    /**function that initializes the attribute bricksPositions3*/
    private void bricksPositions3()
    {
        bricksPositions3 = new ArrayList<Coordinates>();
        
        for(int k = 0, cx = 336; cx <= 432; cx+=32)
        {
            int cy = 80;
            while(cy <= 464)
            {
                bricksPositions3.add(new Coordinates(cx, cy));
                if(k == 0) 
                    cy+=64;
                else 
                    cy+=32;
                    
                if(cx == 336 && cy == 464 && level == 10) break;
                if(cx == 368 && cy == 400 && level == 10) break;
                if(cx == 400 && cy == 400 && level == 10) break;
                if(cx == 432 && cy == 336 && level == 10) break;
            }
            if(k == 0)
                k = 1;
            else 
                k = 0;
        }
    }
    
    public List<Coordinates> getBricksList(int n)
    {
        if(n == 1) 
            return bricksPositions1;
        else if(n == 2)
            return bricksPositions2;
        else //if(n == 3)
            return bricksPositions3;
    }
    
     /**Function that creates randomly the BreakableBricks and put powers randomly behind some bricks*/
    private void createRandomBricks(int n)
    {
        int size = bricksPositions1.size();
        for(int i = 0; i < n; i++)
        {
            int rand = Greenfoot.getRandomNumber(size--);
            Coordinates aux = bricksPositions1.get(rand);
            bricksPositions1.remove(rand);
            createPowers(aux.getX(), aux.getY());
            addObject(new BreakableBrick(), aux.getX(), aux.getY());
        }
        
        size = bricksPositions2.size();
        for(int i = 0; i < n+10; i++)
        {
            int rand = Greenfoot.getRandomNumber(size--);
            Coordinates aux = bricksPositions2.get(rand);
            bricksPositions2.remove(rand);
            createPowers(aux.getX(), aux.getY());
            addObject(new BreakableBrick(), aux.getX(), aux.getY());
        }
        
        int more;
        if(level == 10)
            more = 0;
        else
            more = 8;
        size = bricksPositions3.size();
        for(int i = 0; i < n+more; i++)
        {
            int rand = Greenfoot.getRandomNumber(size--);
            Coordinates aux = bricksPositions3.get(rand);
            bricksPositions3.remove(rand);
            createPowers(aux.getX(), aux.getY());
            addObject(new BreakableBrick(), aux.getX(), aux.getY());
        }
    } 
    
    /**Function that inserts the powers on the map*/
    private void createPowers(int x, int y)
    {
        int rand = Greenfoot.getRandomNumber(25);
        
        switch(rand)
        {
            case 1 : addObject(new BombsUp(), x, y); break;
            case 2 : addObject(new SpeedUp(), x, y); break;
            case 3 : addObject(new BombLevelUp(), x, y); break;
            case 4 : addObject(new Kick(), x, y); break;
            case 10: addObject(new BombsUp(), x, y); break;
            case 11: addObject(new BombLevelUp(), x, y); break;
            default: break;
            
        }
    }
    
    /**Function that ends the game*/
    public void endGame(int player)
    {
        if(level == 10)
        {
            if(player == 1)
            {
                removeObjects(getObjects(null));
                Greenfoot.playSound("Player2Wins.mp3");
                setBackground("p2wins.png");
                addObject(new Button(7, 4, 10), 225, 400);
                addObject(new Button(8, 5, 0), 225, 450);
            }
            else //if(player == 2)
            {
                removeObjects(getObjects(null));
                Greenfoot.playSound("Player1Wins.mp3");
                setBackground("p1wins.png");
                addObject(new Button(5, 4, 10), 225, 400);
                addObject(new Button(6, 5, 0), 225, 450);
            }
            round = 1;
        }
        else //if(level != 10)
        {
            List enemies = getObjects(Enemie.class);
            if(level == 5 && enemies.isEmpty())
            {
                bossLevel.stop();
                removeObjects(getObjects(null));
                Greenfoot.playSound("Win.wav");
                setBackground("win.png");
                addObject(new Button(5, 1, 1), 350, 350);
                addObject(new Button(6, 2, 0), 350, 400);
            }
            else
            {
                removeObjects(getObjects(null));
                Greenfoot.playSound("Lose.wav");
                setBackground("lose.png");
                addObject(new Button(5, 1, 1), 350, 350);
                addObject(new Button(6, 2, 0), 350, 400);
            }
            playerSpeed = 1;
            playerNumberBombs = 1;
            playerBombLevel = 1;
        }
        playerLifes1 = 3;
        playerLifes2 = 3;
        level = 0;
    }
    
    public void restartMultiplayer()
    {
        playerLifes1 = player1.getLifes();
        playerLifes2 = player2.getLifes();
        round++;
        start = true;
    }
    
    private void setPaintOrder()
    {
        setPaintOrder(Power.class);
        setPaintOrder(StaticActor.class);
        setPaintOrder(Poop.class);
        setPaintOrder(Enemie.class);
        setPaintOrder(Three.class);
        setPaintOrder(Five.class);
        setPaintOrder(Six.class);
        setPaintOrder(Four.class);
        setPaintOrder(Boss.class);
        setPaintOrder(Player.class);
        setPaintOrder(Explosion.class);
        setPaintOrder(Bomb.class);
        setPaintOrder(Hurricane.class);
    }
    
    public void end()
    {
        removeObjects(getObjects(null));
        setBackground("end.png");
        startSound.stop();
        Greenfoot.stop();
    }
}
