package com.milan.jpmorganchase.`interface`

import android.view.View
import java.util.*

interface ListClickListener {
    fun onClickListener(view: View, pos: Int, objects: Any)
}