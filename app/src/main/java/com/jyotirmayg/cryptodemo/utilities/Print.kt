package com.jyotirmayg.cryptodemo.utilities

import android.util.Log

object Print {
    private const val TAG = "TestDemo"

    fun e(msg: String?) {
        val ste: StackTraceElement = try {
            Throwable().stackTrace[1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e(TAG, msg!!)
            return
        }
        val caller = ste.fileName.replace(".java", " ") + ":: " + ste.methodName + " - "
        Log.e(TAG, caller + msg)
    }

    fun d(msg: String) {
        val ste: StackTraceElement = try {
            Throwable().stackTrace[1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.d(TAG, msg)
            return
        }
        val caller = ste.fileName.replace(".java", " ") + ":: " + ste.methodName + " - "
        Log.d(TAG, caller + msg)
    }

    fun v(msg: String) {
        val ste: StackTraceElement = try {
            Throwable().stackTrace[1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.v(TAG, msg)
            return
        }
        val caller = ste.fileName.replace(".java", " ") + ":: " + ste.methodName + " - "
        Log.v(TAG, caller + msg)
    }

    fun i(msg: String) {
        val ste: StackTraceElement = try {
            Throwable().stackTrace[1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.i(TAG, msg)
            return
        }
        val caller = ste.fileName.replace(".java", " ") + ":: " + ste.methodName + " - "
        Log.i(TAG, caller + msg)
    }

    fun getStackTrace(e: Exception?): String? {
        return try {
            Log.getStackTraceString(e)
        } catch (ex: Exception) {
            e(ex.message)
            return ex.message
        }
    }

    fun printStackTrace(e: Exception) {
        try {
            e.printStackTrace()
            e(e.message)
        } catch (ex: Exception) {
            e(ex.message)
        }
    }
}