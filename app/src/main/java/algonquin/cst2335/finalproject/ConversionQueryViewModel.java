package algonquin.cst2335.finalproject;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * ViewModel for managing ConversionQuery data in the context of an application.
 * This class provides methods for accessing and modifying ConversionQuery data using the relevant DAO.
 */
public class ConversionQueryViewModel extends AndroidViewModel {
    private final ConversionQueryDao dao;
    private final LiveData<List<ConversionQuery>> allQueries;

    /**
     * Creates a new ConversionQueryViewModel instance.
     *
     * @param application the context of the application
     */
    public ConversionQueryViewModel(Application application) {
        super(application);
        ConversionQueryDatabase db = ConversionQueryDatabase.getDatabase(application);
        dao = db.conversionQueryDao();
        allQueries = dao.getAllQueries();
    }

    /**
     * Returns a LiveData object that provides lists of ConversionQuery objects.
     * The returned LiveData is automatically updated when the database changes.
     *
     * @return LiveData object with all ConversionQuery items
     */
    LiveData<List<ConversionQuery>> getAllQueries() {
        return allQueries;
    }

    /**
     * Inserts a ConversionQuery object into the database.
     * The insertion operation is performed in a separate thread.
     *
     * @param query the ConversionQuery to insert
     */
    void insert(ConversionQuery query) {
        ConversionQueryDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(query);
        });
    }

    /**
     * Deletes a ConversionQuery object from the database.
     * The deletion operation is performed in a separate thread.
     *
     * @param query the ConversionQuery to delete
     */
    void delete(ConversionQuery query) {
        ConversionQueryDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(query);
        });
    }
}

