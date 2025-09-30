package jetbrains.buildServer.unity.logging

class MonoManagerReloadAssemblyBlock : LogBlock {

    override val name = "MonoManager ReloadAssembly"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Begin MonoManager ReloadAssembly.*")
        private val blockEnd = Regex("Mono: successfully reloaded assembly.*")
    }
}