package jetbrains.buildServer.unity.logging

class GoogleStackTraceBlock : LogBlock {

    override val name = "... Google Log"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) =
        blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) =
        blockEndPatterns.any { it.containsMatchIn(text) }

    override fun getText(text: String): String {
        return when {
            filterOut.containsMatchIn(text) -> ""
            isBlockEnd(text) -> text + "\n"
            else -> text
        }
    }

    companion object {
        private val blockStart = Regex("Google.Logger:Log")

        private val blockEndPatterns = listOf(
            Regex("(^\\s*\\(Filename:.*)|(\\s*\\[.* line \\d+\\])"),
            Regex("UnityEditor.EditorAssemblies:ProcessInitializeOnLoadAttributes"),
            Regex("UnityEditor.EditorApplication:Internal_CallUpdateFunctions")
        )

        private val filterOut =
            Regex("^(UnityEngine.Debug|UnityEngine.Logger|UnityEngine.StackTraceUtility).*$")
    }
}
