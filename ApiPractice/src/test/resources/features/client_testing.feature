Feature: Client testing

  @active
  Scenario: update the phone number of the first user to match a specific name
    Given there are at least ten registered clients in the system
    When the client's phone number is updated to "1234567" for the first client that matches by "Laura"
    Then The response to the request should have a status code of 200
    And the response's body structure matches with client Json schema
    # Post-condition: Clean up the test data
    When I send a DELETE request to delete all clients
    Then the client list should be empty


  @active
  Scenario: update and delete a new client
    When I send a POST to create a new client
    And I retrieve the details of the new client
    And I update any client's "name" with the following value "lore"
    Then The response to the request should have a status code of 200
    And  the response's body structure matches with client Json schema
    And I delete the new client




