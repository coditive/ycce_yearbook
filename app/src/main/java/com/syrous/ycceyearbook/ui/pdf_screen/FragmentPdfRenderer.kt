
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
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.PdfRendererBasicFragmentBinding
import java.io.File
import javax.inject.Inject

/**
 * This fragment has a big [ImageView] that shows PDF pages, and 2 [Button]s to move between pages.
 */

class FragmentPdfRenderer : Fragment() {

    @Inject
    lateinit var viewModel: PdfRendererViewModel

    private lateinit var binding: PdfRendererBasicFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = PdfRendererBasicFragmentBinding.inflate(layoutInflater, container, false)

        viewModel.pageBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            binding.pdfImageView.setImageBitmap(bitmap)
        })

        viewModel.previousEnabled.observe(viewLifecycleOwner, Observer {
            binding.previous.isEnabled = it
        })

        viewModel.nextEnabled.observe(viewLifecycleOwner, Observer {
            binding.next.isEnabled = it
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.pdfComponent().create(
            File("sample.text")
        ).inject(this@FragmentPdfRenderer)
    }
}
