package com.cordapp.underwriting.flows.underwritingRequest;

import co.paralleluniverse.fibers.Suspendable;
import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import com.cordapp.underwriting.model.UnderwritingRequestType;
import com.cordapp.underwriting.states.UnderwritingRequestState;
import net.corda.core.contracts.CommandData;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import javax.annotation.Signed;
import java.util.Collections;
import java.util.Date;

// ******************
// * Initiator flow *
// flow from insurace to NHO
// ******************


@InitiatingFlow
@StartableByRPC
public class UnderwritingDataRequestInitiator extends FlowLogic<SignedTransaction> {

    private final ProgressTracker progressTracker = new ProgressTracker();

    private final Party requestingTo;
    private final long ssn;
    private final String requestType;

    public UnderwritingDataRequestInitiator(Party requestingTo, long ssn, String requestType) {
        this.requestingTo = requestingTo;
        this.ssn = ssn;
        this.requestType = requestType;
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {
        // we choose the notary
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        // We get our own identity
        Party issuer = getOurIdentity();

        // We create out inputstate for this transaction
        UnderwritingRequestState underwritingRequestState = new UnderwritingRequestState(ssn, UnderwritingRequestType.valueOf(requestType) , new Date(), issuer, requestingTo);
        CommandData underwritingRequestCommand = new UnderwritingRequestContract.Commands.UnderwritingRequest();

        //Build our Transation
        TransactionBuilder transactionBuilder = new TransactionBuilder(notary);

        transactionBuilder.addCommand(underwritingRequestCommand, issuer.getOwningKey(), requestingTo.getOwningKey());
        transactionBuilder.addOutputState(underwritingRequestState, UnderwritingRequestContract.ID);

        //Check if the transcation meets all the requirement from the contract
        transactionBuilder.verify(getServiceHub());

        FlowSession session = initiateFlow(requestingTo);

        //we sign the transaction with our private  key, making  it immutable
        SignedTransaction signedTransaction =  getServiceHub().signInitialTransaction(transactionBuilder);

        //the Counterparty signs the trancation
        SignedTransaction fullySignedTransaction = subFlow(new CollectSignaturesFlow(signedTransaction, Collections.singletonList(session)));

        return subFlow(new FinalityFlow(fullySignedTransaction, Collections.singletonList(session)));
    }

}
