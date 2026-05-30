package com.github.DUJA.SteelHorizon.steelhorizon.block;

public enum ConveyorTier {
    BASIC(1, 1.0f),
    ADVANCED(2, 2.5f),
    ELITE(3, 4.0f);

    public final int tierNumber;
    public final float speed;

    ConveyorTier(int tierNumber, float speed) {
        this.tierNumber = tierNumber;
        this.speed = speed;
    }
}
