package com.guddu1580.amplifiedworldmod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public
class AmplifiedWorldGenerator extends NoiseBasedChunkGenerator {
    private final NormalNoise amplificationNoise;
    private final double amplificationFactor;

    public AmplifiedWorldGenerator(BiomeSource biomeSource, NoiseGeneratorSettings noiseGeneratorSettings, double amplificationFactor) {
        super(biomeSource, noiseGeneratorSettings);
        this.amplificationNoise = NormalNoise.create(WorldgenRandom.newLegacy(), -3, 1.0);
        this.amplificationFactor = amplificationFactor;
    }

    @Override
    public void buildSurface(WorldgenRandom random, ChunkAccess chunkAccess, BiomeSource biomeSource, NoiseGeneratorSettings noiseGeneratorSettings, LevelHeightAccessor levelHeightAccessor) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int y = chunkAccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                mutableBlockPos.set(x, y, z);
                BlockState blockState = chunkAccess.getBlockState(mutableBlockPos);
                if (blockState.isAir()) {
                    double noiseValue = amplificationNoise.getValue(mutableBlockPos.getX(), mutableBlockPos.getZ(), false);
                    int amplifiedHeight = (int) (noiseValue * amplificationFactor);
                    for (int i = 0; i < amplifiedHeight; i++) {
                        mutableBlockPos.setY(y - i);
                        chunkAccess.setBlockState(mutableBlockPos, noiseGeneratorSettings.defaultBlock(), false);
                    }
                }
            }
        }
    }

    @Override
    public void applyCarvers(WorldgenRandom random, LevelHeightAccessor levelHeightAccessor, BiomeSource biomeSource, GenerationStep.Carving carving, BlockPos pos, ChunkAccess chunkAccess, ChunkGenerator.ChunkGeneratorSettings chunkGeneratorSettings) {
        // Custom carving logic can be added here
    }
}
