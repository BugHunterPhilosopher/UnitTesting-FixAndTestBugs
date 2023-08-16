package com.assetco.hotspots.tests;

import com.assetco.hotspots.optimization.SearchResultHotspotOptimizer;
import com.assetco.search.results.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BugsTest {

  private SearchResults searchResults;
  private Asset disrupting;
  private Asset missing;
  private List<Asset> expected;

  @Test
  void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
    // Given
    AssetVendor partnerVendor = makeVendor("BigShotz!");
    missing = givenAssetInResultsWithVendor(partnerVendor);

    AssetVendor disruptingAssetVendor = makeVendor("Celeb Pix");
    disrupting = givenAssetInResultsWithVendor(disruptingAssetVendor);
    expected = givenFourAssetsFromPartnerVendor(partnerVendor);

    var allAssets = new ArrayList<>(expected);
    allAssets.add(missing);
    allAssets.add(disrupting);

    // When
    whenOptimize();

    // Then
    thenHotspotDoesNotHave(missing);
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
    this.searchResults = new SearchResults();
    this.searchResults.addFound(missing);
    this.searchResults.addFound(disrupting);
    expected.forEach(asset -> this.searchResults.addFound(asset));

    new SearchResultHotspotOptimizer().optimize(this.searchResults);
  }

  private void thenHotspotDoesNotHave(Asset asset) {
    assertTrue(
            this.searchResults.getHotspot(HotspotKey.Showcase).getMembers().stream()
                    .noneMatch(asset1 -> asset1.equals(asset))
    );
  }

  private void thenHotspotHasExactly(List<Asset> expected) {
    assertArrayEquals(
            expected.toArray(),
            this.searchResults.getHotspot(HotspotKey.Showcase).getMembers().toArray(),
            "Both arrays should be equal!");
  }

}
