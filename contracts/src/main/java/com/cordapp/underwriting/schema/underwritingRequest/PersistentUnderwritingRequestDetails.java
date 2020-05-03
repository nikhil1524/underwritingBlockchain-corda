package com.cordapp.underwriting.schema.underwritingRequest;

import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name="UNDERWRITING_REQUEST")
public class PersistentUnderwritingRequestDetails extends PersistentState implements Serializable {

    //@Id private final UUID id;
    @Column private final Long ssn;
    @Column private final String requestType;
    @Column private final Date date;
    @Column private final String requester;
    @Column private final String requestingTo;
    @Column private final Character status;

    public PersistentUnderwritingRequestDetails(long ssn, String requestType, Date date, String requester, String requestingTo, Character status) {
       // this.id = UUID.randomUUID();
        this.ssn = ssn;
        this.requestType = requestType;
        this.date = date;
        this.requester = requester;
        this.requestingTo = requestingTo;
        this.status = status;
    }

    public PersistentUnderwritingRequestDetails(){
        //this.id = null;
        this.ssn = null;
        this.requestType = null;
        this.date = null;
        this.requester = null;
        this.requestingTo = null;
        this.status = null;
    }

//   public UUID getId() {
//        return id;
//    }

    public Long getSsn() {
        return ssn;
    }

    public String getRequestType() {
        return requestType;
    }

    public Date getDate() {
        return date;
    }

    public String getRequester() {
        return requester;
    }

    public String getRequestingTo() {
        return requestingTo;
    }

    public Character getStatus(){ return status; }
}
