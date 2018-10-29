package com.example.angelahe.stepcounter;
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static org.hamcrest.Matchers.allOf;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.PrecisionDescriber;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.Tapper;
import android.support.test.espresso.core.deps.guava.base.Optional;
import android.support.test.espresso.util.HumanReadables;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import org.hamcrest.Matcher;
/**
 * Enables clicking on views.
 */
public final class GeneralClickAction implements ViewAction {
    private final CoordinatesProvider coordinatesProvider;
    private final Tapper tapper;
    private final PrecisionDescriber precisionDescriber;
    private final Optional<ViewAction> rollbackAction;
    public GeneralClickAction(Tapper tapper, CoordinatesProvider coordinatesProvider,
                              PrecisionDescriber precisionDescriber) {
        this(tapper, coordinatesProvider, precisionDescriber, null);
    }
    public GeneralClickAction(Tapper tapper, CoordinatesProvider coordinatesProvider,
                              PrecisionDescriber precisionDescriber, ViewAction rollbackAction) {
        this.coordinatesProvider = coordinatesProvider;
        this.tapper = tapper;
        this.precisionDescriber = precisionDescriber;
        this.rollbackAction = Optional.fromNullable(rollbackAction);
    }
    @Override
    @SuppressWarnings("unchecked")
    public Matcher<View> getConstraints() {
        Matcher<View> standardConstraint = isDisplayingAtLeast(75);
        if (rollbackAction.isPresent()) {
            return allOf(standardConstraint, rollbackAction.get().getConstraints());
        } else {
            return standardConstraint;
        }
    }

    @Override
    public void perform(UiController uiController, View view) {
        float[] coordinates = coordinatesProvider.calculateCoordinates(view);
        float[] precision = precisionDescriber.describePrecision();
        Tapper.Status status = Tapper.Status.FAILURE;
        int loopCount = 0;
        while (status != Tapper.Status.SUCCESS && loopCount < 3) {
            try {
                status = tapper.sendTap(uiController, coordinates, precision);
            } catch (RuntimeException re) {
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(re)
                        .build();
            }
            int duration = ViewConfiguration.getPressedStateDuration();
            // ensures that all work enqueued to process the tap has been run.
            if (duration > 0) {
                uiController.loopMainThreadForAtLeast(duration);
            }
            if (status == Tapper.Status.WARNING) {
                if (rollbackAction.isPresent()) {
                    rollbackAction.get().perform(uiController, view);
                } else {
                    break;
                }
            }
            loopCount++;
        }
        if (status == Tapper.Status.FAILURE) {
            throw new PerformException.Builder()
                    .withActionDescription(this.getDescription())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(new RuntimeException(String.format("Couldn't "
                                    + "click at: %s,%s precision: %s, %s . Tapper: %s coordinate provider: %s precision " +
                                    "describer: %s. Tried %s times. With Rollback? %s", coordinates[0], coordinates[1],
                            precision[0], precision[1], tapper, coordinatesProvider, precisionDescriber, loopCount,
                            rollbackAction.isPresent())))
                    .build();
        }
        if (tapper == Tap.SINGLE && view instanceof WebView) {
            // WebViews will not process click events until double tap
            // timeout. Not the best place for this - but good for now.
            uiController.loopMainThreadForAtLeast(ViewConfiguration.getDoubleTapTimeout());
        }
    }
    @Override
    public String getDescription() {
        return tapper.toString().toLowerCase() + " click";
    }
}
