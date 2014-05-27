package terd.web;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.*;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class cerrar_sesion extends SeleneseTestCase {
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		Selenium selenium = new WebDriverBackedSelenium(driver, "http://127.0.0.1:8888/UltimateTicTacToeWeb.html?gwt.codesvr=127.0.0.1:9997");
//
//		Selenium selenium = new DefaultSelenium(
//				"localhost", 4444, "*firefox", "http://127.0.0.1:8888/UltimateTicTacToeWeb.html?gwt.codesvr=127.0.0.1:9997");
////		Selenium selenium = new DefaultSelenium(
////			    "localhost", 4444, "*firefox", "http://www.yoursite.com"); 
//		selenium.start(); 
		setUp("http://127.0.0.1:8888", "*chrome");
	}
	public void testCerrar_sesion() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html?gwt.codesvr=127.0.0.1:9997");
		selenium.type("css=input.gwt-TextBox", "pepe");
		selenium.type("css=input.gwt-PasswordTextBox", "pepe");
		selenium.click("css=#sendButtonContainer > button.myButton");
		selenium.click("css=button.myButton");
	}
}
