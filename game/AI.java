package game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cli.Utils;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Trade;
import game.utils.Dices;
import game.utils.Fnc;
import game.utils.Offer;

/**
 * Artificial intelligence
 * This class contains methods that
 * define how bots should behave.
 * 
 */

public class AI {

    private State state;
    private Trade trade;
    private Dices dices;

    public AI(State state, Trade trade, Dices dices) {
        this.state = state;
        this.trade = trade;
        this.dices = dices;
    }

    /**
     * This function simulate the behavior of a bot 
     * (after he rolled the dices).
     *  
     * TODO: echanges spontanés 
     * 
     * */
    public void play(Player bot) {
        /** loop on priority actions
         * priority of actions:
         * - improve random colony
         * - buy random colony
         * - buy random road
         * */
        boolean hasPriorActions;
        do {
            int nRoad   = bot.getRoadH().size() + bot.getRoadV().size(),
                nColony = bot.getColonies().size(),
                nCity   = bot.getCities().size();

            hasPriorActions = false;

            hasPriorActions |= improveRandomColony(bot);
            
            int tryBuyColony = buyRandomColony(bot);
            hasPriorActions |= tryBuyColony == 1;

            boolean needRoads = tryBuyColony==2;
            if ((needRoads) || nRoad<(3*nCity)) // sinon il gaspille trop ses ressources en roads
                hasPriorActions |= buyRandomRoad(bot);
        } while (hasPriorActions);

        /** try to find ressources
         * 
         * make an order of priority between roads/colonies/cities
         * 
         * -------------------------------------------
         * priority order:
         * 
         * at beginning you need at least (4?) colonies to 
         * have enough ressources so you can buy
         * 
         * if you already have this number of colonies
         * your next ambition is to have ports
         * with a normal sized map with (5?) random road you
         * should have a road that lead to a port
         * 
         * if you can have a colony on a port
         * buy it
         * 
         * when you have those you can start to prioritise cities
         * 
         * -------------------------------------------
         * following the order:
         * 
         * is there colony to improve?
         *  seek ressources to improve colony
         * is there colony to build?
         *  seek ressources to build colony
         * is there road to build?
         *  seek ressources to build road
         */

        int pRoad  = 0, pColony = 1, pCity = 2; // priority
        int nRoad   = bot.getRoadH().size() + bot.getRoadV().size(),
            nColony = bot.getColonies().size(),
            nCity   = bot.getCities().size();
        int[] priorityRessources = new int[3];
        if (nColony < 4)
            priorityRessources = new int[] {pColony, pCity, pRoad} ;
        //else if (nRoad < 3)
        //  priorityRessources = new int[] {pRoad, pCity, pColony} ;
        else if (canHavePort(bot))
            priorityRessources = new int[] {pRoad, pCity, pColony} ;
        else
            priorityRessources = new int[] {pCity, pColony, pRoad} ;

        boolean needRoads = false;
        for (int i = 0; i < 3; i++) switch(priorityRessources[i]) {
            case 0: /*road*/ {
                optimizeRessource(bot, Offer.makeRessources(
                    Ressource.BRICK, 1,
                    Ressource.WOOD, 1
                )); 
                
                if (needRoads || nRoad<(3*nColony)) // eviter gaspillage
                    buyRandomRoad(bot);
            } break;
            case 1: /*colony*/ {
                optimizeRessource(bot, Offer.makeRessources(
                    Ressource.BRICK, 1,
                    Ressource.WOOD, 1,
                    Ressource.SHEEP, 1,
                    Ressource.WHEAT, 1
                )); 
                
                needRoads = buyRandomColony(bot) == 2;
            } break;
            case 2: /*city*/ {
                optimizeRessource(bot, Offer.makeRessources(
                    Ressource.ROCK, 4,
                    Ressource.WHEAT, 2
                )); improveRandomColony(bot);
            } break;
        }
    }

    private boolean canHavePort(Player bot) {
        /**
         * TODO:
         * check if one of its road leads to a port
         * where he can put a colony
         */
        return false;
    }

    private void optimizeRessource(Player bot, int[] toOptimize) {
        int[] rsc = bot.getRessources();
        for (int i = 0; i < toOptimize.length; i++)
        {
            if (rsc[i] >= toOptimize[i])
                continue; // ressource already fullfilled

            int[] freeRessources = getFreeRessources(rsc, toOptimize);
            int price = Ressource.priceOf(i, bot);

            if (price>Fnc.arrSum(freeRessources))
                continue;

            // choose n ressources (price) in usables ressources (freeRessources)
            int[] losse = new int[toOptimize.length];
            for (int p = 0, j = 1; p < price;)
                if (freeRessources[j]>0)
                {
                    freeRessources[j]--;
                    losse[j]++;
                    p++;
                } else j++;

            // -------

            Offer offer = new Offer(
                bot.getIndex(), -1, 
                losse, Fnc.oneShotEncoding(i, toOptimize.length)
                );

            if (!trade.canBuy(offer))
            {
                Utils.debug(offer.toString());
                Utils.debug("Error, a purchase has been refused to a bot.");
                continue;
            }
            
            trade.buy(offer);
        }
    }

