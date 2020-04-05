package org.jetbrains.dummy.lang

import org.jetbrains.dummy.lang.table.GlobalScope
import org.jetbrains.dummy.lang.table.Scope
import org.jetbrains.dummy.lang.tree.File
import org.jetbrains.dummy.lang.tree.FunctionCall

class FunctionDeclarationChecker(private val reporter: DiagnosticReporter) : AbstractChecker() {
    override val globalScope: Scope = GlobalScope()

    override fun inspect(file: File) {
        try {
            file.accept(visitor, globalScope)
        } catch (e: NoSuchFunctionException) {
            reportUndefinedFunctionCall(e.functionCall)
        }
    }


    private fun reportUndefinedFunctionCall(call: FunctionCall) {
        reporter.report(call, "Call of undefined function '${call.function}' ")
    }
}