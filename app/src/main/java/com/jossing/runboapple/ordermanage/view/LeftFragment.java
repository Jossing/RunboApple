package com.jossing.runboapple.ordermanage.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jossing.runboapple.R;
import com.jossing.runboapple.order.model.Order;
import com.jossing.runboapple.ordermanage.adapter.OrdersAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeftFragment.Listener} interface
 * to handle interaction events.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE = "ARG_PAGE";


    // TODO: Rename and change types of parameters
    private int page;

    private View rootView;
    private RelativeLayout rlEmpty;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvMyOrders;
    private OrdersAdapter adapter;

    private Listener mListener;

    public LeftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page 在 viewPager 中的页数.
     * @return A new instance of fragment LeftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeftFragment newInstance(int page) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_left, container, false);

        initWidget();

        refreshLayout.setRefreshing(true);
        onRefresh();

        return rootView;
    }

    private void initWidget() {
        rlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        adapter = new OrdersAdapter(getActivity(), "buyer");
        rvMyOrders = (RecyclerView) rootView.findViewById(R.id.rv_my_orders_left);
        rvMyOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyOrders.setHasFixedSize(true);
        rvMyOrders.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
            ((MyOrderActivity)getActivity()).queryOrders("buyer");
        }
    }

    public void onQueryOrdersDone(List<Order> orders) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (orders == null || orders.size() == 0) {
            rlEmpty.setVisibility(View.VISIBLE);
        } else {
            rlEmpty.setVisibility(View.GONE);
            adapter.setOrders(orders);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface Listener {
        // TODO: Update argument type and name
        // void onLeftQueryOrdersDone(List<Order> orders);
    }
}
