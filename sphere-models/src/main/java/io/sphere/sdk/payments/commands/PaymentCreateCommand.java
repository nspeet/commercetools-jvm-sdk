package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.PaymentDraft;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;


public interface PaymentCreateCommand extends CreateCommand<Payment>, MetaModelExpansionDsl<Payment, PaymentCreateCommand, PaymentExpansionModel<Payment>> {

    static PaymentCreateCommand of(final PaymentDraft PaymentDraft) {
        return new PaymentCreateCommandImpl(PaymentDraft);
    }
}
