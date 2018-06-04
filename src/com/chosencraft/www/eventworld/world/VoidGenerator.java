package com.chosencraft.www.eventworld.world;

import org.bukkit.Location;
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
    @Deprecated
    public short[][] generateExtBlockSections(World world, Random random, int chunkX, int chunkY, BiomeGrid biomeGrid )
    {
        short[][] result = new short[world.getMaxHeight() / CHUNK_SIZE][];

        return result;
    }


}
