/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.strategy;

public abstract interface ShardStrategy {
	public abstract String getTargetTableName(String paramString1, Object paramObject, String paramString2);
}