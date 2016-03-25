package cmput301w16t15.shareo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import mvc.AppUserSingleton;
import mvc.ShareoData;
import mvc.Thing;

public class SearchFragment extends Fragment {
    private static String TAG ="SearchFragment";
    private List<Thing> searchList;
    private ListView mListView;
    private SearchView mSearchView;
    private CustomAdapters.ThingWithStatusAdapter mListAdapter;
    private RadioGroup mRadioGroup;
    private String mQueryText;
    private String mSearchField;

    private Handler mHandler;
    private Runnable mRunnable;

    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mListView = (ListView) v.findViewById(R.id.listview);
        mListAdapter = new CustomAdapters.ThingWithStatusAdapter(this.getContext(), R.layout.detailed_thing_row, new ArrayList<Thing>());
        mListView.setAdapter(mListAdapter);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);

        if (mSearchField == null) {
            mSearchField = "description";
            mRadioGroup.check(R.id.description);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.description:
                        mSearchField = "description";
                        new SearchTask().execute();
                        break;
                    case R.id.category:
                        mSearchField = "category";
                        new SearchTask().execute();
                        break;
                    case R.id.name:
                        mSearchField = "name";
                        new SearchTask().execute();
                        break;
                    case R.id.ownerID:
                        mSearchField = "ownerID";
                        new SearchTask().execute();
                        break;
                }
            }
        });

        mHandler = new Handler();

        mSearchView = (SearchView) v.findViewById(R.id.searchbox);
        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                mQueryText = query;
                if (mRunnable != null) {
                    mHandler.removeCallbacks(mRunnable);
                }
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        new SearchTask().execute(mSearchField, query);
                    }
                };
                mHandler.postDelayed(mRunnable, 500);

                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddBidFragment abf = new AddBidFragment();
                Bundle bundle = new Bundle();

                bundle.putSerializable("myThing", searchList.get(position));
                //bundle.putString("ownerName", searchList.get(position).getOwner().getFullName());
                bundle.putInt("search", position);
                abf.setArguments(bundle);
                abf.show(getActivity().getFragmentManager(), "search");
            }
        });

        return v;
    }

    class SearchTask extends AsyncTask<String, Void, List<Thing>> {

        @Override
        protected List<Thing> doInBackground(String... params) {
            List<Thing> res = ShareoData.getInstance().getGamesByField(mSearchField, mQueryText);
            List<Thing> filtered = new ArrayList<>();
            for (Thing t : res) {
                if (t.getOwner().getJestID().equals(AppUserSingleton.getInstance().getUser().getJestID())) {
                    continue;
                }
                filtered.add(t);
            }
            return filtered;
        }

        @Override
        protected void onPostExecute(List<Thing> res) {
            mListAdapter = new CustomAdapters.ThingWithStatusAdapter(getActivity(), R.layout.detailed_thing_row, res);
            mListView.setAdapter(mListAdapter);
            mListAdapter.notifyDataSetChanged();
            searchList = res;
        }
    }
}
