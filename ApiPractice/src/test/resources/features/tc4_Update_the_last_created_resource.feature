Feature: Update a resource

  @active
  Scenario: Update the last created resource
    Given there are at least 15 registered clients in the system
    When I sent a GET request to find the latest resource
    And I sent a PUT request to update all the parameters of this resource
    Then the response should have a status code of 200
    And the response's body structure matches with resources Json schema



