package com.syrous.ycceyearbook.ui.home.bottom_nav

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.FontRes
import androidx.annotation.MenuRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import com.syrous.ycceyearbook.R
import kotlin.math.abs

class BottomNavView : View {

    /**
     *  Default attribute values
     */

    private var barBackgroundColor = Color.parseColor(WHITE_COLOR_HEX)
    private var barIndicatorColor = Color.parseColor(DEFAULT_INDICATOR_COLOR)
    private var barIndicatorRadius = d2p(DEFAULT_CORNER_RADIUS)
    private var barSideMargins = d2p(DEFAULT_SIDE_MARGIN)
    private var barCornerRadius = d2p(DEFAULT_BAR_CORNER_RADIUS)

    private var itemPadding = d2p(DEFAULT_ICON_SIZE)
    private var itemAnimDuration = DEFAULT_ANIM_DURATION

    private var itemIconSize = d2p(DEFAULT_ICON_SIZE)
    private var itemIconMargin = d2p(DEFAULT_ICON_MARGIN)
    private var itemIconTint = Color.parseColor(DEFAULT_TINT)
    private var itemIconTintActive = Color.parseColor(WHITE_COLOR_HEX)

    private var itemTextColor = Color.parseColor(WHITE_COLOR_HEX)
    private var itemTextSize = d2p(DEFAULT_TEXT_SIZE)

    @FontRes
    private var itemFontFamily: Int = 0

    /**
     * Dynamic variables
     */
    private var itemWidth: Float = 0F
    private var activeItemIndex: Int = 0
    private var currentIconTint = itemIconTintActive
    private var indicatorLocation = barSideMargins

    private var items = listOf<BottomNavItem>()

    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var onItemReselectedListener: OnItemReselectedListener? = null

    private var onItemSelected: (Int) -> Unit = {}
    private var onItemReselected: (Int) -> Unit = {}

    private val rect = RectF()

