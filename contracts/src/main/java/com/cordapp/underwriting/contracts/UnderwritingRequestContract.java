package com.cordapp.underwriting.contracts;

import net.corda.core.contracts.*;
import net.corda.core.transactions.LedgerTransaction;

import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import static net.corda.core.contracts.ContractsDSL.requireThat;

public class UnderwritingRequestContract implements Contract {

    // This is used to identify our contract when building a transaction.
    public static final String ID = "com.cordapp.underwriting.contracts.UnderwritingRequestContract";

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    @Override
    public void verify(LedgerTransaction tx) {
        CommandWithParties<Commands> command = requireSingleCommand(tx.getCommands(), UnderwritingRequestContract.Commands.class);

        List<StateAndRef<ContractState>> inputs = tx.getInputs();
        List<TransactionState<ContractState>> outputs = tx.getOutputs();
        if (command.getValue() instanceof Commands.UnderwritingRequest) {
            // Issuance verification logic.
            requireThat(req -> {
                req.using("Transaction must have input states.", !inputs.isEmpty());
                return null;
            });
            requireThat( req-> {
                req.using("Transaction should not have output states", outputs.isEmpty());
                return null;
            });
        } else if (command.getValue() instanceof Commands.UnderwritingResponse) {
            // Transfer verification logic.
            requireThat( res ->{
                res.using(" Transcation must have output states.", !outputs.isEmpty());
                return null;
            });
        } else{
            throw new IllegalArgumentException("Unrecognized command");
        }
    }

    // Used to indicate the transaction's intent.
    public interface Commands extends CommandData {
        class UnderwritingRequest implements UnderwritingRequestContract.Commands {} //
        class UnderwritingResponse implements UnderwritingRequestContract.Commands{} //
    }

}
