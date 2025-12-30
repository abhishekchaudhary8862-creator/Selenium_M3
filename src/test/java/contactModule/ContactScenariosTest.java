package contactModule;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import BaseClassUtility.BaseClass;
import GenericUtilities.ExcelFileUtility;
import GenericUtilities.JavaUtility;
import GenericUtilities.UtilityObjectClass;
import GenericUtilities.WebDriverUtility;
import POMUtilities.ContactInfoPompage;
import POMUtilities.ContactPompage;
import POMUtilities.CreateContactPompage;
import POMUtilities.CreateOrgPompage;
import POMUtilities.HomePompage;
import POMUtilities.OrgInfoPompage;
import POMUtilities.OrgPompage;

//@Listeners(GenericUtilities.ListernersUtility.class)
 
//adding comments 
public class ContactScenariosTest extends BaseClass {

	@Test(groups = "smoke", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void createConTest() throws IOException, InterruptedException {

		// Creating instances
		UtilityObjectClass.getStest().log(Status.INFO, "Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();

		// Get the random no
		UtilityObjectClass.getStest().log(Status.INFO, "Get the random no");
		int randomint = jutil.generateRandomNo();

		// Fetch data from excel utility
		UtilityObjectClass.getStest().log(Status.INFO, "Fetch data from excel utility");

		String conname = exutil.fetchDataFromExcelFile("contact", 1, 2) + randomint;

		HomePompage home = new HomePompage(driver);

		// Validate homepage using soft assert
		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, "Validation of homepage using soft assert passed");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Click on contact tab");
		home.getContact_tab();

		// Identify plus icon and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Click on contact plus icon");
		ContactPompage con = new ContactPompage(driver);
		con.getConplusicon();

		// Identify lastname TF and enter cont name
		UtilityObjectClass.getStest().log(Status.INFO, "Enter contact name");
		CreateContactPompage createCon = new CreateContactPompage(driver);
		createCon.getLastnameTF(conname);

		// Identify save btn and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Click on save button");
		createCon.getSaveBtn();

		// Validate contact name
		ContactInfoPompage coninfo = new ContactInfoPompage(driver);
		String verifyconname = coninfo.getVerifyLastname();

		// Using hard assert and validating Contact name
		Assert.assertTrue(verifyconname.contains(conname), "Verified contact name failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Validation of contact using hard assert passed");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Click on contact tab");
		home.getContact_tab();

		// Identify delete link and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Delete the contact");
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		// Handle alert popup
		UtilityObjectClass.getStest().log(Status.INFO, "Handling alert popup");
		wutil.switchToAlert_ClickOK(driver);

		exutil.closeExcelFile();
		UtilityObjectClass.getStest().log(Status.INFO, "Closed Excel");

		soft.assertAll();
	}

	@Test(groups = "reg", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void conWithOrgTest() throws IOException, InterruptedException {

		// Creating instances
		UtilityObjectClass.getStest().log(Status.INFO, "Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		// Get the random no
		UtilityObjectClass.getStest().log(Status.INFO, "Get the random no");
		int randomint = jutil.generateRandomNo();

		// Fetch data from excel utility
		UtilityObjectClass.getStest().log(Status.INFO, "Fetch data from excel utility");
		String conname = exutil.fetchDataFromExcelFile("contact", 4, 2) + randomint;
		String orgname = exutil.fetchDataFromExcelFile("contact", 4, 3) + randomint;

		HomePompage home = new HomePompage(driver);

		// Validate homepage using soft assert
		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, "Validate homepage using soft assert");

		// Identify org tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify org tab and click on it");
		home.getOrg_tab();

		// Identify plus icon and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify plus icon and click on it");
		OrgPompage org = new OrgPompage(driver);
		org.getPlusicon();

		// Identify org TF and enter orgname
		UtilityObjectClass.getStest().log(Status.INFO, "Identify org TF and enter orgname");
		CreateOrgPompage createOrg = new CreateOrgPompage(driver);
		createOrg.getOrgnameTF(orgname);

		// Identify save btn and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify save btn and click on it");
		createOrg.getSaveBtn();

		// Validate org name
		OrgInfoPompage orginfo = new OrgInfoPompage(driver);
		String verifyorg = orginfo.getVerifyOrg();
		// Using Hard Assert validate orgname
		Assert.assertEquals(verifyorg, orgname, "Verified orgname failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Using Hard Assert validate orgname");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify contact tab and click on it");
		home.getContact_tab();

		// Identify plus icon and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify plus icon and click on it");
		ContactPompage con = new ContactPompage(driver);
		con.getConplusicon();

		// Identify lastname TF and enter cont name
		UtilityObjectClass.getStest().log(Status.INFO, "Identify lastname TF and enter cont name");
		CreateContactPompage createcon = new CreateContactPompage(driver);
		createcon.getLastnameTF(conname);

		// Identify org plus icon and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify org plus icon and click on it");
		createcon.getOrg_plusicon();

		// Fetch parent window id
		UtilityObjectClass.getStest().log(Status.INFO, "Fetch parent window id");
		String pwid = wutil.fetchTheParentWindowId(driver);

		// Switch the driver control to child window
		UtilityObjectClass.getStest().log(Status.INFO, "Switch the driver control to child window");
		wutil.switchToWindowUsingUrl(driver, "Accounts&action");

		// Search for org name
		UtilityObjectClass.getStest().log(Status.INFO, "Search for org name");
		createcon.getSearchTF(orgname);
		createcon.getSearchNowBtn();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		// Switch back to parent window
		UtilityObjectClass.getStest().log(Status.INFO, "Switch back to parent window");
		wutil.switchBackToParent(driver, pwid);

		// Identify save btn and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify save btn and click on it");
		createcon.getSaveBtn();

		// Validate contact name
		ContactInfoPompage coninfo = new ContactInfoPompage(driver);
		String verifyconname = coninfo.getVerifyLastname();
		// Using Hard Assert
		Assert.assertEquals(verifyconname, conname, "Verified contact name failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Validate contact name Using Hard Assert");

		// Validate org name in contact info
		String verifyorg_con = coninfo.getVerifyOrgname();
		// Using Hard Assert
		Assert.assertEquals(verifyorg_con, orgname, "Verified orgname in contact info failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Validate org name in contact info Using Hard Assert");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify contact tab and click on it");
		home.getContact_tab();

		// Identify delete link and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify delete link and click on it");
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		// Handle alert popup
		UtilityObjectClass.getStest().log(Status.INFO, "Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		// Identify org tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify org tab and click on it");
		home.getOrg_tab();

		// Delete the org
		UtilityObjectClass.getStest().log(Status.INFO, "Delete the org");
		driver.findElement(By.xpath(
				"//a[text()='" + orgname + "' and contains(@href,'Marketing&')]/../../descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		// Handle alert popup
		UtilityObjectClass.getStest().log(Status.INFO, "Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		soft.assertAll();
	}

	@Test(groups = "reg", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void conWithSuppDateTest() throws IOException, InterruptedException {

		// Creating instances
		UtilityObjectClass.getStest().log(Status.INFO, "Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		// Get the random no
		UtilityObjectClass.getStest().log(Status.INFO, "Get the random no");
		int randomint = jutil.generateRandomNo();

		// Fetch data from excel utility
		UtilityObjectClass.getStest().log(Status.INFO, "Fetch data from excel utility");
		String conname = exutil.fetchDataFromExcelFile("contact", 7, 2) + randomint;

		HomePompage home = new HomePompage(driver);

		// Validate homepage using soft assert
		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, "Validate homepage using soft assert");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify contact tab and click on it");
		home.getContact_tab();

		// Identify plus icon and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify plus icon and click on it");
		ContactPompage con = new ContactPompage(driver);
		con.getConplusicon();

		// Identify lastname TF and enter cont name
		UtilityObjectClass.getStest().log(Status.INFO, "Identify lastname TF and enter cont name");
		CreateContactPompage createCon = new CreateContactPompage(driver);
		createCon.getLastnameTF(conname);

		// Generate Todays date
		UtilityObjectClass.getStest().log(Status.INFO, "Generate Todays date");
		String startdate = jutil.fetchTodaysDate();
		Reporter.log(startdate, true);

		// Identify Start date TF and pass the date
		UtilityObjectClass.getStest().log(Status.INFO, "Identify Start date TF and pass the date");
		createCon.getSuppStartdateTF(startdate);

		// Generate date after 30 days
		UtilityObjectClass.getStest().log(Status.INFO, "Generate date after 30 days");
		String enddate = jutil.fetchDateAfterGivenDays(30);
		Reporter.log(enddate, true);

		// Identify End date TF and pass the date
		UtilityObjectClass.getStest().log(Status.INFO, "Identify End date TF and pass the date");
		createCon.getSuppEnddateTF(enddate);

		// Identify save btn and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify save btn and click on it");
		createCon.getSaveBtn();

		// Validate contact name
		ContactInfoPompage coninfo = new ContactInfoPompage(driver);
		String verifyconname = coninfo.getVerifyLastname();
		// Using Hard Assert
		Assert.assertTrue(verifyconname.contains(conname), "Verified contact name failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Validate contact name Using Hard assert");

		// Validate Start Date
		String verifyStartDate = coninfo.getVerifyStartdate();
		// Using Hard Assert
		Assert.assertTrue(verifyStartDate.contains(startdate), "Verified support start date failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Using Hard Assert Validating start date");

		// Validate End date
		String verifyenddate = coninfo.getVerifyEnddate();
		// Using Hard Assert
		Assert.assertTrue(verifyenddate.contains(enddate), "Verified support end date failed");
		UtilityObjectClass.getStest().log(Status.PASS, "Using Hard Assert Vlaidating Enddate");

		// Identify contact tab and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify contact tab and click on it");
		home.getContact_tab();

		// Identify delete link and click on it
		UtilityObjectClass.getStest().log(Status.INFO, "Identify delete link and click on it");
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		// Handle alert popup
		UtilityObjectClass.getStest().log(Status.INFO, "Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		soft.assertAll();
	}
}
