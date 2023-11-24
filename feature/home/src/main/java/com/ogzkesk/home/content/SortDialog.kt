package com.ogzkesk.home.content

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.RadioButton
import com.ogzkesk.home.R

class SortDialog(context: Context): Dialog(context) {

    private lateinit var radioName : RadioButton
    private lateinit var radioNumber: RadioButton
    private var selectedRadio: SortType = SortType.NUMBER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sort_dialog)

        radioName = findViewById(R.id.rb_name)
        radioNumber = findViewById(R.id.rb_number)

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun showDialog(onDialogClicked: (SortType) -> Unit){
        show()

        radioName.isChecked = selectedRadio == SortType.NAME
        radioNumber.isChecked = selectedRadio == SortType.NUMBER

        radioName.setOnClickListener {
            selectedRadio = SortType.NAME
            onDialogClicked(SortType.NAME)
            dismiss()
        }

        radioNumber.setOnClickListener {
            selectedRadio = SortType.NUMBER
            onDialogClicked(SortType.NUMBER)
            dismiss()
        }
    }
}