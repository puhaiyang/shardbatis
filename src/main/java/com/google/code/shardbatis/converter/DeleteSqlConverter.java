/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

public class DeleteSqlConverter extends AbstractSqlConverter {
	protected Statement doConvert(Statement statement, Object params, String mapperId) {
		if (!(statement instanceof Delete)) {
			throw new IllegalArgumentException("The argument statement must is instance of Delete.");
		}

		Delete delete = (Delete) statement;

		String name = delete.getTable().getName();
		delete.getTable().setName(convertTableName(name, params, mapperId));

		return delete;
	}
}