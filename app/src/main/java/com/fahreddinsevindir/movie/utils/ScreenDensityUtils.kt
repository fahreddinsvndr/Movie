package com.fahreddinsevindir.movie.utils

import android.content.res.Resources


fun Float.toDP(): Float = (this / Resources.getSystem().displayMetrics.density)
fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)