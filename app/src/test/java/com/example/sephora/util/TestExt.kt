package com.example.sephora.util

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

fun <T> lenientGiven(methodCall: T): OngoingStubbing<T> = Mockito.lenient().`when`(methodCall)
fun <T> OngoingStubbing<T>.willReturn(value: T): OngoingStubbing<T> = this.thenReturn(value)