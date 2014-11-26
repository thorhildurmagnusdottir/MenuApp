package com.gunnarsturla.menuapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import menu.Item;
import menu.Order;
import menu.SubMenu;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//	private static final String ARG_PARAM1 = "param1";
//	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	//private String mParam1;
	//private String mParam2;

	private OnFragmentInteractionListener mListener;

	// Geymsla til að geyma Item sem hefur verið eytt,
	// en mun hugsanlega verða "undo-að"
	Item removedItem;


	// Stuffs til að halda utan um RecyclerView
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;

	private View editingItem;

	private TextView orderTotal;
	private View orderContainer;


	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment OrderFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static OrderFragment newInstance() { // (String param1, String param2) {
		OrderFragment fragment = new OrderFragment();
		/*Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);*/
		return fragment;
	}

	public OrderFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_order, container, false);

		mRecyclerView = (RecyclerView) v.findViewById(R.id.order_listR);
		orderTotal = (TextView) v.findViewById(R.id.orderTotal);

		Button borga = (Button) v.findViewById(R.id.payButton);
		borga.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Vilt þú staðfesta þessa pöntun");
                    alertDialog.setButton("Já", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity().getApplicationContext(), "Pöntunin er staðfest og send inn í eldhús", Toast.LENGTH_LONG).show();
                            SubMenu order = Order.getOrder(); // submenu med orderinu
                            JsonOrder pontun = new JsonOrder();
                            pontun.OrderToJSon(order);
                            Order.pay();
                            getActivity().finish();
                        }
                    });
                    alertDialog.setButton2("Nei", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }

		});

		Log.i("Fragment:", "Setting RV");


		View orderContainer = v.findViewById(R.id.orderContainer);

		return v;
	}

    // TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);



	}

	@Override
	public void onStart() {
		super.onStart();
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// specify an adapter (see also next example)
		mAdapter = new OrderListAdapter(this);
		mRecyclerView.setAdapter(mAdapter);

		orderTotal.setText(Order.getTotal() + " kr.");
		editingItem = null;
		updateTotal();


		Log.i("Fragment:", "Starting Fragment");


	}
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

    public void updateTotal() {
        orderTotal.setText(Order.getTotal() + " kr.");
    }

    /**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

	public void setRemovedItem(Item i) {
		removedItem = i;
	}
	public Item getRemovedItem() {
		return removedItem;
	}
	public void openFragment(){

	}

	public void enableComment(View v) {

		if(editingItem != null)
			disableComment(editingItem);

		TextView commentView = (TextView) v.findViewById(R.id.orderItemComment);
		EditText editComment = (EditText) v.findViewById(R.id.orderEditComment);

		String comment = (String) commentView.getText();

		editComment.setText(comment);

		commentView.setVisibility(View.INVISIBLE);
		editComment.setVisibility(View.VISIBLE);

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				disableComment(v);
			}
		});

		editingItem = v;
	}

	public void disableComment(View v) {

		TextView commentView = (TextView) v.findViewById(R.id.orderItemComment);
		EditText editComment = (EditText) v.findViewById(R.id.orderEditComment);
		TextView itemPosition = (TextView) v.findViewById(R.id.orderItemPosition);

		int pos = Integer.parseInt((String) itemPosition.getText());
		String comment = editComment.getText().toString();

		Order.setComment(pos, comment);

		commentView.setText(comment);

		commentView.setVisibility(View.VISIBLE);
		editComment.setVisibility(View.INVISIBLE);

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enableComment(v);
			}
		});

		editingItem = null;
	}

}
