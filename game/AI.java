package game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * TODO: echanges spontanés 
     * 
     * priority of actions:
     * - improve random colony
     * - buy random colony
     * - buy random road
     * 
     * 
     * */
    public void play(Player bot) {
        // loop on priority actions
        boolean hasPriorActions;
        do {
            
            hasPriorActions = false;

            hasPriorActions |= improveRandomColony(bot);
            hasPriorActions |= buyRandomColony(bot);
            hasPriorActions |= buyRandomRoad(bot);

        } while (hasPriorActions);

        // try to find ressources

        // plus tard
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
        
        for (int j = 0; j < 4; j++) { 
            switch (j) {
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

    private boolean buyRandomColony(Player bot) {
        int who = bot.getIndex();

        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1,
                Ressource.SHEEP, 1,
                Ressource.WHEAT, 1
            ))) return false;

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
                return true;
            }
        }

        // if not found
        return false;
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