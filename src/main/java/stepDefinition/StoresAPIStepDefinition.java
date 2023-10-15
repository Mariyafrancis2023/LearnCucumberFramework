package stepDefinition;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.TestContext;

public class StoresAPIStepDefinition {

	private TestContext context;

	public StoresAPIStepDefinition(TestContext context) {
			super();
			this.context = context;
		}
	
	@Then("extract the storeId")
	public void extract_the_store_id() {
		context.storeId = context.response.body().jsonPath().getInt("id");
		System.out.println("store Id : " + context.storeId); 
	}

	@When("I invoke stores api with get method for single store")
	public void i_invoke_stores_api_with_get_method_for_single_store() {
		context.response = given().get("stores/{id}",context.storeId);
	}

	@When("I invoke stores api with delete method for single store")
	public void i_invoke_stores_api_with_delete_method_for_single_store() {
		context.response = given().delete("stores/{id}",context.storeId);
	}
}
