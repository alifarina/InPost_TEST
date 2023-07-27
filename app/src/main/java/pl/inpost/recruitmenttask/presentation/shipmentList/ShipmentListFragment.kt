package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.presentation.viewmodels.ShipmentListViewModel

@AndroidEntryPoint
class ShipmentListFragment : Fragment(), CustomExpandableListAdapter.Companion.ListCallback {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var binding: FragmentShipmentListBinding
    private val viewModel: ShipmentListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        expandableListView = binding.expandableListView
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefresh.isRefreshing = false
        }
        // binding.recyclerView.layoutManager = LinearLayoutManager(context)
        return requireNotNull(binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(requireActivity()) { shipmentMap ->
            expandableListView.setAdapter(context?.let {
                CustomExpandableListAdapter(
                    it,
                    listOf("Gotowe do odbioru", "Pozostałe"),
                    shipmentMap as HashMap<String, List<ShipmentNetwork>>, this
                )


            })
            // To expand list by default
            shipmentMap.keys.forEachIndexed { index, _ ->
                expandableListView.expandGroup(index)
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }

    override fun setItemArchived(shipment_number: String) {
        viewModel.setItemArchived(shipment_number)
    }
}
