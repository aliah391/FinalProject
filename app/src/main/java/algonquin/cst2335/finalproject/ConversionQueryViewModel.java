package algonquin.cst2335.finalproject;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ConversionQueryViewModel extends AndroidViewModel {
    private final ConversionQueryDao dao;
    private final LiveData<List<ConversionQuery>> allQueries;

    public ConversionQueryViewModel(Application application) {
        super(application);
        ConversionQueryDatabase db = ConversionQueryDatabase.getDatabase(application);
        dao = db.conversionQueryDao();
        allQueries = dao.getAllQueries();
    }

    LiveData<List<ConversionQuery>> getAllQueries() {
        return allQueries;
    }

    void insert(ConversionQuery query) {
        ConversionQueryDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(query);
        });
    }

    void delete(ConversionQuery query) {
        ConversionQueryDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(query);
        });
    }
}

