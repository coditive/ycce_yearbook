package com.syrous.ycceyearbook.ui.semester

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.data.model.Subject
import com.syrous.ycceyearbook.databinding.SemesterCardLayoutBinding
import com.syrous.ycceyearbook.databinding.SubjectCardLayoutBinding
import timber.log.Timber

class SemAdapter(private val semName: String,
                 private val sem: Int,
                 private val redirectClickHandler: FragmentSem.RedirectClickHandler,
                 private val toggleClickHandler: FragmentSem.ToggleStateClickHandler):
    ListAdapter<Subject, SemAdapter.SubjectHolder>(CALLBACK){

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
            is SubjectHolder.SemesterHeaderVH -> holder.bind(semName, sem, toggleClickHandler)
            is SubjectHolder.SubjectInsideVH -> holder.bind(getItem(position), redirectClickHandler)
        }
    }


    sealed class SubjectHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        class SemesterHeaderVH(private val binding: SemesterCardLayoutBinding)
            : SubjectHolder(binding.root) {
            fun bind (semName: String, sem: Int, toggleClickHandler: FragmentSem.ToggleStateClickHandler) {
                binding.semCardTextview.apply {
                    text = semName
                    setOnClickListener {
                        toggleClickHandler.clickListener(sem)
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
            }
        }
    }

    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Subject> () {
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem.course_code == newItem.course_code
            }

            override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return  oldItem.course_code == oldItem.course_code
            }
        }

        const val SEMESTER = 0
        const val SUBJECT = 1
    }
}