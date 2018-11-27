// This Java API uses camelCase instead of the snake_case as documented in the API docs.
//     Otherwise the names of methods are consistent.

import hlt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;

public class MyBot {
    public static void main(final String[] args) {
        final long rngSeed;
        if (args.length > 1) {
            rngSeed = Integer.parseInt(args[1]);
        } else {
            rngSeed = System.nanoTime();
        }
        final Random rng = new Random(rngSeed);

        Game game = new Game();
        // At this point "game" variable is populated with initial map data.
        // This is a good place to do computationally expensive start-up pre-processing.
        // As soon as you call "ready" function below, the 2 second per turn timer will start.
        //ArrayList<String> mapScan = new ArrayList<String>();
        //ArrayList<int> hmap = new ArrayList<int>();
        game.ready("J5isALIVEbotv1");

        Log.log("Successfully created bot! My Player ID is " + game.myId + ". Bot rng seed is " + rngSeed + ".");

        for (;;) {
            game.updateFrame();
            final Player me = game.me;
            final GameMap gameMap = game.gameMap;

            final ArrayList<Command> commandQueue = new ArrayList<>();

            for (final Ship ship : me.ships.values()) {
                if(Constants.MAX_HALITE > 4000){
                    ship.makeDropoff();
                }

                if(gameMap.at(ship).halite < Constants.MAX_HALITE / 10 && ship.halite > 800 && ship.halite > (gameMap.calculateDistance(ship.position, me.shipyard.position)*10)){
                    if(gameMap.at(me.shipyard.position).isOccupied() && gameMap.calculateDistance(ship.position, me.shipyard.position) <= 1){
                        if(ship.position.x < me.shipyard.position.x){
                            commandQueue.add(ship.move(Direction.NORTH));
                        }
                        else  if(ship.position.x > me.shipyard.position.x){
                            commandQueue.add(ship.move(Direction.SOUTH));
                        }
                        //commandQueue.add(ship.move(MyBot.getTargetDirection(ship.position, me.shipyard.position, gameMap)));
                    }
                    else

                        commandQueue.add(ship.move(gameMap.naiveNavigate(ship, me.shipyard.position)));

                }

                else if (gameMap.at(ship).halite < Constants.MAX_HALITE / 10 ) {

                    final Direction randomDirection = Direction.ALL_CARDINALS.get(rng.nextInt(4));
                    if(gameMap.at(ship.position.directionalOffset(randomDirection)).isEmpty()){ 
                        if((gameMap.at(ship.position.directionalOffset(randomDirection)).equals(gameMap.at(me.shipyard.position)))){

                            commandQueue.add(ship.move(gameMap.naiveNavigate(ship, scanMap(ship, gameMap, me))));
                        }
                        else{
                            //gameMap.at(ship.position.directionalOffset(randomDirection)).markUnsafe(ship);
                            commandQueue.add(ship.move(gameMap.naiveNavigate(ship, scanMap(ship, gameMap, me))));
                        }

                    }
                } else {
                    commandQueue.add(ship.stayStill());

                }
            }
            //if(ship.position == me.shipyard.position && ship.halite == 0)
                //gameMap.at(ship.position).markSafe();

            if (game.turnNumber <= 250 && me.halite >= Constants.SHIP_COST && !gameMap.at(me.shipyard).isOccupied())
            {
                commandQueue.add(me.shipyard.spawn());
            }
        

            game.endTurn(commandQueue);
        }
    }

    public static Position scanMap(Ship s, GameMap gameMap, Player me){
        //HashMap nextMove = new HashMap();
        Position max = s.position;
        if(max.equals(me.shipyard.position)){
            return new Position(0,1);
        }

        for(Position rd : s.position.getSurroundingCardinals()){

            if(gameMap.at(rd).halite >= gameMap.at(max).halite){
                max = rd;
            }

        }
        return max;

                        
    }
    /* public static Direction getTargetDirection(Position p, Position q, GameMap g){
    for(final Direction d : Direction.ALL_CARDINALS) {
    if(g.get    
    //if(g.at(p).x > g.at.(q).x){
    //d = West;}
    //if(p.x < q.x){
    //d = East;}
    // if(p.y > q.y){
    //d = South;}
    //else
    //d = North;
    return d;

    }*/

                    
}
