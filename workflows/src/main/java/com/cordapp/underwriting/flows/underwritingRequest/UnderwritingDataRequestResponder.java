package com.cordapp.underwriting.flows.underwritingRequest;

import co.paralleluniverse.fibers.Suspendable;
import com.cordapp.underwriting.flows.underwritingRequest.UnderwritingDataRequestInitiator;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;
import org.jetbrains.annotations.NotNull;

@InitiatedBy(UnderwritingDataRequestInitiator.class)
public class UnderwritingDataRequestResponder extends FlowLogic<Void> {

    private final FlowSession otherSide;

    public UnderwritingDataRequestResponder(FlowSession otherSide) {
        this.otherSide = otherSide;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        SignedTransaction signedTransaction = subFlow(new SignTransactionFlow(otherSide) {
            @Suspendable
            @Override
            protected void checkTransaction(@NotNull SignedTransaction stx) throws FlowException {
                //Implement responder flow transaction checks here
            }
        });

        subFlow(new ReceiveFinalityFlow(otherSide, signedTransaction.getId()));
        return null;
    }
}
