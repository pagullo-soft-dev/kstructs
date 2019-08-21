package com.softwarementors.kstructs

import com.softwarementors.kpointers.Size

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class MaxLen(val value : Size) {
}
