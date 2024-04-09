package com.asm2_ktnc;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewTest3 {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		WebDriverManager.edgedriver().setup();
	}

	@Test
	public void login() throws InterruptedException {
		EdgeOptions options = new EdgeOptions();
		options.addExtensions(new File("D:\\New folder\\asm2-ktnc\\asm2-ktnc\\My-Fpoly-Extension.crx"));
		driver = new EdgeDriver(options);
		Actions action = new Actions(driver);
		String url = "chrome://extensions";
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(2000);
		WebElement toggle = driver.findElement(By.className("c01440"));
		action.moveToElement(toggle);
		action.click();
		action.perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("https://ap.poly.edu.vn/login");
		String newTabUrl = "https://ap.poly.edu.vn/login";
		String originalHandle = driver.getWindowHandle();
		((EdgeDriver) driver).executeScript("window.open('" + newTabUrl + "', '_blank');");
		Thread.sleep(5000);
		driver.get("https://ap.poly.edu.vn/login");
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
		driver.get("https://ap.poly.edu.vn/login");
		Thread.sleep(3000);

		WebElement findButton = driver.findElement(By.className("topBarButton"));
		findButton.click();
		Thread.sleep(1000);
		WebElement caoDangButton = findButton.findElement(By.xpath("//span[contains(text(), 'Cao đẳng')]"));
		caoDangButton.click();
		Thread.sleep(4000);

		WebElement findEmail = driver.findElement(By.id("identifierId"));
		findEmail.sendKeys("namhhps31884@fpt.edu.vn");
		Thread.sleep(1000);
		WebElement findNext = driver.findElement(By.xpath("//span[text()='Next']"));
		findNext.click();
		Thread.sleep(2000);

		// Google password
		WebElement findPassword = driver.findElement(By.name("Passwd"));
		findPassword.sendKeys("HuynhHaoNam1204@");
		Thread.sleep(1000);
		WebElement findNext1 = driver.findElement(By.xpath("//span[text()='Next']"));
		findNext1.click();
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		WebElement findContinue = wait1
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Continue']")));
		findContinue.click();
		Thread.sleep(1000);

		// Đăng nhập thành công
		WebElement findClose = driver.findElement(By.className("close"));
	}
}