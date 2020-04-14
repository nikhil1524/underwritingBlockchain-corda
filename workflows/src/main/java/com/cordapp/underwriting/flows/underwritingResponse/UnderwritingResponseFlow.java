package com.cordapp.underwriting.flows.underwritingResponse;

import co.paralleluniverse.fibers.Suspendable;
import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import com.cordapp.underwriting.flows.util.UnderwritingRandomHealthDetailsProvider;
import com.cordapp.underwriting.states.UnderwriterHealthDetails;
import com.cordapp.underwriting.states.UnderwritingRequestState;
import com.cordapp.underwriting.states.UnderwritingResponseNHOState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UnderwritingResponseFlow {

    @InitiatingFlow
    @StartableByRPC
    public static class UnderwritingResponseInitiator extends FlowLogic<SignedTransaction> {

        private final ProgressTracker progressTracker = new ProgressTracker();

        //private final Party respondingTo;
        private final long ssn;
        //private final String requestType;

        public UnderwritingResponseInitiator(long ssn) {
            this.ssn = ssn;

        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {
            // we choose the notary
            Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

            //We get our own identity
            Party responder = getOurIdentity();

            // Query the vault to fetch a list of all Insurance state, and filter the results based on the policyNumber
            // to fetch the desired Insurance state from the vault. This filtered state would be used as input to the
            // transaction.
            List<StateAndRef<UnderwritingRequestState>> underwritingRequestStateAndRefList = getServiceHub().getVaultService()
                    .queryBy(UnderwritingRequestState.class).getStates();

            StateAndRef<UnderwritingRequestState> underwritingRestStateAndRef = underwritingRequestStateAndRefList.stream().filter(underwringStateAndRef -> {
                UnderwritingRequestState insuranceState = underwringStateAndRef.getState().getData();
                return insuranceState.getSsn() == ssn ;
            }).findAny().orElseThrow(() -> new IllegalArgumentException("UnderwringRequest Not Found"));

            UnderwritingRequestState underwritingRequestState = underwritingRestStateAndRef.getState().getData();

            Party respondingTo = underwritingRequestState.getRequester();

            UnderwriterHealthDetails underwriterHealthDetails = UnderwritingRandomHealthDetailsProvider.getRandomHealthDetails(ssn);

            UnderwritingResponseNHOState underwritingResponseNHOState = new UnderwritingResponseNHOState(ssn,underwriterHealthDetails,new Date(),
                    responder, respondingTo);

            // Build the transaction.
            TransactionBuilder transactionBuilder = new TransactionBuilder(underwritingRestStateAndRef.getState().getNotary())
                    .addInputState(underwritingRestStateAndRef)
                    .addOutputState(underwritingResponseNHOState, UnderwritingRequestContract.ID)
                    .addCommand(new UnderwritingRequestContract.Commands.UnderwritingResponse(),
                            Collections.singletonList(getOurIdentity().getOwningKey()));

            // Verify the transaction
            transactionBuilder.verify(getServiceHub());

            // Sign the transaction
            SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(transactionBuilder);

            // Call finality Flow
            FlowSession counterpartySession = initiateFlow(respondingTo);

            return subFlow(new FinalityFlow(signedTransaction, Collections.singletonList(counterpartySession)));
        }
    }

    @InitiatedBy(UnderwritingResponseInitiator.class)
    public static class UnderwritingResponseResponder extends FlowLogic<SignedTransaction> {

        private FlowSession counterpartySession;

        public UnderwritingResponseResponder(FlowSession counterpartySession) {
            this.counterpartySession = counterpartySession;
        }

        @Override
        @Suspendable
        public SignedTransaction call() throws FlowException {
            return subFlow(new ReceiveFinalityFlow(counterpartySession));
        }

    }
}
