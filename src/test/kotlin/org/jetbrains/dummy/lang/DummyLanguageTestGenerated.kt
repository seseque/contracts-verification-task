package org.jetbrains.dummy.lang

import org.junit.Test

class DummyLanguageTestGenerated : AbstractDummyLanguageTest() {
    @Test
    fun testBad() {
        doTest("testData/bad.dummy")
    }
    
    @Test
    fun testFuncBad() {
        doTest("testData/funcBad.dummy")
    }
    
    @Test
    fun testGood() {
        doTest("testData/good.dummy")
    }
}
