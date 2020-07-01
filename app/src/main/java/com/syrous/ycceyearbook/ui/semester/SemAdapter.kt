package com.syrous.ycceyearbook.ui.semester

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.data.model.Subject
import com.syrous.ycceyearbook.databinding.SemesterCardLayoutBinding
import com.syrous.ycceyearbook.databinding.SubjectCardLayoutBinding
import timber.log.Timber
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class SemAdapter(private val semester: Semester,
private val clickHandler: FragmentSem.ClickHandler):
    RecyclerView.Adapter<SemAdapter.SubjectHolder>() {

    private var isExpanded: Boolean by Delegates.observable(false) {_: KProperty<*>, _:Boolean, newExpandedValue: Boolean ->
        if(newExpandedValue) {
            notifyItemRangeInserted(1, semester.subjectList.size)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, semester.subjectList.size)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
        Timber.d("Click Listener Called!!!")
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
           0 -> SubjectHolder.SemesterHeaderVH(SemesterCardLayoutBinding.inflate(inflater, parent, false))
           else -> SubjectHolder.SubjectInsideVH(SubjectCardLayoutBinding.inflate(inflater, parent, false))
       }
    }

    override fun getItemCount(): Int {
       return if(isExpanded) {
           (semester.subjectList.size + 1)
       } else {
           1
       }
    }

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        when(holder) {
            is SubjectHolder.SemesterHeaderVH -> { holder.bind(semester, onHeaderClickListener)}
            is SubjectHolder.SubjectInsideVH -> { holder.bind(semester.subjectList[position - 1], clickHandler)}
         }
    }


    sealed class SubjectHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        class SemesterHeaderVH(private val binding: SemesterCardLayoutBinding)
            : SubjectHolder(binding.root) {

            fun bind (semester: Semester, onClickListener: View.OnClickListener) {
                binding.semCardTextview.text = semester.name
                binding.semCardTextview.setOnClickListener(onClickListener)
            }

        }
        class SubjectInsideVH(private val binding: SubjectCardLayoutBinding)
            : SubjectHolder(binding.root) {

            fun bind (subject: Subject, clickHandler: FragmentSem.ClickHandler) {
                binding.apply {
                    semCardTextview.text = subject.course
                }
                binding.subjectCardView.setOnClickListener {
                    clickHandler.clickListener(subject)
                }
            }

        }

    }
}