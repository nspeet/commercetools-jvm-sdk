package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZonedDateTime;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            final String sku = randomKey();
            final int quantityOnStock = 10;
            final ZonedDateTime expectedDelivery = tomorrowZonedDateTime();
            final int restockableInDays = 3;
            final InventoryEntryDraft inventoryEntryDraft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);

            final InventoryEntry inventoryEntry = execute(InventoryEntryCreateCommand.of(inventoryEntryDraft));

            assertThat(inventoryEntry.getSku()).isEqualTo(sku);
            assertThat(inventoryEntry.getQuantityOnStock()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getAvailableQuantity()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getExpectedDelivery()).contains(expectedDelivery);
            assertThat(inventoryEntry.getRestockableInDays()).contains(restockableInDays);
            assertThat(inventoryEntry.getSupplyChannel()).contains(channel.toReference());

            //delete
            final DeleteCommand<InventoryEntry> deleteCommand = InventoryDeleteCommand.of(inventoryEntry);
            final InventoryEntry deletedEntry = execute(deleteCommand);
        });
    }
}