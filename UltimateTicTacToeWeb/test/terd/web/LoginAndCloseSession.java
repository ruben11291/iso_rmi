package terd.web;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.*;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class LoginAndCloseSession extends SeleneseTestCase {
	public void setUp() throws Exception {

		setUp("http://127.0.0.1:8888/", "*chrome");

	}
	public void testCerrar_sesion() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=input.gwt-TextBox", "pepe");
		selenium.type("css=input.gwt-PasswordTextBox", "pepe");
		selenium.click("css=#sendButtonContainer > button.myButton");
		selenium.click("css=button.myButton");
		selenium.close();
	}
}
