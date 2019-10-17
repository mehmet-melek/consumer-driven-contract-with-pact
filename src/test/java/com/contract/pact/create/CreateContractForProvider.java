package com.contract.pact.create;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.contract.pact.util.ContractTestUtil;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class CreateContractForProvider {


    @Rule
    public PactProviderRuleMk2 pactProviderRuleMk2 = new PactProviderRuleMk2(
            "ProviderName",//Provider Application Name

            //Mock Server
            "localhost",
            8112,
            this);


    @Pact(consumer = "ConsumerName") // Consumer Application Name (Our application) - Consumer defined with @Pact annotation
    public RequestResponsePact createPactForServiceName(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");//Defined headers

        //Defined responses with PactDslJsonBody()
        DslPart expectedResultBodyWhenGetCustomerSearch = new PactDslJsonBody()
                .integerType("CustomerTypeId", 12311)
                .integerType("customerNumber", 2354454)
                .booleanType("isActive")
                .asBody();

        //Defined responses with PactDslJsonBody()
        DslPart expectedResultBodyWhenGetCustomerTypes = new PactDslJsonBody()
                .integerType("id",12311)
                .stringType("code", "P")
                .stringType("name", "PRIVATE")
                .asBody();

        return builder
                .uponReceiving("A request for all customers")
                .path("/customer/search")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(expectedResultBodyWhenGetCustomerSearch)
                //Response bodyies and headers used in return builder


                //Second Contract
                .uponReceiving("A request to customer types")
                .path("/customer-types")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(expectedResultBodyWhenGetCustomerTypes).toPact();
                //Response bodyies and headers used in return builder
        //  We can define more than one endpoint with .uponReceiving or .given
    }


    //Then we have to test beacuse contracts are created on test stage.
    //When we say test with  @PactVerification, the server we described above stands up(localhost:8112). İf we get localhost:8112/customer/search its return expectedResultBodyWhenGetCustomerSearch.If the test is successful, the contracts is create.
    @Test
    @PactVerification()
    public void pactVerification() {
        int customerNumber= (Integer) new ContractTestUtil(pactProviderRuleMk2.getPort()).getContractResponse("/customer/search",
                "customerNumber");
        assertTrue(customerNumber == 2354454);
        int id= (Integer) new ContractTestUtil(pactProviderRuleMk2.getPort()).getContractResponse("/customer-types", "id");
        assertTrue(id == 12311);
    }
}
