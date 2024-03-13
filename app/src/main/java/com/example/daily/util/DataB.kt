package com.example.daily.util

import com.example.daily.R
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorsBG
import com.example.daily.ui.fragment.edit.textEditing.TextEdit
import com.example.daily.ui.fragment.edit.textEditing.font.FontEditting

object DataB {
    val colorList = listOf(
        R.color.color_one,
        R.color.color_two,
        R.color.color_three,
        R.color.color_four,
        R.color.color_five,
        R.color.color_six,
        R.color.color_seven,
        R.color.color_eight,
        R.color.color_nine,
        R.color.color_ten,
        R.color.color_eleven,
        R.color.color_twelve,
        R.color.color_thirteen,
        R.color.color_fourteen,
        R.color.color_fifteen,
        R.color.color_sixteen,
        R.color.color_seventeen
    )

    var listDrawableColors: List<ColorsBG>? = listOf(
        R.drawable.color_one,
        R.drawable.color_two,
        R.drawable.color_three,
        R.drawable.color_four,
        R.drawable.color_five,
        R.drawable.color_six,
        R.drawable.color_seven,
        R.drawable.color_eight,
        R.drawable.color_nine,
        R.drawable.color_ten,
        R.drawable.color_eleven,
        R.drawable.color_twelve,
        R.drawable.color_thirteen,
        R.drawable.color_fourteen,
        R.drawable.color_fifteen,
        R.drawable.color_sixteen,
        R.drawable.color_seventeen
    ).map { ColorsBG(it) }

    val listTextEditing: List<TextEdit> = listOf(
        TextEdit(R.drawable.icon_color, "Color"),
        TextEdit(R.drawable.icon_font, "Font"),
        TextEdit(R.drawable.icon_size, "Size"),
        TextEdit(R.drawable.icon_alignment, "Alignment"),
        TextEdit(R.drawable.icon_alignment_top, "Alignment Top"),
        TextEdit(R.drawable.icon_case, "Case"),
        TextEdit(R.drawable.icon_shadow, "Shadow"),
        TextEdit(R.drawable.icon_stroke, "Stroke")
    )

    var listFontEditing: List<FontEditting>? = listOf(
        R.drawable.amatic_bold,
        R.drawable.sacramento,
        R.drawable.bahiana,
        R.drawable.bubblerone,
        R.drawable.bangers,
        R.drawable.bellota,
        R.drawable.caveatbrush,
        R.drawable.bellefair,
        R.drawable.coiny,
        R.drawable.beaurivage
    ).map { FontEditting(it) }

}