package com.example.daily.util

import android.view.Gravity
import com.example.daily.R
import com.example.daily.ui.fragment.categories.model.Content
import com.example.daily.ui.fragment.themes.edit.backGroundEditing.colorEdittingBG.ColorsBG
import com.example.daily.ui.fragment.themes.edit.textEditing.textEffect.PickerItem
import com.example.daily.ui.fragment.themes.edit.textEditing.textEffect.pickerItem.ItemPickerModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.AddYourOwnFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite.FavouriteFragment
import com.example.daily.ui.fragment.settingDaiLy.setting.general.GeneralFragment
import com.example.daily.ui.fragment.settingDaiLy.setting.reminders.RemindersFragment
import com.example.daily.ui.fragment.settingDaiLy.settingMain.SettingModel
import com.example.daily.ui.fragment.settingDaiLy.setting.widgets.WidgetsFragment
import com.example.daily.ui.fragment.themes.edit.backGroundEditing.unsplash.UnsplashModel
import com.example.daily.ui.fragment.themes.themBackground.background.model.TitleBackgroundModel

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

    var listColor: List<ItemPickerModel> = listOf(
        ItemPickerModel("#5369EC", 0, R.drawable.color_one, "Color", 0, 0, 0, ""),
        ItemPickerModel("#4BD392", 0, R.drawable.color_two, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EC8DF4", 0, R.drawable.color_three, "Color", 0, 0, 0, ""),
        ItemPickerModel("#FFD6B0", 0, R.drawable.color_four, "Color", 0, 0, 0, ""),
        ItemPickerModel("#B0FAFF", 0, R.drawable.color_five, "Color", 0, 0, 0, ""),
        ItemPickerModel("#FFB0B0", 0, R.drawable.color_six, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EA6E14", 0, R.drawable.color_seven, "Color", 0, 0, 0, ""),
        ItemPickerModel("#2984C6", 0, R.drawable.color_eight, "Color", 0, 0, 0, ""),
        ItemPickerModel("#3A4790", 0, R.drawable.color_nine, "Color", 0, 0, 0, ""),
        ItemPickerModel("#225E4F", 0, R.drawable.color_ten, "Color", 0, 0, 0, ""),
        ItemPickerModel("#C1DA28", 0, R.drawable.color_eleven, "Color", 0, 0, 0, ""),
        ItemPickerModel("#791BB2", 0, R.drawable.color_twelve, "Color", 0, 0, 0, ""),
        ItemPickerModel("#76412A", 0, R.drawable.color_thirteen, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EFFD53", 0, R.drawable.color_fourteen, "Color", 0, 0, 0, ""),
        ItemPickerModel("#717797", 0, R.drawable.color_fifteen, "Color", 0, 0, 0, ""),
        ItemPickerModel("#16D0F9", 0, R.drawable.color_sixteen, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EC8DF4", 0, R.drawable.color_seventeen, "Color", 0, 0, 0, ""),
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

    val listFont: List<ItemPickerModel> = listOf(
        ItemPickerModel("", R.font.amatic_bold, R.drawable.amatic_bold, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.sacramento_regular, R.drawable.sacramento, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.bahiana_regular, R.drawable.bahiana, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.bangers_regular, R.drawable.bangers, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.bellota_regular, R.drawable.bellota, "Font", 0, 0, 0, ""),
        ItemPickerModel(
            "",
            R.font.caveat_brush_regular,
            R.drawable.caveatbrush,
            "Font",
            0,
            0,
            0,
            ""
        ),
        ItemPickerModel("", R.font.bellefair_regular, R.drawable.bellefair, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.coiny_regular, R.drawable.coiny, "Font", 0, 0, 0, ""),
        ItemPickerModel("", R.font.beau_rivage_regular, R.drawable.bellefair, "Font", 0, 0, 0, "")
    )

    val listSize: List<ItemPickerModel> = listOf(
        ItemPickerModel("", 0, R.drawable.size1, "Size", 15, 0, 0, ""),
        ItemPickerModel("", 0, R.drawable.size2, "Size", 19, 0, 0, ""),
        ItemPickerModel("", 0, R.drawable.size3, "Size", 23, 0, 0, ""),
        ItemPickerModel("", 0, R.drawable.size4, "Size", 26, 0, 0, ""),
        ItemPickerModel("", 0, R.drawable.size5, "Size", 35, 0, 0, ""),

        )

    var listAligment: List<ItemPickerModel> = listOf(
        ItemPickerModel("#5369EC", 0, R.drawable.aligmentleft, "Color", 0, Gravity.START, 0, ""),
        ItemPickerModel(
            "#4BD392",
            0,
            R.drawable.aligmentcenter,
            "Color",
            0,
            Gravity.NO_GRAVITY,
            0,
            ""
        ),
        ItemPickerModel("#EC8DF4", 0, R.drawable.aligmentright, "Color", 0, Gravity.RIGHT, 0, "")
    )

    var listAligmentTop: List<ItemPickerModel> = listOf(
        ItemPickerModel("#4BD392", 0, R.drawable.aligntop, "Color", 0, 0, Gravity.TOP, ""),
        ItemPickerModel(
            "#5369EC",
            0,
            R.drawable.aligncenter,
            "Color",
            0,
            0,
            Gravity.NO_GRAVITY,
            ""
        ),
        ItemPickerModel("#EC8DF4", 0, R.drawable.alignbottom, "Color", 0, 0, Gravity.BOTTOM, "")
    )

    var listCase: List<ItemPickerModel> = listOf(
        ItemPickerModel("#4BD392", 0, R.drawable.case1, "Color", 0, 0, 0, "toUpperCase"),
        ItemPickerModel("#5369EC", 0, R.drawable.case2, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EC8DF4", 0, R.drawable.case3, "Color", 0, 0, 0, "toLowerCase")
    )

    var listStroke: List<ItemPickerModel> = listOf(
        ItemPickerModel("#4BD392", 0, R.drawable.stroke1, "Color", 0, 0, 0, ""),
        ItemPickerModel("#5369EC", 0, R.drawable.stroke2, "Color", 0, 0, 0, ""),
        ItemPickerModel("#EC8DF4", 0, R.drawable.stroke3, "Color", 0, 0, 0, "")
    )


    val listSettings = listOf(
//        SettingModel("General", R.drawable.icon_setting, GeneralFragment::class.java),
        SettingModel("Reminders", R.drawable.icon_reminders, RemindersFragment::class.java),
        SettingModel("Widgets", R.drawable.icon_widgets, WidgetsFragment::class.java),
        SettingModel("Purchase", R.drawable.icon_purchase, null)
    )

    val listAffirmations = listOf(
        SettingModel("Collection", R.drawable.icon_book_open, CollectionsFragment::class.java),
        SettingModel("Add your own", R.drawable.feather, AddYourOwnFragment::class.java),
        SettingModel("Favorite", R.drawable.icon_favourite, FavouriteFragment::class.java)
    )

    val listTitleBg = listOf(
        "Most popular",
        "Free today",
        "Plain",
        "Spiritual",
        "Iridescent",
        "Tropical",
        "Flower",
        "Gems",
        "Sun set",
        "Timeless heritage"
    ).map { TitleBackgroundModel(it) }

    val listTitleContent = listOf(
        TitleBackgroundModel("Most popular"),
        TitleBackgroundModel("Improve your relationships"),
        TitleBackgroundModel("Throught provoking"),
        TitleBackgroundModel("Improve your mindset"),
        TitleBackgroundModel("Look on the bright side"),
        TitleBackgroundModel("Stay mentally strong"),
    )

    val listCategories = listOf(
        Content(
            "General", "true", listOf(
                " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
                " The only way to do great work is to love what you do.- Steve Jobs ",
                " The only way to do great work is to love what you do. - Steve Jobs ",
                " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
                " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
                " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
                " Life is a journey, not a destination. - Ralph Waldo Emerson ",
                " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
                " The only way to do great work is to love what you do.- Steve Jobs ",
                " The only way to do great work is to love what you do. - Steve Jobs ",
                " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
                " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
                " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
                " Life is a journey, not a destination. - Ralph Waldo Emerson ",
            ), R.drawable.icon_general
        ),
        Content("My favorite", "true", listOf("456", "789"), R.drawable.icon_favourite),
        Content("My affirmations", "true", listOf(), R.drawable.icon_user_content),
    )

    val listDataLocal = listOf(
        " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
        " The only way to do great work is to love what you do.- Steve Jobs ",
        " The only way to do great work is to love what you do. - Steve Jobs ",
        " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
        " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
        " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
        " Life is a journey, not a destination. - Ralph Waldo Emerson ",
        " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
        " The only way to do great work is to love what you do.- Steve Jobs ",
        " The only way to do great work is to love what you do. - Steve Jobs ",
        " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
        " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
        " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
        " Life is a journey, not a destination. - Ralph Waldo Emerson ",
    )

    val listUnsplash = listOf(
        UnsplashModel(R.drawable.bg_one),
        UnsplashModel(R.drawable.bg_two),
        UnsplashModel(R.drawable.bg_three),
        UnsplashModel(R.drawable.bg_five),
        UnsplashModel(R.drawable.bg_four),
        UnsplashModel(R.drawable.bg_six),
        UnsplashModel(R.drawable.bg_seven),
        UnsplashModel(R.drawable.bg_eight),
        UnsplashModel(R.drawable.bg_nine)
    )
}