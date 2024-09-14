Feature: Update and delete a client

  @active
  Scenario: update and delete a new client
    When I send a POST to create a new client
    And I retrieve the details of the new client
    And I update any client's "name" with the following value "lore"
    Then The response to the request should have a status code of 200
    And  the response's body structure matches with client Json schema
    And I delete the new client
    And I validate that the client is not in the system.
