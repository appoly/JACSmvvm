    package com.jacs.mvvm.base;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.activity.OnBackPressedCallback;
    import androidx.annotation.Nullable;
    import androidx.databinding.DataBindingUtil;
    import androidx.databinding.ViewDataBinding;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProviders;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;

    import com.jacs.mvvm.interfaces.JACSOnTopFragmentFoundListener;


public abstract class JACSBaseFragment<BindingType extends ViewDataBinding, ViewModelType extends JACSViewModel> extends Fragment {

    private View view;
    private Observer<Integer> navigationObserver;
    protected ViewModelType viewModel;

    /**
     * viewModelClass is the class of the viewModel for this view,  i.e FirstFragment would have FirstFragmentViewModel.class
     * <p></p>
     * This must be set in the OnCreate method of a fragment
     */
    protected Class<ViewModelType> viewModelClass;
    /**
     * layoutID is the resource id for the layout that this fragment will display,  i.e: R.layout.fragment_one
     * <p></p>
     * This must be set in the OnCreate method of a fragment
     */
    protected int layoutID;
    /**
     * viewBinding is a base level binding that you should cast to your fragments binding, for Example: "FragmentOneBinding mBinding = (FragmentOneBinding) viewBinding;"
     * <p></p>
     * This must be set in the OnCreate method of a fragment
     */
    protected BindingType viewBinding;

    /**
     * isKeepingView is a boolean which, when set to true, will hold a reference to this fragments view so that it is not re-created
     * <p></p>
     * This should be set in the OnCreate method of a fragment
     */
    protected boolean isKeepingView = false;

    /**
     * isKeepingView is a boolean which, when set to true, will hold a reference to this fragments view so that it is not re-created
     * <p></p>
     * This should be set in the OnCreate method of a fragment
     */
    protected boolean isOverridingOnbackPressed = true;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (viewModelClass != null) {
            viewModel = ViewModelProviders.of(this).get(viewModelClass);
        }

        setUpOnbackPressed();

        if (isKeepingView) {
            if (view == null) {
                view = inflater.inflate(layoutID, container, false);
                viewBinding = DataBindingUtil.bind(view);
                viewBinding.setLifecycleOwner(this);
                setupViews();
            }
            return view;
        }

        view = inflater.inflate(layoutID, container, false);
        setupViews();
        if (viewModel != null){
            setUpDataBinding(view);
        }
        return view;
    }



    private void setUpOnbackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(isOverridingOnbackPressed) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(getView());
                if (!navController.navigateUp()) {
                    getActivity().finish();
                }
            }
        });
    }

    private void setUpDataBinding(View viewRoot){
        try {
            viewBinding = DataBindingUtil.bind(viewRoot);
            if (viewBinding != null) {
                viewBinding.setLifecycleOwner(this);
                setUpViewModel();
            } else {
                Log.e("JACSBaseFragment", "viewBinding was null");
            }
        } catch (Throwable t) {
            Log.e("JACSBaseFragment", "DataBinding error", t);
        }
    }

    public void setUpViewModel() {
        navigationObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    performNavigationAction(integer, viewModel.bundle);
                    viewModel.navigationActionLiveData.setValue(null);
                }
            }
        };
        viewModel.navigationActionLiveData.observe(this, navigationObserver);
    }

    /**
     * This is called once the view has bound itself to it's rootView (during onCreateView()). If isKeepingView is true then this will only get called if the view is destroyed
     * <p></p>
     * Use this method to `setup` and views, for example: set the image for an imageView or the adapter for a recyclerView e.t.c
     * <p></p>
     * Every fragment must override this method in order to setup it's view components
     */
    protected void setupViews() {}


    /**
     * Method for getting the top fragment in the stack once it is added
     * @param fragClass The class for the fragment which should be in the top of the stack
     * @param listener Returns the fragment if and when it is added to the BackStack
     */
    public <FragType extends JACSBaseFragment> void getTopFragmentWhenAdded(final Class<FragType> fragClass, final JACSOnTopFragmentFoundListener listener) {
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1);

                if (fragment.getClass() == fragClass) {
                    listener.topFragmentFound((JACSBaseFragment) fragment);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    return;
                }

                listener.wrongFragmentFound(fragment);
                fragmentManager.removeOnBackStackChangedListener(this);
            }
        });
    }
    /**
     * Method for getting the top fragment in the stack once it is added
     * @param fragClass The class for the fragment which should be in the top of the stack
     * @param listener Returns the fragment if and when it is added to the BackStack
     * @param fragmentManager Use this to add the listeners for a custom fragment stack
     */
    public <FragType extends JACSBaseFragment> void getTopFragmentWhenAdded(final Class<FragType> fragClass, final FragmentManager fragmentManager, final JACSOnTopFragmentFoundListener listener) {
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1);

                if (fragment.getClass() == fragClass) {
                    listener.topFragmentFound((JACSBaseFragment) fragment);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    return;
                }

                listener.wrongFragmentFound(fragment);
                fragmentManager.removeOnBackStackChangedListener(this);
            }
        });
    }

    /**
     * Used to push `navigate backwards` in the stack. This does the same as popFragment() but works for the android navigation component
     * <p></p>
     * Use this to go back to a previous fragment
     */
    public void navigateBack() {
        if (getView() != null) {
            NavController navController = Navigation.findNavController(getView());
            if (!navController.navigateUp()) {
                getActivity().finish();
            }
        } else {
            Log.e("BaseFrag navigateBack", "No view");
        }
    }


    public Object getBundleData(String key) {
        if (getArguments() != null) {
            return getArguments().get(key);
        }
        return null;
    }


    /**
     * Used to push fragments using the navigation component for android and send a bundle of data to that fragment.
     * <p></p>
     * When using a graph the `action` will be an auto generated ID which is created when you connect up 2 views in the graph.
     * @param navigationAction Auto generated Integer action used for pushing fragments through navigation,  e.g: R.id.action_frag1_to_frag2
     * @param bundle Used to send bundlable data through to the next fragment (retrieve the data through the `getArguments()` method)
     */
    public void performNavigationAction(int navigationAction, @Nullable Bundle bundle) {
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(navigationAction, bundle);
        } else {
            Log.e("performNavigationAction", "No view");
        }
    }

    /**
     * Used to push fragments using the navigation component for android and send a bundle of data to that fragment.
     * <p></p>
     * When using a graph the `action` will be an auto generated ID which is created when you connect up 2 views in the graph.
     * @param graphID The ID for the graph you would like to navigate to,  e.g: R.navigation.exampleGraph
     * @param bundle Used to send bundlable data through to the next fragment (retrieve the data through the `getArguments()` method)
     */
    public void performGraphNavigation(int graphID, @Nullable Bundle bundle) {
        if (getView() != null) {
            Navigation.findNavController(getView()).setGraph(graphID, bundle);
        } else {
            Log.e("performNavigationAction", "No view");
        }
    }

}
