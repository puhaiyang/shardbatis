/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.plugin;

import com.google.code.shardbatis.builder.ShardConfigHolder;
import com.google.code.shardbatis.builder.ShardConfigParser;
import com.google.code.shardbatis.converter.SqlConverterFactory;
import com.google.code.shardbatis.util.ReflectionUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = {
		java.sql.Connection.class }) })
public class ShardPlugin implements Interceptor {
	private static final Log log = LogFactory.getLog(ShardPlugin.class);
	public static final String SHARDING_CONFIG = "shardingConfig";
	private static final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap();

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		MappedStatement mappedStatement = null;
		if (statementHandler instanceof RoutingStatementHandler) {
			StatementHandler delegate = (StatementHandler) ReflectionUtils.getFieldValue(statementHandler, "delegate");

			mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");
		} else {
			mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(statementHandler, "mappedStatement");
		}

		String mapperId = mappedStatement.getId();

		if (isShouldParse(mapperId)) {
			String sql = statementHandler.getBoundSql().getSql();
			if (log.isDebugEnabled()) {
				log.debug("Original Sql [" + mapperId + "]:" + sql);
			}
			Object params = statementHandler.getBoundSql().getParameterObject();

			SqlConverterFactory cf = SqlConverterFactory.getInstance();
			sql = cf.convert(sql, params, mapperId);
			if (log.isDebugEnabled()) {
				log.debug("Converted Sql [" + mapperId + "]:" + sql);
			}
			ReflectionUtils.setFieldValue(statementHandler.getBoundSql(), "sql", sql);
		}

		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String config = properties.getProperty("shardingConfig", null);
		if ((config == null) || (config.trim().length() == 0)) {
			throw new IllegalArgumentException("property 'shardingConfig' is requested.");
		}

		ShardConfigParser parser = new ShardConfigParser();
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream(config);
			parser.parse(input);
		} catch (IOException e) {
		} catch (Exception e) {
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
		}
	}

	private boolean isShouldParse(String mapperId) {
		Boolean parse = (Boolean) cache.get(mapperId);

		if (parse != null) {
			return parse.booleanValue();
		}

		if (!(mapperId.endsWith("!selectKey"))) {
			ShardConfigHolder configHolder = ShardConfigHolder.getInstance();

			if ((!(configHolder.isIgnoreId(mapperId)))
					&& (((!(configHolder.isConfigParseId())) || (configHolder.isParseId(mapperId))))) {
				parse = Boolean.valueOf(true);
			}
		}

		if (parse == null) {
			parse = Boolean.valueOf(false);
		}
		cache.put(mapperId, parse);
		return parse.booleanValue();
	}
}