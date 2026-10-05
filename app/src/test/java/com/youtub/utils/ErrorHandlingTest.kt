/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.utils

import org.junit.Assert.*
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class ErrorHandlingTest {

    @Test
    fun `fromThrowable maps Network errors correctly`() {
        assertTrue(YouTubError.fromThrowable(UnknownHostException()) is YouTubError.Network)
        assertTrue(YouTubError.fromThrowable(IOException()) is YouTubError.Network)
    }

    @Test
    fun `fromThrowable maps AuthError correctly`() {
        assertTrue(YouTubError.fromThrowable(SecurityException()) is YouTubError.AuthError)
    }

    @Test
    fun `fromThrowable maps ApiThrottled correctly`() {
        val throttledException = Exception("HTTP 429 Too Many Requests")
        assertTrue(YouTubError.fromThrowable(throttledException) is YouTubError.ApiThrottled)
    }

    @Test
    fun `getMessage returns human readable strings`() {
        assertEquals("No internet connection", YouTubError.Network.getMessage())
        assertEquals("Authentication required", YouTubError.AuthError.getMessage())
        assertEquals("Custom Error", YouTubError.Unknown("Custom Error").getMessage())
    }
}
