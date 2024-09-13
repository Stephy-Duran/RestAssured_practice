package com.globant.academy.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features ="src/test/resources/features",
        glue = "com.globant.academy.stepDefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        tags = "@active"
)


public class Runner {

}
