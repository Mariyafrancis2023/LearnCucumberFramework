@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @tag1
  Scenario: Generate a random number using single range
    Given I provide range 100, 1000 and 1
    When I invoke the randomnumberapi
    Then status code is 200
    And it returns 1 randomnumber

