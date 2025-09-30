package jetbrains.buildServer.unity.logging

class UnityMemoryParamsBlock : LogBlock {

    override val name = "Unity Memory Setup"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) =
        startPattern.containsMatchIn(text)

    override fun isBlockEnd(text: String) =
        !memoryLinePattern.containsMatchIn(text)

    override fun getText(text: String) = "" // always filter out

    companion object {
        private val startPattern = Regex("\\[UnityMemory] Configuration Parameters")
        private val memoryLinePattern = Regex("memorysetup-.*")
    }
}
