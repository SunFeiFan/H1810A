package com.example.mode_xiaowangzi.fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mode_xiaowangzi.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * 读取联系人
     */
    private Button mBt;
    private XRecyclerView mXrl;
    private LinearLayout mLl;
    private ContentResolver mContentResolver;

    public CallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_call, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mBt = (Button) inflate.findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mXrl = (XRecyclerView) inflate.findViewById(R.id.xrl);
        mLl = (LinearLayout) inflate.findViewById(R.id.ll);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                readContacts();
                break;
        }
    }

    /**
     * 读取联系人
     */
    private void readContacts() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            executeReadContacts();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, 500);
        }
    }

    /**
     * 4.处理申请结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 500) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户给你权限了
                executeReadContacts();
            } else {
                //用户拒绝了
                showToast("用户拒绝了");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "Main8Activity";

    /**
     * 实际执行读取联系人
     */
    private void executeReadContacts() {
        ContentResolver resolver = getActivity().getContentResolver();

        //raw_contacts表,content://com.android.contacts/raw_contacts
        Uri contactUri = ContactsContract.RawContacts.CONTENT_URI;
        //data表,"content://com.android.contacts/data"
        Uri dataUri = ContactsContract.Data.CONTENT_URI;
        Cursor query = resolver.query(contactUri, null, null, null, null);

        mLl.removeAllViews();
        if (query != null && query.moveToFirst()) {
            do {
                //有几个id,就有几个联系人
                String contact_id = query.getString(query.getColumnIndex("contact_id"));
                Log.d(TAG, "contact_id: " + contact_id);

                //id为null的不能查询
                if (TextUtils.isEmpty(contact_id)) {
                    //结束本次循环,开始下次循环
                    continue;
                }

                //根据contact_id,去查data表中的信息
                //mimetype_id,系统不允许查,可以查mimetype
                Cursor query1 = resolver.query(dataUri, new String[]{"data1", "mimetype"}, "raw_contact_id=?",
                        new String[]{contact_id}, null);

                if (query1 != null && query1.moveToFirst()) {
                    TextView textView = new TextView(getActivity());
                    textView.setTextSize(30);
                    //线程不安全,效率高,可变
                    StringBuilder sb = new StringBuilder();

                    do {
                        String data1 = query1.getString(query1.getColumnIndex("data1"));
                        String mimetype = query1.getString(query1.getColumnIndex("mimetype"));

                        Log.d(TAG, "data1: " + data1 + ",mimetype:" + mimetype);

                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            sb.append("电话:" + data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            sb.append("名字:" + data1);
                        } else if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                            sb.append("邮箱:" + data1);
                        }


                    } while (query1.moveToNext());
                    query1.close();

                    textView.setText(sb.toString());
                    mLl.addView(textView);
                }
            } while (query.moveToNext());

            query.close();
        }
    }


}
