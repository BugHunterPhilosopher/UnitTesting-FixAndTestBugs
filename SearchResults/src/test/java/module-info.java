module SearchResults_test {
    opens com.assetco.search.tests to org.junit.platform.commons, org.junit.jupiter.api;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.engine;
    requires SearchResults_impl;
    exports com.assetco.search.tests;
}