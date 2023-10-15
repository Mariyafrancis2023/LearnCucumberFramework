@tag
Feature: Create Issue APIs Tests
  I want Create Issue APIs Tests

	Background:
		When I invoke the CreateIssueMetadata API
		Then validate the response with schema "CreateIssueMetadataSchema.json"
		And the response code should be 200
		And I extract the projectId and issueTypeId

  @tag1
  Scenario: Test Create Issue APIs
 #   Then the response code for create issue api should be 200
 #   And  I extract the projectId and issueTypeId
 		When i invoke the createIssueAPI 
		Then the response code for create issue api should be 201
		And verify the story id is present in response 

  @tag2
  Scenario Outline: Test Create Issue APIs
		When I invoke the CreateIssueMetadata API
		Then the response code for create issue api should be 200
		And I extract the projectId and "<issueType>"
		When i invoke the createIssueAPI 
		Then the response code for create issue api should be 201
		And verify the story id is present in response 
 			Examples:
 			|issueType|
 			|Story|
			|Bug|
