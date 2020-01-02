package com.test.templeteproject.utils.sectionadapter;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
interface ItemProvider {

    int getSectionCount();

    int getItemCount(int sectionIndex);

    boolean showHeadersForEmptySections();

    boolean showFooters();
}