package fr.hgerard;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

/**
 * Created by hadri on 12/02/2016.
 */
@RunWith(CustomRoboelectricTestRunner.class)
public class LibraryActivityTest {


    LibraryActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(LibraryActivity.class);
    }

    @Test
    public void should_only_display_book_list() throws Exception
    {
        // Given
        // When
        // Then
        Assertions.assertThat(activity.mainFrameLayout).isVisible();
        Assertions.assertThat(activity.secondFrameLayout).isGone();
    }

}
