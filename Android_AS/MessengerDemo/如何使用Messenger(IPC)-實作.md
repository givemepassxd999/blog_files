在[如何使用Messenger(IPC)](http://givemepass.blogspot.tw/2015/11/messengeripc.html)中簡單介紹怎麼透過Messenger來進行IPC(跨行程溝通),
這邊就實際做個範例來呈現。

跟[如何使用AIDL-非同步實作]()一樣, 會有兩個app被安裝, 
一個用來遠端啟動Service, 另外一邊就是Client端。
### Remote Side

只需要建立一個Service即可。
```java
public class RemoteService extends Service {
    private Messenger mMessenger;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mHandlerThread = new HandlerThread("remote service");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                try {
                    int rand = (int) Math.random()*3 + 1;
                    Thread.sleep(rand * 1000);
                    int what = msg.what;
                    Bundle b = new Bundle();
                    b.putString("remote", "task " + what + " is done\n");
                    Message m = new Message();
                    m.setData(b);
                    msg.replyTo.send(m);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mMessenger = new Messenger(mHandler);
    }

    @Override
    public void onDestroy() {
        mHandlerThread.quit();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
```
Service做的事情非常簡單, 利用HandlerThread以及Looper跟Messenger進行聯繫。
```java
mHandlerThread = new HandlerThread("remote service");
mHandlerThread.start();
mHandler = new Handler(mHandlerThread.getLooper());
```
聯繫的時候, 將Messenger的Binder回傳出去。
```java
@Override
public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
}
```

而當結束聯繫的時候, 則關閉Thread。
```java
mHandlerThread.quit();
```

而假設你需要雙向溝通的時候, 其實可以透過Message.replyTo這個方法再將Message回傳。
```java
int what = msg.what;
Bundle b = new Bundle();
b.putString("remote", "task " + what + " is done\n");
Message m = new Message();
m.setData(b);
msg.replyTo.send(m);
```

記得在AndroidMenifest加入process
```xml
<service
    android:name=".RemoteService"
    android:process=":remote">
    <intent-filter>
        <action android:name="service.remote" />
    </intent-filter>
</service>
```
這樣Remote Side就講解的差不多了。

### Client Side

一開始宣告兩個Button用來處理連接跟中斷Remote端的Service,
TextView用來呈現連接以及中斷的結果。
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="example.givemepass.messengerclientdemo.MainActivity">
    <Button
        android:id="@+id/connect_remote"
        android:text="connect to remote"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <Button
        android:id="@+id/disconnect_remote"
        android:text="disconnect"
        android:layout_toRightOf="@id/connect_remote"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <TextView
        android:id="@+id/result"
        android:layout_below="@id/connect_remote"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />
</RelativeLayout>
```

主程式
```java
public class MainActivity extends AppCompatActivity {
    private Button connectRemote;
    private Button disconnectRemote;
    private ServiceConnection mServiceConnection;
    private Messenger messenger;
    private boolean isBound;
    private TextView result;
    private StringBuffer sb;
    private int index = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void clearText(String msg){
        sb.delete(0, sb.length());
        if(msg != null) {
            sb.append(msg);
            sb.append("\n");
        }
        result.setText(sb);
    }

    private void initData(){
        sb = new StringBuffer();
    }

    private void initView(){
        result = (TextView) findViewById(R.id.result);
        connectRemote = (Button) findViewById(R.id.connect_remote);
        disconnectRemote = (Button) findViewById(R.id.disconnect_remote);
        connectRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBound) {
                    clearText(null);
                    mServiceConnection = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            messenger = new Messenger(service);
                            isBound = true;
                            clearText("remote is connected");
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            messenger = null;
                            isBound = false;
                            clearText("remote is disconnected.");
                        }
                    };
                    Intent intent = new Intent();
                    intent.setAction("service.remote");
                    intent.setPackage("example.givemepass.messengerremotedemo");
                    bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                } else{
                    try {
                        Message m = new Message();
                        m.what = index;
                        m.replyTo = new Messenger(new Handler(getMainLooper()){
                            @Override
                            public void handleMessage(Message msg) {
                                String msgStr = msg.getData().getString("remote");
                                if(msgStr != null){
                                    sb.append(msgStr);
                                    result.setText(sb);
                                }
                            }
                        });
                        messenger.send(m);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    index++;
                }
            }
        });
        disconnectRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messenger = null;
                isBound = false;
                index = 1;
                clearText("remote is disconnected.");
            }
        });
    }
}
```
雖然看起來很長, 但是其實處理的事情很單純,
一開始會透過ServiceConnection來判斷是否已經連接, 
如果連接就會顯示已連結, 
接著把傳過來的IBinder物件丟進Messenger就完成初始化了。
```java
mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);
        isBound = true;
        clearText("remote is connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        messenger = null;
        isBound = false;
        clearText("remote is disconnected.");
    }
};
```
接著對Remote的Service真實連接。
```java
Intent intent = new Intent();
intent.setAction("service.remote");
intent.setPackage("example.givemepass.messengerremotedemo");
bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
```
如果已經連接了, 
由於可以透過replyTo雙向溝通,
我們就可以透過Messenger來取得Handler傳過來的Message物件,
將物件打開塞進我們的TextView。
```java
try {
    Message m = new Message();
    m.what = index;
    m.replyTo = new Messenger(new Handler(getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            String msgStr = msg.getData().getString("remote");
            if(msgStr != null){
                sb.append(msgStr);
                result.setText(sb);
            }
        }
    });
    messenger.send(m);
} catch (RemoteException e) {
    e.printStackTrace();
}
index++;
```
那當中斷連線, 我們就可以把數值再次的全部初始化, 並且顯示已斷線。
```java
messenger = null;
isBound = false;
index = 1;
clearText("remote is disconnected.");
```
這樣就完成IPC的串接了。
我們來看結果,
連續按下connect to remote的Button, 第一次會進行連接,
如果已經連接remote則會送出任務,
而按下disconnect則會中斷連接,
然後就重複以上的行為。
![](https://dl.dropboxusercontent.com/u/24682760/Android_AS/MessengerDemo/messenger_demo.gif)

[Remote程式碼](https://dl.dropboxusercontent.com/u/24682760/Android_AS/MessengerDemo/MessengerRemoteDemo.zip)
[Client程式碼](https://dl.dropboxusercontent.com/u/24682760/Android_AS/MessengerDemo/MessengerClientDemo.zip)
