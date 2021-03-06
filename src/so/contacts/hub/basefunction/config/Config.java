package so.contacts.hub.basefunction.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;
import so.contacts.hub.ContactsApp;
import so.contacts.hub.basefunction.storage.db.DatabaseHelper;

public class Config
{
    public static final String KEY = "233&*Adc^%$$per";
    public static int STATE = 1;
    /**
     * 服务器域名
     */
    public static final String SERVER_HOST = "http://api.putao.so";
    
    /**
     * CMS请求地址前缀
     */
    public static final String NEW_CMS_SERVER_HOST = SERVER_HOST+"/scmsface";
    
    public static Gson mGson = new Gson();
    
    private static DatabaseHelper mDatabaseHelper;

    public static DatabaseHelper getDatabaseHelper()
    {
        if (mDatabaseHelper == null)
        {
            mDatabaseHelper = new DatabaseHelper(ContactsApp.getInstance());
        }
        return mDatabaseHelper;
    }
    
    public static void execute(Runnable runnable)
    {
        getExecutor().execute(runnable);
    }
    
    /**
     * 线程池，线程数量控制在10个，防止线程堵车，请求等待时间过长
     */
    private static final int CORE_POOL_SIZE = 10;

    private static ExecutorService mExecutorService;

    private static ThreadFactory mThreadFactory = new ThreadFactory()
    {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r)
        {
            return new Thread(r, "putao thread #" + mCount.getAndIncrement());
        }
    };

    public static ExecutorService getExecutor()
    {
        if (mExecutorService == null)
        {
            mExecutorService = Executors.newFixedThreadPool(CORE_POOL_SIZE, mThreadFactory);
        }
        return mExecutorService;
    }
    /**
     * 账户
     */
    public static final String ACCOUNT_SERVER_HOST = SERVER_HOST + "/sandroid1";
    
    public static final String SERVER = ACCOUNT_SERVER_HOST + "/PT_SERVER/interface.s";
    
    /**
     * 业务
     */
    public static final String NEW_BIZ_SERVER_HOST = SERVER_HOST + "/sbiz";
    
    /**
     * 用户从葡萄服务端获取上传图片token的接口地址
     */
    public static final String YELLOW_PAGE_FEEDBACK_IMG_UPLOAD_TOKEN = SERVER_HOST + "/scms/uptoken";
    
    /**
     * 在七牛服务器中对应的域名
     */
    public static final String BUCKET_NAME_URL = "http://img.putao.so/";
    
    /**
     * 上传用户基础数据
     */
    public static final String UPLOAD_BASIC_INFO_URL = NEW_BIZ_SERVER_HOST + "/user/upload_baisc_info";
    
    /**
     * 获取用户基础数据
     */
    public static final String GET_BASIC_INFO_URL = NEW_BIZ_SERVER_HOST + "/user/get_baisc_info";
    
    /**
     * 获取静态文件下载地址
     */
    public static final String URL_GET_STATIC_FILE = "http://dl.putao.so/file/?name=";
    
    // 快递静态资源文件名
    public static final String KEY_EXPRESS_NEW = "putao_express.txt";

    // 城市列表静态资源文件名
    public static final String KEY_CITY_LIST_NEW = "putao_citylist.txt";

    // 归属地静态资源文件名
    public static final String KEY_TELAREA_NEW = "putao_telArea.sf";

    // 火车票静态资源文件名
    public static final String KEY_TRAIN_TICKET_NEW = "putao_train_ticket.txt";

    // 水电煤静态资源文件名
    public static final String KEY_WEG_DATA_NEW = "putao_weg_data.txt";

    // 违章城市静态资源文件名
    public static final String KEY_VIOLATION_CITY_NEW = "putao_violation_city.txt";

    // 飞机票城市列表资源文件名
    public static final String KEY_FLIGHT_CITY_NEW = "putao_flight_city.txt";
}
