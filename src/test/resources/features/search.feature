Feature: Functions of Campus Coffee
  Scenario: Compare Prices
    Given there are no registered CoffeShops
    When a CoffeShop with Name "Crazy Sheep",Location "Uni" and returnpoint true and "Cappuccino" Price 5 is added to the System
    And a CoffeShop with Name "Cafeteria",Location "Uni" and returnpoint true and "Cappuccino" Price 4 is added to the System
    And the price for "Cappuccino" gets compared for "Crazy Sheep" and "Cafeteria"
    Then the System should show "Cafeteria" as the one with cheaper coffee

  Scenario: Register a new CoffeeShop
    Given there is no CoffeShop with Name "Java Jive" registered in the System
    When a CoffeShop with Name "Java Jive",Location "Uni" and returnpoint true is added to the System
    Then "Java Jive" should be registered as a CoffeShop

  Scenario: Search for CoffeeShops close to Uni
    Given there are no registered CoffeShops
    And a CoffeShop with Name "Crazy Sheep",Location "Uni" and returnpoint true is added to the System
    And a CoffeShop with Name "Cafeteria",Location "Uni" and returnpoint true is added to the System
    And a CoffeShop with Name "City Beans",Location "City" and returnpoint true is added to the System
    When the System searches for CoffeShops close to "Uni"
    Then the result should contain "Crazy Sheep" and "Cafeteria"
    And the result should not contain "City Beans"