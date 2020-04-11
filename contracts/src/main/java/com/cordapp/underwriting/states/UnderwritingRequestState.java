package com.cordapp.underwriting.states;

import com.cordapp.underwriting.contracts.TemplateContract;
import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import com.cordapp.underwriting.model.UnderwritingRequestType;
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
    private final UnderwritingRequestType requestType;
    private final Date date;

    private final Party requester;
    private final Party requestingTo;

    public UnderwritingRequestState(long ssn, UnderwritingRequestType requestType, Date date, Party requester, Party requestingTo) {
        this.ssn = ssn;
        this.requestType = requestType;
        this.date = date;
        this.requester = requester;
        this.requestingTo = requestingTo;
    }

    public long getSsn() {
        return ssn;
    }

    public UnderwritingRequestType getRequestType() {
        return requestType;
    }

    public Date getDate() {
        return date;
    }

    public Party getRequester() {
        return requester;
    }

    public Party getRequestingTo() {
        return requestingTo;
    }

    @Override
    public List<AbstractParty> getParticipants(){
        return Arrays.asList(requester, requestingTo);
    }


}


