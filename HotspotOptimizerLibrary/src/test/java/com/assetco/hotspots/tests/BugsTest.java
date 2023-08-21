package com.assetco.hotspots.tests;

import com.assetco.hotspots.optimization.SearchResultHotspotOptimizer;
import com.assetco.search.results.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BugsTest {

  private SearchResults searchResults;
  private Asset disrupting;
  private Asset missing;
  private List<Asset> expected;
  private AssetVendor partnerVendor;

  @BeforeEach
  void setUp() {
    partnerVendor = makeVendor("BigShotz!");
    missing = givenAssetInResultsWithVendor(partnerVendor);

    AssetVendor disruptingAssetVendor = makeVendor("Celeb Pix");
    disrupting = givenAssetInResultsWithVendor(disruptingAssetVendor);

    this.searchResults = new SearchResults();
    this.searchResults.addFound(missing);
    this.searchResults.addFound(disrupting);
  }

  @Test
  void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
    // Given
    expected = givenFourAssetsFromPartnerVendor(partnerVendor);

    var allAssets = new ArrayList<>(expected);
    allAssets.add(missing);

    // When
    whenOptimize();

    // Then
    thenHotspotDoesNotHave(disrupting);
    thenHotspotHasExactly(allAssets);
  }

  private AssetVendor makeVendor(final String vendorName) {
    return new AssetVendor(Any.string(), vendorName, AssetVendorRelationshipLevel.Partner, Any.anyLong());
  }

  private Asset givenAssetInResultsWithVendor(AssetVendor partnerVendor) {
    return new Asset(Any.string(), Any.string(), Any.URI(), Any.URI(), Any.assetPurchaseInfo(), Any.assetPurchaseInfo(), Any.setOfTopics(), partnerVendor);
  }

  private List<Asset> givenFourAssetsFromPartnerVendor(AssetVendor partnerVendor) {
    return List.of(
            givenAssetInResultsWithVendor(partnerVendor),
            givenAssetInResultsWithVendor(partnerVendor),
            givenAssetInResultsWithVendor(partnerVendor),
            givenAssetInResultsWithVendor(partnerVendor)
    );
  }

  private void whenOptimize() {
    expected.forEach(asset -> this.searchResults.addFound(asset));

    var optimizer = new SearchResultHotspotOptimizer();
    optimizer.optimize(this.searchResults);
  }

  private void thenHotspotDoesNotHave(Asset asset) {
    assertFalse(
            this.searchResults.getHotspot(HotspotKey.Showcase).getMembers().contains(asset)
    );
  }

  private void thenHotspotHasExactly(List<Asset> expected) {
    assertEquals(
            new HashSet<>(expected),
            new HashSet<>(this.searchResults.getHotspot(HotspotKey.Showcase).getMembers()),
            "Both sets should be equal!");
  }

}
