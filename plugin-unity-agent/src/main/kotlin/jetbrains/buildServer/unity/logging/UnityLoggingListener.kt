

package jetbrains.buildServer.unity.logging

import jetbrains.buildServer.agent.BuildProgressLogger
import jetbrains.buildServer.agent.runner.ProcessListenerAdapter
import jetbrains.buildServer.messages.Status
import jetbrains.buildServer.messages.serviceMessages.BlockClosed
import jetbrains.buildServer.messages.serviceMessages.BlockOpened
import jetbrains.buildServer.messages.serviceMessages.Message
import jetbrains.buildServer.unity.messages.BuildProblem
import java.util.*

class UnityLoggingListener(
    private val logger: BuildProgressLogger,
    private val problemsProvider: LineStatusProvider
) : ProcessListenerAdapter() {

    private var blocks = Stack<LogBlock>()
    private val currentBlock: LogBlock
        get() = if (blocks.isEmpty()) defaultBlock else blocks.peek()

    override fun onStandardOutput(text: String) {
        var blockClosed = false
        var lastLineLogType = currentBlock.logLastLine
        var previousBlockLastLineLogType = currentBlock.logLastLine
        while (tryCloseCurrentBlock(text)) {
            if (blockClosed)
                lastLineLogType = previousBlockLastLineLogType
            blockClosed = true
            previousBlockLastLineLogType = currentBlock.logLastLine
        }

        val foundBlock = loggers.firstOrNull {
            it.isBlockStart(text)
        }
        if (foundBlock != null && foundBlock != currentBlock) {
            foundBlock.apply {
                when (logFirstLine) {
                    LogType.Outside -> {
                        logMessage(text)
                        logBlockOpened(name)
                        blocks.push(this)
                    }
                    LogType.Inside -> {
                        logBlockOpened(name)
                        blocks.push(this)
                        logMessage(text)
                    }
                    else -> {
                        logBlockOpened(name)
                        blocks.push(this)
                    }
                }
            }
        } else {
            if (!blockClosed || lastLineLogType == LogType.Outside) logMessage(text)
        }
    }


    private fun tryCloseCurrentBlock(text: String): Boolean {
        currentBlock.apply {
            if (isBlockEnd(text)) {
                when (logLastLine) {
                    LogType.Outside -> {
                        logBlockClosed(name)
                        blocks.pop()
                    }

                    LogType.Inside -> {
                        logMessage(text)
                        logBlockClosed(name)
                        blocks.pop()
                    }

                    else -> {
                        logBlockClosed(name)
                        blocks.pop()
                    }
                }
                return true
            }
        }
        return false
    }

    private fun logMessage(text: String) {
        val message = currentBlock.getText(text)
        val status = problemsProvider.getLineStatus(message)
        val serviceMessage = when (status) {
            LineStatus.Warning -> Message(message, Status.WARNING.text, null).asString()
            LineStatus.Error -> BuildProblem(message).asString()
            LineStatus.NonFatalFailure -> Message(message, Status.FAILURE.text, null).asString()
            else -> message
        }
        logger.message(serviceMessage)
    }

    private fun logBlockOpened(name: String) {
        logger.message(BlockOpened(name, null).asString())
    }

    private fun logBlockClosed(name: String) {
        logger.message(BlockClosed(name).asString())
    }

    companion object {
        private val defaultBlock = DefaultBlock()
        private val loggers = listOf(
            BuildReportBlock(),
            CommandLineBlock(),
            CompileBlock(),
            ExtensionsBlock(),
            LightmapBlock(),
            MonoBlock(),
            PackageManagerBlock(),
            PerformanceBlock(),
            PlayerStatisticsBlock(),
            PrepareBlock(),
            ScriptCompilationBlock(),
            UpdateBlock(),
            AssetDatabaseInitialRefreshBlock(),
            AssetDatabaseRefreshBlock(),
            MonoManagerReloadAssemblyBlock(),
            UnityStackTraceBlock(),
            DomainReloadProfilingBlock(),
            RefreshInfoBlock(),
            UnityCoroutineStackTraceBlock(),
            ManifestCurrentFilesBlock(),
            ManifestParsingBlock(),
            MonoDependenciesBlock(),
            CompilingShaderBlock(),
            IL2CppProgressBlock(),
            CompilationInfoBlock(),
            AssetImportingBlock(),
            MemoryStatisticsBlock(),
            GoogleStackTraceBlock(),
            InfoProcessorBlock(),
            UnityMemoryParamsBlock(),
            FailedMenuItemInsertionBlock()
        )
    }
}
