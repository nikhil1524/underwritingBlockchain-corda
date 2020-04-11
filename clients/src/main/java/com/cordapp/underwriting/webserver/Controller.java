package com.cordapp.underwriting.webserver;

import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.NodeInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.List;

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

    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return proxy.networkMapSnapshot().toString();
    }

}