package cli.visuals;

import java.util.Set;

import cli.Utils;
import game.Engine;
import game.constants.Biome;
import game.constants.Developpement;
import game.constants.Port;
import game.constants.Ressource;
import game.state.Player;
import game.utils.Fnc;

public class Resume {

    private Engine engine;

    public Resume(Engine engine) {
        this.engine = engine;
    }
    
    // ------------------------
    
    public void resume() {
        Utils.delim();
        System.out.println("Resume :");
        System.out.println();
        Utils.delim(10, '-', true);
        showMap();
        Utils.delim(10, '-', true);
        showRCC(); // RCCD = road colonies and cities and dices
        Utils.delim();

    }

    private void showRCC() {
        int size = engine.getMap().getSize();
        int sizepp = size+1;

        System.out.println("Ports:");
        int[][] ports = engine.getMap().getPorts();
        for (int i = 0; i < ports.length; i++)
        {
            System.out.print("\t" + Port.toString(i) + " : ");
            for (int j = 0; j < ports[i].length; j++)
                System.out.print("("+
                Fnc.conv1dto2d_x(ports[i][j], sizepp) +";"+
                Fnc.conv1dto2d_y(ports[i][j], sizepp) +") "
                );

            System.out.println();
        }

        Utils.delim(10, '-', false);

        for (Player p: engine.getState().getPlayers()) {
            System.out.println(p + " has :");
            
            System.out.println("\tpoints: " + p.getRessource(Ressource.POINT));

            System.out.print("\troad H : ");
            for (int coord: p.getRoadH())
                System.out.print(
                    "("+
                    Fnc.conv1dto2d_x(coord, size)+
                    ", "+
                    Fnc.conv1dto2d_y(coord, size)+
                    "), ");
            System.out.println();

            System.out.print("\troad V : ");
            for (int coord: p.getRoadV())
                System.out.print(
                    "("+
                    Fnc.conv1dto2d_x(coord, size)+
                    ", "+
                    Fnc.conv1dto2d_y(coord, size)+
                    "), ");
            System.out.println();

            System.out.print("\tcolonies : ");
            for (int coord: p.getColonies())
                System.out.print(
                    "("+
                    Fnc.conv1dto2d_x(coord, size)+
                    ", "+
                    Fnc.conv1dto2d_y(coord, size)+
                    "), ");
            System.out.println();

            System.out.print("\tcities : ");
            for (int coord: p.getCities())
                System.out.print(
                    "("+
                    Fnc.conv1dto2d_x(coord, size)+
                    ", "+
                    Fnc.conv1dto2d_y(coord, size)+
                    "), ");
            System.out.println();

            System.out.print("\tdices indexes : ");
            for (int i: p.getDicesIdx())
                System.out.print(i+ ", ");
            System.out.println();

            System.out.println();
        }
    }

    public void showInventory(int who) {
        Player player = engine.getState().getPlayer(who);

        System.out.println("Inventory of " + player + " :");

        // show cards
        int[] developpements = player.getDeveloppements();
        for (int i = 0; i < Developpement.nDeveloppements; i++)
            if (player.hasDeveloppement(i))
                System.out.println(" - " + developpements[i] + "x" + Utils.makeFirstWord(Developpement.toString(i))+" (code="+i+")");
        System.out.println();

        System.out.println("Ressources of " + player + " :");
        int[] ressources = player.getRessources();
        for (int i = 0; i < Ressource.nRessources; i++)
            if (ressources[i] > 0)
                System.out.println(" - " + ressources[i] + "x" + Utils.makeFirstWord(Ressource.toString(i))+" (code="+i+")");
        System.out.println();


    }

    public void metadata() {
        Utils.delim();
        System.out.println("List of Cards      : " + Developpement.resume());
        System.out.println("List of Biomes     : " + Biome.resume());
        System.out.println("List of Ressources : " + Ressource.resume());
        Utils.delim();
    }

    // ------------------------
    
    public void showMap() {
        int[][] dices = engine.getMap().getDiceIndexes();
        int[][] biomes = engine.getMap().getBiomes();

        System.out.println("Map resume :");
        System.out.println();

        System.out.print("\t");
        for (int x = 0; x < dices[0].length; x++)
        {
            System.out.print(x);
            System.out.print("\t");
            System.out.print("");
            System.out.print("\t| ");
        }
        System.out.println();

        System.out.print("\t");
        for (int x = 0; x < dices[0].length; x++)
        {
            System.out.print("--");
            System.out.print("\t");
            System.out.print("");
            System.out.print("\t| ");
        }
        System.out.println();

        for (int y = 0; y < dices[0].length; y++) {
            //System.out.print((char)((int)'A'+y)+"|");
            System.out.print(y+"|");
            System.out.print("\t");

            for (int x = 0; x < dices[0].length; x++)
            {
                System.out.print(dices[x][y]);
                System.out.print("\t");
                System.out.print(game.constants.Biome.toString(biomes[x][y]));
                System.out.print("\t| ");
            }

            System.out.println();
        }

        System.out.println();
    }
}
