package stepDefinition;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpotifyApiStepDefinition {
	
	WebDriver wd;
	String accessToken;
	String authGrant;
	String refreshToken;
	
	@Before
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		authGrant = getAuthGrant();
		accessToken = getAccessToken();
	}

	@Given("spotify API is available")
	public String getAuthGrant(){
		wd = new ChromeDriver();

		wd.get(
				"https://accounts.spotify.com/authorize?response_type=code&client_id=cf74a910eaf349acb37fa5d207633621&redirect_uri=https://pivotcoachingacademy.ca/about-us");
		wd.manage().window().maximize();
		wd.findElement(By.id("login-username")).sendKeys("francismariapadayatty@gmail.com");
		wd.findElement(By.id("login-password")).sendKeys("Spotify2023");
		wd.findElement(By.id("login-button")).click();

		Wait<WebDriver> wait = new FluentWait<WebDriver>(wd).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		WebElement acceptBtn = wait.until(ExpectedConditions
				.elementToBeClickable(wd.findElement(By.cssSelector("button[data-testid='auth-accept']"))));
			   
		acceptBtn.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']")));

		System.out.println("Pivot page displayed :" + wd
				.findElement(
						By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']"))
				.isDisplayed());

		String url = wd.getCurrentUrl();
		authGrant = url.substring(url.indexOf("=") + 1).trim();
		return authGrant;
	}
	
	@When("I invoke spotify api with post method")
	public String getAccessToken() {

		Response response = (Response) RestAssured.given().header("Authorization",
				"Basic Y2Y3NGE5MTBlYWYzNDlhY2IzN2ZhNWQyMDc2MzM2MjE6ZTYzOTUxOTRjMDc4NGE4NGE0YjFkZDI2NmZhNWNkMDA=")
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "authorization_code")
				.formParam("code",authGrant)
				.formParam("redirect_uri", "https://pivotcoachingacademy.ca/about-us").log().all().when().
				post("https://accounts.spotify.com/api/token");

		assertEquals(200, response.statusCode());
		accessToken = response.jsonPath().getString("access_token");
		System.out.println(accessToken);
		refreshToken = response.jsonPath().getString("refresh_token");
		System.out.println(refreshToken);
		return accessToken;
	}

	@Test
	public void getPlaylist() {

		Response response = (Response) RestAssured.given().header("Authorization", "Bearer " + accessToken).log().all()
				.when().get("https://api.spotify.com/v1/playlists/37i9dQZEVXbMda2apknTqH");
		assertEquals(200, response.statusCode());
	} 

	@After
	public void afterTest() {
		wd.quit();
	}
}
