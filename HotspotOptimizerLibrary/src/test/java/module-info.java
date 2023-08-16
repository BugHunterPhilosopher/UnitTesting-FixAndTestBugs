module HotspotOptimizerLibrary_test {
    opens com.assetco.hotspots.tests to org.junit.platform.commons, org.junit.jupiter.params, org.junit.jupiter.api;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.platform.commons;
    requires SearchResults_impl;
    requires HotspotOptimizerLibrary_impl;
    exports com.assetco.hotspots.tests;
}