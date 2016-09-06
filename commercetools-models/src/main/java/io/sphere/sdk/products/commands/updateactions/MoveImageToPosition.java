package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 *  Moves an image to a new position within a product variant.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#moveImageToPositionBySku()}
 *  {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#moveImageToPositionByVariantId()}
 *
 *  @see ProductVariant#getImages()
 */
public final class MoveImageToPosition extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    private final String imageUrl;
    private final Integer position;
    @Nullable
    private final String sku;


    private MoveImageToPosition(final Integer position, final String imageUrl, @Nullable final Integer variantId, @Nullable final String sku) {
        super("moveImageToPosition");
        this.position = position;
        this.imageUrl = imageUrl;
        this.variantId = variantId;
        this.sku = sku;
    }

    public static MoveImageToPosition ofImageUrlAndVariantId(final String imageUrl, final Integer variantId, final Integer position) {
        return new MoveImageToPosition(position, imageUrl, variantId, null);
    }

    public static MoveImageToPosition ofImageUrlAndSku(final String imageUrl, final String sku, final Integer position) {
        return new MoveImageToPosition(position, imageUrl, null, sku);
    }

    public Integer getVariantId() {
        return variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPosition() {
        return position;
    }

    @Nullable
    public String getSku() {
        return sku;
    }
}
