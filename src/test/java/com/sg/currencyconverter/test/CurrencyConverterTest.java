package com.sg.currencyconverter.test;

import java.io.File;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.Testable;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sg.currencyconverter.IConverterBean;

public class CurrencyConverterTest extends Arquillian {

	@Deployment
	public static EnterpriseArchive createDeployment(){
		EnterpriseArchive ear = ShrinkWrap.createFromZipFile(EnterpriseArchive.class, new File("../earapp/target/earapp-0.0.1-SNAPSHOT.ear"))
				.addAsModule(Testable.archiveToTest(ShrinkWrap.create(WebArchive.class, "test.war")
						.addClass(CurrencyConverterTest.class)
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")));
		ear.as(ZipExporter.class).exportTo(new File("earapp.ear"), true);
		return ear;
	}
	
	@EJB(lookup="java:app/currencyconverter-0.0.1-SNAPSHOT/ConverterBean!com.sg.currencyconverter.IConverterBean")
	IConverterBean converter;
	
	@Test
	public void testDollarToYen(){
		Assert.assertEquals(converter.dollarToYen(1), 83.0602, 0.005);
	}
	
	@Test
	public void testYenToEuro(){
		Assert.assertEquals(converter.yenToEuro(1), 0.0093016, 0.005);
	}
}
