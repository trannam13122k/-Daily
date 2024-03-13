package com.example.daily.util

import com.example.daily.R
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorsBG
import com.example.daily.ui.fragment.edit.textEditing.PickerItem
import com.example.daily.ui.fragment.edit.textEditing.pickerItem.ItemColorModel

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

    var listColor: List<ItemColorModel> = listOf(
        ItemColorModel("#5369EC", 0, R.drawable.color_one, "Color", 0),
        ItemColorModel("#4BD392", 0, R.drawable.color_two, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.color_three, "Color", 0),
        ItemColorModel("#FFD6B0", 0, R.drawable.color_four, "Color", 0),
        ItemColorModel("#B0FAFF", 0, R.drawable.color_five, "Color", 0),
        ItemColorModel("#FFB0B0", 0, R.drawable.color_six, "Color", 0),
        ItemColorModel("#EA6E14", 0, R.drawable.color_seven, "Color", 0),
        ItemColorModel("#2984C6", 0, R.drawable.color_eight, "Color", 0),
        ItemColorModel("#3A4790", 0, R.drawable.color_nine, "Color", 0),
        ItemColorModel("#225E4F", 0, R.drawable.color_ten, "Color", 0),
        ItemColorModel("#C1DA28", 0, R.drawable.color_eleven, "Color", 0),
        ItemColorModel("#791BB2", 0, R.drawable.color_twelve, "Color", 0),
        ItemColorModel("#76412A", 0, R.drawable.color_thirteen, "Color", 0),
        ItemColorModel("#EFFD53", 0, R.drawable.color_fourteen, "Color", 0),
        ItemColorModel("#717797", 0, R.drawable.color_fifteen, "Color", 0),
        ItemColorModel("#16D0F9", 0, R.drawable.color_sixteen, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.color_seventeen, "Color", 0),
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

    val listTextEditings: List<PickerItem> = listOf(
        PickerItem(R.drawable.icon_color, "Color"),
        PickerItem(R.drawable.icon_font, "Font"),
        PickerItem(R.drawable.icon_size, "Size"),
        PickerItem(R.drawable.icon_alignment, "Alignment"),
        PickerItem(R.drawable.icon_alignment_top, "Alignment Top"),
        PickerItem(R.drawable.icon_case, "Case"),
        PickerItem(R.drawable.icon_shadow, "Shadow"),
        PickerItem(R.drawable.icon_stroke, "Stroke")
    )

    val listFont: List<ItemColorModel> = listOf(
        ItemColorModel("", R.font.amatic_bold, R.drawable.amatic_bold, "Font", 0),
        ItemColorModel("", R.font.sacramento_regular, R.drawable.sacramento, "Font", 0),
        ItemColorModel("", R.font.bahiana_regular, R.drawable.bahiana, "Font", 0),
        ItemColorModel("", R.font.bangers_regular, R.drawable.bangers, "Font", 0),
        ItemColorModel("", R.font.bellota_regular, R.drawable.bellota, "Font", 0),
        ItemColorModel("", R.font.caveat_brush_regular, R.drawable.caveatbrush, "Font", 0),
        ItemColorModel("", R.font.bellefair_regular, R.drawable.bellefair, "Font", 0),
        ItemColorModel("", R.font.coiny_regular, R.drawable.coiny, "Font", 0),
        ItemColorModel("", R.font.beau_rivage_regular, R.drawable.bellefair, "Font", 0)
    )

    val listSize: List<ItemColorModel> = listOf(
        ItemColorModel("", 0, R.drawable.size1, "Size", 16),
        ItemColorModel("", 0, R.drawable.size2, "Size", 18),
        ItemColorModel("", 0, R.drawable.size3, "Size", 20),
        ItemColorModel("", 0, R.drawable.size4, "Size", 22),
        ItemColorModel("", 0, R.drawable.size5, "Size", 24),

        )

    var listAligment: List<ItemColorModel> = listOf(
        ItemColorModel("#5369EC", 0, R.drawable.aligmentleft, "Color", 0),
        ItemColorModel("#4BD392", 0, R.drawable.aligmentcenter, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.aligmentright, "Color", 0)
    )

    var listAligmentTop: List<ItemColorModel> = listOf(
        ItemColorModel("#4BD392", 0, R.drawable.aligntop, "Color", 0),
        ItemColorModel("#5369EC", 0, R.drawable.aligncenter, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.alignbottom, "Color", 0)
    )

    var listCase: List<ItemColorModel> = listOf(
        ItemColorModel("#4BD392", 0, R.drawable.case1, "Color", 0),
        ItemColorModel("#5369EC", 0, R.drawable.case2, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.case3, "Color", 0)
    )

    var listStroke : List<ItemColorModel> = listOf(
        ItemColorModel("#4BD392", 0, R.drawable.stroke1, "Color", 0),
        ItemColorModel("#5369EC", 0, R.drawable.stroke2, "Color", 0),
        ItemColorModel("#EC8DF4", 0, R.drawable.stroke3, "Color", 0)
    )


}