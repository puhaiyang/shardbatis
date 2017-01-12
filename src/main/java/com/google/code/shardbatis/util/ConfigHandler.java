package com.google.code.shardbatis.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.code.shardbatis.builder.ShardConfigHolder;
import com.google.code.shardbatis.strategy.ShardStrategy;

public class ConfigHandler extends DefaultHandler {
//	private static final Log log = LogFactory.getLog(ConfigHandler.class);
	private String parentElement;
	ShardConfigHolder configHolder;

	public ConfigHandler(ShardConfigHolder configHolder) {
		this.configHolder = configHolder;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("strategy".equals(qName)) {
			String table = attributes.getValue("tableName");
			String className = attributes.getValue("strategyClass");
			ShardStrategy strategy;
			try {
				Class clazz = Class.forName(className);
				strategy = (ShardStrategy) clazz.newInstance();
				configHolder.register(table, strategy);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		if (("ignoreList".equals(qName)) || ("parseList".equals(qName))) {
			this.parentElement = qName;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if ("ignoreList".equals(this.parentElement)) {
			configHolder.addIgnoreId(new String(ch, start, length).trim());
		} else if ("parseList".equals(this.parentElement)) {
			configHolder.addParseId(new String(ch, start, length).trim());
		}
	}

}
