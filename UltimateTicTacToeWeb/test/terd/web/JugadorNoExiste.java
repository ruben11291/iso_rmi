package terd.web;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class JugadorNoExiste extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://127.0.0.1:8888/", "*chrome");
	}
	public void testUser_no_existe() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=input.gwt-TextBox", "ramoncin");
		selenium.type("css=input.gwt-PasswordTextBox", "ramoncin");
		selenium.click("css=#sendButtonContainer > button.myButton");
	//	assertEquals("Usuario ya logueado o no registrado en el sistema", selenium.getAlert());
		assertTrue(selenium.isElementPresent("css=#sendButtonContainer > button.myButton"));
		assertTrue(selenium.isElementPresent("css=#registerButton > button.myButton"));
	}
}
