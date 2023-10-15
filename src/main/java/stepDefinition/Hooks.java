package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.TestContext;

import static io.restassured.RestAssured.*;

import org.junit.Assert;

public class Hooks {

	private TestContext context;

	public Hooks(TestContext testContext) {
		this.context = testContext;
	}

	@Before
	public void setup() {
		System.out.println("In Before scenario method");
	}

	@After
	public void tearDown() {
		System.out.println("In After scenario method");
	}

	@Before("@viewSingleStoreDetails")
	public void callMethodBeforeSingleStoreAPI() {
		System.out.println("In before @viewSingleStoreDetails scenario method");
	}

	@After("@Stores")
	public void deleteStoreAfterEachScenario() {
		System.out.println("In After delete stores scenario :  context store id " + context.storeId);
	//	given().log().all().delete("stores/{id}", context.storeId).then().log().all().statusCode(200);
	}
}
