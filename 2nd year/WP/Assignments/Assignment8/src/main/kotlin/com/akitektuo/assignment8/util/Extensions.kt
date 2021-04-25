package com.akitektuo.assignment8.util

inline fun <T : AutoCloseable?, R> T.use(block: (T) -> R) = try {
    block(this).apply { this@use?.close() }
} catch (exception: Exception) {
    this?.close()
    throw exception
}

fun renderError(error: String?) = error?.let {
    """<span class="error">$it</span>"""
} ?: ""