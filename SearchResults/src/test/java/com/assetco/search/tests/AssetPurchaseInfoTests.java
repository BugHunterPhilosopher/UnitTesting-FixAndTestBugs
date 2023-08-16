package com.assetco.search.tests;

import com.assetco.search.results.AssetPurchaseInfo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssetPurchaseInfoTests {

    @Test
    void storesAndReturnsValues() {
        var timesShown = Any.anyLong();
        long timesPurchased = Any.anyLong();
        var totalRevenue = Any.money();
        var royaltiesOwed = Any.money();

        var info = new AssetPurchaseInfo(timesShown, timesPurchased, totalRevenue, royaltiesOwed);

        assertEquals(timesShown, info.getTimesShown());
        assertEquals(timesPurchased, info.getTimesPurchased());
        Assertions.assertEquals(totalRevenue, info.getTotalRevenue());
        Assertions.assertEquals(royaltiesOwed, info.getTotalRoyaltiesOwed());
    }
}
