For excel to export a proper CSV file for our program the List separator must be set as ; in your settings.

To change that:
Go to the Control Panel --> Regional and Language Options (or Date, Time and Regional Options --> Regional and Language Option on some machines) --> click the Customize button. In the dialog box that opens you will see and option for "List separator" in which you can change the comma to a different character --> click Apply then Ok and then Apply again.

After you have saved the file with parameters you open it with excel.
A dialog will appear, you must press yes there and click next. In the next window, in the upper left corner, choose semicolon as the delimiter and click next. In the last window activate the first column in the preview at the bottom and shift+click on the last column to activate all of them and choose text as the column data format in the upper left corner and click finish. Now it should have imported int excel and each of the data points is in a separate column.
You can edit the data as you wish, but it must remain in the same format.

When you want to save the file then be sure to save it as a CSV(ms-dos) (macintosh should work as well) file, not as a CSV(comma delimited).

The saved file should be usable by the program.