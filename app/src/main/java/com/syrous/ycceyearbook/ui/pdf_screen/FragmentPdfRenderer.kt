
package com.syrous.ycceyearbook.ui.pdf_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPdfRendererBinding
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * This fragment has a big [ImageView] that shows PDF pages, and 2 [Button]s to move between pages.
 */

class FragmentPdfRenderer : Fragment() {

    @Inject
    lateinit var viewModel: PdfRendererViewModel

    private val args: FragmentPdfRendererArgs by navArgs()

    private var count = 1

    private lateinit var binding: FragmentPdfRendererBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPdfRendererBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("File name = ${args.pdfRef}")
        val paperFile = requireActivity().getExternalFilesDir("papers/${args.pdfRef}") as File

        (requireActivity().application as YearBookApplication).appComponent.pdfComponent().create(
            paperFile
        ).inject(this@FragmentPdfRenderer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pageBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            binding.pdfImageView.setImageBitmap(bitmap)
        })

        binding.pageNumber.text = count.toString()

        viewModel.previousEnabled.observe(viewLifecycleOwner, Observer {
            binding.previous.isEnabled = it
        })

        viewModel.nextEnabled.observe(viewLifecycleOwner, Observer {
            binding.next.isEnabled = it
        })

        binding.apply {
            previous.setOnClickListener {
                binding.pageNumber.text = (count - 1).toString()
                viewModel.showPrevious()
            }
            next.setOnClickListener {
                binding.pageNumber.text = (count + 1).toString()
                viewModel.showNext()
            }
        }
    }
}
