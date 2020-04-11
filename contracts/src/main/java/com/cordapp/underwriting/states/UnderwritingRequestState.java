package com.cordapp.underwriting.states;

import com.cordapp.underwriting.contracts.TemplateContract;
import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

// *********
// * State for UnderwritingRequesting*
// *********

@BelongsToContract(UnderwritingRequestContract.class)
public class UnderwritingRequestState implements ContractState {

    private final long ssn;
    private final String requestType;
    private final Date date;
    private final String requestingTo;

    private final Party requester;
    private final Party responder;

    public UnderwritingRequestState(long ssn, String requestType, Date date, String requestingTo, Party requester, Party responder) {
        this.ssn = ssn;
        this.requestType = requestType;
        this.date = date;
        this.requestingTo = requestingTo;
        this.requester = requester;
        this.responder = responder;
    }

    public long getSsn() {
        return ssn;
    }

    public String getRequestType() {
        return requestType;
    }

    public Date getDate() {
        return date;
    }

    public String getRequestingTo() {
        return requestingTo;
    }

    public Party getRequester() {
        return requester;
    }

    public Party getResponder() {
        return responder;
    }

    @Override
    public List<AbstractParty> getParticipants(){
        return Arrays.asList(requester, responder);
    }


}


