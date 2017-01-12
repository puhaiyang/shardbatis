/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.converter;

import com.google.code.shardbatis.builder.ShardConfigHolder;
import com.google.code.shardbatis.strategy.ShardStrategy;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public abstract class AbstractSqlConverter implements SqlConverter {
	public String convert(Statement statement, Object params, String mapperId) {
		return doDeParse(doConvert(statement, params, mapperId));
	}

	protected String doDeParse(Statement statement) {
		StatementDeParser deParser = new StatementDeParser(new StringBuilder());
//		StatementDeParser deParser = new StatementDeParser(new StringBuffer());
		statement.accept(deParser);
		return deParser.getBuffer().toString();
	}

	protected String convertTableName(String tableName, Object params, String mapperId) {
		ShardConfigHolder configFactory = ShardConfigHolder.getInstance();
		ShardStrategy strategy = configFactory.getStrategy(tableName);
		return strategy.getTargetTableName(tableName, params, mapperId);
	}

	protected abstract Statement doConvert(Statement paramStatement, Object paramObject, String paramString);
}