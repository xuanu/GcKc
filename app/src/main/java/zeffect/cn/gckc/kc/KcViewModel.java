package zeffect.cn.gckc.kc;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by zeffect on 18-12-29.
 */

public class KcViewModel extends ViewModel {

    public MutableLiveData<String> baiduLocationStr = new MutableLiveData<>();

    public MutableLiveData<String> gaodeLocationStr = new MutableLiveData<>();

    public MutableLiveData<String> pointSpaceStr = new MutableLiveData<>();

}
