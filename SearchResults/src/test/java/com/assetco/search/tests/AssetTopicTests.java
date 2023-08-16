package com.assetco.search.tests;

import com.assetco.search.results.AssetTopic;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssetTopicTests {
    @Test
    void storesAndRetrievesValues() {
        var id = Any.string();
        var displayName = Any.string();

        var topic = new AssetTopic(id, displayName);

        assertEquals(id, topic.getId());
        assertEquals(displayName, topic.getDisplayName());
    }
}
