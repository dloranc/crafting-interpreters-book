package com.craftinginterpreters.lox;

import java.util.List;

class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
  String print(List<Stmt> statements) {
    StringBuilder builder = new StringBuilder();

    for (Stmt statement : statements) {
      builder.append(statement.accept(this));
    }

    return builder.toString();
  }

  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) {
      return "nil";
    }

    return expr.value.toString();
  }

  @Override
  public String visitLogicalExpr(Expr.Logical expr) {
    return expr.left.accept(this)
        + " " + expr.operator.lexeme + " "
        + expr.right.accept(this);
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }

  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return expr.name.lexeme;
  }

  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return expr.name.lexeme + " = " + expr.value.accept(this);
  }

  @Override
  public String visitBlockStmt(Stmt.Block block) {
    StringBuilder builder = new StringBuilder();

    builder.append("{");

    for (Stmt statement : block.statements) {
      builder.append(statement.accept(this));
    }

    builder.append("}");

    return builder.toString();
  }

  @Override
  public String visitExpressionStmt(Stmt.Expression stmt) {
    return stmt.expression.accept(this) + ";";
  }

  @Override
  public String visitIfStmt(Stmt.If stmt) {
    StringBuilder builder = new StringBuilder();
    builder.append("if (");
    builder.append(stmt.condition.accept(this));
    builder.append(")");

    builder.append(stmt.thenBranch.accept(this));

    if (stmt.elseBranch != null) {
      builder.append(stmt.elseBranch.accept(this));
    }

    return builder.toString();
  }

  @Override
  public String visitPrintStmt(Stmt.Print stmt) {
    return "print " + stmt.expression.accept(this) + ";";
  }

  @Override
  public String visitVarStmt(Stmt.Var stmt) {
    StringBuilder builder = new StringBuilder();
    builder.append("var ");
    builder.append(stmt.name.lexeme);

    if (stmt.initializer != null) {
      builder.append(" = ");
      builder.append(stmt.initializer.accept(this));
    }
    builder.append(";");

    return builder.toString();
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");

    return builder.toString();
  }
}
