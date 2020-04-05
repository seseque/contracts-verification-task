package org.jetbrains.dummy.lang

import org.jetbrains.dummy.lang.table.Scope
import org.jetbrains.dummy.lang.tree.File
import org.jetbrains.dummy.lang.tree.ScopeDummyLangVisitor

abstract class AbstractChecker {
    val visitor = ScopeDummyLangVisitor()

    abstract val globalScope: Scope

    abstract fun inspect(file: File)
}