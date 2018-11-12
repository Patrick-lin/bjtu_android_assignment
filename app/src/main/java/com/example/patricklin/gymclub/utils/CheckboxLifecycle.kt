package com.example.patricklin.gymclub.utils

import android.view.View

class CheckboxLifecycle<Type>(private val maxChecked: Int = -1) {
    private var checkboxMap = mutableMapOf<Type, Boolean>()
    var onMaxTargetCheckedListener: (() -> Unit)? = null
    var onBelowMaxTargetCheckedListener: (() -> Unit)? = null
    var onItemToggle: ((view: View, item: Type, state: Boolean) -> Unit)? = null

    fun toggleItem(view: View, item: Type) {
        if (checkboxMap[item] == true) {
            checkboxMap.remove(item)
            onUncheck(view, item)
        } else {
            if (maxChecked > 0 && checkboxMap.size >= maxChecked) {
                return
            }
            checkboxMap[item] = true
            onCheck(view, item)
        }
    }

    private fun onCheck(view: View, item: Type) {
        onItemToggle?.invoke(view, item, true)
        if (maxChecked > 0 && checkboxMap.size == maxChecked) {
            onMaxTargetCheckedListener?.invoke()
        }
    }

    private fun onUncheck(view: View, item: Type) {
        onItemToggle?.invoke(view, item, false)
        if (maxChecked > 0 && checkboxMap.size == maxChecked - 1) {
            onBelowMaxTargetCheckedListener?.invoke()
        }
    }

    fun isItemChecked(item: Type) = checkboxMap[item] == true
    fun isAllChecked(): Boolean = maxChecked > 0 && checkboxMap.size >= maxChecked
    fun getSelectedItems() = checkboxMap.map { it -> it.key }
}