    /**Which ressources can be used to buy other ressources*/
    private int[] getFreeRessources(int[] rsc, int[] toOptimize) {
        int[] free = new int[rsc.length];
        for (int i = 1; i < rsc.length; i++)
        {
            int surplus = rsc[i]-toOptimize[i];
            free[i] = surplus > 0 ? surplus : 0;
        }
        return free;
    }

    private boolean buyRandomRoad(Player bot) {
        int who = bot.getIndex();

        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1
            ))) return false;

        Map map = state.getMap();
        int sizepp = map.getSizePP();
        int size = map.getSize();

        int[] lookUpPriority = Fnc.randomIndexArray(3);
        for (int l = 0; l < 3; l++) switch (lookUpPriority[l]) {
            case 0: // try around roadsH
                List<Integer> roadsH = bot.getRoadH();
                int[] priority = Fnc.randomIndexArray(roadsH.size());
                for (int i = 0; i < roadsH.size(); i++) {
                    int x = Fnc.conv1dto2d_x(roadsH.get(priority[i]), size);
                    int y = Fnc.conv1dto2d_y(roadsH.get(priority[i]), size);

                    RoadResponse road;
                    if ((road = roadOnLocation(who, x, y)).valid)
                    {
                        trade.buyRoad(who, road.h, road.x, road.y);
                        System.out.println(bot+" has built the road on "+(
                            road.h?"horizontal":"vertical")+" "+road.x+";"+road.y+".");
                        return true;
                    }
                    if ((road = roadOnLocation(who, x+1, y)).valid)
                    {
                        trade.buyRoad(who, road.h, road.x, road.y);
                        System.out.println(bot+" has built the road on "+(
                            road.h?"horizontal":"vertical")+" "+road.x+";"+road.y+".");
                        return true;
                    }
                }
                break;
            
            case 1: // try around roadsV
                List<Integer> roadsV = bot.getRoadV();
                priority = Fnc.randomIndexArray(roadsV.size());
                for (int i = 0; i < roadsV.size(); i++) {
                    int x = Fnc.conv1dto2d_x(roadsV.get(priority[i]), sizepp);
                    int y = Fnc.conv1dto2d_y(roadsV.get(priority[i]), sizepp);

                    RoadResponse road;
                    if ((road = roadOnLocation(who, x, y)).valid)
                    {
                        trade.buyRoad(who, road.h, road.x, road.y);
                        System.out.println(bot+" has built the road on "+(
                            road.h?"horizontal":"vertical")+" "+road.x+";"+road.y+".");
                        return true;
                    }
                    if ((road = roadOnLocation(who, x, y+1)).valid)
                    {
                        trade.buyRoad(who, road.h, road.x, road.y);
                        System.out.println(bot+" has built the road on "+(
                            road.h?"horizontal":"vertical")+" "+road.x+";"+road.y+".");
                        return true;
                    }
                }
                break;

            case 2: // try around colonies
                List<Integer> colonies = bot.getColonies();
                priority = Fnc.randomIndexArray(colonies.size());
                for (int i = 0; i < colonies.size(); i++) {
                    int x = Fnc.conv1dto2d_x(colonies.get(priority[i]), sizepp);
                    int y = Fnc.conv1dto2d_y(colonies.get(priority[i]), sizepp);

                    RoadResponse road = roadOnLocation(who, x, y);

                    if (road.valid)
                    {
                        trade.buyRoad(who, road.h, road.x, road.y);
                        System.out.println(bot+" has built the road on "+(
                            road.h?"horizontal":"vertical")+" "+road.x+";"+road.y+".");
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    public static class RoadResponse {
        public final boolean valid;
        public final boolean h;
        public final int x;
        public final int y;
    
        public RoadResponse (boolean valid, boolean h, int x, int y) {
            this.valid = valid;
            this.h = h;
            this.x = x;
            this.y = y;
        }
    
        public RoadResponse() {
            this(false,false,0,0);
        }
    }
    
    public RoadResponse roadOnLocation(int who, int x, int y) {
        boolean h = true;
        int rx = 0, ry = 0;

        int size = state.getMap().getSize();
        
        int[] priority = Fnc.randomIndexArray(4);
        for (int j = 0; j < 4; j++) { 
            switch (priority[j]) {
                case 0: if (x < size) {
                    h = true;
                    rx = x;
                    ry = y;
                } break;
                case 1: if (x > 0) {
                    h = true;
                    rx = x-1;
                    ry = y;
                } break;
                case 2: if (y < size) {
                    h = false;
                    rx = x;
                    ry = y;
                } break;
                case 3: if (y > 0) {
                    h = false;
                    rx = x;
                    ry = y-1;
                } break;
            }
            
            if (state.getMap().canBuyRoad(who, h, rx, ry)) {
                return new RoadResponse(true, h, rx, ry);
            }
        }

        return new RoadResponse();
    }

    /**
     *
     * return : </br>
     *  0: don't have ressources </br>
     *  1: successful </br>
     *  2: nowhere to build </br>
     */
    private int buyRandomColony(Player bot) {
        int who = bot.getIndex();

        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1,
                Ressource.SHEEP, 1,
                Ressource.WHEAT, 1
            ))) return 0;

        // find valid xy
        Map map = state.getMap();
        int sizepp = map.getSizePP();
        int size = map.getSize();
        int[] priority = Fnc.randomIndexArray(sizepp*sizepp);

        for (int i = 0; i < priority.length; i++)
        {
            int cx = Fnc.conv1dto2d_x(priority[i], sizepp);
            int cy = Fnc.conv1dto2d_y(priority[i], sizepp);
            
            if (trade.canBuyColony(who, cx, cy)) {
                trade.buyColony(who, cx, cy);
                System.out.println(bot+" has built the colony on "+cx+";"+cy+".");
                return 1;
            }
        }

        // if not found
        return 2;
    }

    private boolean improveRandomColony(Player bot) {
        int who = bot.getIndex();
        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.ROCK, 4,
                Ressource.WHEAT, 2
            ))) return false;

        // find valid xy
        Map map = state.getMap();
        int sizepp = map.getSizePP();
        int size = map.getSize();
        
        List<Integer> colonies = bot.getColonies();
        int[] priority = Fnc.randomIndexArray(colonies.size());
        for (int i = 0; i < priority.length; i++)
        {
            int cx = Fnc.conv1dto2d_x(colonies.get(priority[i]), sizepp);
            int cy = Fnc.conv1dto2d_y(colonies.get(priority[i]), sizepp);
            
            if (trade.canImproveColony(who, cx, cy)) {
                trade.improveColony(who, cx, cy);
                System.out.println(bot+" has improved the colony on "+cx+";"+cy+".");
                return true;
            }
        }
        
        // if not found
        return false;
    }

    /**This method Returns true when bot p2 consent to the offer.*/
    public boolean consent(Player bot, Offer offer) {
        if (Offer.countRessources(offer.r1) < Offer.countRessources(offer.r2))
            return false;

        return Fnc.rand(2) == 1;
    }

    /** Choose cards to abandon 
     * By default it will abandon developpements cards
     * For bots, usage of those cards is not implemented yet
    */
    public void retribution(Player bot) {
        int[] devs = bot.getDeveloppements();
        int[] rsc  = bot.getRessources();
        
        int toAbandon = bot.nCards()/2;
        int fromDev = Fnc.arrSum(devs);

        /**
         * Par défaut : enlever des cartes randoms
         * TODO : atteindre un equilibre de cartes
         */
        
        int j = 0;

        int[] rnd = Fnc.randomIndexArray(devs.length);
        for (int i = 0; j < toAbandon && i < devs.length;)
            if (devs[rnd[i]]>0)
            {    
                devs[rnd[i]]--;
                j++;
            }
            else i++;
            

        if (toAbandon <= fromDev)
            return;
        
        /**
         * Par défaut : enlever des cartes randoms
         * TODO : atteindre un equilibre de cartes
         */
        
        rnd = Fnc.randomIndexArray(rsc.length-1);
        for (int i = 0; j < toAbandon && i < rsc.length;)
            if (rsc[rnd[i]+1]>0)
            {    
                rsc[rnd[i]+1]--;
                j++;
            }
            else i++;
    }

    /** Chose where to place the robber */
    public void steal(Player bot) {
        Set<Integer> safe = bot.getDicesIdx();
        Set<Integer> other = new HashSet<Integer>();
        for (Player p: state.getPlayers()) if (p!=bot)
            other.addAll(p.getDicesIdx());

        int idx = (int)other.toArray()[ Fnc.rand(other.size()) ];

        other.removeAll(safe);
        if (!other.isEmpty())
            idx = (int)other.toArray()[ Fnc.rand(other.size()) ];

        state.getMap().moveRobber(
            state.getMap().getDicesX()[idx], 
            state.getMap().getDicesY()[idx]);
    }
    
}