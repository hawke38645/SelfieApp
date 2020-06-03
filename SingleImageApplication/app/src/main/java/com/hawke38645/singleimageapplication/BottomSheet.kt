package com.hawke38645.singleimageapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet: BottomSheetDialogFragment() {

    private var listener: BottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    fun setBottomSheetListener(bottomSheetListener: BottomSheetListener) {
        listener = bottomSheetListener
    }

    interface BottomSheetListener {
        fun onButtonClicked(buttonName: String)
    }
}