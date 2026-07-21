package org.campuscoffee.steps;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import jdk.javadoc.doclet.Taglet;
import org.campuscoffee.CoffeStore;
import org.campuscoffee.Search;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchSteps {
    Search search = new Search();
    CoffeStore output;

    @ParameterType("true|false")
    public Boolean booleanValue(String value) {
        return Boolean.parseBoolean(value);
    }

    @Before
    public void before() {
         search = new Search();
         output = null;
     }

    @Given("there are no registered CoffeShops")
    public void there_are_no_registered_CoffeShops() {
        search.clearStores();
        assertTrue(search.getAllStores().isEmpty());
    }
    @When("a CoffeShop with Name {string},Location {string} and returnpoint {booleanValue} and {string} Price {int} is added to the System")
    public void add_store_with_price(final String name, final String location, final boolean reuse, final String coffetype, final int price) {
        CoffeStore cs = new CoffeStore(name, org.campuscoffee.Location.valueOf(location.toUpperCase()),reuse);
        cs.setPrice(coffetype, price);
        search.addStore(cs);
    }
    @When("the price for {string} gets compared for {string} and {string}")
    public void comparePrices(final String coffetype, final String name1, final String name2) {
        output = search.compareByPrice(name1,name2,coffetype);
    }
    @Then("the System should show {string} as the one with cheaper coffee")
    public void checkOutput(final String storeName) {
        assertEquals(output.getName(), storeName);
    }

    @Given("there is no CoffeShop with Name {string} registered in the System")
    public void there_is_no_coffeshop_with_name(final String name) {
        boolean exists = search.getAllStores().stream().anyMatch(s -> s.getName().equals(name));
        assertFalse(exists);
    }

    @When("a CoffeShop with Name {string},Location {string} and returnpoint {booleanValue} is added to the System")
    public void add_store(final String name, final String location, final boolean reuse) {
        CoffeStore cs = new CoffeStore(name, org.campuscoffee.Location.valueOf(location.toUpperCase()), reuse);
        search.addStore(cs);
    }

    @Then("{string} should be registered as a CoffeShop")
    public void should_be_registered(final String name) {
        boolean exists = search.getAllStores().stream().anyMatch(s -> s.getName().equals(name));
        assertTrue(exists);
    }

    List<CoffeStore> results;

    @When("the System searches for CoffeShops close to {string}")
    public void search_close_to(final String location) {
        results = search.getStoresClose(org.campuscoffee.Location.valueOf(location.toUpperCase()));
    }

    @Then("the result should contain {string} and {string}")
    public void result_should_contain(final String name1, final String name2) {
        List<String> names = results.stream().map(CoffeStore::getName).toList();
        assertTrue(names.contains(name1));
        assertTrue(names.contains(name2));
    }

    @Then("the result should not contain {string}")
    public void result_should_not_contain(final String name) {
        List<String> names = results.stream().map(CoffeStore::getName).toList();
        assertFalse(names.contains(name));
    }

}
