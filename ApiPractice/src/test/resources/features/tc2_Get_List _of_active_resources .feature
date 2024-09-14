Feature: Update the all resources to inactive status

  @active
  Scenario: Retrieve and update the list of resources to inactive
    Given there are at least 5 registered clients in the system
    When I send a GET request to find all active resources
    Then the response should have a status code of 200
    When I send a PUT request to update all retrieved resources to inactive
    Then the response should have a status code of 200
    And the response's body structure matches with resources list Json schema
