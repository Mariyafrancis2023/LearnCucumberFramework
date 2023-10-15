package stepDefinition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import model.createIssueRequest.CreateIssueRequest;
import model.createIssueResponses.CreateIssueMetadataResponse;
import model.createIssueResponses.Issuetype;
import utils.JsonReader;
import utils.TestContext;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CreateIssueApiStepDefinition {

    private TestContext context;
    
	public CreateIssueApiStepDefinition(TestContext context) {
		super();
		this.context = context;
	}
	
	Gson gson = new Gson();
	CreateIssueMetadataResponse createMetadataResponse;

	@When("I invoke the CreateIssueMetadata API")
	public void i_invoke_the_create_issue_metadata_api() {
		baseURI = "https://mariafp.atlassian.net";
		context.response = given().header("Authorization",
				"Basic ZnJhbmNpc21hcmlhcGFkYXlhdHR5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBoZENUMUJfMEtaMjV0Z21VU1JPdUF1bGgzcWdxT1lRWDdnc21vaUo4eURNX1N4WXAxTGlMdnRmVmVwa3N0alp2d2NiZWpVTlVaVlRqRGVBbmJHdGIwX3NMODZsUGJPb0M4ZmtQRnBpNHdXQUtROGUtcV9kWHl2bVdXTU0wc25hVHNXSUR4eFFrVFFSRkJ5LS1vaTQ2aDhWTFJBYWl6Ujk1U0FMWWhURDJmVWc9QkM4NEJGQkQ=")
				.contentType("application/json").get("/rest/api/3/issue/createmeta");
	}

	@Then("I extract the projectId and issueTypeId")
	public void i_extract_the_project_id_and_issue_type_id() {
		// Step 2 - Parse the response and store the values in CreateIssueMetadataResponse pojo class
		createMetadataResponse = context.response.as(CreateIssueMetadataResponse.class);
		
		// Step 3 - Extract the values from the pojo class
		context.projectId = createMetadataResponse.getProjects().get(0).getId();
		System.out.println("Project Id : " + context.projectId);

		List<Issuetype> issueTypes = createMetadataResponse.getProjects().get(0).getIssuetypes();
		for (Issuetype issuetype : issueTypes) {
			if (issuetype.getName().equals("Story")) {
				context.issueId = issuetype.getId();
			}
		}
		System.out.println("Issue Id : " + context.issueId);
	}
	
	@Then("I extract the projectId and {string}")
	public void i_extract_the_project_id_and_issue_type_id(String issueType) {
		createMetadataResponse = context.response.as(CreateIssueMetadataResponse.class);
		
		context.projectId = createMetadataResponse.getProjects().get(0).getId();
		System.out.println("Project Id : " + context.projectId);

		List<Issuetype> issueTypes = createMetadataResponse.getProjects().get(0).getIssuetypes();
		for (Issuetype issuetype : issueTypes) {
			if (issuetype.getName().equals(issueType)) {
				context.issueId = issuetype.getId();
			}
		}
		System.out.println("Issue Id : " + context.issueId);
	}
	
	@When("i invoke the createIssueAPI")
	public void i_invoke_the_create_issue_api() {   
		//Read the json file
		JSONObject jsonObject = JsonReader.readJsonFile("CreateIssue.json");
		
		//Convert this Json to a pojo class	
		CreateIssueRequest createIssueRequest = gson.fromJson(jsonObject.toString(), CreateIssueRequest.class);			
		
		//Set in the dynamic values
		createIssueRequest.getFields().getProject().setId(context.projectId);
		createIssueRequest.getFields().getIssuetype().setId(context.issueId);
		
		context.response = given().header("Authorization",
				"Basic ZnJhbmNpc21hcmlhcGFkYXlhdHR5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBoZENUMUJfMEtaMjV0Z21VU1JPdUF1bGgzcWdxT1lRWDdnc21vaUo4eURNX1N4WXAxTGlMdnRmVmVwa3N0alp2d2NiZWpVTlVaVlRqRGVBbmJHdGIwX3NMODZsUGJPb0M4ZmtQRnBpNHdXQUtROGUtcV9kWHl2bVdXTU0wc25hVHNXSUR4eFFrVFFSRkJ5LS1vaTQ2aDhWTFJBYWl6Ujk1U0FMWWhURDJmVWc9QkM4NEJGQkQ=")
				.contentType("application/json").body(createIssueRequest).log().all().post("/rest/api/3/issue");
	}
	
	@Then("verify the story id is present in response")
	public void verify_the_story_id_is_present_in_response() {
	   Assert.assertNotNull(context.response.body().jsonPath().getString("id"));
	}
	
	@Then("the response code for create issue api should be {int}")
	public void the_response_code_for_create_issue_api_should_be(Integer int1) {
	   System.out.println("Then Method");
	   Assert.assertEquals(Long.toString(context.response.statusCode()), Long.toString(int1));
	}
	
	@Then("validate the response with schema {string}")
	public void validateSchema(String schemaFileName) {	
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/data/"+schemaFileName)));
	}
}
