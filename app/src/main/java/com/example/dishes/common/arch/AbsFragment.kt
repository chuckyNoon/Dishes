package com.example.dishes.common.arch

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class AbsFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId)