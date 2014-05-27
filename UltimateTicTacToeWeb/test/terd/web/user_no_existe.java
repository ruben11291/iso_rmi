package terd.web;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class user_no_existe extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://127.0.0.1:8888/", "*chrome");
	}
	public void testUser_no_existe() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html?gwt.codesvr=127.0.0.1:9997");
		selenium.type("css=input.gwt-TextBox", "ramon");
		selenium.type("css=input.gwt-PasswordTextBox", "ramon");
		selenium.click("css=#sendButtonContainer > button.myButton");
		assertEquals("Usuario ya logueado o no registrado en el sistema", selenium.getAlert());
		assertTrue(selenium.isElementPresent("css=#sendButtonContainer > button.myButton"));
		assertTrue(selenium.isElementPresent("css=#registerButton > button.myButton"));
	}
}
