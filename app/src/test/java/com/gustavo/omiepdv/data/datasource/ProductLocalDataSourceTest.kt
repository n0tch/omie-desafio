package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource.Companion.PRODUCTS_KEY
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ProductLocalDataSourceTest {

    private val gson = mockk<Gson>()
    private val sharedPreferences = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>()
    private val productLocalDataSource = ProductLocalDataSource(sharedPreferences, gson)

    @Before
    fun setup(){
        every { sharedPreferences.edit() } returns editor
        every { editor.apply() } just runs
    }

    @Test
    fun getProducts() {
        every { sharedPreferences.getStringSet(PRODUCTS_KEY, setOf()) } returns setOf("")
        every { gson.fromJson("", Product::class.java) } returns Product()

        productLocalDataSource.getProducts()

        verify(exactly = 1) { sharedPreferences.getStringSet(PRODUCTS_KEY, setOf()) }
    }

    @Test
    fun saveProduct() {
        val product = Product()
        every { sharedPreferences.getStringSet(PRODUCTS_KEY, setOf()) } returns setOf("")
        every { gson.toJson(any(), Product::class.java) } returns ""
        every { editor.putStringSet(PRODUCTS_KEY, any()) } returns editor
        productLocalDataSource.saveProduct(product)
    }
}