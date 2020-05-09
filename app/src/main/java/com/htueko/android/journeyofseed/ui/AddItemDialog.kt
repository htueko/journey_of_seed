package com.htueko.android.journeyofseed.ui

//interface AddItemDialogListener {
//    fun onSaveButtonClicked(plant: PlantModel)
//}
//
//class DialogAddItem(context: Context, private var listener: AddItemDialogListener) :
//    AppCompatDialog(context) {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_add_item)
//
//        val name = edt_name_dialog_add_item.text.toString()
//        val location = edt_location_dialog_add_item.text.toString()
//
//        btn_save_dialog_add_item.setOnClickListener {
//            Log.d("TAG", "btn $name, $location")
//            validateInput(name, location)
//            dismiss()
//        }
//
//        btn_cancel_dialog_add_item.setOnClickListener {
//            cancel()
//        }
//
//    }
//
//    private fun validateInput(name: String, location: String) {
//        Log.d("TAG", "$name, $location")
//        if (name.isNotEmpty()) {
//            if (location.isNotEmpty()) {
//                    val item = PlantModel(name, location, "")
//                    listener.onSaveButtonClicked(item)
//            }
//        } else if (name.isEmpty()) {
//            // to show error, request the keyboard focus and show the keyboard
//            textinput_name_dialog_add_item.error = R.string.text_name_error.toString()
//            edt_name_dialog_add_item.apply {
//                requestFocus()
//                showKeyboard(context, this)
//            }
//        } else if (location.isEmpty()) {
//            // to show error, request the keyboard focus and show the keyboard
//            textinput_location_dialog_add_item.error = R.string.text_location_error.toString()
//            edt_location_dialog_add_item.apply {
//                requestFocus()
//                showKeyboard(context, this)
//            }
//        } else{
//            return
//        }
//
//    }
//
//}