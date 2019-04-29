package com.ali77gh.codelive.ui.activity;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.ColorThemeData;

public class EditorTheme {

    public static void SetupTheme(final CodeView codeView) {

        ColorThemeData myTheme = ColorTheme.SOLARIZED_LIGHT.theme()
                .withBgContent(android.R.color.black)
                .withNoteColor(android.R.color.white);

        codeView.getOptions().setTheme(myTheme);
    }
}
