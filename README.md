# api project

### Installation

1. Java 11
2. Checkout the 'master' branch
3. Install lombok plugin in IntelliJ IDEA (preferences -> plugins -> marketplace -> Lombok plugin). Restart IntelliJ
   IDEA
4. Enable `Reformat code` and `Optimize imports` in settings before committing (See IDE Settings)

### Branch naming

Format:
initials + ticket id + short actionable description of what this task is about.</br>
Example: ok_sn-4444-add-download-document-test</br> If there is no task, skip ticked id and write only initials +
description.

### Commit & pull request naming

Commit message should has short, clear and meaningful summary in the imperative: "Fix bug" and not "Fixed bug" or "Fixes
bug". Capitalize the subject line and remove unnecessary punctuation marks.

Format: [ticket id] + commit message </br>
Example: [SN-4444] Add send invite for document group tests

Use same naming logic for pull request. Also add links to Jira task and test case to pull request’s description. If
there is no task, add short explanation of what is done.

Note: Avoid commits messages like: “some fix”, “review fix”, "cosmetic", "final", etc. Even if you have some pull
requests changes you should always use clear commit messages.

### IDE Settings

Go to any Test class, in menu bar of IntelliJ IDEA go to `Code` and then `Reformat file...` and in appeared window set
(toggle, so they are applied) next values to have an opportunity to prettify your class via hotkey:

Scope:

* Whole file

Options:

* Optimize imports
* Rearrange code
* Code cleanup

Commit settings that ought to be turned on:

* Reformat code
* Rearrange code
* Optimize imports
* Analyze code
* Check TODO
* Cleanup
* Perform SonarLint analysis

Go to "Settings" -> "Editor" -> "Code Style" and check "Enable EditorConfig support" checkbox. Better to Invalidate
caches and restart after all these changes: "File" -> "Invalidate caches..." then check:

* Clear file system cache and Local History
* Clear VCS Log caches and indexes
* Mark downloaded shared indexes as excluded

checkboxes and click "Invalidate and Restart" button

