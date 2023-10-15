@tag
Feature: Generate Token
   As a user I should be able to generate token
   
   Scenario: Get Token
    Given spotify API is available
    When I invoke spotify Api with post method
    Then the response code for spotify Api should be 200
