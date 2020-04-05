package org.jetbrains.dummy.lang.table

sealed class Symbol {
    abstract val name: String
    abstract val cathegory: SymbolCathegory
}


class VariableSymbol(override val name: String, var value: Any? = UNINITIALIZED) : Symbol() {
    override val cathegory = SymbolCathegory.VARIABLE
}

class BuiltInTypeSymbol(override val name: String, var type: Any?, var value : Any? = null) : Symbol() {
    override val cathegory = SymbolCathegory.BUILTINTYPE
}

class MethodSymbol(override val name: String) : Symbol() {
    override val cathegory = SymbolCathegory.METHOD
}

enum class SymbolCathegory {
    VARIABLE, BUILTINTYPE, METHOD
}


// служебный объект для отметки, что символ еще не был проинициализирован
object UNINITIALIZED