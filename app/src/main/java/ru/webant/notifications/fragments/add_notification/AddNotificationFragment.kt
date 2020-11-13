package ru.webant.notifications.fragments.add_notification

import android.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_add_notification.*
import ru.webant.notifications.App
import ru.webant.notifications.R
import ru.webant.notifications.fragments.base.BaseFragment
import ru.webant.notifications.utils.openTimePickerFragment

class AddNotificationFragment : BaseFragment(), AddNotificationView {

    override val layoutId = R.layout.fragment_add_notification

    @InjectPresenter
    lateinit var presenter: AddNotificationPresenter

    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideAddNotificationPresenter()

    override fun setUpUI() {
        super.setUpUI()

        val spinnerAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)
        spinnerAdapter.addAll(presenter.getActionsList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerAction.adapter = spinnerAdapter
    }

    override fun setUpListeners() {
        super.setUpListeners()
        setUpTimeListeners()

        spinnerAction.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                presenter.onActionChanged(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        tvCancel.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        bSave.setOnClickListener {
            presenter.onSaveNotificationClicked()
        }
    }

    override fun openStartTimePickerFragment(initialTime: Long) {
        fragmentManager?.let {
            openTimePickerFragment(it, initialTime) { hourOfDay: Int, minute: Int ->
                presenter.onStartTimeChanged(hourOfDay, minute)
            }
        }
    }

    override fun openEndTimePickerFragment(initialTime: Long) {
        fragmentManager?.let {
            openTimePickerFragment(it, initialTime) { hourOfDay: Int, minute: Int ->
                presenter.onEndTimeChanged(hourOfDay, minute)
            }
        }
    }

    override fun showStartTime(startTimeText: String) {
        etStartTime.setText(startTimeText, TextView.BufferType.EDITABLE)
    }

    override fun showEndTime(endTimeText: String) {
        etEndTime.setText(endTimeText, TextView.BufferType.EDITABLE)
    }

    override fun showSuccessfulDialog(messageId: Int) {
        AlertDialog.Builder(context)
            .setMessage(resources.getString(messageId))
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    private fun setUpTimeListeners() {
        etStartTime.setOnClickListener {
            presenter.onStartTimeClicked()
        }

        etStartTime.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                presenter.onStartTimeClicked()
            }
        }

        etEndTime.setOnClickListener {
            presenter.onEndTimeClicked()
        }

        etEndTime.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                presenter.onEndTimeClicked()
            }
        }

        etRepeat.doOnTextChanged { text, _, _, _ -> presenter.onRepeatTimeChanged(text) }
    }
}