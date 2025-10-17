package com.example.inventory.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    private var item1 = Item(1, "Apples", 10.0, 20)
    private var item2 = Item(2, "Bananas", 15.0, 25)
    private var item1Updated = Item(1, "Apples", 20.0, 50)
    private var item2Updated = Item(2, "Bananas", 10.0, 10)

    private suspend fun insertOneItemIntoDb() {
        itemDao.insert(item1)
    }

    private suspend fun insertTwoItemsIntoDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    fun itemDao_insertItemIntoDb_itemExistsInDb() = runBlocking {
        insertOneItemIntoDb()
        val allItems = itemDao.getAllItems().first()

        assertEquals(allItems[0], item1)
    }

    @Test
    fun itemDao_getAllItems_returnsAllItemsFromDb() = runBlocking {
        insertTwoItemsIntoDb()
        val allItems = itemDao.getAllItems().first()

        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    fun itemDao_updateItems_updatesItemsInDb() = runBlocking {
        insertTwoItemsIntoDb()
        itemDao.update(item1Updated)
        itemDao.update(item2Updated)
        val allItems = itemDao.getAllItems().first()

        assertEquals(item1Updated, allItems[0])
        assertEquals(item2Updated, allItems[1])
    }

    @Test
    fun itemDao_deleteItem_deletesItemFromDb() = runBlocking {
        insertTwoItemsIntoDb()
        itemDao.delete(item1)
        itemDao.delete(item2)

        val allItems = itemDao.getAllItems().first()

        assertTrue(allItems.isEmpty())
    }

}