package organizationModule;

import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import BaseClassUtility.BaseClass;
import GenericUtilities.ExcelFileUtility;
import GenericUtilities.JavaUtility;
import GenericUtilities.UtilityObjectClass;
import GenericUtilities.WebDriverUtility;
import POMUtilities.CreateOrgPompage;
import POMUtilities.HomePompage;
import POMUtilities.OrgInfoPompage;
import POMUtilities.OrgPompage;

//changes from eclipse to git
//changes from git to eclilpse
@Listeners(GenericUtilities.ListernersUtility.class)
public class OrgScenariosTest extends BaseClass {

	
	@Test(groups = "smoke", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void createOrgTest() throws IOException, InterruptedException {

		UtilityObjectClass.getStest().log(Status.INFO, " Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		UtilityObjectClass.getStest().log(Status.INFO, " Get the random no");
		int randomint = jutil.generateRandomNo();

		UtilityObjectClass.getStest().log(Status.INFO, " Fetch data from excel utility");
		String orgname = exutil.fetchDataFromExcelFile("organization", 1, 2) + randomint;

		HomePompage home = new HomePompage(driver);

		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate homepage using soft assert");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Identify plus icon and click on it");
		OrgPompage org = new OrgPompage(driver);
		org.getPlusicon();

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org TF and enter orgname");
		CreateOrgPompage createOrg = new CreateOrgPompage(driver);
		createOrg.getOrgnameTF(orgname);

		UtilityObjectClass.getStest().log(Status.INFO, " Identify save btn and click on it");
		createOrg.getSaveBtn();

		OrgInfoPompage orginfo = new OrgInfoPompage(driver);

		String verifyorg = orginfo.getVerifyOrg();

		Assert.assertEquals(verifyorg, orgname, "Verified org name failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate org name Using HardAssert");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Delete the org");
		driver.findElement(By.xpath(
				"//a[text()='" + orgname + "' and contains(@href,'Marketing&')]/../../descendant::a[text()='del']"))
				.click();
		Thread.sleep(2000);

		UtilityObjectClass.getStest().log(Status.INFO, " Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		soft.assertAll();
	}

	@Test(groups = "reg", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void OrgWithIndAndTypeTest() throws IOException, InterruptedException {

		UtilityObjectClass.getStest().log(Status.INFO, " Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		UtilityObjectClass.getStest().log(Status.INFO, " Get the random no");
		int randomint = jutil.generateRandomNo();

		UtilityObjectClass.getStest().log(Status.INFO, " Fetch data from excel utility");
		String orgname = exutil.fetchDataFromExcelFile("organization", 4, 2) + randomint;
		String industry = exutil.fetchDataFromExcelFile("organization", 4, 3);
		String type = exutil.fetchDataFromExcelFile("organization", 4, 4);

		HomePompage home = new HomePompage(driver);

		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate homepage using soft assert");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Identify plus icon and click on it");
		OrgPompage org = new OrgPompage(driver);
		org.getPlusicon();
		
		UtilityObjectClass.getStest().log(Status.INFO, " Identify org TF and enter orgname");
		CreateOrgPompage createOrg = new CreateOrgPompage(driver);
		createOrg.getOrgnameTF(orgname);
		
		UtilityObjectClass.getStest().log(Status.INFO, " Identify industry DD and select the value");
		WebElement ind_dd = createOrg.getIndustryDD();
		wutil.selectDDByValue(ind_dd, industry);

		UtilityObjectClass.getStest().log(Status.INFO, " Identify type DD and select the value");
		WebElement type_dd = createOrg.getTypeDD();
		wutil.selectDDByValue(type_dd, type);

		UtilityObjectClass.getStest().log(Status.INFO, " Identify save btn and click on it");
		createOrg.getSaveBtn();

		OrgInfoPompage orginfo = new OrgInfoPompage(driver);
		String verifyorg = orginfo.getVerifyOrg();

		Assert.assertTrue(verifyorg.contains(orgname), "Verified Org name failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate org name Using Hard assert");

		String verifyInd = orginfo.getVerifyIndustry();
		Assert.assertEquals(verifyInd, industry, "Verified indutry failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Verify industry");

		String verifytype = orginfo.getVerifyType();
		Assert.assertEquals(verifytype, type, "Verified type failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Verify type");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Delete the org");
		driver.findElement(By.xpath(
				"//a[text()='" + orgname + "' and contains(@href,'Marketing&')]/../../descendant::a[text()='del']"))
				.click();
		Thread.sleep(2000);

		UtilityObjectClass.getStest().log(Status.INFO, " Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		soft.assertAll();
	}

	@Test(groups = "reg", retryAnalyzer = GenericUtilities.RetryAnalyserUtility.class)
	public void createOrgWithPhnoTest() throws InterruptedException, IOException {

		UtilityObjectClass.getStest().log(Status.INFO, " Creating instances");
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		UtilityObjectClass.getStest().log(Status.INFO, " Get the random no");
		int randomint = jutil.generateRandomNo();

		UtilityObjectClass.getStest().log(Status.INFO, " Fetch data from excel utility");
		String orgname = exutil.fetchDataFromExcelFile("organization", 7, 2) + randomint;
		String phno = exutil.fetchDataFromExcelFile("organization", 7, 3);

		HomePompage home = new HomePompage(driver);

		String acthomeheader = home.getHomeheader();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(acthomeheader, "Home");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate homepage using soft assert");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Identify plus icon and click on it");
		OrgPompage org = new OrgPompage(driver);
		org.getPlusicon();

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org TF and enter orgname");
		CreateOrgPompage createOrg = new CreateOrgPompage(driver);
		createOrg.getOrgnameTF(orgname);
		UtilityObjectClass.getStest().log(Status.INFO, " identify Phno Tf and pass the phno");
		createOrg.getPhnoTF(phno);
		UtilityObjectClass.getStest().log(Status.INFO, " Identify save btn and click on it");
		createOrg.getSaveBtn();

		OrgInfoPompage orginfo = new OrgInfoPompage(driver);

		String verifyorg = orginfo.getVerifyOrg();
		Assert.assertEquals(verifyorg, orgname, "Verified orgname failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate org name Using hard assert");

		String verifyphno = orginfo.getVerifyPhno();
		Assert.assertTrue(verifyphno.contains(phno), "Verified phno failed");
		UtilityObjectClass.getStest().log(Status.PASS, " Validate ph no");

		UtilityObjectClass.getStest().log(Status.INFO, " Identify org tab and click on it");
		home.getOrg_tab();

		UtilityObjectClass.getStest().log(Status.INFO, " Delete the org");
		driver.findElement(By.xpath(
				"//a[text()='" + orgname + "' and contains(@href,'Marketing&')]/../../descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		UtilityObjectClass.getStest().log(Status.INFO, " Handle alert popup");
		wutil.switchToAlert_ClickOK(driver);

		soft.assertAll();

	}
}
