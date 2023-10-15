package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ConfigBaseURI;
import utils.TestContext;

import static io.restassured.RestAssured.*;

import org.junit.Assert;

public class ViewStoreStepDefinition {

	private TestContext context;
    
	public ViewStoreStepDefinition(TestContext context) {
		super();
		this.context = context;
	}
	
	@Given("store API is available")
	public void store_api_is_available() {
		System.out.println("Given method");
		baseURI = "http://localhost:3030/";
	}  

/*	@Given("store API is available")
	public void store_api_is_available() {
		String baseURI = ConfigBaseURI.getBaseURI("baseURI");
		System.out.println(" ********** baseURI is : " + baseURI);
	} */
	
	@When("I invoke stores api with get method")
	public void i_invoke_stores_api_with_get_method() {
		System.out.println("When Method");
		context.response = given().when().get("stores");
		given().when().log().all().get("stores").then().log().all();;
	}

	@Then("the response code should be {int}")
	public void the_response_code_should_be(Integer int1) {
		System.out.println("Then Method");	
	//	storeId = response.jsonPath().getInt("id");
		System.out.println("Context.response in ViewStoreStepDefinition : "+ context.response.asString());
		Assert.assertEquals(Long.toString(context.response.statusCode()), Long.toString(int1));
	}

	@When("I invoke {string} api with get method")
	public void i_invoke_api_with_get_method(String endpoint) {
		System.out.println("2nd When Method");
	//	response = given().log().all().when().get(endpoint,storeId);
		context.response = given().log().all().when().get(endpoint,4);
	}
	
	@When("I invoke stores api with get method and {int}")
	public void i_invoke_stores_api_with_get_method(int limit) {
		context.response = (Response) given().log().all().queryParam("$limit",limit).when().get("stores");
	}
}
