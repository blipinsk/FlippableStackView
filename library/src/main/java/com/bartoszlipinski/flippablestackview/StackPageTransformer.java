package com.bartoszlipinski.flippablestackview;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.bartoszlipinski.flippablestackview.exception.WrongValueException;

/**
 * Created by Bartosz Lipinski
 * 28.01.15
 */
public class StackPageTransformer implements ViewPager.PageTransformer {
    public enum Gravity {
        TOP, CENTER, BOTTOM
    }

    private int mNumberOfStacked;

    private float mAlphaFactor;
    private float mZeroPositionScale;
    private float mStackedScaleFactor;
    private float mOverlapFactor;
    private float mOverlap;
    private float mTopSpace;
    private float mBottomSpace;

    private boolean mInitialValuesCalculated = false;

    private Gravity mGravity;

    /**
     * Used to construct the basic method for visual transformation in <code>FlippableStackView</code>.
     *
     * @param numberOfStacked  Number of pages stacked under the current page.
     * @param currentPageScale Scale of the current page. Must be a value from (0, 1].
     * @param topStackedScale  Scale of the top stacked page. Must be a value from
     *                         (0, <code>currentPageScale</code>].
     * @param overlapFactor    Defines the usage of available space for the overlapping by stacked
     *                         pages. Must be a value from [0, 1]. Value 1 means that the whole
     *                         available space (obtained due to the scaling with
     *                         <code>currentPageScale</code>) will be used for the purpose of displaying
     *                         stacked views. Value 0 means that no space will be used for this purpose
     *                         (in other words - no stacked views will be visible).
     * @param gravity          Specifies the alignment of the stack (vertically) withing <code>View</code>
     *                         bounds.
     */
    public StackPageTransformer(int numberOfStacked, float currentPageScale, float topStackedScale, float overlapFactor, Gravity gravity) {
        validateValues(currentPageScale, topStackedScale, overlapFactor);

        mNumberOfStacked = numberOfStacked;
        mAlphaFactor = 1.0f / (mNumberOfStacked + 1);
        mZeroPositionScale = currentPageScale;
        mStackedScaleFactor = (currentPageScale - topStackedScale) / mNumberOfStacked;
        mOverlapFactor = overlapFactor;
        mGravity = gravity;
    }

    @Override
    public void transformPage(View view, float position) {
        int height = view.getHeight();

        if (!mInitialValuesCalculated) {
            mInitialValuesCalculated = true;
            calculateInitialValues(height);
        }

        view.setRotationX(0);
        view.setPivotY(height / 2f);
        view.setPivotX(view.getWidth() / 2f);

        if (position < -mNumberOfStacked - 1) {
            view.setAlpha(0f);
        } else if (position <= 0) {
            float scale = mZeroPositionScale + (position * mStackedScaleFactor);
            float baseTranslationY = (-position * height);
            float shiftTranslation = calculateShiftForScale(position, scale, height);
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationY(baseTranslationY + shiftTranslation);
            view.setAlpha(1.0f + (position * mAlphaFactor));
        } else if (position <= 1) {
            float baseTranslationY = position * height;
            float scale = mZeroPositionScale - position;
            scale = (scale < 0) ? 0f : scale;
            float shiftTranslation = (1.0f - position) * mOverlap;
            view.setPivotY(height);
            view.setRotationX(-position * 90);
            view.setScaleX(mZeroPositionScale);
            view.setScaleY(scale);
            view.setTranslationY(-baseTranslationY - mBottomSpace - shiftTranslation);
            view.setAlpha(1.0f - position);
        } else if (position > 1) {
            view.setAlpha(0f);
        }
    }

    private void calculateInitialValues(int height) {
        float scaledHeight = mZeroPositionScale * height;

        float overlapBase = (height - scaledHeight) / (mNumberOfStacked + 1);
        mOverlap = overlapBase * mOverlapFactor;

        float mVerticalSpace = 0.5f * height * (1 - mOverlapFactor) * (1 - mZeroPositionScale);
        switch (mGravity) {

            case TOP:
                mTopSpace = 0;
                mBottomSpace = 2 * mVerticalSpace;
                break;
            case CENTER:
                mTopSpace = mVerticalSpace;
                mBottomSpace = mVerticalSpace;
                break;
            case BOTTOM:
                mTopSpace = 2 * mVerticalSpace;
                mBottomSpace = 0;
                break;
        }
    }

    private float calculateShiftForScale(float position, float scale, int height) {
        //difference between centers
        return mTopSpace + ((mNumberOfStacked + position) * mOverlap) + (height * 0.5f * (scale - 1));
    }

    private void validateValues(float currentPageScale, float topStackedScale, float overlapFactor) {
        if (currentPageScale <= 0 || currentPageScale > 1) {
            throw new WrongValueException(this.getClass().getSimpleName() + ": Current page scale not correctly defined. " +
                    "Be sure to set it to value from (0, 1].");
        }

        if (topStackedScale <= 0 || topStackedScale > currentPageScale) {
            throw new WrongValueException(this.getClass().getSimpleName() + ": Top stacked page scale not correctly defined. " +
                    "Be sure to set it to value from (0, currentPageScale].");
        }

        if (overlapFactor < 0 || overlapFactor > 1) {
            throw new WrongValueException(this.getClass().getSimpleName() + ": Overlap factor not correctly defined. " +
                    "Be sure to set it to value from [0, 1].");
        }
    }

}
