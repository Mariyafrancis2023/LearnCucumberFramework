package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import org.junit.Assert;

public class RandomApiStepDefinition {

	Response response;
	RequestSpecification request;
	
	@Given("I provide {int}, {int} and {int}")
	public void provideInputValue(int min, int max, int count) {
		baseURI = "http://www.randomnumberapi.com/api/v1.0/random";
		request = given().queryParams("min",min, "max",max, "count",count);
	}
	
	@When("I invoke randomnumberapi")
	public void invokeApi() {
		response = request.given().when().log().all().get();
	}
	
	@Then("status code is {int}")
	public void verifyResponseCode(int code) {
		Assert.assertEquals(response.statusCode(), code);	
	}
	
	@Then("it returns {int} randomNumber")
	public void verifyResponseBody(int count) {
		
	}
}

