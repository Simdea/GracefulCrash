package pt.simdea.gracefulcrash.sample.views.androidviews.mvpimpl;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import pt.simdea.gracefulcrash.sample.R;
import pt.simdea.gracefulcrash.sample.views.androidviews.custom.LockableText;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IHomeViewMvp;

public class HomeViewMvpImpl extends BaseViewMvpImpl
        implements IHomeViewMvp {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvTest)
    TextView mTestResult;

    private StartTestViewMvcListener mTestStartListener;

    private final LockableText mTestResultLockable;

    @SuppressWarnings("WeakerAccess")
    @BindDrawable(android.R.drawable.btn_default)
    Drawable testBackground;

    @BindString(R.string.lockable_view_locked)
    String mLockedViewText;
    @BindString(R.string.lockable_view_locked_instruction)
    String mLockedViewInstructionText;
    @BindString(R.string.lockable_view_unlocked)
    String mUnlockedViewText;

    @Inject
    public HomeViewMvpImpl(@NonNull final LayoutInflater layoutInflater, @Nullable final ViewGroup container) {
        super(layoutInflater, container);
        // add this MVP View's initialization here
        mTestResult.setBackground(testBackground);
        mTestResultLockable = new LockableText(mTestResult, false);
    }

    /** {@inheritDoc} */
    @Override
    public Bundle getViewState() {
        return null; // return the proper Bundle for this MVP View
    }

    /** {@inheritDoc} */
    @Override
    @LayoutRes
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    /** {@inheritDoc} */
    @Override
    public void setTestStartListener(@NonNull final StartTestViewMvcListener listener) {
        mTestStartListener = listener;
    }

    /** {@inheritDoc} */
    @Override
    public void onDataProcessedByModel(@NonNull final String content) {
        mTestResult.setText(content);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.bTest)
    protected void callTestExecution() {
        mTestStartListener.onTestStartRequest();
        testLockMechanism(true);
    }

    @SuppressWarnings("unused")
    @OnLongClick(R.id.bTest)
    protected boolean unlockView() {
        testLockMechanism(false);
        return true;
    }

    private void testLockMechanism(final boolean lock) {
        if (lock) {
            if (mTestResultLockable.isLocked()) {
                mTestResult.setText(mLockedViewInstructionText);
            } else {
                mTestResultLockable.setLocked();
                mTestResult.setText(mLockedViewText);
            }
        } else {
            if (mTestResultLockable.isLocked()) {
                mTestResultLockable.setUnlocked();
                mTestResult.setText(mUnlockedViewText);
            }
        }
    }

    // Add remaining base logic here...

}