    private val paintBackground = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barBackgroundColor
    }

    private val paintIndicator = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val paintText = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = itemTextColor
        textSize = itemTextSize
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BottomNavView, 0, 0)
        barBackgroundColor = typedArray.getColor(R.styleable.BottomNavView_backgroundColor, this.barBackgroundColor)
        barIndicatorColor = typedArray.getColor(R.styleable.BottomNavView_indicatorColor, this.barIndicatorColor)
        barIndicatorRadius = typedArray.getDimension(R.styleable.BottomNavView_indicatorRadius, this.barIndicatorRadius)
        barSideMargins = typedArray.getDimension(R.styleable.BottomNavView_sideMargins, this.barSideMargins)
        barCornerRadius = typedArray.getDimension(R.styleable.BottomNavView_cornerRadius, this.barCornerRadius)
        itemPadding = typedArray.getDimension(R.styleable.BottomNavView_itemPadding, this.itemPadding)
        itemTextColor = typedArray.getColor(R.styleable.BottomNavView_textColor, this.itemTextColor)
        itemTextSize = typedArray.getDimension(R.styleable.BottomNavView_textSize, this.itemTextSize)
        itemIconSize = typedArray.getDimension(R.styleable.BottomNavView_iconSize, this.itemIconSize)
        itemIconTint = typedArray.getColor(R.styleable.BottomNavView_iconTint, this.itemIconTint)
        itemIconTintActive = typedArray.getColor(R.styleable.BottomNavView_iconTintActive, this.itemIconTintActive)
        activeItemIndex = typedArray.getInt(R.styleable.BottomNavView_activeItem, this.activeItemIndex)
        itemFontFamily = typedArray.getResourceId(R.styleable.BottomNavView_itemFontFamily, this.itemFontFamily)
        itemAnimDuration = typedArray.getInt(R.styleable.BottomNavView_duration, this.itemAnimDuration.toInt()).toLong()
        items = BottomNavParser(context, typedArray.getResourceId(R.styleable.BottomNavView_menu, 0)).parse()
        typedArray.recycle()

        // Update default attribute values
        paintBackground.color = barBackgroundColor
        paintIndicator.color = barIndicatorColor
        paintText.color = itemTextColor
        paintText.textSize = itemTextSize

        if (itemFontFamily != 0) {
            paintText.typeface = ResourcesCompat.getFont(context, itemFontFamily)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        var lastX = barSideMargins
        itemWidth = (width - (barSideMargins * 2)) / items.size

        for(item in items) {
            // Prevent text overflow by shortening the item title
            var shorted = false
            while (paintText.measureText(item.title) > itemWidth - itemIconSize - itemIconMargin - (itemPadding * 2)) {
                item.title = item.title.dropLast(1)
                shorted = true
            }

            // Add ellipsis character to item text if it is shorted
            if (shorted) {
                item.title = item.title.dropLast(1)
                item.title += context.getString(R.string.ellipsis)
            }

            item.rect = RectF(lastX, 0f, itemWidth + lastX, height.toFloat())
            lastX += itemWidth

        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (barCornerRadius > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                barCornerRadius,
                barCornerRadius,
                paintBackground
            )
        } else {
            canvas.drawRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                paintBackground
            )
        }

        // Draw indicator
        rect.left = indicatorLocation
        rect.top = items[activeItemIndex].rect.centerY() - itemIconSize / 2 - itemPadding
        rect.right = indicatorLocation + itemWidth
        rect.bottom = items[activeItemIndex].rect.centerY() + itemIconSize / 2 + itemPadding

        canvas.drawRoundRect(
            rect,
            barIndicatorRadius,
            barIndicatorRadius,
            paintIndicator
        )

        val textHeight = (paintText.descent() + paintText.ascent()) / 2

        for ((index, item) in items.withIndex()) {
            val textLength = paintText.measureText(item.title)

            item.icon.mutate()
            item.icon.setBounds(
                item.rect.centerX().toInt() - itemIconSize.toInt() / 2 - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                height / 2 - itemIconSize.toInt() / 2,
                item.rect.centerX().toInt() + itemIconSize.toInt() / 2 - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                height / 2 + itemIconSize.toInt() / 2
            )

            DrawableCompat.setTint(
                item.icon,
                if(index == activeItemIndex) currentIconTint else itemIconTint
            )

            item.icon.draw(canvas)
            this.paintText.alpha = item.alpha

            canvas.drawText(
                item.title,
                item.rect.centerX() + itemIconSize / 2 + itemIconMargin,
                item.rect.centerY() - textHeight, paintText
            )
        }

    }

    /**
     * Handle item clicks
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && abs(event.downTime - event.eventTime) < 500) {
            for ((itemId, item) in items.withIndex()) {
                if (item.rect.contains(event.x, event.y)) {
                    if (itemId != this.activeItemIndex) {
                        setActiveItem(itemId)
                        onItemSelected(itemId)
                        onItemSelectedListener?.onItemSelect(itemId)
                    } else {
                        onItemReselected(itemId)
                        onItemReselectedListener?.onItemReselect(itemId)
                    }
                }
            }
        }

        return true
    }


    private fun d2p(dp: Float): Float {
        return resources.displayMetrics.densityDpi.toFloat() / 160.toFloat() * dp
    }

    @SuppressLint("ResourceType")
    fun setMenuRes(@MenuRes menuRes: Int, activeItem: Int? = null) {
        items = BottomNavParser(context, menuRes).parse()
        invalidate()

        activeItem?.let {
            setActiveItem(it)
        }
    }

    fun setActiveItem(pos: Int) {
        activeItemIndex = pos

        for ((index, item) in items.withIndex()) {
            if (index == pos) {
                animateAlpha(item, OPAQUE)
            } else {
                animateAlpha(item, TRANSPARENT)
            }
        }

        animateIndicator(pos)
        animateIconTint()
    }

    fun getActiveItem(): Int {
        return activeItemIndex
    }

    private fun animateAlpha(item: BottomNavItem, to: Int) {
        val animator = ValueAnimator.ofInt(item.alpha, to)
        animator.duration = itemAnimDuration

        animator.addUpdateListener {
            val value = it.animatedValue as Int
            item.alpha = value
            invalidate()
        }

        animator.start()
    }

    private fun animateIndicator(pos: Int) {
        val animator = ValueAnimator.ofFloat(indicatorLocation, items[pos].rect.left)
        animator.duration = itemAnimDuration
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            indicatorLocation = animation.animatedValue as Float
        }
        animator.start()
    }

    private fun animateIconTint() {
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), itemIconTint, itemIconTintActive)
        animator.duration = itemAnimDuration
        animator.addUpdateListener {
            currentIconTint = it.animatedValue as Int
        }
        animator.start()
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.onItemSelectedListener = listener
    }

    fun setOnItemReselectedListener(listener: OnItemReselectedListener) {
        this.onItemReselectedListener = listener
    }

    fun setupWithNavController(menu: Menu, navController: NavController){
        NavigationComponentHelper.setupWithNavController(menu,this,navController)
    }



}