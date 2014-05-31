package terd.web;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

public class RegisterLoginAndCloseSession {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		FuncionesAuxiliaresTests.borrarBD();

		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		selenium.start();
	}

	@Test
	public void testRegisterLoginAndCloseSession() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=#emailRegistro > input.gwt-TextBox", "user");
		selenium.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "user");
		selenium.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "user");
		selenium.click("css=#registerButton > button.myButton");
		Thread.sleep(10000);
		assertEquals("Usuario registrado", selenium.getAlert());
		selenium.type("css=input.gwt-TextBox", "user");
		selenium.type("css=input.gwt-PasswordTextBox", "user");
		selenium.click("css=#sendButtonContainer > button.myButton");
		assertTrue(selenium.isElementPresent("css=select.lista"));
		assertTrue(selenium.isElementPresent("css=div.labelLista"));
		assertTrue(selenium.isElementPresent("css=button.myButton"));
		Thread.sleep(1000);
		selenium.click("css=button.myButton");
		Thread.sleep(1000);
		assertTrue(selenium.isElementPresent("css=select.lista"));
		assertTrue(selenium.isElementPresent("css=div.labelLista"));
		assertTrue(selenium.isElementPresent("css=button.myButton"));
	}

	@After
	public void tearDown() throws Exception {
		
		selenium.stop();
		FuncionesAuxiliaresTests.borrarBD();

	}
}
