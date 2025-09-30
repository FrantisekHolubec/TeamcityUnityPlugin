package jetbrains.buildServer.unity.logging

class DomainReloadProfilingBlock : LogBlock {

    override val name = "Domain Reload Profiling:"

    override val logFirstLine = LogType.None

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Domain Reload Profiling:.*")
        private val blockEnd = Regex("AwakeInstancesAfterBackupRestoration.*")
    }
}