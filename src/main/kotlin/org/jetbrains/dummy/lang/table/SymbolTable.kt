package org.jetbrains.dummy.lang.table

class SymbolTable() {
    private val table: MutableMap<String, Symbol> = HashMap()

    fun put(symbol: Symbol) {
        table.put(symbol.name, symbol)
    }

    fun remove(symbol: Symbol) {
         table.remove(symbol.name)
    }

    fun get(name: String): Symbol? {
        return table[name]
    }
}