package com.jibase.utils.image

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.jibase.BaseApp
import com.jibase.utils.getBitmapResource
import com.jibase.utils.getDrawableResource
import com.jibase.utils.log
import com.jibase.utils.print

/**
 * Create level drawable runtim
 * @param defaultResource: int default drawable if  paths isEmpty
 * @params paths: list name of drawable (tip: not contain .png, .jpg ...)
 * @return drawable: levelist drawable
 */
fun createLevelDrawable(defaultResource: Int, vararg paths: String): Drawable {
    try {
        val levelDrawable = LevelListDrawable()
        paths.forEachIndexed { index, s ->
            if (s.isEmpty()) {
                return getDrawableResource(defaultResource)
            }
            levelDrawable.addLevel(index, index, Drawable.createFromPath(s))
        }
        return levelDrawable
    } catch (e: Exception) {
        e.print()
    }
    return getDrawableResource(defaultResource)
}

/***
 * @param defaultResource: default resId in drawable
 * @param selectPath: select resource name in drawable
 * @param normalPath: normal resource name in drawable
 */
fun createSelectDrawable(defaultResource: Int, selectPath: String, normalPath: String): Drawable {
    if (selectPath.isNotEmpty() && normalPath.isNotEmpty()) {
        try {
            val stateDrawable = StateListDrawable()
            stateDrawable.addState(intArrayOf(android.R.attr.state_selected), Drawable.createFromPath(selectPath))
            stateDrawable.addState(StateSet.WILD_CARD, Drawable.createFromPath(normalPath))
            return stateDrawable
        } catch (e: Exception) {
            e.print()
        }
    }
    return getDrawableResource(defaultResource) as Drawable
}

/**
 * @param defaultResource: default resId in drawable
 * @param path: path of file like explore
 * @return drawable or defaultDrawable
 */
fun createDrawable(defaultResource: Int, path: String): Drawable {
    if (path.isNotEmpty()) {
        try {
            return Drawable.createFromPath(path)
        } catch (e: Exception) {
            e.print()
        }
    }
    return getDrawableResource(defaultResource)
}

/**
 * @param path: path of file in asset: ex: icon/ix.png
 */
fun createDrawableFormAsset(path: String): Drawable? {
    return Drawable.createFromStream(BaseApp.instance.assets.open(path), "")
}



/**
 * Create circle bitmap
 * @param bitmap: bitmap src require not null
 * @return: bitmap or null
 */
fun createCircleBitmap(bitmap: Bitmap): Bitmap? {
    val output = Bitmap.createBitmap(bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val color = Color.RED
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawOval(rectF, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}

/**
 * Create rounded bimap
 * @param bitmap: bitmap src require not null
 * @param radius: radius of rounded
 * @return: bimap or null
 */
fun createRoundedBitmap(bitmap: Bitmap, radius: Float = 10f): Bitmap? {
    val round = RoundedBitmapDrawableFactory.create(BaseApp.instance.resources, bitmap)
    round.cornerRadius = radius
    return round.bitmap
}

/**
 * Create rounded bimap
 * @param id: int from drawable
 * @param radius: radius of rounded
 * @return: bimap or null
 */

fun createRoundedBitmap(id: Int, radius: Float = 10f): Bitmap? {
    return createRoundedBitmap(getBitmapResource(id), radius)
}

/**
 * Create rounded bitmap
 * @param path: string of file like explore
 * @param radius: radius of rounded
 * @return: bitmap or null
 */

fun createRoundedBitmap(path: String, radius: Float = 10f): Bitmap? {
    try {
        val bm = BitmapFactory.decodeFile(path)
        return createRoundedBitmap(bm, radius)
    } catch (e: Exception) {
        e.print()
    }
    return null
}

/**
 * Create bitmap from string
 * @param path: string of file like explore
 * @return: bitmap or null
 */
fun createBitmap(path: String): Bitmap? {
    if (path.isNotEmpty()) {
        try {
            return BitmapFactory.decodeFile(path)
        } catch (e: Exception) {
            e.print()
        }
    }
    return null
}
