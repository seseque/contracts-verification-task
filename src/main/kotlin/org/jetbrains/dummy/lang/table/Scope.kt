package org.jetbrains.dummy.lang.table

interface Scope {
    val scopeName: String?
    val enclosingScope: Scope?
    val dictionary: SymbolTable

    fun resolve(symbolName: String): Symbol? {
        val symbol = dictionary.get(symbolName)
        if (symbol != null) return symbol
        if (enclosingScope != null)
            return enclosingScope?.resolve(symbolName)
        return null
    }
}


class GlobalScope : Scope {
    override val scopeName = "global"
    override val enclosingScope: Scope? = null
    override val dictionary = SymbolTable()
}

class LocalScope(
    override val scopeName: String?,
    override val enclosingScope: Scope,
    override val dictionary: SymbolTable = SymbolTable()
) : Scope