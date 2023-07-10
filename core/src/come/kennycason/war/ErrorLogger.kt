package come.kennycason.war

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class ErrorLogger {
    private val dateTimeFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss")

    fun log(e: Exception) {
        println("Logging error to file")
        println(e.message)
        e.printStackTrace()
        createBaseDirectoryIfNeeded()

        val errorFile = File(buildErrorFilePath())

        if (e.message != null) {
            errorFile.writeText(e.message!!)
        }
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        e.printStackTrace(printWriter)
        errorFile.writeText(stringWriter.toString())
    }

    private fun buildErrorFilePath() = buildBaseErrorDirectoryPath() + "error_" + dateTimeFormat.format(Date()) +  ".yml"

    private fun buildBaseErrorDirectoryPath() = System.getProperty("user.home") + File.separator + ".ninja_turdle" + File.separator + "errors" + File.separator

    private fun createBaseDirectoryIfNeeded() {
        val errorPath = buildBaseErrorDirectoryPath()
        if (!File(errorPath).exists()) {
            println("Creating Base Errors Directory: $errorPath")
            File(errorPath).mkdirs()
        }
    }

}