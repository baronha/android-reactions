package com.github.pgreze.reactions.sample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import com.github.pgreze.reactions.PopupGravity
import com.github.pgreze.reactions.ReactionPopup
import com.github.pgreze.reactions.dsl.reactionConfig
import com.github.pgreze.reactions.dsl.reactionPopup
import com.github.pgreze.reactions.dsl.reactions

fun MainActivity.setupTopRight() {
    // Popup DSL + listener via function
    val popup1 = reactionPopup(this, ::onReactionSelected) {
        reactions {
            resId    { R.drawable.ic_crypto_btc }
            resId    { R.drawable.ic_crypto_eth }
            resId    { R.drawable.ic_crypto_ltc }
            reaction { R.drawable.ic_crypto_dash scale ImageView.ScaleType.FIT_CENTER }
            reaction { R.drawable.ic_crypto_xrp scale ImageView.ScaleType.FIT_CENTER }
            drawable { drawable(R.drawable.ic_crypto_xmr) }
            drawable { drawable(R.drawable.ic_crypto_doge) }
            reaction { drawable(R.drawable.ic_crypto_steem) scale ImageView.ScaleType.FIT_CENTER }
            reaction { drawable(R.drawable.ic_crypto_kmd) scale ImageView.ScaleType.FIT_CENTER }
            drawable { drawable(R.drawable.ic_crypto_zec) }
        }
        reactionTexts = R.array.crypto_symbols
        popupCornerRadius = 40
        popupColor = Color.LTGRAY
        popupAlpha = 255
        reactionSize = resources.getDimensionPixelSize(R.dimen.crypto_item_size)
        horizontalMargin = resources.getDimensionPixelSize(R.dimen.crypto_item_margin)
        verticalMargin = horizontalMargin / 2
    }
    // Setter also available
    popup1.reactionSelectedListener = { position ->
        toast("$position selected")
        true
    }
    findViewById<View>(R.id.top_right_btn).setOnTouchListener(popup1)
}

fun MainActivity.setupRight() {
    // Config DSL + listener in popup constructor
    val config = reactionConfig(this) {
        reactionsIds = intArrayOf(
            R.drawable.ic_crypto_btc,
            R.drawable.ic_crypto_eth,
            R.drawable.ic_crypto_ltc,
            R.drawable.ic_crypto_dash,
            R.drawable.ic_crypto_xrp,
            R.drawable.ic_crypto_xmr,
            R.drawable.ic_crypto_doge,
            R.drawable.ic_crypto_steem,
            R.drawable.ic_crypto_kmd,
            R.drawable.ic_crypto_zec
        )
        reactionTextProvider = { position -> "Item $position" }
        popupGravity = PopupGravity.PARENT_RIGHT
        popupMargin = resources.getDimensionPixelSize(R.dimen.crypto_item_size)
        textBackground = ColorDrawable(Color.TRANSPARENT)
        textColor = Color.BLACK
        textHorizontalPadding = 0
        textVerticalPadding = 0
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
        popupAlpha = 255
    }
    val popup2 = ReactionPopup(this, config) { position -> true.also {
        toast("$position selected")
    } }
    findViewById<View>(R.id.right_btn).setOnTouchListener(popup2)
    val strings = arrayOf("like", "love", "laugh", "wow", "sad", "angry")
    val reactionArray = intArrayOf(R.drawable.like, R.drawable.love, R.drawable.haha, R.drawable.wow, R.drawable.sad, R.drawable.angry)
    val reactionArrayColor = intArrayOf(R.color.color_like, R.color.color_love, R.color.color_got_it, R.color.orange,  R.color.orange,  R.color.orange)
    val fbButton = findViewById<AppCompatCheckBox>(R.id.fb_btn)
    val config3 = reactionConfig(this) {
        reactionsIds = reactionArray
        reactionTextProvider = { position -> strings[position] }
        textBackground = ColorDrawable(Color.TRANSPARENT)
        textColor = Color.BLACK
        textHorizontalPadding = 0
        textVerticalPadding = 0
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
        popupColor = Color.BLACK
    }
    val popup3 = ReactionPopup(this, config3) { position ->
        true.also {
            toast("$position selected")
            if (position >= 0) {
                fbButton.apply {
                    isChecked = true
                    text = strings[position]
                    setTextColor(ContextCompat.getColor(context, reactionArrayColor[position]))
                    buttonDrawable = ContextCompat.getDrawable(context,reactionArray[position])
                }
            } else {
                fbButton.apply {
                    text = context.getString(R.string.txt_like)
                    tag = context.getString(R.string.txt_like)
                    setTextColor(ContextCompat.getColor(context, R.color.light_gray))
                    buttonDrawable = ContextCompat.getDrawable(context,R.drawable.ic_like_gray)
                }
            }
        }
    }
    fbButton.setOnLongClickListener(popup3)

    fbButton.setOnClickListener {
        fbButton.apply {
            buttonDrawable = ContextCompat.getDrawable(context,R.drawable.bg_like_selector)

            if (isChecked) {
                text = context.getString(R.string.txt_like_it)
                setTextColor(ContextCompat.getColor(context, R.color.color_like))
            } else {
                text = context.getString(R.string.txt_like)
                setTextColor(ContextCompat.getColor(context, R.color.light_gray))
            }
        }
    }
}

fun MainActivity.onReactionSelected(position: Int) = true.also {
    toast("$position selected")
}

fun MainActivity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT)
            .apply { setGravity(Gravity.CENTER, 0, 300) }
            .show()
}

@Suppress("DEPRECATION")
fun MainActivity.drawable(@DrawableRes id: Int): Drawable =
    resources.getDrawable(id)
