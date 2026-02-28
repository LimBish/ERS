Feature: Employee registration form workflow

  Scenario: Submit employee form with valid data from client workflow
    Given the employee registration client is authenticated with username "admin" and password "password123"
    When the client fills the employee form with:
      | name       | email                    | contactNumber | position          |
      | Alice Smith| alice.smith@example.com  | 9801234567    | Software Engineer |
    Then the employee form should be submitted successfully
    And the saved employee list should include the email "alice.smith@example.com"