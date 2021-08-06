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
    private lateinit var fileEditorManagerEx: FileEditorManagerEx

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = (project != null)
        fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project!!)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
//        val data: Array<VirtualFile> = fileEditorManagerEx.currentWindow.files
        val data: Array<EditorWindow> = fileEditorManagerEx.splitters.windows
        val file0 = data[0].selectedFile
        val file1 = data[1].selectedFile
        val cont1: DiffContent = DiffContentFactory.getInstance().create(e.project, file0)
        val cont2: DiffContent = DiffContentFactory.getInstance().create(e.project, file1)
        val request = SimpleDiffRequest("Opened Files Diff", cont1, cont2, "Cont1", "Cont2")
        DiffManager.getInstance().showDiff(e.project, request)
//        val data = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY) as Array<VirtualFile>
//        Messages.showMessageDialog(data.joinToString("\n"), "Error", Messages.getInformationIcon()) // 1ファイルだけ表示
//        Messages.showMessageDialog(data[1].toString(), "Error", Messages.getInformationIcon()) // 何も表示されない
//        Messages.showMessageDialog(data.size.toString(), "Error", Messages.getInformationIcon()) // 1
//        Messages.showMessageDialog(file0.toString() + "\n" + file1.toString(), "Error", Messages.getInformationIcon()) // 1ファイルだけ表示
    }
}