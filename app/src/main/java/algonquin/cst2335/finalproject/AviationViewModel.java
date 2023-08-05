package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AviationViewModel extends ViewModel {
    public MutableLiveData<ArrayList<NameOfflight>> details = new MutableLiveData<>();
    public  MutableLiveData<NameOfflight> selectedMessage = new MutableLiveData< >();

    public MutableLiveData<ArrayList<NameOfflight>> flightDetails = new MutableLiveData<>();

}
