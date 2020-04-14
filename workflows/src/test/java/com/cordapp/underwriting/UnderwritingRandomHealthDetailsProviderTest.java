package com.cordapp.underwriting;

import com.cordapp.underwriting.flows.util.UnderwritingRandomHealthDetailsProvider;
import com.cordapp.underwriting.states.UnderwriterHealthDetails;
import org.junit.Assert;
import org.junit.Test;

public class UnderwritingRandomHealthDetailsProviderTest {

    @Test
    public void geneatedUnderwritingHealthDetailsShouldHaveSameSSN(){
        UnderwriterHealthDetails details = UnderwritingRandomHealthDetailsProvider.getRandomHealthDetails(1);
        Assert.assertEquals("same ssn", details.getSsn(), 1);
    }
}
