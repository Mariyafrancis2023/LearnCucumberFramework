@tag
Feature: Create Stores
  As a user I should be able to create stores

  @createStoreUsingDataTable
  Scenario Outline: Create store using input values from example
    Given store API is available
    And create request for the method using following values
    |name|address|city|state|zip|
    |<name>|<address>|<city>|<state>|<zip>|
    When I invoke stores api with post method
	  Then the response code for create api should be 201
      Examples: 
      |name|address|city|state|zip|
      |BestBuy|243 City Centre|Brampton|ON|L5J 8R2|
      |BestBuyStore|657 Square One Centre|Mississauga|ON|M3N 3N5|
      
  @createStoreUsingJsonFile  
  Scenario: Create Store using input from json file
     Given store API is available
     And populate the request with json from "CreateStoreRequest.json"
     When I invoke stores api with post method
		 Then the response code for create api should be 201