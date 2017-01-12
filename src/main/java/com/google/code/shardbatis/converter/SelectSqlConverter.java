/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis.converter;

import java.util.Iterator;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

public class SelectSqlConverter extends AbstractSqlConverter {
	protected Statement doConvert(Statement statement, Object params, String mapperId) {
		if (!(statement instanceof Select)) {
			throw new IllegalArgumentException("The argument statement must is instance of Select.");
		}

		TableNameModifier modifier = new TableNameModifier(params, mapperId);
		((Select) statement).getSelectBody().accept(modifier);
		return statement;
	}

	private class TableNameModifier implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor {
		private Object params;
		private String mapperId;

		public TableNameModifier(Object params, String mapperId) {
			this.params = params;
			this.mapperId = mapperId;
		}

		@Override
		public void visit(ExpressionList paramExpressionList) {
			Iterator iter = paramExpressionList.getExpressions().iterator();
			while (iter.hasNext()) {
				Expression expression = (Expression) iter.next();
				expression.accept(this);
			}
		}

		@Override
		public void visit(MultiExpressionList paramMultiExpressionList) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(NullValue paramNullValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Function paramFunction) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(SignedExpression paramSignedExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(JdbcParameter paramJdbcParameter) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(JdbcNamedParameter paramJdbcNamedParameter) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(DoubleValue paramDoubleValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(LongValue paramLongValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(HexValue paramHexValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(DateValue paramDateValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(TimeValue paramTimeValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(TimestampValue paramTimestampValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Parenthesis paramParenthesis) {
			paramParenthesis.getExpression().accept(this);

		}

		@Override
		public void visit(StringValue paramStringValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Addition paramAddition) {
			visitBinaryExpression(paramAddition);
		}

		@Override
		public void visit(Division paramDivision) {
			visitBinaryExpression(paramDivision);
		}

		@Override
		public void visit(Multiplication paramMultiplication) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Subtraction paramSubtraction) {
			visitBinaryExpression(paramSubtraction);
		}

		@Override
		public void visit(AndExpression paramAndExpression) {
			visitBinaryExpression(paramAndExpression);
		}

		@Override
		public void visit(OrExpression paramOrExpression) {
			visitBinaryExpression(paramOrExpression);
		}

		@Override
		public void visit(Between between) {
			between.getLeftExpression().accept(this);
			between.getBetweenExpressionStart().accept(this);
			between.getBetweenExpressionEnd().accept(this);
		}

		@Override
		public void visit(EqualsTo paramEqualsTo) {
			visitBinaryExpression(paramEqualsTo);
		}

		@Override
		public void visit(GreaterThan paramGreaterThan) {
			visitBinaryExpression(paramGreaterThan);

		}

		@Override
		public void visit(GreaterThanEquals paramGreaterThanEquals) {
			visitBinaryExpression(paramGreaterThanEquals);
		}

		@Override
		public void visit(InExpression paramInExpression) {
			paramInExpression.getLeftExpression().accept(this);

			paramInExpression.getLeftItemsList().accept(this);
			paramInExpression.getRightItemsList().accept(this);
		}

		@Override
		public void visit(IsNullExpression paramIsNullExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(LikeExpression paramLikeExpression) {
			visitBinaryExpression(paramLikeExpression);
		}

		@Override
		public void visit(MinorThan paramMinorThan) {
			visitBinaryExpression(paramMinorThan);
		}

		@Override
		public void visit(MinorThanEquals paramMinorThanEquals) {
			visitBinaryExpression(paramMinorThanEquals);
		}

		@Override
		public void visit(NotEqualsTo paramNotEqualsTo) {
			visitBinaryExpression(paramNotEqualsTo);
		}

		@Override
		public void visit(Column paramColumn) {
			// TODO Auto-generated method stub
		}

		@Override
		public void visit(CaseExpression paramCaseExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(WhenClause paramWhenClause) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(ExistsExpression paramExistsExpression) {
			paramExistsExpression.getRightExpression().accept(this);
		}

		@Override
		public void visit(AllComparisonExpression paramAllComparisonExpression) {
			paramAllComparisonExpression.getSubSelect().getSelectBody().accept(this);
		}

		@Override
		public void visit(AnyComparisonExpression paramAnyComparisonExpression) {
			paramAnyComparisonExpression.getSubSelect().getSelectBody().accept(this);
		}

		@Override
		public void visit(Concat paramConcat) {
			visitBinaryExpression(paramConcat);
		}

		@Override
		public void visit(Matches paramMatches) {
			visitBinaryExpression(paramMatches);

		}

		@Override
		public void visit(BitwiseAnd paramBitwiseAnd) {
			visitBinaryExpression(paramBitwiseAnd);
		}

		@Override
		public void visit(BitwiseOr paramBitwiseOr) {
			visitBinaryExpression(paramBitwiseOr);
		}

		@Override
		public void visit(BitwiseXor paramBitwiseXor) {
			visitBinaryExpression(paramBitwiseXor);
		}

		public void visitBinaryExpression(BinaryExpression binaryExpression) {
			binaryExpression.getLeftExpression().accept(this);
			binaryExpression.getRightExpression().accept(this);
		}

		@Override
		public void visit(CastExpression paramCastExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Modulo paramModulo) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(AnalyticExpression paramAnalyticExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(WithinGroupExpression paramWithinGroupExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(ExtractExpression paramExtractExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(IntervalExpression paramIntervalExpression) {
			// paramIntervalExpression.getExpression().accept(this);

		}

		@Override
		public void visit(OracleHierarchicalExpression paramOracleHierarchicalExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(RegExpMatchOperator paramRegExpMatchOperator) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(JsonExpression paramJsonExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(RegExpMySQLOperator paramRegExpMySQLOperator) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(UserVariable paramUserVariable) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(NumericBind paramNumericBind) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(KeepExpression paramKeepExpression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(MySQLGroupConcat paramMySQLGroupConcat) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(RowConstructor paramRowConstructor) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Table paramTable) {
			String table = paramTable.getName();
			table = SelectSqlConverter.this.convertTableName(table, this.params, this.mapperId);

			paramTable.setName(table);
		}

		@Override
		public void visit(SubSelect paramSubSelect) {
			paramSubSelect.getSelectBody().accept(this);
		}

		@Override
		public void visit(SubJoin paramSubJoin) {
			paramSubJoin.getLeft().accept(this);
			paramSubJoin.getJoin().getRightItem().accept(this);
		}

		@Override
		public void visit(LateralSubSelect paramLateralSubSelect) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(ValuesList paramValuesList) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(PlainSelect plainSelect) {
			plainSelect.getFromItem().accept(this);
			if (plainSelect.getJoins() != null) {
				Iterator joinsIt = plainSelect.getJoins().iterator();
				while (joinsIt.hasNext()) {
					Join join = (Join) joinsIt.next();
					join.getRightItem().accept(this);
				}
			}
			if (plainSelect.getWhere() != null)
				plainSelect.getWhere().accept(this);
		}

		@Override
		public void visit(SetOperationList paramSetOperationList) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(WithItem paramWithItem) {
			// TODO Auto-generated method stub

		}

	}
}