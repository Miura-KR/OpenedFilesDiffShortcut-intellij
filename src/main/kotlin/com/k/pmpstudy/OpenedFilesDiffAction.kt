package com.k.pmpstudy

import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffManager
import com.intellij.diff.contents.DiffContent
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class OpenedFilesDiffAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = (project != null)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val fileEditorManagerEx: FileEditorManagerEx = FileEditorManagerEx.getInstanceEx(e.project!!)
        val data: Array<EditorWindow> = fileEditorManagerEx.splitters.windows

        if (data.size <= 1) {
            Messages.showMessageDialog(
                "At least 2 windows is needed.",
                "Error",
                Messages.getInformationIcon()
            )
        } else {
            val contentList: List<DiffContent> =
                data.map { it.selectedFile }
                    .map { DiffContentFactory.getInstance().create(e.project, it) }
                    .take(2)
                    .toList()
            val request = SimpleDiffRequest(
                "Opened Files Diff",
                contentList[0],
                contentList[1],
                "Cont1",
                "Cont2"
            )
            DiffManager.getInstance().showDiff(e.project, request)
        }
    }
}