package com.cordapp.underwriting;

import com.google.common.collect.ImmutableList;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.identity.CordaX500Name;
import net.corda.testing.core.TestIdentity;
import net.corda.testing.driver.DriverParameters;
import net.corda.testing.driver.NodeHandle;
import net.corda.testing.driver.NodeParameters;
import org.junit.Test;

import java.util.List;

import static net.corda.testing.driver.Driver.driver;
import static org.junit.Assert.assertEquals;

public class DriverBasedTest {
    private final TestIdentity insuraceCompanyA = new TestIdentity(new CordaX500Name("InsuraceCompany1", "", "NO"));
    private final TestIdentity norwayHealthOrganization = new TestIdentity(new CordaX500Name("NorwayHealthOrganization", "", "NO"));

    /*
     * This test case tries to up the 2 nodes and
     * then trying to get a test RPC call to each other
     * to check if the nodes are able to communicate with
     * each other
     *
     */
    @Test
    public void nodeTest() {
        driver(new DriverParameters().withIsDebug(true).withStartNodesInProcess(true), dsl -> {
            // Start a pair of nodes and wait for them both to be ready.
            List<CordaFuture<NodeHandle>> handleFutures = ImmutableList.of(
                    dsl.startNode(new NodeParameters().withProvidedName(insuraceCompanyA.getName())),
                    dsl.startNode(new NodeParameters().withProvidedName(norwayHealthOrganization.getName()))
            );

            try {
                NodeHandle partyAHandle = handleFutures.get(0).get();
                NodeHandle partyBHandle = handleFutures.get(1).get();

                // From each node, make an RPC call to retrieve another node's name from the network map, to verify that the
                // nodes have started and can communicate.

                // This is a very basic test: in practice tests would be starting flows, and verifying the states in the vault
                // and other important metrics to ensure that your CorDapp is working as intended.
                assertEquals(partyAHandle.getRpc().wellKnownPartyFromX500Name(norwayHealthOrganization.getName()).getName(), norwayHealthOrganization.getName());
                assertEquals(partyBHandle.getRpc().wellKnownPartyFromX500Name(insuraceCompanyA.getName()).getName(), insuraceCompanyA.getName());
            } catch (Exception e) {
                throw new RuntimeException("Caught exception during test: ", e);
            }

            return null;
        });
    }
}