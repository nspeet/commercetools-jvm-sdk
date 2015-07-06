package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.RemoveDiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeReference;
import io.sphere.sdk.test.IntegrationTest;
import org.javamoney.moneta.function.MonetaryUtil;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartQueryTest extends IntegrationTest {
    @Test
    public void expandDiscountCodeReference() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = execute(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));

            final CartQuery query = CartQuery.of()
                    .withPredicate(m -> m.id().is(cart.getId()))
                    .withExpansionPaths(m -> m.discountCodes());
            final Cart loadedCart = execute(query).head().get();


            final DiscountCodeReference discountCodeReference = loadedCart.getDiscountCodes().get(0);
            assertThat(discountCodeReference.getDiscountCode()).isEqualTo(discountCode.toReference());
            assertThat(discountCodeReference.getDiscountCode().getObj()).isPresent();

            //clean up
            final Cart updatedCart = execute(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void byCustomerIdAndByCustomerEmail() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final CartQuery cartQuery = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1)
                    .withPredicate(
                            m -> m.customerId().is(customer.getId())
                                    .and(m.customerEmail().is(customer.getEmail())));
            final Cart loadedCart = execute(cartQuery
            ).head().get();
            assertThat(loadedCart.getCustomerId()).contains(customer.getId());
        });
    }

    @Test
    public void queryTotalPrice() throws Exception {
        withFilledCart(client(), cart -> {
            final long centAmount = cart.getTotalPrice().multiply(100).getNumber().longValueExact();
            final Cart loadedCart = execute(CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1)
                    .withPredicate(
                            m -> m.totalPrice().centAmount().isGreaterThan(centAmount - 1)
                                    .and(m.totalPrice().centAmount().isLessThan(centAmount + 1)
                                            .and(m.totalPrice().currencyCode().is(EUR))
                                    ))).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }
}