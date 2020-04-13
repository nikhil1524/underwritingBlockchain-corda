package com.cordapp.underwriting.insurance.webserver;

import com.cordapp.underwriting.flows.underwritingRequest.UnderwritingDataRequestFlowInitiator;
import com.cordapp.underwriting.flows.underwritingResponse.UnderwritingResponseFlow;
import com.cordapp.underwriting.model.UnderwritingRequestType;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.NodeInfo;
import net.corda.core.transactions.SignedTransaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Define your API endpoints here.
 */


@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
public class Controller {
    private final CordaRPCOps proxy;
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(NodeRPCConnection rpc) {
        this.proxy = rpc.proxy;
    }

    @GetMapping(value="/networkMap", produces ="application/json")
    private String getNetworkMap(){
      List<NodeInfo> a = proxy.networkMapSnapshot();
        JSONArray array = new JSONArray();
        for(NodeInfo nodeInfo : a) {
            JSONObject object = new JSONObject();
            object.put("nodeAddress", nodeInfo.getAddresses().get(0).toString());
            object.put("nodeSerial", nodeInfo.getSerial());
            String nodeDetails = nodeInfo.getLegalIdentities().get(0).toString();
            String[] nodeString = nodeDetails.split(",");

            for(String str: nodeString){
                if(str.contains("O=")){
                    object.put("nodeName", str.substring(str.indexOf("=")+1));
                } else if(str.contains("L=")){
                    object.put("nodeLocation", str.substring(str.indexOf("=")+1));
                } else if(str.contains("C=")){
                    object.put("nodeCountry", str.substring(str.indexOf("=")+1));
                }
            }
          //  object.put("nodeDetails", );
            array.add(object);
        }
        return array.toJSONString();
    }

    @GetMapping(value = "/startRequest/{ssn}")
    private ResponseEntity<String> getUnderwritingDetailsForSSN(@PathVariable("ssn") String ssn){
        //O=InsuranceCompany,L=Bergen,C=NO"
        CordaX500Name insuraceCompanyName =CordaX500Name.parse("O=NorwayHealthOrganization,L=Oslo,C=NO");
        Party NHONode = proxy.wellKnownPartyFromX500Name(insuraceCompanyName);

        CordaFuture<SignedTransaction> future = proxy.startFlowDynamic(UnderwritingDataRequestFlowInitiator.class, NHONode,
                Long.valueOf(ssn).longValue(), UnderwritingRequestType.REQUEST_TYPE_HEALTH.getAction()).getReturnValue();

        try {
            SignedTransaction signedTransaction = future.get();

            return ResponseEntity.ok().body("Started the transaction with id"+ signedTransaction.getId());
        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/sendHealthDetails/{ssn}")
    private ResponseEntity<String> sendHealthDetailsForSSN(@PathVariable("ssn") String ssn){
        CordaFuture<SignedTransaction> future = proxy.startFlowDynamic(UnderwritingResponseFlow.UnderwritingResponseInitiator.class,
                Long.valueOf(ssn).longValue()).getReturnValue();
        try{
            SignedTransaction signedTransaction = future.get();
            return ResponseEntity.ok().body("Got the health Details for the user" + signedTransaction.getId());
        } catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

//    @GetMapping(value="/showRecievedUnderwritingDetails")
//    private ResponseEntity<String> showRecievedUnderwritingDetails(){
//
//        Vault.Page<UnderwritingResponseNHOState> a = proxy.vaultQuery(UnderwritingResponseNHOState.class);
//        return ResponseEntity.ok().build();
//    }
//


    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return proxy.networkMapSnapshot().toString();
    }

}