package com.example.homework11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework11.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter

    companion object {
        private val itemList: MutableList<Item> by lazy {
            mutableListOf()
        }
        private var lastId = 0
        private var forUpdateItemIndex = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnRadioButtonsClickListeners()
        initRecyclerView()
        binding.addItem.setOnClickListener {
            onBtnClick()
        }

        binding.update.setOnClickListener {
            finish()
            startActivity(intent)
        }

    }


    private fun initRecyclerView() {
        adapter = MyAdapter()
        val list = mutableListOf<Item>()
        list.addAll(itemList)
        adapter.submitList(list)
        adapter.setOnDeleteListener {
            deleteAt(it)
        }

        adapter.setOnUpdateListener {
            updateAt(it)
        }

        binding.myRecyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun setOnRadioButtonsClickListeners() {
        binding.item2RadioBtn.setOnClickListener {
            binding.item2Special.visibility = View.VISIBLE
        }
        binding.item1RadioBtn.setOnClickListener {
            binding.item2Special.visibility = View.GONE
        }
    }

    private fun onBtnClick() {
        var item: Item
        binding.apply {
            if (name.text.toString().isEmpty()) {
                myToast("Please enter name")
                return@onBtnClick
            }
            if (!item1RadioBtn.isChecked && !item2RadioBtn.isChecked) {
                myToast("Please check one of the buttons")
                return@onBtnClick
            }
            if (item2RadioBtn.isChecked && item2Special.text.toString().isEmpty()) {
                myToast("Please enter text in item 2 special")
                return@onBtnClick
            }
            item = if (item1RadioBtn.isChecked) {
                Item(id = ++lastId, name = name.text.toString(), type = 1)
            } else {
                Item(
                    id = ++lastId,
                    name = name.text.toString(),
                    type = 2,
                    type2Special = item2Special.text.toString()
                )
            }
        }
        if (binding.addItem.text == getString(R.string.update)) {
            binding.apply {
                addItem.text = getString(R.string.add_item)
                item2RadioBtn.isEnabled = true
                item1RadioBtn.isEnabled = true
            }
            lastId--
            val id = itemList[forUpdateItemIndex].id
            item = Item(id, item.name, item.type, item.type2Special)
            itemList[forUpdateItemIndex] = item
        } else {
            itemList.add(item)
        }
        val newList = mutableListOf<Item>()
        newList.addAll(itemList)
        adapter.submitList(newList)

    }

    private fun deleteAt(position: Int) {
        itemList.removeAt(position)
        val newList = mutableListOf<Item>()
        newList.addAll(itemList)
        adapter.submitList(newList)

    }

    private fun updateAt(position: Int) {
        forUpdateItemIndex = position
        binding.apply {
            addItem.text = getString(R.string.update)
            name.setText(itemList[position].name)
        }
        if (itemList[position].type == 1) {
            binding.apply {
                item2Special.visibility = View.GONE
                item2RadioBtn.isEnabled = false
                item1RadioBtn.isChecked = true
            }
        } else {
            binding.apply {
                item2Special.visibility = View.VISIBLE
                item1RadioBtn.isEnabled = false
                item2RadioBtn.isChecked = true
                item2Special.setText(itemList[position].type2Special)
            }
        }
    }

    private fun myToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}