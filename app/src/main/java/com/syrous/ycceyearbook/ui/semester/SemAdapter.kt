package com.syrous.ycceyearbook.ui.semester

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.SemesterCardLayoutBinding
import com.syrous.ycceyearbook.databinding.SubjectCardLayoutBinding
import com.syrous.ycceyearbook.model.SemSubModel
import com.syrous.ycceyearbook.model.Semester
import com.syrous.ycceyearbook.model.Subject
import timber.log.Timber

class SemAdapter(private val redirectClickHandler: FragmentSem.RedirectClickHandler,
                 private val index: Int,
                 private val toggleSubjectList: (sem: Int, index: Int) -> Unit):
    ListAdapter<SemSubModel, SemAdapter.SubjectHolder>(CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
        return when(viewType) {
            SEMESTER -> {
                Timber.d("CreateView For SemesterHeader Called!!!")
                SubjectHolder.SemesterHeaderVH(SemesterCardLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
            }

            SUBJECT -> SubjectHolder.SubjectInsideVH(SubjectCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

            else -> throw IllegalArgumentException("invalid view holder request : $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) SEMESTER
        else SUBJECT
    }

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        when (holder) {
            is SubjectHolder.SemesterHeaderVH -> holder.bind(getItem(position) as Semester, index, toggleSubjectList)
            is SubjectHolder.SubjectInsideVH -> holder.bind(getItem(position) as Subject, redirectClickHandler)
        }
    }


    sealed class SubjectHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        class SemesterHeaderVH(private val binding: SemesterCardLayoutBinding)
            : SubjectHolder(binding.root) {
            fun bind (semester: Semester, index: Int, toggleSubjectList: (sem:Int, index: Int) -> Unit) {
                binding.apply {
                    semCardTextview.apply {
                        text = semester.name
                    }

                    semCardView.setOnClickListener {
                       toggleSubjectList(semester.sem, index)
                    }
                }


                Timber.d("Semester Header Binding Called!!")
            }
        }
        class SubjectInsideVH(private val binding: SubjectCardLayoutBinding)
            : SubjectHolder(binding.root) {
            fun bind (subject: Subject, redirectClickHandler: FragmentSem.RedirectClickHandler) {
                binding.apply {
                    semCardTextview.text = subject.course
                }
                binding.subjectCardView.setOnClickListener {
                    redirectClickHandler.clickListener(subject)
                }
                Timber.d("Subject Binding Called!!!")
            }
        }
    }

    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<SemSubModel> () {
            override fun areItemsTheSame(oldItem: SemSubModel, newItem: SemSubModel): Boolean =
                if(oldItem is Semester && newItem is Semester)
                    oldItem.sem == newItem.sem
                else if(oldItem is Subject && newItem is Subject)
                    oldItem.course_code == newItem.course_code
                else false


            override fun areContentsTheSame(oldItem: SemSubModel, newItem: SemSubModel): Boolean =
                if(oldItem is Semester && newItem is Semester)
                    oldItem.name == newItem.name
                else if(oldItem is Subject && newItem is Subject)
                    oldItem.course == newItem.course
                else true
        }

        const val SEMESTER = 0
        const val SUBJECT = 1
    }
}
