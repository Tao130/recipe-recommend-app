package com.example.recipeapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.SavedRecipe
import com.example.recipeapp.database.SavedRecipeDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var savedRecipeDao: SavedRecipeDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        savedRecipeDao = db.savedRecipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertSavedRecipe() {
        val recipe = SavedRecipe("お好み焼き", "キャベツ切って小麦粉混ぜる")
        savedRecipeDao.insertSavedRecipe(recipe)

    }
}