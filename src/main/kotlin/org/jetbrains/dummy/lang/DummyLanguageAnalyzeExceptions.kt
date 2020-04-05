package org.jetbrains.dummy.lang

import org.jetbrains.dummy.lang.tree.FunctionCall
import org.jetbrains.dummy.lang.tree.VariableAccess
import java.lang.RuntimeException

class UninitializedVariableAccessException(val variableAccess: VariableAccess) : RuntimeException()

class NoSuchFunctionException(val functionCall: FunctionCall) : RuntimeException()