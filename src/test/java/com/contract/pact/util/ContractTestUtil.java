package com.contract.pact.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ContractTestUtil {

    int port=8111;

    public ContractTestUtil(int port) {
        this.port=port;
        System.out.println("Custom port "+port);
    }

    public  Object getContractResponse(String path,String object) {
        try {
            System.setProperty("pact.rootDir", "./target/pacts");
            System.setProperty("pact.rootDir", "./target/pacts");

            String url=String.format("Http://localhost:%d"+path, port);
            System.out.println("using url: "+url);
            HttpResponse httpResponse = Request.Get(url).execute().returnResponse();
            String json = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("json="+json);
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.get(object);
        }
        catch (Exception e) {
            System.out.println("Unable to get object="+e);
            return null;
        }
    }
}
