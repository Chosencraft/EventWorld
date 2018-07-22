package com.chosencraft.www.eventworld.world;

import com.chosencraft.www.eventworld.EventWorldMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator
{

    private int CHUNK_SIZE = 16;
    /**
     * Override to spawn a
     *
     * @param world World to change spawn location of
     * @param random Randomizer, unused
     * @return Returns spawn location
     */
    @Override
    public Location getFixedSpawnLocation(World world, Random random)
    {
        return new Location(world, 0, 100, 0 );
    }

    /**
     * Override to empty the block populator
     *
     * @param world World to empty
     * @return Returns empty block populator
     */
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        return new ArrayList<BlockPopulator>();
    }


    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
    {
        ChunkData chunk = createChunkData(world);
        for (int xCoord = 0; xCoord < 16; xCoord++)
        {
         for (int zCoord = 0; zCoord < 16; zCoord++)
         {
             for (int yCoord = 0;  yCoord < chunk.getMaxHeight(); yCoord++)
             {
                chunk.setBlock(xCoord,yCoord,zCoord, Material.AIR);
             }
         }
        }
        return chunk;
    }
}
