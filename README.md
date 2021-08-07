# OpenedFilesDiffShortcut-intellij

The Action showing difference between the files opened in split windows.

Execute from `Tools > Show Opened Files Diff`.

Work at following conditions.

- Case1 : Editor is not split, and at least 2 tabs are opened.
  - Show differences between the first 2 files.
- Case2 : At least 2 split windows are opened.
  - Show differences between the files being displayed on the first 2 split windows.

---

IntelliJで開いている2つのファイルの差分表示をするアクションを追加するIntelliJプラグインです。

`Tools > Show Opened Files Diff`から実行できます。

以下のような条件で所定の動作をします。
- エディターの画面分割をしておらず、タブに2つ以上のファイルを開いている場合
  - 先頭の2ファイルの差分表示する。
- エディターを2画面以上に分割している場合
  - 初めに開いた2画面それぞれで表示中のファイルの差分表示をする。
