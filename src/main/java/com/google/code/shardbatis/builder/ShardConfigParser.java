/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.builder;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.code.shardbatis.util.ConfigHandler;

public class ShardConfigParser {
//	private static final Log log = LogFactory.getLog(ShardConfigParser.class);

	public ShardConfigHolder parse(InputStream input) throws Exception {
		ShardConfigHolder configHolder = ShardConfigHolder.getInstance();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(true);
		spf.setNamespaceAware(true);
		SAXParser parser = spf.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		ConfigHandler handler = new ConfigHandler(configHolder);
		reader.setContentHandler(handler);
		reader.setEntityResolver(handler);
		reader.setErrorHandler(handler);
		reader.parse(new InputSource(input));
		
		return configHolder;
	}


}