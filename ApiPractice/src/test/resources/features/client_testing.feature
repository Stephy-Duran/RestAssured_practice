Feature: Client testing

  Scenario: update the phone number of the first user to match a specific name
    Given there are at least ten registered clients in the system
    When the client's phone number is updated to "1234567" for the first client that matches by "Laura"
    Then the status code of the request was 200
    And the response's body structure matches with client Json schema
    # Post-condition: Clean up the test data
    When I delete all clients
    Then the client list should be empty




