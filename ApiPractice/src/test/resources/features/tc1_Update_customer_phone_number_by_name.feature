Feature: Update the phone number by name

  @active @PostConditionDeleteClients
  Scenario: update the phone number of the first user to match a specific name
    Given there are at least ten registered clients in the system
    When the client's phone number is updated to "1234567" for the first client that matches by "Laura"
    Then The response to the request should have a status code of 200
    And the response's body structure matches with client Json schema






