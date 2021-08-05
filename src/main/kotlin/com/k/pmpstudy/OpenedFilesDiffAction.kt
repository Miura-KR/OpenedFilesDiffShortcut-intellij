package com.k.pmpstudy

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class OpenedFilesDiffAction : AnAction() {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = (project != null)
        fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project!!)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val data: Array<VirtualFile> = fileEditorManagerEx.currentWindow.files
//        val data = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY) as Array<VirtualFile>
        Messages.showMessageDialog(data.joinToString("\n"), "Error", Messages.getInformationIcon()) // 1ファイルだけ表示
//        Messages.showMessageDialog(data[1].toString(), "Error", Messages.getInformationIcon()) // 何も表示されない
//        Messages.showMessageDialog(data.size.toString(), "Error", Messages.getInformationIcon()) // 1
    }
}