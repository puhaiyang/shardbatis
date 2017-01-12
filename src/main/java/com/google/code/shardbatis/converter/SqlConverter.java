/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;

public abstract interface SqlConverter {
	public abstract String convert(Statement paramStatement, Object paramObject, String paramString);
}