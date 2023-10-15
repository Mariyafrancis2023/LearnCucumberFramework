@tag
Feature: View Stores
   As a user I should be able to view the different stores

  @viewAllStores
  Scenario: Get All Stores
    Given store API is available
    When I invoke stores api with get method
    Then the response code should be 200
  
  @viewSingleStoreDetails 
  Scenario: Get a Store using store id
    Given store API is available
    When I invoke "stores/{id}" api with get method
    Then the response code should be 200
  
  @viewStoreDetailsUsingLimit
  Scenario Outline:  Invoke the store API with limit
		Given store API is available
		When I invoke stores api with get method and <limit>
 		Then the response code should be 200
 	Examples:
 	|limit|
 	|1|
 	|4|
 	|10|