/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.strategy.impl;

import com.google.code.shardbatis.strategy.ShardStrategy;

public class NoShardStrategy implements ShardStrategy {
	public String getTargetTableName(String baseTableName, Object params, String mapperId) {
		return baseTableName;
	}
}