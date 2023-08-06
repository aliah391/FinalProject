package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * Sets the  different arrays and prevents data loss upon rotation
 */
public class AviationViewModel extends ViewModel {
    /**
     * AN array of type NameOfflfight
     * */
    public MutableLiveData<ArrayList<NameOfflight>> details = new MutableLiveData<>();
    /**
     * A NameOfflight list s
     */
    public  MutableLiveData<NameOfflight> selectedMessage = new MutableLiveData< >();
    /**
     * An array list of type NAmeOfflight
     */
    public MutableLiveData<ArrayList<NameOfflight>> flightDetails = new MutableLiveData<>();

}
