package io.github.ccjhr.hikaku.extensions

import java.io.File

fun File.checkFileValidity(vararg extensions: String) = this.toPath().checkFileValidity(*extensions)