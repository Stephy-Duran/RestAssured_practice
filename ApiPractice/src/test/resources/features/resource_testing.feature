Feature: Resource testing

  #@active
  Scenario: Retrieve and update the list of active resources
    Given there are at least 5 registered clients in the system
    When I send a GET request to find all active resources
    Then the response should have a status code of 200
    #Post-condition
    When I send a PUT request to update all retrieved resources to inactive
    Then the response should have a status code of 200
    And the response's body structure matches with resources list Json schema


  #@active
  Scenario: Update the last created resource
    Given there are at least 15 registered clients in the system
    When I send a GET request to find the latest resource
    And I sent a PUT request to update all the parameters of this resource
    Then the response should have a status code of 200
    And the response's body structure matches with resources Json schema



