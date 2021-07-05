package com.example.mvi_example.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvi_example.databinding.MealCategoryListItemBinding
import com.example.mvi_example.model.MealCategory

class MealCategoryAdapter(
    private val onMealSelected: (MealCategory) -> Unit
) :
    RecyclerView.Adapter<MealCategoryAdapter.CategoryViewHolder>() {

    var categoryList: List<MealCategory> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MealCategoryListItemBinding.inflate(inflater, parent, false)
        val holder = CategoryViewHolder(binding)
        binding.root.setOnClickListener {
            val meal = categoryList[holder.bindingAdapterPosition]
            onMealSelected(meal)
        }
        return holder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    inner class CategoryViewHolder(private val binding: MealCategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: MealCategory) {
            with(binding) {
                Glide.with(root.context)
                    .load(category.thumbnailUrl)
                    .into(imgThumbnail)

                txtCategory.text = category.name
                txtDescription.text = category.description
            }
        }
    }
}