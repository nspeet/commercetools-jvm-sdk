package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.CreateCommandImpl;

public class TaxCategoryCreateCommand extends CreateCommandImpl<TaxCategory, NewTaxCategory> {
    public TaxCategoryCreateCommand(final NewTaxCategory body) {
        super(body);
    }

    @Override
    protected String httpEndpoint() {
        return "/tax-categories";
    }

    @Override
    protected TypeReference<TaxCategory> typeReference() {
        return TaxCategory.typeReference();
    }
}
