package com.asm2_ktnc;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewTest {
	public WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	HSSFWorkbook workbook;
	HSSFSheet worksheet;
	Map<String, Object[]> TestNGResults;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	@Test(description = "Opens web", priority = 1)
	public void openWeb() throws Exception{
		try {
			driver.get("https://ap.poly.edu.vn/login");
//			driver.manage().window().maximize();
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Widget containing a Cloudflare security challenge']")));
	        driver.findElement(By.cssSelector("input[type='checkbox']")).click();

			TestNGResults.put("2", new Object[] { 1d, "Open web AP", "Site gets opened", "Passed" });
		} catch (Exception e) {
			TestNGResults.put("2", new Object[] { 1d, "Open web AP", "Site gets opened", "Failed" });
			AssertJUnit.assertTrue(false);
		}
	}
	
	@Test(description = "Click login by mail fpt", priority = 2)
	public void clickLogin() throws Exception{
		try {
			Thread.sleep(5000);
			 // Nhấp vào nút "Login with @fpt.edu.vn" để mở dropdown
	        WebElement loginButton = driver.findElement(By.xpath("//button[contains(.,'Login with @fpt.edu.vn')]"));
	        loginButton.click();
	        
	        // Chờ đến khi dropdown được mở ra
	        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("loginDropDown")));
	        
	        WebElement caoDangButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[span[contains(text(),'Cao đẳng')]]")));
	        caoDangButton.click();
	        TestNGResults.put("3", new Object[] { 1d, "Click login button", "click", "Passed" });
		} catch (Exception e) {
			// TODO: handle exception
			TestNGResults.put("3", new Object[] { 1d, "Click login button", "click", "Failed" });
			AssertJUnit.assertTrue(false);
		}
	}
	

	
	
	
	


	@SuppressWarnings("deprecation")
	@BeforeClass(alwaysRun = true)
	public void suiteSetup() {
	    workbook = new HSSFWorkbook();
	    worksheet = workbook.createSheet("TestNG Result Summary");
	    TestNGResults = new LinkedHashMap<String, Object[]>();
	    // Thêm test result vào file excel ở cột header
	    TestNGResults.put("1", new Object[] { "Test Step No.", "Action", "Expected Output", "Actual Output" });
	    try {
	        // Lấy địa chỉ làm việc hiện tại và tải dữ liệu vào file
	        workingDir = System.getProperty("user.dir");
//	      
//	        WebDriverManager.chromedriver().setup();
	        
//	        ChromeOptions options = new ChromeOptions();
//	        options.addArguments("user-data-dir=C:\\Users\\ducti\\AppData\\Local\\Google\\Chrome\\User Data");
//	        options.addArguments("profile-directory=Profile 4");
//	        options.addExtensions(new File("D:\\New folder\\asm2-ktnc\\myfpl.crx"));
	        driver = new FirefoxDriver();
	        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // No Such Element Exception
	    } catch (Exception e) {
	        throw new IllegalStateException("Can't start the Chrome web driver", e);
	    }

	}


	@AfterClass
	public void suiteTearDown() {
		Set<String> keyset = TestNGResults.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = worksheet.createRow(rownum++);
			Object[] objArr = TestNGResults.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
			}
			try {
				FileOutputStream out = new FileOutputStream(new File("TestToExel.xls"));
				workbook.write(out);
				out.close();
				System.out.println("Successfully save Selenium Webdriver TestNG result to Excel File!!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		driver.close();
//		driver.quit();
	}

}
