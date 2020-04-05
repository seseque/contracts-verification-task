package org.jetbrains.dummy.lang.tree

import org.jetbrains.dummy.lang.NoSuchFunctionException
import org.jetbrains.dummy.lang.UninitializedVariableAccessException
import org.jetbrains.dummy.lang.table.*

class ScopeDummyLangVisitor : DummyLangVisitor<Unit, Scope>() {
    override fun visitElement(element: Element, scope: Scope) {
        element.accept(this, scope)
    }

    override fun visitFile(file: File, scope: Scope) {
        for (function in file.functions) {
            val funcSymbol = MethodSymbol(function.name)
            scope.dictionary.put(funcSymbol)
        }

        for (function in file.functions) {
            val funcScope = LocalScope(function.name, scope)
            function.accept(this, funcScope)
        }
    }

    override fun visitFunctionDeclaration(functionDeclaration: FunctionDeclaration, scope: Scope) {
        for (param in functionDeclaration.parameters) {
            val paramSymbol = VariableSymbol(param)
            scope.dictionary.put(paramSymbol)
        }
        functionDeclaration.body.accept(this, scope)
    }

    override fun visitBlock(block: Block, scope: Scope) {
        for (statement in block.statements) {
            statement.accept(this, scope)
        }
    }

    override fun visitStatement(statement: Statement, scope: Scope) {
        statement.accept(this, scope)
    }

    override fun visitAssignment(assignment: Assignment, scope: Scope) {
        assignment.rhs.accept(this, scope)

        val symbol = scope.resolve(assignment.variable) as VariableSymbol
        symbol.value = assignment.rhs
    }

    override fun visitIfStatement(ifStatement: IfStatement, scope: Scope) {
        ifStatement.condition.accept(this, scope)
        val thenScope = LocalScope("${scope.scopeName}.thenBlock", scope)
        val elseScope = LocalScope("${scope.scopeName}.elseBlock", scope)

        ifStatement.condition.accept(this, thenScope)
        ifStatement.condition.accept(this, elseScope)
    }

    override fun visitVariableDeclaration(variableDeclaration: VariableDeclaration, scope: Scope) {
        variableDeclaration.initializer?.accept(this, scope)

        val variableSymbol = if (variableDeclaration.initializer != null) {
            VariableSymbol(variableDeclaration.name, variableDeclaration.initializer)
        } else {
            VariableSymbol(variableDeclaration.name)
        }
        scope.dictionary.put(variableSymbol)
    }

    override fun visitReturnStatement(returnStatement: ReturnStatement, scope: Scope) {
        returnStatement.accept(this, scope)
    }

    override fun visitExpression(expression: Expression, scope: Scope) {
        expression.accept(this, scope)
    }

    override fun visitVariableAccess(variableAccess: VariableAccess, scope: Scope) {
        val resolvedVariable = scope.resolve(variableAccess.name) as VariableSymbol?
        if ((resolvedVariable == null)
            or (resolvedVariable?.value == UNINITIALIZED)
        ) {
            throw UninitializedVariableAccessException(variableAccess)
        }
    }

    override fun visitIntConst(intConst: IntConst, scope: Scope) {
        val intSymbol = BuiltInTypeSymbol("int", Int, intConst.value)
        scope.dictionary.put(intSymbol)
    }

    override fun visitBooleanConst(booleanConst: BooleanConst, scope: Scope) {
        val booleanSymbol = BuiltInTypeSymbol("boolean", Boolean, booleanConst.value)
        scope.dictionary.put(booleanSymbol)
    }

    override fun visitFunctionCall(functionCall: FunctionCall, scope: Scope) {
        for (arg in functionCall.arguments) {
            arg.accept(this, scope)
        }
        scope.resolve(functionCall.function) as MethodSymbol? ?: throw NoSuchFunctionException(functionCall)
    }
}