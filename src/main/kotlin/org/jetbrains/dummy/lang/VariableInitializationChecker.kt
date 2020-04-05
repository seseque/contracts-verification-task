package org.jetbrains.dummy.lang

import org.jetbrains.dummy.lang.table.GlobalScope
import org.jetbrains.dummy.lang.table.Scope
import org.jetbrains.dummy.lang.tree.File
import org.jetbrains.dummy.lang.tree.FunctionCall
import org.jetbrains.dummy.lang.tree.ScopeDummyLangVisitor
import org.jetbrains.dummy.lang.tree.VariableAccess

class VariableInitializationChecker(private val reporter: DiagnosticReporter) : AbstractChecker() {
    override val globalScope: Scope = GlobalScope()

    override fun inspect(file: File) {
        try {
            file.accept(visitor, globalScope)
        }
        catch (e: UninitializedVariableAccessException) {
            reportAccessBeforeInitialization(e.variableAccess)
        }
        catch (e: NoSuchFunctionException) {
            reportUndefinedFunctionCall(e.functionCall)
        }
    }

    // Use this method for reporting errors
    private fun reportAccessBeforeInitialization(access: VariableAccess) {
        reporter.report(access, "Variable '${access.name}' is accessed before initialization")
    }

    private fun reportUndefinedFunctionCall(call: FunctionCall) {
        reporter.report(call, "Call of undefined function '${call.function}' ")
    }
}