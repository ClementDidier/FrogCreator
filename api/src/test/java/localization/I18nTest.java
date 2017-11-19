package localization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import utils.FrogException;

public class I18nTest {

	private static final String INVALID_KEY = "myInvalidKey";
	private static final String VALID_KEY = "myValidKey";
	private static final String VALID_FR_VALUE = "maValeurValide";
	private static final String VALID_EN_VALUE = "myValidValue";
	
	
	private I18nProperties properties;
	
	@Before
	public void initialize() throws FrogException, FileNotFoundException, UnsupportedEncodingException
	{
		// Create resources files
		// fr_FR
		String filePath = String.format("%s%s%s_fr_FR.properties", I18nProperties.DEFAULT_RESOURCES_PATH, File.separator, I18nProperties.DEFAULT_BUNDLE_NAME);
		PrintWriter writer = new PrintWriter(filePath, "UTF-8");
		writer.println(String.format("%s=%s", VALID_KEY, VALID_FR_VALUE));
		writer.close();
		// en_US
		filePath = String.format("%s%s%s_en_US.properties", I18nProperties.DEFAULT_RESOURCES_PATH, File.separator, I18nProperties.DEFAULT_BUNDLE_NAME);
		writer = new PrintWriter(filePath, "UTF-8");
		writer.println(String.format("%s=%s", VALID_KEY, VALID_EN_VALUE));
		writer.close();
		
		this.properties = new I18nProperties();
	}

	@Test
	public void changeLocaleToENTest() throws FrogException
	{
		this.properties.setDefault(Locale.ENGLISH);
		this.properties.reload();
		assertEquals("Locales différentes", Locale.ENGLISH, this.properties.getCurrentLocale());
	}
	
	@Test
	public void changeLocaleToFRTest() throws FrogException
	{
		this.properties.setDefault(Locale.FRANCE);
		this.properties.reload();
		assertEquals("Locales différentes", Locale.FRANCE, this.properties.getCurrentLocale());
	}
	
	@Test(expected=FrogException.class)
	public void getInvalidKeyTest() throws FrogException
	{
		this.properties.getString(INVALID_KEY);
		fail("Test de clé invalide : NO OK");
	}
	
	@Test
	public void getValidKeyValueENTest() throws FrogException
	{
		this.properties.setDefault(Locale.US);
		this.properties.reload();
		String value = this.properties.getString(VALID_KEY);
		assertEquals("Valeur non égales", VALID_EN_VALUE, value);
	}
	
	@Test
	public void getValidKeyValueFRTest() throws FrogException
	{
		this.properties.setDefault(Locale.FRANCE);
		this.properties.reload();
		String value = this.properties.getString(VALID_KEY);
		assertEquals("Valeur non égales", VALID_FR_VALUE, value);
	}
}
