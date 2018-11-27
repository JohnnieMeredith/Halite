if(gameMap.calculateDistance(ship, shipyard)<=1&& gameMap.at(shipyard).isEmpty(){
                    commandQueue.add(ship.move(
                    
                     public static Position lastStep(Ship s, GameMap gameMap){
     //HashMap nextMove = new HashMap();
                    Position max = s.position;
                    for(Position rd : s.position.getSurroundingCardinals()){
                       
                        if(gameMap.calculateDistance(gameMap.at(rd), shipyard) < gameMap.calculateDistance(gameMap.at(max), shipyard){
                        max = rd;
                        }

                    }
                    return max;