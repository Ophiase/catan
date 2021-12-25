package cli.visuals;

import game.Engine;

public class Resume {

    private Engine engine;

    public Resume(Engine engine) {
        this.engine = engine;
    }

    // ------------------------
    
    public void showMap() {
        int[][] dices = engine.getMap().getDiceIndexes();
        int[][] biomes = engine.getMap().getBiomes();


        System.out.print("Map resume :");
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
            System.out.print((char)((int)'A'+y)+"|");
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
    }
}
