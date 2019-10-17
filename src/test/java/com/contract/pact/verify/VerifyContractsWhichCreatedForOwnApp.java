package com.contract.pact.verify;


import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.contract.pact.PactApplication;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
@Provider("ProviderName") //Set up name of Own app
@Consumer("ConsumerName")// Set up name of the app who create contract for us
@PactBroker(port = "8113", host = "http://localhost", tags = "latest") //Contracts getting from here
public class VerifyContractsWhichCreatedForOwnApp {


    private static ConfigurableWebApplicationContext configurableWebApplicationContext;

    @BeforeClass
    public static void start() {
        configurableWebApplicationContext = (ConfigurableWebApplicationContext)
                SpringApplication.run(PactApplication.class);
    }

    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new HttpTarget(8080);

}
