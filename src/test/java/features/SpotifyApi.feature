@tag
Feature: Generate Token
   As a user I should be able to generate token
   
   Scenario: Get Token
    Given spotify API is available
    When I invoke spotify api with post method
    Then the response code for spotify api should be 200
