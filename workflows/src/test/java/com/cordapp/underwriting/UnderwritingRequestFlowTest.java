package com.cordapp.underwriting;

import com.cordapp.underwriting.flows.underwritingRequest.UnderwritingDataRequestInitiator;
import com.cordapp.underwriting.model.UnderwritingRequestType;
import com.google.common.collect.ImmutableList;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.transactions.SignedTransaction;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import net.corda.testing.node.TestCordapp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnderwritingRequestFlowTest {

    private  final MockNetwork network = new MockNetwork(new MockNetworkParameters(ImmutableList.of(
            TestCordapp.findCordapp("com.cordapp.underwriting.contracts"),
            TestCordapp.findCordapp("com.cordapp.underwriting.flows.underwritingRequest")
    )));
    private  final StartedMockNode insuraceNode = network.createNode();
    private  final StartedMockNode nhoNode = network.createNode();

    @Before
    public void setUp(){
        network.runNetwork();
    }

    @After
    public void tearDown(){
        network.stopNodes();
    }


    //simple example test to test if the issue insurance flow only carries one output.
    @Test
    public void dummyTest() throws Exception {

        UnderwritingDataRequestInitiator flow = new UnderwritingDataRequestInitiator(nhoNode.getInfo().getLegalIdentities().get(0), 1234, UnderwritingRequestType.REQUEST_TYPE_HEALTH.getAction());
        CordaFuture<SignedTransaction> future = insuraceNode.startFlow(flow);
        network.runNetwork();
        SignedTransaction ptx = future.get();

        //assertion for single output
        Assert.assertEquals(1, ptx.getTx().getOutputStates().size());
        System.out.println(ptx.getTx().getOutputStates());
    }

}

