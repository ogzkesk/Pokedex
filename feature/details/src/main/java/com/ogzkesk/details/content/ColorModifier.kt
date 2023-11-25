package com.ogzkesk.details.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import com.ogzkesk.core.ext.capitalize
import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.ui.PokemonColor
import com.ogzkesk.details.databinding.FragmentDetailsBinding

class ColorModifier(private val context: Context, private val binding: FragmentDetailsBinding) {

    private var data: PokemonModel? = null

    fun init(data: PokemonModel?) = apply {
        this.data = data
    }

    fun setColors() = apply {

        binding.apply {
            container.setBackgroundColor(getPrimaryColor())
            appBar.setBackgroundColor(getPrimaryColor())
            tvAbout.setTextColor(getPrimaryColor())
            tvBaseStats.setTextColor(getPrimaryColor())
        }
    }

    fun addChips() = apply {
        data?.let { data ->
            getChips(data.types).forEach {
                binding.chipContainer.addView(it)
            }
        }
    }

    private fun getChips(types: List<String>): List<Chip> {
        return types.map { type ->
            val chipColor = PokemonColor.valueOf(type.capitalize()).color
            Chip(context).apply {
                chipBackgroundColor = ColorStateList.valueOf(context.getColor(chipColor))
                shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(48f)
                isClickable = false
                chipStrokeWidth = 0f
                text = type
                setTextAppearanceResource(com.ogzkesk.core.R.style.Pokedex_BodySmall)
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(Color.WHITE)
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).also {
                    it.setMargins(32, 0, 0, 0)
                    layoutParams = it
                }
            }
        }
    }

    private fun getPrimaryColor(): Int {
        if (data == null) {
            return context.getColor(com.ogzkesk.core.R.color.md_theme_light_primary)
        }

        return context.getColor(data!!.color)
    }
}