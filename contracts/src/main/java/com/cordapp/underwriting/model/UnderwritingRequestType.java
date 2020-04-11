package com.cordapp.underwriting.model;

import net.corda.core.serialization.CordaSerializable;

@CordaSerializable
public enum UnderwritingRequestType {

    REQUEST_TYPE_HEALTH, REQUEST_TYPE_INSURANCE, REQUEST_TYPE_CREDIT_INFO
}
