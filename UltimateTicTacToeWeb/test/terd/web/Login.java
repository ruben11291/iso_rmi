package terd.web;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class Login extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://127.0.0.1:8888/", "*chrome");
	}
	public void testLogin() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=input.gwt-TextBox", "pepe");
		selenium.type("css=input.gwt-PasswordTextBox", "pepe");
		selenium.click("css=#sendButtonContainer > button.myButton");
		selenium.close();
	}
}
