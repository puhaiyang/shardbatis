/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

public class UpdateSqlConverter extends AbstractSqlConverter {
	protected Statement doConvert(Statement statement, Object params, String mapperId) {
		if (!(statement instanceof Update)) {
			throw new IllegalArgumentException("The argument statement must is instance of Update.");
		}

		Update update = (Update) statement;
		if(update.getTables().isEmpty()){
			throw new IllegalArgumentException("The table name is unknow.");
		}
//		String name = update.getTable().getName();
//		update.getTable().setName(convertTableName(name, params, mapperId));
		String name  = update.getTables().get(0).getName();
		update.getTables().get(0).setName(convertTableName(name, params, mapperId));
		return update;
	}
}