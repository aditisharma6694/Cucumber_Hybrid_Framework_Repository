package stepdefinition;

import java.util.Date;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import junit.framework.Assert;
import pages.AccountPage;
import pages.AccountSuccessPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.CommonUtils;

public class Register {

	WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private AccountPage accountPage;
	private RegisterPage registerPage;
	private AccountSuccessPage accountSuccessPage;
	
	@Given("User navigates to Register Account page")
	public void user_navigates_to_register_account_page() {
		driver = DriverFactory.getDriver();
		homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		registerPage = homePage.selectRegisterOption();   
	}

	@When("User enters the details into below fields")
	public void user_enters_the_details_into_below_fields(DataTable dataTable) {
	   Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
	   
	   //registerPage = new RegisterPage(driver);
	   registerPage.enterFirstnameField(dataMap.get("firstName"));
	   registerPage.enterLastnameField(dataMap.get("lastName"));
	   registerPage.enterEmailAddress(CommonUtils.getEmailWithTimeStamp());
	   registerPage.enterTelephone(dataMap.get("telephone"));
	   registerPage.enterPasswordField(dataMap.get("password"));
	   registerPage.enterConfirmPasswordField(dataMap.get("password"));
	}
	
	@When("User enters the details into below fields with duplicate email")
	public void user_enters_the_details_into_below_fields_with_duplicate_email(DataTable dataTable) {
	   Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
	   
	   //registerPage = new RegisterPage(driver);
	   registerPage.enterFirstnameField(dataMap.get("firstName"));
	   registerPage.enterLastnameField(dataMap.get("lastName"));
	   registerPage.enterEmailAddress(dataMap.get("email"));
	   registerPage.enterTelephone(dataMap.get("telephone"));
	   registerPage.enterPasswordField(dataMap.get("password"));
	   registerPage.enterConfirmPasswordField(dataMap.get("password"));
	}

	@When("User selects Privacy Policy")
	public void user_selects_privacy_policy() {
		
		//registerPage = new RegisterPage(driver);
		registerPage.selectPrivacyPolicyOption(); 
	}

	@When("User clicks on Continue button")
	public void user_clicks_on_continue_button() {
		
		//registerPage = new RegisterPage(driver);
		accountSuccessPage = registerPage.clickOncontinuebutton();
	}

	@Then("User account should get created successfully")
	public void user_account_should_get_created_successfully() {
		//accountSuccessPage = new AccountSuccessPage(driver);
		Assert.assertEquals("Your Account Has Been Created!", accountSuccessPage.getPageHeadingText());
	}

	@When("User selects Yes for Newsletter")
	public void user_selects_yes_for_newsletter() {
		//registerPage = new RegisterPage(driver);
		registerPage.selectYesNewsletterOption();
	}

	@Then("User should get a proper warning about duplicate email")
	public void user_should_get_a_proper_warning_about_duplicate_email() {
		//registerPage = new RegisterPage(driver);
		Assert.assertTrue(registerPage.getWarningMessageText().contains("Warning: E-Mail Address is already registered!"));
	}
	
	
	@When("User dont enter any details into fields")
	public void user_dont_enter_any_details_into_fields() {
		//registerPage = new RegisterPage(driver);
		//Intentionally kept blank
		
		registerPage.enterFirstnameField("");
		registerPage.enterLastnameField("");
		registerPage.enterEmailAddress("");
		registerPage.enterTelephone("");
		registerPage.enterPasswordField("");
		registerPage.enterConfirmPasswordField("");
		
	}

	@Then("User should get proper warning messages for every mandatory field")
	public void user_should_get_proper_warning_messages_for_every_mandatory_field() {
		//registerPage = new RegisterPage(driver);
		Assert.assertTrue(registerPage.getWarningMessageText().contains("Warning: You must agree to the Privacy Policy!"));
		Assert.assertEquals("First Name must be between 1 and 32 characters!", registerPage.getFirstNameWarning());
		Assert.assertEquals("Last Name must be between 1 and 32 characters!", registerPage.getLastNameWarning());
		Assert.assertEquals("E-Mail Address does not appear to be valid!", registerPage.getEmailWarning());
		Assert.assertEquals("Telephone must be between 3 and 32 characters!", registerPage.getTelephoneWarning());
		Assert.assertEquals("Password must be between 4 and 20 characters!", registerPage.getPasswordWarning());
			
	}
	
}
