package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Ingredient::class, SavedRecipe::class, SearchedRecipe::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun savedRecipeDao(): SavedRecipeDao
    abstract fun searchedRecipeDao(): SearchedRecipeDao

    private class IngredientDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.ingredientDao())
                }
            }
        }

        suspend fun populateDatabase(ingredientDao: IngredientDao) {
            // Delete all content here.
            ingredientDao.deleteAll()

        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ingredient_database"
                )
                    .addCallback(
                        IngredientDatabaseCallback(
                            scope
                        )
                    )
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}