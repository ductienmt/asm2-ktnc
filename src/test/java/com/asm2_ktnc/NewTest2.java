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
import org.junit.experimental.theories.Theories;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewTest2 {
	public WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	HSSFWorkbook workbook;
	HSSFSheet worksheet;
	Map<String, Object[]> TestNGResults;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

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
//			WebDriverManager.chromedriver().setup();
//	        FirefoxOptions options = new FirefoxOptions();
//	        options.add
//			ChromeOptions options = new ChromeOptions();
//	        options.addArguments("user-data-dir=C:\\Users\\ducti\\AppData\\Local\\Google\\Chrome\\User Data");
//	        options.addArguments("profile-directory=Profile 4");
//			options.addExtensions(new File("D:\\New folder\\asm2-ktnc\\asm2-ktnc\\My-Fpoly-Extension.crx"));
//	        driver = new FirefoxDriver();
//			driver = new ChromeDriver(options);
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File("D:\\New folder\\asm2-ktnc\\asm2-ktnc\\My-Fpoly-Extension.crx"));
			driver = new ChromeDriver(options);
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
				FileOutputStream out = new FileOutputStream(new File("TestToExcel1.xls"));
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

	@Test(description = "Open Gmail", priority = 1)
	public void openGmail() throws Exception {
		try {
			driver.manage().window().maximize();
			Thread.sleep(2000);
			driver.get("https://www.google.com/intl/vi/gmail/about/");
			Thread.sleep(2000);
			WebElement button = driver
					.findElement(By.cssSelector("a.button--medium[data-action='sign in'][data-label='header']"));
			button.click();
			TestNGResults.put("2", new Object[] { 2d, "Open gmail", "open", "Passed" });
		} catch (Exception e) {
			TestNGResults.put("2", new Object[] { 2d, "Open gmail", "open", "Failed" });
			AssertJUnit.assertTrue(false);
			// TODO: handle exception
		}
	}

	@Test(description = "Fill username", priority = 2)
	public void fillUsername() throws Exception {
		try {
			Thread.sleep(2000);
			WebElement username = driver.findElement(By.name("identifier"));
			username.sendKeys("nhuthmps32162@fpt.edu.vn");
			Thread.sleep(1000);
			WebElement button = driver.findElement(By.xpath("//span[text()='Next']"));
			button.click();

			TestNGResults.put("3", new Object[] { 3d, "Click login username", "click", "Passed" });
		} catch (Exception e) {
			// TODO: handle exception
			TestNGResults.put("3", new Object[] { 3d, "Click login username", "click", "Failed" });
			AssertJUnit.assertTrue(false);
		}
	}

	@Test(description = "Fill pass", priority = 3)
	public void fillPass() throws Exception {
		try {
			Thread.sleep(2000);
			WebElement pass = driver.findElement(By.name("Passwd"));
			pass.sendKeys("hominhnhut123");
			Thread.sleep(1000);
			WebElement button = driver.findElement(By.xpath("//span[text()='Next']"));
			button.click();

			TestNGResults.put("4", new Object[] { 4d, "Click login pass", "click", "Passed" });
		} catch (Exception e) {
			// TODO: handle exception
			TestNGResults.put("4", new Object[] { 4d, "Click login pass", "click", "Failed" });
			AssertJUnit.assertTrue(false);
		}
	}

	@Test(description = "open chrome extension", priority = 4)
	public void openAP() throws Exception {
		boolean flag = true;
		try {
			Thread.sleep(20000);
			while (true) {
				// Lấy địa chỉ web hiện tại
				String currentUrl = driver.getCurrentUrl();
				System.out.println("Current URL: " + currentUrl);

				// So sánh với địa chỉ web cho trước
				if (currentUrl.equals("https://mail.google.com/mail/u/0/#inbox")) {
					flag = false;
//					String url = "chrome://extensions";
//					driver.get(url);
//					Actions action = new Actions(driver);
//					WebElement toggle = driver.findElement(By.className("circle toggle-ink"));
//					action.moveToElement(toggle);
//					action.click();
//					action.perform();
					String originalHandle = driver.getWindowHandle();
					// Mở tab mới và truy cập vào link ap.poly.edu.vn/login
					((ChromeDriver) driver).executeScript("window.open('https://ap.poly.edu.vn/login','_blank');");
					Thread.sleep(2000);
					for (String handle : driver.getWindowHandles()) {
						if (!handle.equals(originalHandle)) {
							driver.switchTo().window(handle);
							break;
						}
					}
					break;
				} else {
					// Trở về và đợi 2s
					flag = true;
					Thread.sleep(2000);
				}
			}
			TestNGResults.put("5", new Object[] { 5d, "Open chrome extension", "click", "Passed" });
		} catch (Exception e) {
			TestNGResults.put("5", new Object[] { 5d, "Open chrome extension", "click", "Failed" });
			AssertJUnit.assertTrue(false);
			// TODO: handle exception
		}
	}

	@Test(description = "open ap and Click login by mail fpt", priority = 5)
	public void clickLogin() throws Exception{
		try {
//			driver.get("https://ap.poly.edu.vn/login");
			
			
			

			

//			Thread.sleep(60000);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(span, 'Login with @fpt.edu.vn')]")));

	        // Nhấp vào nút "Login with @fpt.edu.vn"
	        WebElement loginButton = driver.findElement(By.xpath("//button[contains(span, 'Login with @fpt.edu.vn')]"));
	        loginButton.click();

	        // Chờ cho dropdown xuất hiện
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("loginDropDown")));
//	        System.out.println("tìm thấy");
	        Thread.sleep(2000);

	        // Nhấp vào nút "Cao đẳng" trong dropdown
	        WebElement caoDangButton = driver.findElement(By.xpath("//button[contains(span, 'Cao đẳng')]"));
	        caoDangButton.click();
//	        System.out.println("đã click");
	        
	        
	        TestNGResults.put("6", new Object[] { 6d, "Click login button", "click", "Passed" });
		} catch (Exception e) {
			// TODO: handle exception
			TestNGResults.put("6", new Object[] { 6d, "Click login button", "click", "Failed" });
			AssertJUnit.assertTrue(false);
		}
	}
	
	
	
	


}
